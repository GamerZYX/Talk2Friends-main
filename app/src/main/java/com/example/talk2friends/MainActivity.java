package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogIn = findViewById(R.id.buttonLogIn);
        Button buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonLogIn.setOnClickListener(v -> {
            Intent signInIntent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(signInIntent);
        });

        buttonSignUp.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
        });
    }
}
