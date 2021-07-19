package com.momentum.coaching.leaderboard;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momentum.coaching.R;
import com.momentum.coaching.leaderboard.chemistry.LeaderBoardCheB;
import com.momentum.coaching.leaderboard.chemistry.LeaderBoardCheE;
import com.momentum.coaching.leaderboard.chemistry.LeaderBoardCheI;
import com.momentum.coaching.leaderboard.englishl.LeaderBoardEnglB;
import com.momentum.coaching.leaderboard.englishl.LeaderBoardEnglE;
import com.momentum.coaching.leaderboard.englishl.LeaderBoardEnglI;
import com.momentum.coaching.leaderboard.physics.LeaderBoardPhyB;
import com.momentum.coaching.leaderboard.physics.LeaderBoardPhyE;
import com.momentum.coaching.leaderboard.physics.LeaderBoardPhyI;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderScoreLevel extends AppCompatActivity {

    private Uri personPhoto;
    private CircleImageView biginnerImage, interImage, expartImage;
    private TextView nevName, nevGmail;
    private String personName;
    private FirebaseAuth mAuth;
    private LinearLayout beginner;
    private LinearLayout intermediate;
    private LinearLayout expert;
    private Toolbar toolbar;
    private String subjectName;
    private Bundle bundle;
    private TextView beginner_text, intermediate_text, expert_text;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_score_level);

        bundle = getIntent().getExtras();
        subjectName = bundle.getString("MOMENTUM");

        toolbar = findViewById(R.id.leaderScoreLevel_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        beginner_text = findViewById(R.id.beginner_text);
        intermediate_text = findViewById(R.id.inter_text);
        expert_text = findViewById(R.id.expert_text);

        biginnerImage = findViewById(R.id.biginnerImage);
        interImage = findViewById(R.id.interImage);
        expartImage = findViewById(R.id.exparImage);

        beginner = (LinearLayout) findViewById(R.id.begin);
        intermediate = (LinearLayout) findViewById(R.id.inter);
        expert = (LinearLayout) findViewById(R.id.expert);

        //Admob Banner Ads
        mAdView = findViewById(R.id.Leader_score_adView);
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView.loadAd(new AdRequest.Builder().build());



        if (subjectName.equals("Physics Score")) {
            beginner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardPhyB.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            intermediate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardPhyI.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //Admob interstitial ad
                         interstitialAd();


                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            expert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardPhyE.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

        } else if (subjectName.equals("Chemistry Score")) {
            beginner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardCheB.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //Admob interstitial ad
                        interstitialAd();

                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });
            intermediate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardCheI.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });
            expert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardCheE.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

        } else if (subjectName.equals("English Score")) {
            beginner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardEnglB.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });
            intermediate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardEnglI.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });
            expert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardEnglE.class);
                        intent.putExtra("MOMENTUM", subjectName);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        setUserDetails(user);
    }

    public void interstitialAd()
    {
        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(LeaderScoreLevel.this);
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

    private void setUserDetails(FirebaseUser user) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personPhoto = acct.getPhotoUrl();
            Glide.with(this).asBitmap().load(personPhoto).fitCenter().into(biginnerImage);
            Glide.with(this).asBitmap().load(personPhoto).fitCenter().into(interImage);
            Glide.with(this).asBitmap().load(personPhoto).fitCenter().into(expartImage);


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
