package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity {

    /* calling Firebase */
    FirebaseDatabase root;
    DatabaseReference reference;
    private EditText usernameEditText, nameEditText, emailEditText, passwordEditText, interestEditText;
    private String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Spinner regionSpinner = findViewById(R.id.regionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dropdown_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        usernameEditText = findViewById(R.id.usernameSignUpField);
        nameEditText = findViewById(R.id.nameSignUpField);
        emailEditText = findViewById(R.id.emailSignUpField);
        passwordEditText = findViewById(R.id.passwordSignUpField);
        interestEditText = findViewById(R.id.interestSignUpField);

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(v -> createUser());
    }

    public void createUser() {
        String username = usernameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String interest = interestEditText.getText().toString().trim();


        // Check if email is a valid USC email
        if (!email.endsWith("@usc.edu")) {
            // Not a valid USC email address
            Toast.makeText(this, "Please use a valid USC email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] interests = interest.split(", ");
        ArrayList<String> interestList = new ArrayList<>(Arrays.asList(interests));

        User user = new User(username, name, email, region, password, interestList);

        root = FirebaseDatabase.getInstance();
        reference = root.getReference(username); // our key in DB

        /* then, save the value */
        reference.setValue(user);
        Intent MainPageActivityIntent = new Intent(SignUpActivity.this, MainPageActivity.class);
        MainPageActivityIntent.putExtra("user", user.toString());
        MainPageActivityIntent.putExtra("username", username);
        MainPageActivityIntent.putExtra("myFriendList", user.getFriendList());
        MainPageActivityIntent.putExtra("region", region);
        startActivity(MainPageActivityIntent);
    }
}
