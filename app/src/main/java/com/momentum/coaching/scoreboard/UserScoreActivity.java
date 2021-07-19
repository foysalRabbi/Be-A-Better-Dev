package com.momentum.coaching.scoreboard;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;
import com.momentum.coaching.R;
import com.momentum.coaching.leaderboard.LeaderScoreLevel;
import com.momentum.coaching.leaderboard.finalscore.LeaderBoardFinal;


public class UserScoreActivity extends AppCompatActivity {

    private LinearLayout physics;
    private LinearLayout chemistry;
    private LinearLayout english;
    private LinearLayout finale;
    private Toolbar toolbar;
    private TextView physicsText, chemistryText, englishText, finalText;
    private Bundle bundle;
    private String subjectName;
    private InterstitialAd mInterstitialAd;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_score);

        bundle = getIntent().getExtras();
        subjectName = bundle.getString("MOMENTUM");

        toolbar = findViewById(R.id.userViewScore_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        physicsText = findViewById(R.id.physicsText_id);
        chemistryText = findViewById(R.id.chemistryText_id);
        englishText = findViewById(R.id.englishText_id);
        finalText = findViewById(R.id.finalText_id);

        physics = (LinearLayout) findViewById(R.id.fundamentals);
        chemistry = (LinearLayout) findViewById(R.id.operating);
        english = (LinearLayout) findViewById(R.id.hardware);
        finale = (LinearLayout) findViewById(R.id.finale);


        if (subjectName.equals("Your Score")) {

            physics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String physicName = physicsText.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), LocalScoreViewActivity.class);
                    intent.putExtra("MOMENTUM", physicName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //Admob interstitial ad
                    InterstitialAdMethod();


                }
            });
            chemistry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chemistryName = chemistryText.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), LocalScoreViewActivity.class);
                    intent.putExtra("MOMENTUM", chemistryName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String englishName = englishText.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), LocalScoreViewActivity.class);
                    intent.putExtra("MOMENTUM", englishName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            finale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String finalName = finalText.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), FinalScoreActivity.class);
                    intent.putExtra("MOMENTUM", finalName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //Admob interstitial ad
                    InterstitialAdMethod();


                }
            });
        } else if (subjectName.equals("Leader Board")) {
            physics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String physicName = physicsText.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), LeaderScoreLevel.class);
                    intent.putExtra("MOMENTUM", physicName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            chemistry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String chemistryName = chemistryText.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), LeaderScoreLevel.class);
                    intent.putExtra("MOMENTUM", chemistryName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //Admob interstitial ad
                    InterstitialAdMethod();

                }
            });
            english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String englishName = englishText.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), LeaderScoreLevel.class);
                    intent.putExtra("MOMENTUM", englishName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //Admob interstitial ad
                    InterstitialAdMethod();

                }
            });
            finale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String finalName = finalText.getText().toString().trim();
                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(getApplicationContext(), LeaderBoardFinal.class);
                        intent.putExtra("MOMENTUM", finalName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    } else {

                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    public void InterstitialAdMethod()
    {
        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(UserScoreActivity.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
