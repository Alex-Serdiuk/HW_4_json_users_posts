package com.example.hw_4_json_users_posts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tvHelloMessage;
    private Button btnGetUsers;
    private ProgressBar pbGetUsers;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        btnGetUsers.setOnClickListener(this::getUsersAndGoNextActivity);
    }

    private void getUsersAndGoNextActivity(View view) {
        pbGetUsers.setVisibility(View.VISIBLE);

        PlaceholderApi placeholderAPI = NetworkService.getInstance().getApi();
        Call<List<User>> call = placeholderAPI.getAllUsers();

        call.enqueue(new Callback<List<User>>() { // Метод виконається в момент отримання результату з сервера
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) { // метод, що виконається після отримання відповіді
                users = response.body();

                pbGetUsers.setVisibility(View.INVISIBLE); // прогресбар - НЕвидимий

                goUsersActivity();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) { // Метод виконається при виникненні помилки
                Toast.makeText(MainActivity.this, "ERROR: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goUsersActivity(){
        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
        //intent.putParcelableArrayListExtra("usersList", (ArrayList<? extends Parcelable>) users);
        intent.putParcelableArrayListExtra(ConstantsStore.USER_LIST, (ArrayList<? extends Parcelable>) users);
        startActivity(intent);
    }

    private void initView() {
        tvHelloMessage = findViewById(R.id.tvHelloMessage);
        btnGetUsers = findViewById(R.id.btnGetUsers);
        pbGetUsers = findViewById(R.id.pbGetUsers);
    }
}