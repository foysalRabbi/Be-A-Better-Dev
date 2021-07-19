package com.momentum.coaching.allmodeltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.momentum.coaching.Adapter.DepartmentAdapter;
import com.momentum.coaching.R;


public class ModelFirstActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private int [] subject_image = {R.drawable.momentumlogo,R.drawable.momentumlogo,R.drawable.momentumlogo,R.drawable.momentumlogo,
            R.drawable.momentumlogo};
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_first);

        toolbar = findViewById(R.id.firstModel_toolbar);
        toolbar.setTitle("Model Test");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Admob Banner Ad
        mAdView = findViewById(R.id.model_adView);
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView.loadAd(new AdRequest.Builder().build());

        final String[] subject_name = getResources().getStringArray(R.array.model_tes);
        recyclerView = findViewById(R.id.modelFirs_recycler);
        recyclerView.setHasFixedSize(true);
        DepartmentAdapter recyclerAdapter = new DepartmentAdapter(this,subject_name,subject_image);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
