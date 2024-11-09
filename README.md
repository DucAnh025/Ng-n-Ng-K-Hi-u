# NASA Client - Mobile Application Development Project

### Group ID: 04

### Project Name: NASA Client

### Group Members:
- Bui Le Duc Anh (22BI13010) (Leader)
- Bui Quang Trung (22BI13434)
- Hoang Kim Thanh (22BI13407)
- Nguyen Xuan Hong Vu (22BI13479)
- Phan Viet Tu (22BI13443)

### Info about the backend and server:
- Our server use Flask as controller to build API endpoints (/api/news, /api/planets/, /login, /register, /get_user_info, /change_user_info, /change_password)
- The database we used is MySQL Lite
- The auth process between this server and the mobile app is by using JWT token
- The server is deployed in our physical linux server at my home (It simply is an TV box) (I have config port forwarding, nginx, systemctl, gunicorn for load balancing and dynu as dynamic dns)
- Domain: boxgateway.kozow.com

### Team Contributions:
- **Bui Le Duc Anh**: [App designer, Home layout design, news section and stuff, News API]
- **Bui Quang Trung**: [Some initial app ui design, nav bar intergration, API, Server, Edit Profile API]
- **Hoang Kim Thanh**: [Onboarding activity UI and implementm,navigation back end, and Password API]
- **Nguyen Xuan Hong Vu**: [Planets UI and its activity along with INFO,sign in API]
- **Phan Viet Tu**: [Setting section designing, all tabs on setting activity designing, sign up API]

### Initial UI App Design

This is our initial UI app design:

![Initial UI Design](demo_app.png)
