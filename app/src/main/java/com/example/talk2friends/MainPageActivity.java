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

public class MainPageActivity extends AppCompatActivity {

    /* calling Firebase */
    FirebaseDatabase root;
    DatabaseReference reference;
    private String user, username, region;

    private ArrayList<String> myInterest;

    private final ArrayList<String> recommendUsers = new ArrayList<>();
    private final ArrayList<String> recommendInterests = new ArrayList<>();

    private ArrayList<String> myFriendList;
    private LinearLayout friendList;
    private LinearLayout userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        username = intent.getStringExtra("username");
        region = intent.getStringExtra("region");
        myFriendList = intent.getStringArrayListExtra("myFriendList");

        root = FirebaseDatabase.getInstance();
        reference = root.getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendList = findViewById(R.id.friendList);
                userList = findViewById(R.id.userList);
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    if (key.equals("meetings") || key.equals(username)) continue;
                    TextView tv = new TextView(MainPageActivity.this);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                            LinearLayout.LayoutParams.WRAP_CONTENT  // Height of TextView
                    ));
                    User user = childSnapshot.getValue(User.class);
                    String region = user.getRegion();
                    String text = key + ": " + region;
                    tv.setText(text);
                    if (myFriendList != null && !myFriendList.isEmpty() && myFriendList.contains(key)) {
                        friendList.addView(tv);
                        Button bt = new Button(MainPageActivity.this);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                                LinearLayout.LayoutParams.WRAP_CONTENT  // Height of TextView
                        ));
                        bt.setText("Remove Friend");
                        bt.setTextSize(10);
                        bt.setTextColor(Color.WHITE);
                        bt.setBackgroundColor(Color.BLUE);
                        bt.setOnClickListener(v -> removeFriend(key));
                        friendList.addView(bt);
                    } else {
                        userList.addView(tv);
                        Button bt = new Button(MainPageActivity.this);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                                LinearLayout.LayoutParams.WRAP_CONTENT  // Height of TextView
                        ));
                        bt.setText("Add Friend");
                        bt.setTextSize(10);
                        bt.setTextColor(Color.WHITE);
                        bt.setBackgroundColor(Color.BLUE);
                        bt.setOnClickListener(v -> addFriend(key));
                        userList.addView(bt);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainPageActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        loadMyInterest();
        findRecommendations();
    }

    private void addFriend(String userName) {
        reference = root.getReference(username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                ArrayList<String> friendList = user.getFriendList();
                if (friendList == null) {
                    friendList = new ArrayList<>();
                }
                friendList.add(userName);
                user.setFriendList(friendList);
                reference.child("friendList").setValue(friendList);
                Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                intent.putExtra("user", user.toString());
                intent.putExtra("username", username);
                intent.putExtra("region", region);
                intent.putExtra("myFriendList", friendList);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainPageActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFriend(String userName) {
        reference = root.getReference(username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                ArrayList<String> friendList = user.getFriendList();
                friendList.remove(userName);
                user.setFriendList(friendList);
                reference.child("friendList").setValue(friendList);
                Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                intent.putExtra("user", user.toString());
                intent.putExtra("username", username);
                intent.putExtra("region", region);
                intent.putExtra("myFriendList", friendList);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainPageActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMyInterest() {
        reference = root.getReference(username); // Reference to the current user in the database
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if (currentUser != null && currentUser.getInterestList() != null && !currentUser.getInterestList().isEmpty()) {
                    myInterest = new ArrayList<>(currentUser.getInterestList());
                } else {
                    myInterest = new ArrayList<>(); // Initialize with an empty list if no interests are found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainPageActivity.this, "Failed to load user interests.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void findRecommendations() {
        DatabaseReference usersRef = root.getReference(); // Assuming this points to the root of your Firebase database

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Skip the current user and non-user nodes
                    if (userSnapshot.getKey().equals(username) || userSnapshot.getKey().equals("meetings"))
                        continue;

                    User user = userSnapshot.getValue(User.class);
                    if (user != null && user.getInterestList() != null && !user.getInterestList().isEmpty()) {
                        // Check for matching interests
                        for (String interest : user.getInterestList()) {
                            if (myInterest.contains(interest)) {
                                recommendUsers.add(user.getUsername());
                                recommendInterests.add(interest);
                                break; // Break the loop after adding the first matching interest
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainPageActivity.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void showProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("username", username);
        intent.putExtra("myFriendList", myFriendList);
        intent.putExtra("region", region);
        startActivity(intent);
    }

    public void showMeetings(View view) {
        Intent intent = new Intent(this, ViewMeetingsActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("username", username);
        intent.putExtra("myFriendList", myFriendList);
        intent.putExtra("region", region);
        startActivity(intent);
    }

    public void showRecommendActivity(View view) {
        Intent intent = new Intent(this, RecommendActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("username", username);
        intent.putExtra("region", region);
        intent.putExtra("myFriendList", myFriendList);
        intent.putExtra("myInterest", myInterest);
        intent.putExtra("recommendUsers", recommendUsers);
        intent.putExtra("recommendInterests", recommendInterests);
        startActivity(intent);
    }


}

