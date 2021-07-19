package com.momentum.coaching.leaderboard.physics;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.momentum.coaching.Adapter.LeaderBoardAdapterPhyB;
import com.momentum.coaching.R;
import com.momentum.coaching.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardPhyB extends AppCompatActivity {

    private ProgressBar progressBar;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private String subjectName;
    private List<String> mUsersKeyList;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserRefDatabase;
    private ChildEventListener mChildEventListener;
    private LeaderBoardAdapterPhyB leaderBoardAdapterSec1;
    private ConnectivityManager connectivityManager;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_phy_b);

        Bundle bundle2 = getIntent().getExtras();
        subjectName = bundle2.getString("MOMENTUM");

        toolbar = findViewById(R.id.leaderPhysicsB_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressbar_physicB);
        mAuth = FirebaseAuth.getInstance();
        mUserRefDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        setUserRecyclerView();
        setUsersKeyList();
        setAuthListener();
        progressBar.setVisibility(View.VISIBLE);

    }

    private void setUserRecyclerView() {
        recyclerView = findViewById(R.id.leaderRecyclerB);
        leaderBoardAdapterSec1 = new LeaderBoardAdapterPhyB(LeaderBoardPhyB.this, new ArrayList<UserModel>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        leaderBoardAdapterSec1.sortingScore();
        recyclerView.setAdapter(leaderBoardAdapterSec1);

    }

    private void setUsersKeyList() {
        mUsersKeyList = new ArrayList<String>();
    }

    private void setAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    queryAllUsers();

                } else {
                    // User is signed out
                }
            }
        };
    }

    private void queryAllUsers() {
        clearCurrentUsers();
        mChildEventListener = getChildEventListener();
        mUserRefDatabase.limitToFirst(50).addChildEventListener(mChildEventListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        clearCurrentUsers();

        if (mChildEventListener != null) {
            mUserRefDatabase.removeEventListener(mChildEventListener);
        }
    }

    private void clearCurrentUsers() {
        leaderBoardAdapterSec1.clear();
        mUsersKeyList.clear();
    }

    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {

                    String userUid = dataSnapshot.getKey();
                    if (dataSnapshot.hasChildren()) {
                        UserModel recipient = dataSnapshot.getValue(UserModel.class);
                        recipient.setmRecipientId(userUid);
                        mUsersKeyList.add(userUid);
                        leaderBoardAdapterSec1.refill(recipient);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    String userUid = dataSnapshot.getKey();

                    UserModel user = dataSnapshot.getValue(UserModel.class);

                    int index = mUsersKeyList.indexOf(userUid);
                    if (index > -1) {
                        leaderBoardAdapterSec1.changeUser(index, user);
                    }

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
