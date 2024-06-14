package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private String user, username, region;
    private ArrayList<String> myFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        username = intent.getStringExtra("username");
        region = intent.getStringExtra("region");
        myFriendList = intent.getStringArrayListExtra("myFriendList");
        TextView nameTextView = findViewById(R.id.userInfoTextView);
        nameTextView.setText(user);
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainPageActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("username", username);
        intent.putExtra("region", region);
        intent.putExtra("myFriendList", myFriendList);
        startActivity(intent);
    }

    public void updateProfile(View view) {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}