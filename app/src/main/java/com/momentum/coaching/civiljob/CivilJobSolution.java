package com.momentum.coaching.civiljob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.momentum.coaching.R;

public class CivilJobSolution extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView simpleList;
    private String[] civilSubjectName;
    private int []civilSubjectImage = {R.drawable.momentumlogo, R.drawable.momentumlogo, R.drawable.momentumlogo,
            R.drawable.momentumlogo, R.drawable.momentumlogo};
    private InterstitialAd interstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civil_job);

        toolbar = findViewById(R.id.civilToolbar);
        setSupportActionBar(toolbar);
        setTitle("Civil Job Solution");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //admob Banner Ads
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView = findViewById(R.id.civil_adView);
        mAdView.loadAd(new AdRequest.Builder().build());

        interstitialAd = new InterstitialAd(this, getString(R.string.facebook_interstial));
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                //Log.e(TAG, "Interstitial ad displayed.");
            }
            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                //Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                // Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                // Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                //Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        interstitialAd.loadAd();

        civilSubjectName = getResources().getStringArray(R.array.civil_subject);
        simpleList = findViewById(R.id.civilListView_id);
        simpleList.setHasFixedSize(true);
        simpleList.setLayoutManager(new LinearLayoutManager(this));
        CivilAdapter customAdapter = new CivilAdapter(this,civilSubjectName, civilSubjectImage);
        simpleList.setAdapter(customAdapter);

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
