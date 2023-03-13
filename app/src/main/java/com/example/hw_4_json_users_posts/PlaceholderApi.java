package com.example.hw_4_json_users_posts;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlaceholderApi {
    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") long userId);
}
