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

public class SignInActivity extends AppCompatActivity {

    FirebaseDatabase root;
    DatabaseReference reference;
    private EditText usernameEditText, passwordEditText;
    private ArrayList<String> myFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameEditText = findViewById(R.id.usernameSignInField);
        passwordEditText = findViewById(R.id.passwordSignInField);

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        root = FirebaseDatabase.getInstance();
        reference = root.getReference(username);

//        /* then, read from DB */
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    String realPassword = snapshot.child("password").getValue(String.class);
                    if (password.equals(realPassword)) {
                        Intent MainPageActivityIntent = new Intent(SignInActivity.this, MainPageActivity.class);
                        MainPageActivityIntent.putExtra("user", user.toString());
                        MainPageActivityIntent.putExtra("username", user.getUsername());
                        MainPageActivityIntent.putExtra("myFriendList", user.getFriendList());
                        MainPageActivityIntent.putExtra("region", user.getRegion());
                        startActivity(MainPageActivityIntent);
                    } else {
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // display some error message to user
                Toast.makeText(SignInActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
