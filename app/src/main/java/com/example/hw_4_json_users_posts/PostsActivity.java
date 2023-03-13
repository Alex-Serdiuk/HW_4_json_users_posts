package com.example.hw_4_json_users_posts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostsActivity extends AppCompatActivity {
    private ListView lvPosts;
    //private TextView tvListOfPosts;

    private SimpleAdapter adapter;

    private List<Post> postList = new ArrayList<>();
    //private String name;


    private final List<Map<String, String>> postsTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        loudDataFromUsersActivity();

        initMapCollection();

        lvPosts = findViewById(R.id.lvPosts);
        //tvListOfPosts = findViewById(R.id.tvListOfPosts);
        createAndSetAdapter();

    }

    private void createAndSetAdapter() {
        adapter = new SimpleAdapter(
                this,
                postsTitles,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "body"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        lvPosts.setAdapter(adapter);
    }

    private void initMapCollection() {
        Map<String, String> map;
        for (Post post : this.postList) {
            map = new HashMap<>();
            map.put("title", post.getTitle());
            map.put("body", " ");
            postsTitles.add(map);
        }
    }

    private void loudDataFromUsersActivity() {
        Intent intent = getIntent();
        this.postList = intent.getParcelableArrayListExtra("post");
        //name = intent.getStringExtra("user");
//        //tvListOfPosts.setText(name + "'s posts :");
    }
}