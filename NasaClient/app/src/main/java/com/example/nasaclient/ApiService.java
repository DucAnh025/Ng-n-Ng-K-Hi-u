package com.example.nasaclient;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @POST("register")
    Call<Void> registerUser(@Body User user);

    @GET("api/news")
    Call<List<News>> getNews(); // Adjust `News` class as per your data model
}

