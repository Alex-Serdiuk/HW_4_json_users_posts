package com.example.hw_4_json_users_posts;



import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService networkService;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private Retrofit retrofit;

    private NetworkService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static NetworkService getInstance() {
        if (networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }

    public PlaceholderApi getApi(){
        return retrofit.create(PlaceholderApi.class);
    }

    public Api getApiPosts(){
        return retrofit.create(Api.class);
    }

}
