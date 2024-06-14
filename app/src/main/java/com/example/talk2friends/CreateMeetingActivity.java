package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateMeetingActivity extends AppCompatActivity {

    /* calling Firebase */
    FirebaseDatabase root;
    DatabaseReference reference;
    private EditText meetingNameEditText, topicEditText, timeEditText, locationEditText;

    private String user, username, region;
    private ArrayList<String> myFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        username = intent.getStringExtra("username");
        region = intent.getStringExtra("region");
        myFriendList = intent.getStringArrayListExtra("myFriendList");

        meetingNameEditText = findViewById(R.id.meetingName);
        topicEditText = findViewById(R.id.meetingTopic);
        timeEditText = findViewById(R.id.meetingTime);
        locationEditText = findViewById(R.id.meetingLocation);

        Button createMeetingButton = findViewById(R.id.createMeetingButton);
        createMeetingButton.setOnClickListener(v -> createMeeting());
    }

    public void createMeeting() {
        String name = meetingNameEditText.getText().toString().trim();
        String topic = topicEditText.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

//        if (!region.equals("native") && !region.equals("international")) {
//            Toast.makeText(this, "Please choose from 'international' or 'native'.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        root = FirebaseDatabase.getInstance();
        reference = root.getReference("meetings"); // our key in DB

        /* then, save the value */
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Meeting>> t = new GenericTypeIndicator<List<Meeting>>() {
                };
                List<Meeting> meetings = dataSnapshot.getValue(t);
                List<Meeting> list = new ArrayList<>();
                if (meetings != null) {
                    list = meetings;
                }
                Meeting meeting = new Meeting(name, topic, time, region, location);
                list.add(meeting);
                reference.setValue(list);
                Intent ViewMeetingsActivityIntent = new Intent(CreateMeetingActivity.this, ViewMeetingsActivity.class);
                ViewMeetingsActivityIntent.putExtra("user", user);
                ViewMeetingsActivityIntent.putExtra("username", username);
                ViewMeetingsActivityIntent.putExtra("region", region);
                ViewMeetingsActivityIntent.putExtra("myFriendList", myFriendList);
                startActivity(ViewMeetingsActivityIntent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CreateMeetingActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backToMeetings(View view) {
        Intent intent = new Intent(this, ViewMeetingsActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("username", username);
        intent.putExtra("region", region);
        intent.putExtra("myFriendList", myFriendList);
        startActivity(intent);
    }
}