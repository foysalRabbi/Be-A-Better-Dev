package com.momentum.coaching.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.momentum.coaching.R;


public class RandomActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String subjectName;
    private Button button;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        Bundle bundle2 = getIntent().getExtras();
        subjectName = bundle2.getString("SubjectName");

        toolbar = findViewById(R.id.random_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Admob Banner Ad
        mAdView = findViewById(R.id.random_adView);
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView.loadAd(new AdRequest.Builder().build());

        button = findViewById(R.id.startRandomButton);



        Bundle bundle=getIntent().getExtras();
        final String tableName;
        if(bundle!=null){
            tableName=(String)bundle.get("MOMENTUM");
            //Log.d("Table Name",tableName);
        }
        else
        {
            tableName="";
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getApplicationContext(),RandomQuestionActivity.class);
                intent.putExtra("MOMENTUM",tableName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                //Admob interstitial ad
                mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(RandomActivity.this);
                mInterstitialAd.setAdUnitId(getString(R.string.mInterstitialAd));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                mInterstitialAd.setAdListener(new AdListener() {
                                                  @Override
                                                  public void onAdLoaded() {
                                                      if (mInterstitialAd.isLoaded()) {
                                                          mInterstitialAd.show();
                                                      }
                                                      super.onAdLoaded();
                                                  }
                                              }
                );

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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
