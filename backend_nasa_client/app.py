# Our server use Flask as controller to build API endpoints
# The database we used is MySQL Lite
# The auth process between this server and the mobile app is by using JWT token
# The server is deployed in our physical linux server (I have config port forwarding, nginx, systemctl, gunicorn for load balancing and dynu as dynamic dns)
# Domain: boxgateway.kozow.com

from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt
import jwt
import datetime
import uuid
from functools import wraps
import os
import re
import json

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///app.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SECRET_KEY'] = 'your_secret_key'  

app.config['UPLOAD_FOLDER'] = 'static/uploads/'  
app.config['MAX_CONTENT_LENGTH'] = 2 * 1024 * 1024  
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}  

db = SQLAlchemy(app)
bcrypt = Bcrypt(app)

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    full_name = db.Column(db.String(100), nullable=False)
    username = db.Column(db.String(50), unique=True, nullable=False)
    phone_number = db.Column(db.String(15))
    email = db.Column(db.String(120), unique=True, nullable=False)
    hashed_password = db.Column(db.String(128), nullable=False)
    salt = db.Column(db.String(128), nullable=False)
    image_url = db.Column(db.String(200))
    created_time = db.Column(db.DateTime, default=datetime.datetime.utcnow)

class Notification(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    notification_text = db.Column(db.String(255), nullable=False)
    user_email = db.Column(db.String(120), db.ForeignKey('user.email'), nullable=False)
    created_time = db.Column(db.DateTime, default=datetime.datetime.utcnow)

with app.app_context():
    db.create_all()

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None
        if 'Authorization' in request.headers:
            token = request.headers['Authorization'].split(" ")[1]

        if not token:
            return jsonify({"message": "Token is missing!"}), 403
        try:
            data = jwt.decode(token, app.config['SECRET_KEY'], algorithms=["HS256"])
            current_user = User.query.get(data['user_id'])
        except jwt.ExpiredSignatureError:
            return jsonify({"message": "Token has expired!"}), 403
        except jwt.InvalidTokenError:
            return jsonify({"message": "Invalid token!"}), 403
        return f(current_user, *args, **kwargs)
    return decorated

@app.route('/register', methods=['POST'])
def register():
    data = request.json
    full_name = data.get('full_name')
    email = data.get('email')
    password = data.get('password')

    # Validate input data
    if not full_name or not email or not password:
        return jsonify({"message": "Full name, email, and password are required!"}), 400

    existing_user = User.query.filter_by(email=email).first()
    if existing_user:
        return jsonify({"message": "Email already exists!"}), 400
    
    base_username = re.sub(r'\s+', '', full_name).lower()  
    username = base_username
    counter = 1

    while User.query.filter_by(username=username).first() is not None:
        username = f"{base_username}{counter}"
        counter += 1

    salt = bcrypt.generate_password_hash(str(uuid.uuid4())).decode('utf-8')
    hashed_password = bcrypt.generate_password_hash(data['password'] + salt).decode('utf-8')

    new_user = User(
        full_name=full_name,
        username=username,
        email=email,
        hashed_password=hashed_password,
        salt=salt,
        phone_number=None,
        image_url=None
    )

    db.session.add(new_user)
    db.session.commit()

    return jsonify({"message": "User registered successfully!", "username": username}), 201


@app.route('/login', methods=['POST'])
def login():
    data = request.json
    user = User.query.filter_by(email=data['email']).first()
    if user and bcrypt.check_password_hash(user.hashed_password, data['password'] + user.salt):
        # Generate JWT
        token = jwt.encode({
            'user_id': user.id,
            'exp': datetime.datetime.utcnow() + datetime.timedelta(days=30)
        }, app.config['SECRET_KEY'], algorithm="HS256")

        return jsonify({"message": "Login successful", "token": token}), 200
    return jsonify({"message": "Invalid credentials"}), 401

@app.route('/change_password', methods=['PUT'])
@token_required
def change_password(current_user):
    data = request.json
    if bcrypt.check_password_hash(current_user.hashed_password, data['old_password'] + current_user.salt):
        new_salt = bcrypt.generate_password_hash(str(uuid.uuid4())).decode('utf-8')
        current_user.hashed_password = bcrypt.generate_password_hash(data['new_password'] + new_salt).decode('utf-8')
        current_user.salt = new_salt
        db.session.commit()
        return jsonify({"message": "Password changed successfully"}), 200
    return jsonify({"message": "Old password is incorrect"}), 400

@app.route('/get_user_info', methods=['GET'])
@token_required
def get_user_info(current_user):
    user_info = {
        "full_name": current_user.full_name,
        "username": current_user.username,
        "phone_number": current_user.phone_number,
        "email": current_user.email,
        "image_url": current_user.image_url
    }
    return jsonify(user_info), 200

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/change_user_info', methods=['PUT'])
@token_required
def change_user_info(current_user):
    data = request.form.to_dict()  
    image_url = None
    
    if 'image' in request.files:
        file = request.files['image']
        if file and allowed_file(file.filename):
            filename = f"{current_user.email}_profile.jpg"
            file_path = os.path.join(app.config['UPLOAD_FOLDER'], filename)
            file.save(file_path)  
            
            image_url = f'https://boxgateway.kozow.com/{app.config["UPLOAD_FOLDER"]}{filename}'
    new_username = data.get('username')
    if new_username and new_username != current_user.username:
        existing_user = User.query.filter_by(username=new_username).first()
        if existing_user:
            return jsonify({"message": "Username already taken!"}), 400

        current_user.username = new_username
    current_user.full_name = data.get('full_name', current_user.full_name)
    current_user.phone_number = data.get('phone_number', current_user.phone_number)
    current_user.image_url = image_url or current_user.image_url  
    
    db.session.commit()

    return jsonify({"message": "User info updated successfully"})

# @Teacher
# Here we should get space news from an space organization or some where else
# But here this just simulate the news data (stored it in json file) and it only response these whenever calling this endpoint (the data response will not change)

with open('news_data.json', 'r') as file:
    news_data = json.load(file)

@app.route('/api/news', methods=['GET'])
def get_news():
    return jsonify(news_data)

with open('planets_data.json', 'r') as file:
    planets_data = json.load(file)

@app.route('/api/planets/<planet_id>', methods=['GET'])
def get_planet(planet_id):
    planet = next((p for p in planets_data if p['id'] == planet_id), None)
    return jsonify(planet)

if __name__ == '__main__':
    app.run(debug=True)