package com.example.talk2friends;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMeetingsActivity extends AppCompatActivity {

    FirebaseDatabase root;
    DatabaseReference reference;
    private String user, username, region;
    private ArrayList<String> myFriendList;
    private LinearLayout meetingsContainer;
    private Button createMeetingButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetings);

        // Retrieving data from Intent and Firebase setup
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        username = intent.getStringExtra("username");
        region = intent.getStringExtra("region");
        myFriendList = intent.getStringArrayListExtra("myFriendList");
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("meetings");

        // Setup for createMeetingButton and backButton
        createMeetingButton = findViewById(R.id.createMeetingButton);
        backButton = findViewById(R.id.backButton);

        createMeetingButton.setOnClickListener(v -> {
            Intent createMeetingIntent = new Intent(ViewMeetingsActivity.this, CreateMeetingActivity.class);
            createMeetingIntent.putExtra("user", user);
            createMeetingIntent.putExtra("username", username);
            createMeetingIntent.putExtra("region", region);
            createMeetingIntent.putExtra("myFriendList", myFriendList);
            startActivity(createMeetingIntent);
        });

        backButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(ViewMeetingsActivity.this, MainPageActivity.class);
            backIntent.putExtra("user", user);
            backIntent.putExtra("username", username);
            backIntent.putExtra("region", region);
            backIntent.putExtra("myFriendList", myFriendList);
            startActivity(backIntent);
        });

        // Meeting list initialization
        meetingsContainer = findViewById(R.id.meetingsContainer);

        // Listening for changes in Firebase Database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingsContainer.removeAllViews(); // Clear views to avoid duplication
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Meeting meeting = snapshot.getValue(Meeting.class);
                    if (meeting != null) {
                        meeting.setId(snapshot.getKey());
                        displayMeeting(meeting);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewMeetingsActivity.this, "Error loading meetings.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayMeeting(Meeting meeting) {
        TextView tv = new TextView(ViewMeetingsActivity.this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        tv.setText(meeting.toString());
        tv.setTextSize(18);
        meetingsContainer.addView(tv);

        Button cancelBtn = new Button(ViewMeetingsActivity.this);
        cancelBtn.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        cancelBtn.setText("Cancel");
        cancelBtn.setTextSize(18);
        cancelBtn.setTextColor(Color.WHITE);
        cancelBtn.setBackgroundColor(Color.RED);
        cancelBtn.setOnClickListener(v -> cancelMeeting(meeting.getId()));
        meetingsContainer.addView(cancelBtn);
    }

    private void cancelMeeting(String meetingId) {
        DatabaseReference meetingRef = reference.child(meetingId);
        meetingRef.removeValue().addOnSuccessListener(aVoid ->
                Toast.makeText(ViewMeetingsActivity.this, "Meeting cancelled successfully.", Toast.LENGTH_SHORT).show()
        ).addOnFailureListener(e ->
                Toast.makeText(ViewMeetingsActivity.this, "Failed to cancel meeting.", Toast.LENGTH_SHORT).show()
        );
    }
}



