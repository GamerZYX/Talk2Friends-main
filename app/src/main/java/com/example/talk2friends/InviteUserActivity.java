package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InviteUserActivity extends AppCompatActivity {
    private String username;
    private EditText nameEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        nameEditText = findViewById(R.id.nameInviteField);
        emailEditText = findViewById(R.id.emailInviteField);
    }

    public void sendInviteEmail(View view) {
        String inviteName = nameEditText.getText().toString().trim();
        String inviteEmail = emailEditText.getText().toString().trim();

        // Check if email is a valid USC email
        if (!inviteEmail.endsWith("@usc.edu")) {
            // Not a valid USC email address
            Toast.makeText(this, "Please use a valid USC email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{inviteEmail}); // recipient's email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Join Talk2Friends!");

        String body = "Hello, " + inviteName + "This is an invite from " + username + " to join Talk2Friends!\nThis app is designed to foster English-speaking practice between USC students and alumni.\n Go ahead and sign up!";
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(InviteUserActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}