package com.momentum.coaching.scoreboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momentum.coaching.DbHelper.DbHelper;
import com.momentum.coaching.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class FinalScoreActivity extends AppCompatActivity {

    private Uri personPhoto;
    private Toolbar toolbar;
    private String subjectName;
    private FirebaseAuth mAuth;
    private Bundle bundle;
    private CircleImageView circleImageView;
    private DbHelper dbHelper = new DbHelper(this);
    private TextView scoreTextView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);


        bundle = getIntent().getExtras();
        subjectName = bundle.getString("MOMENTUM");

        toolbar = findViewById(R.id.finalScore_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        circleImageView = findViewById(R.id.finalUserImage);
        scoreTextView = findViewById(R.id.scoreTextView);

        //Admob Banner Ad
        mAdView = findViewById(R.id.userFinal_adView);
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        int finalScore=0;
        finalScore=dbHelper.getScoreRandom();
        if(finalScore<10)
        {
            scoreTextView.setText("0"+ finalScore);
        }
        else
        {
            scoreTextView.setText(""+ finalScore);
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //set user image
        setUserDetails(user);

    }

    public void setUserDetails(FirebaseUser user)
    {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personPhoto = acct.getPhotoUrl();
            Glide.with(this).asBitmap().load(personPhoto).fitCenter().into(circleImageView);
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
