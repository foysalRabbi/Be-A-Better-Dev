package com.momentum.coaching.scoreboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.momentum.coaching.DbHelper.DbHelper;
import com.momentum.coaching.R;
import com.momentum.coaching.quiz.User_Level1;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserLevel_Score extends AppCompatActivity {

    private Uri personPhoto;
    private CardView yourScore;
    private CardView leaderBoard;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRefDatabase;
    private ChildEventListener mChildEventListener;
    private DbHelper dbHelper = new DbHelper(this);
    private Toolbar toolbar;
    private String comingName;
    private CircleImageView yourScoreImage, leaderImage;
    private TextView textView1,textView2;
    private String userId;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_level__score);

        Bundle bundle2 = getIntent().getExtras();
        comingName = bundle2.getString("MOMENTUM");

        toolbar = findViewById(R.id.userScore_toolbar);
        toolbar.setTitle(comingName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        yourScore = findViewById(R.id.cardView_yourScore);
        leaderBoard = findViewById(R.id.cardView_leader);

        yourScoreImage = findViewById(R.id.yourScoreImage);
        leaderImage = findViewById(R.id.leaderImage);
        textView1 = findViewById(R.id.yourScore_Text);
        textView2 = findViewById(R.id.leaderText_id);

        //Admob Banner Ad
        mAdView = findViewById(R.id.score_adView);
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView.loadAd(new AdRequest.Builder().build());

        FirebaseUser user = mAuth.getCurrentUser();
        setUserDetails(user);
        mUserRefDatabase = FirebaseDatabase.getInstance().getReference();


        int physicsMarksB=dbHelper.getScorePhysicB();
        int physicsMarksI=dbHelper.getScorePhysicI();
        int physicsMarksE=dbHelper.getScorePhysicE();
        int chemistryMarksB=dbHelper.getScoreChemistryB();
        int chemistryMarksI=dbHelper.getScoreChemistryI();
        int chemistryMarksE=dbHelper.getScoreChemistryE();
        int englishMarksB=dbHelper.getScoreEnglishB();
        int englishMarksI=dbHelper.getScoreEnglishI();
        int englishMarksE=dbHelper.getScoreEnglishE();
        int finalMarks=dbHelper.getScoreRandom();

        userId = mAuth.getCurrentUser().getUid();

        mUserRefDatabase.child("Users").child(userId).child("physicMarksB").setValue(physicsMarksB);
        mUserRefDatabase.child("Users").child(userId).child("physicMarksI").setValue(physicsMarksI);
        mUserRefDatabase.child("Users").child(userId).child("physicMarksE").setValue(physicsMarksE);
        mUserRefDatabase.child("Users").child(userId).child("chemistryMarksB").setValue(chemistryMarksB);
        mUserRefDatabase.child("Users").child(userId).child("chemistryMarksI").setValue(chemistryMarksI);
        mUserRefDatabase.child("Users").child(userId).child("chemistryMarksE").setValue(chemistryMarksE);
        mUserRefDatabase.child("Users").child(userId).child("englishMarksB").setValue(englishMarksB);
        mUserRefDatabase.child("Users").child(userId).child("englishMarksI").setValue(englishMarksI);
        mUserRefDatabase.child("Users").child(userId).child("englishMarksE").setValue(englishMarksE);
        mUserRefDatabase.child("Users").child(userId).child("finalMarks").setValue(finalMarks);

        yourScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yourScore = textView1.getText().toString();
                Intent intent= new Intent(getApplicationContext(),UserScoreActivity.class);
                intent.putExtra("MOMENTUM",yourScore);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                //Google interstitial ad
                mInterstitialAd = new InterstitialAd(UserLevel_Score.this);
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
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), UserScoreActivity.class);
                String leaderScore = textView2.getText().toString();
                intent.putExtra("MOMENTUM",leaderScore);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });

    }
    public void setUserDetails(FirebaseUser user)
    {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personPhoto = acct.getPhotoUrl();
            Glide.with(this).load(personPhoto).fitCenter().into(yourScoreImage);
        }
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
