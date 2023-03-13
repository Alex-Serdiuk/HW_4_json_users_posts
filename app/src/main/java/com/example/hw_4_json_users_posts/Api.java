package com.example.hw_4_json_users_posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/posts/{id}")
    Call<Post> getPostByID(@Path("id") int postId);
    @GET("/posts")
    Call<List<Post>> getPostsOfUser(@Query("userId") int id);
}
