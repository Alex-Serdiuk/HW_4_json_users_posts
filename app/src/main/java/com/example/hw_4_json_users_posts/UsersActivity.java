package com.example.hw_4_json_users_posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends AppCompatActivity {
    private ListView lvUsers;
    private SimpleAdapter adapter;
    private List<User> users = new ArrayList<>();

    private List<Post> posts = new ArrayList<>();

    //private ProgressBar pbGetPosts;

    private long idUser;
    private List<Map<String, String>> usersNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        loudDataFromMainActivity();

        initMapCollection();

        lvUsers = findViewById(R.id.lvUsers);

        createAndSetAdapter();

        setListeners();
    }



    private void setListeners() {
        lvUsers.setOnItemClickListener((adapterView, view, position, id) -> {
            //pbGetPosts.setVisibility(View.VISIBLE);
            Intent newIntent = new Intent(this, PostsActivity.class);
//            newIntent.putParcelableArrayListExtra("posts", (ArrayList<? extends Parcelable>) posts);
            //ArrayList<String> posts = new ArrayList<>();

            idUser=users.get(position).getId();

            Log.d("idUser", String.valueOf(idUser));
            Api api = NetworkService.getInstance().getApiPosts();
            Call<List<Post>> call = api.getPostsOfUser((int) idUser);
            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    posts = response.body();

                    newIntent.putParcelableArrayListExtra("post", (ArrayList<? extends Parcelable>) posts);
                    //newIntent.putExtra("user",user.getName());
                    for (Post post : posts) {
                        Log.d("xxx", post.toString());
                    }
                    //pbGetPosts.setVisibility(View.INVISIBLE); // прогресбар - НЕвидимий
                    startActivity(newIntent);
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.d("xxx", t.getMessage());
                }
            });

           //newIntent.putExtra(KEY_USER_ID, idUser);
           startActivity(newIntent);
        });
    }

    private void createAndSetAdapter() {
        adapter = new SimpleAdapter(
                this,
                usersNames,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "userName"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        lvUsers.setAdapter(adapter);
    }

    private void initMapCollection() {
        Map<String, String> map;
        for (User user : users) {
            map = new HashMap<>();
            map.put("name", user.getName());
            map.put("userName", user.getUsername());
            usersNames.add(map);
        }
    }

    private void loudDataFromMainActivity() {
        Intent intent = getIntent();
        users = intent.getParcelableArrayListExtra(ConstantsStore.USER_LIST);
    }
}
