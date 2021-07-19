package com.momentum.coaching.quiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momentum.coaching.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class User_Level2 extends AppCompatActivity {

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
    private TextView beginner_text, intermediate_text, expert_text;
    private AdView mAdView;
    private InterstitialAd mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__level2);


        Bundle bundle2 = getIntent().getExtras();
        subjectName = bundle2.getString("SubjectName");

        toolbar = findViewById(R.id.level2_toolbar);
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

        beginner = findViewById(R.id.begin);
        intermediate = findViewById(R.id.inter);
        expert = findViewById(R.id.expert);

        //Admob Banner Ad
        MobileAds.initialize(this, getString(R.string.admobBanneradsMain));
        mAdView = findViewById(R.id.level2_score_adView);
        mAdView.loadAd(new AdRequest.Builder().build());


        Bundle bundle = getIntent().getExtras();
        final String tableName;
        if (bundle != null) {
            tableName = (String) bundle.get("MOMENTUM");
            Log.d("Table Name", tableName);
        } else {
            tableName = "";
        }

        //check the user authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //set user image
        setUserDetails(user);


        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quizType = beginner_text.getText().toString();
                Intent intent = new Intent(getApplicationContext(), QuestionSection2.class);
                intent.putExtra("MOMENTUM", tableName);
                intent.putExtra("level_name", "B");
                intent.putExtra("QuizType", quizType);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String quizType = intermediate_text.getText().toString();
                Intent intent2 = new Intent(getApplicationContext(), QuestionSection2.class);
                intent2.putExtra("MOMENTUM", tableName);
                intent2.putExtra("level_name", "I");
                intent2.putExtra("QuizType", quizType);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                //Admob interstitial ad
                mInterstitial = new InterstitialAd(User_Level2.this);
                mInterstitial.setAdUnitId(getString(R.string.mInterstitialAd));
                mInterstitial.loadAd(new AdRequest.Builder().build());
                mInterstitial.setAdListener(new AdListener() {
                                                @Override
                                                public void onAdLoaded() {
                                                    if (mInterstitial.isLoaded()) {
                                                        mInterstitial.show();
                                                    }
                                                    super.onAdLoaded();
                                                }
                                            }
                );


            }
        });
        expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String quizType = expert_text.getText().toString();
                Intent intent2 = new Intent(getApplicationContext(), QuestionSection2.class);
                intent2.putExtra("MOMENTUM", tableName);
                intent2.putExtra("level_name", "E");
                intent2.putExtra("QuizType", quizType);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }


    private void setUserDetails(FirebaseUser user) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personPhoto = acct.getPhotoUrl();

            Glide.with(this).load(personPhoto).fitCenter().into(biginnerImage);
            Glide.with(this).load(personPhoto).fitCenter().into(interImage);
            Glide.with(this).load(personPhoto).fitCenter().into(expartImage);
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
