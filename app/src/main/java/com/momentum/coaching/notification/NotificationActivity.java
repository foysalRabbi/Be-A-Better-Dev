package com.momentum.coaching.notification;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.momentum.coaching.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private List<NotificationModel> modelList;
    private NotificationAdapter notificationAdapter;
    private DatabaseReference notificationDataBase;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<NotificationModel> ReversedModelList;
    private AdView mAdView;
    private ConnectivityManager connectivityManager;
    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = findViewById(R.id.notificationToolbar);
        setSupportActionBar(toolbar);
        setTitle("Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressBar = findViewById(R.id.progressbar_notification);
        recyclerView = findViewById(R.id.notificationRecyclerView);
        //Admob Banner
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView = findViewById(R.id.notiAdView);
        mAdView.loadAd(new AdRequest.Builder().build());

        modelList = new ArrayList<>();
        ReversedModelList = new ArrayList<>();

        notificationAdapter = new NotificationAdapter(NotificationActivity.this, modelList);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        notificationDataBase = FirebaseDatabase.getInstance().getReference("Notification");

        FetchNottification();
        progressBar.setVisibility(View.VISIBLE);


    }

    public void FetchNottification() {
        notificationDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelList.clear();
                ReversedModelList.clear();
                for (DataSnapshot pstSnapshot : dataSnapshot.getChildren()) {
                    NotificationModel obj = pstSnapshot.getValue(NotificationModel.class);
                    modelList.add(obj);
                }

                for (int i = modelList.size() - 1; i >= 0; i--) {

                    ReversedModelList.add(modelList.get(i));
                }

                notificationAdapter = new NotificationAdapter(NotificationActivity.this, ReversedModelList);
                recyclerView.setAdapter(notificationAdapter);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NotificationActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

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
