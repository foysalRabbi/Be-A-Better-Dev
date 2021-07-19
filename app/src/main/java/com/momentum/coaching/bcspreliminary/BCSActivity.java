package com.momentum.coaching.bcspreliminary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.momentum.coaching.R;

import java.util.ArrayList;
import java.util.List;

public class BCSActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Toolbar toolbar;
    public static RecyclerView bcsRecyclerView;
    private List<FileUploadModel> mListItem;
    public static DatabaseReference BCSDataBase;
    private long downloadID;
    private StorageReference bcsStorage;
    public static BCSCustomAdapter customAdapter;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private boolean mGranted;
    private ConnectivityManager connectivityManager;
    private boolean connected;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private AdView mAdView;
    // private final String TAG = InterstitialAdActivity.class.getSimpleName();
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcsacitivity);

        toolbar = findViewById(R.id.bcsToolbar);
        setSupportActionBar(toolbar);
        setTitle("BCS Preliminary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Admob Banner Ad
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView = findViewById(R.id.bcs_adView);
        mAdView.loadAd(new AdRequest.Builder().build());

        //Facebook InterstitialAd
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

        getPermission();
        progressBar = findViewById(R.id.progressbar_bcs);
        bcsRecyclerView = findViewById(R.id.bcsListView_id);
        bcsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListItem = new ArrayList<>();
        BCSDataBase = FirebaseDatabase.getInstance().getReference("BCS Preliminary");

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        if (connected == true) {
            FetchBCSPreliminary();
        } else {
            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.VISIBLE);
    }


    public void FetchBCSPreliminary() {
        try {

            BCSDataBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mListItem.clear();
                    for (DataSnapshot pstSnapshot : dataSnapshot.getChildren()) {
                        FileUploadModel fileUploadModel = pstSnapshot.getValue(FileUploadModel.class);
                        mListItem.add(fileUploadModel);
                    }

                    customAdapter = new BCSCustomAdapter(BCSActivity.this, mListItem);
                    bcsRecyclerView.setAdapter(customAdapter);
                    //customAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(BCSActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Today Limit Expired..", Toast.LENGTH_SHORT).show();
        }

    }

    public void getPermission() {
        String externalReadPermission = Manifest.permission.READ_EXTERNAL_STORAGE.toString();
        String externalWritePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE.toString();

        if (ContextCompat.checkSelfPermission(this, externalReadPermission) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, externalWritePermission) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{externalReadPermission, externalWritePermission}, STORAGE_PERMISSION_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                mGranted = true;
            } else {
                Toast.makeText(this, "Please allow the permission to read data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = bcsRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            bcsRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
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
