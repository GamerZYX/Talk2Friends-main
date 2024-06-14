package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateProfileActivity extends AppCompatActivity {

    /* calling Firebase */
    FirebaseDatabase root;
    DatabaseReference reference;
    private EditText nameEditText, emailEditText, passwordEditText, interestEditText;
    private String user, username, region;
    private ArrayList<String> myFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        username = intent.getStringExtra("username");
        region = intent.getStringExtra("region");
        myFriendList = intent.getStringArrayListExtra("myFriendList");

        nameEditText = findViewById(R.id.nameUpdateField);
        emailEditText = findViewById(R.id.emailUpdateField);
        passwordEditText = findViewById(R.id.passwordUpdateField);
        interestEditText = findViewById(R.id.interestUpdateField);

        Button signUpButton = findViewById(R.id.updateButton);
        signUpButton.setOnClickListener(v -> updateProfile(username));

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> backToMain());

        root = FirebaseDatabase.getInstance();
    }

    private void backToMain() {
        Intent intent = new Intent(this, MainPageActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("username", username);
        intent.putExtra("region", region);
        intent.putExtra("myFriendList", myFriendList);
        startActivity(intent);
    }

    private void updateProfile(String originalUsername) {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String interest = interestEditText.getText().toString().trim();

        reference = root.getReference(originalUsername);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference.child("name").setValue(name);
                reference.child("email").setValue(email);
                reference.child("password").setValue(password);

                String[] interests = interest.split(", ");
                ArrayList<String> interestList = new ArrayList<>(Arrays.asList(interests));
                reference.child("interestList").setValue(interestList);
                Toast.makeText(UpdateProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfileActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}