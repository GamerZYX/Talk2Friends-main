package com.example.talk2friends;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Collections;

public class RecommendActivity extends AppCompatActivity {

    FirebaseDatabase root;
    DatabaseReference reference;
    private String user, username, region, myInterest;
    private ArrayList<String> recommendUsers, recommendInterests;
    private ArrayList<String> myFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        username = intent.getStringExtra("username");
        region = intent.getStringExtra("region");
        ArrayList<String> myInterest = (ArrayList<String>) getIntent().getSerializableExtra("myInterest");
        recommendUsers = getIntent().getStringArrayListExtra("recommendUsers");
        recommendInterests = getIntent().getStringArrayListExtra("recommendInterests");
        myFriendList = intent.getStringArrayListExtra("myFriendList");

        root = FirebaseDatabase.getInstance();

        TextView myInterestTextView = findViewById(R.id.myInterestTextView);
        // Joining all interests from the ArrayList into a single string
        String interestsText = TextUtils.join(", ", Collections.singleton(myInterest));

// Setting the text to the TextView
        myInterestTextView.setText("My interests: " + interestsText);

        LinearLayout recommendationsLayout = findViewById(R.id.recommendationsLayout);

        // Check if the recommendUsers list is empty or null
        if (recommendUsers == null || recommendUsers.isEmpty()) {
            TextView noRecommendationTextView = new TextView(this);
            noRecommendationTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            noRecommendationTextView.setText("You are an explorer!");
            noRecommendationTextView.setTextSize(18);
            recommendationsLayout.addView(noRecommendationTextView);
        } else {
            // Display each recommended user and their interest
            for (int i = 0; i < recommendUsers.size(); i++) {
                String key = recommendUsers.get(i);
                if(myFriendList.contains(key)) continue;
                String displayText = recommendUsers.get(i) + ": " + recommendInterests.get(i);
                TextView userInterestTextView = new TextView(this);
                userInterestTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                userInterestTextView.setText(displayText);
                userInterestTextView.setTextSize(18);
                recommendationsLayout.addView(userInterestTextView);
                Button bt = new Button(RecommendActivity.this);
                bt.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        LinearLayout.LayoutParams.WRAP_CONTENT  // Height of TextView
                ));
                bt.setText("Add Friend");
                bt.setTextSize(10);
                bt.setTextColor(Color.WHITE);
                bt.setBackgroundColor(Color.BLUE);
                bt.setOnClickListener(v -> addFriend(key));
                recommendationsLayout.addView(bt);
            }
        }
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
                Intent intent = new Intent(RecommendActivity.this, MainPageActivity.class);
                intent.putExtra("user", user.toString());
                intent.putExtra("username", username);
                intent.putExtra("region", region);
                intent.putExtra("myFriendList", myFriendList);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecommendActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void invitePage(View view) {
        Intent intent = new Intent(this, InviteUserActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("username", username);
        intent.putExtra("region", region);
        startActivity(intent);
    }

    public void backToMain(View view) {
        finish();
    }
}






