package com.momentum.coaching.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momentum.coaching.DbHelper.DbHelper;
import com.momentum.coaching.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private int score=0,scoreCHEMISTRY=0,scorePHYSICS=0,scoreENGLISH=0,scoreRandom=0;
    private DbHelper dbHelper = new DbHelper(this);
    private Button btnWrongQstns;
    private Uri personPhoto;
    public ArrayList<String> wrongQuests = new ArrayList<String>();
    public ArrayList<String> selectedAnswers = new ArrayList<String>();
    public ArrayList<String> actualAnswers = new ArrayList<String>();
    private FirebaseAuth mAuth;
    private ImageView img, yourScore;
    private TextView tvPerc;
    private String tableName="",catName="";
    private Toolbar toolbar;
    private InterstitialAd admobAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        toolbar = findViewById(R.id.result_toolbar);
        toolbar.setTitle("Result");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img = (ImageView) findViewById(R.id.imageView);
        yourScore = findViewById(R.id.yourScore_Image);
        btnWrongQstns = (Button) findViewById(R.id.btnWrongQstns);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point);
        rotate.setRepeatCount(1);
        img.startAnimation(rotate);
        //get text view
        TextView txtCorrectAns = findViewById(R.id.txtCorrectAns);

        admobAds = new InterstitialAd(this);
        admobAds.setAdUnitId(getString(R.string.mInterstitialAd));
        AdRequest adRequest = new AdRequest.Builder().build();
        admobAds.loadAd(adRequest);

        //get score
        final Bundle b = getIntent().getExtras();
        if (b.containsKey("scoreChemistry")) {
            scoreCHEMISTRY = b.getInt("scoreChemistry");
            tableName=b.getString("section");
            catName=b.getString("category");
            dbHelper.insertScoreChemistry(scoreCHEMISTRY,tableName,catName);
            score = scoreCHEMISTRY;
        } else if (b.containsKey("scorePhysics")) {
            scorePHYSICS = b.getInt("scorePhysics");
            tableName=b.getString("section");
            catName=b.getString("category");
            dbHelper.insertScorePhysic(scorePHYSICS,tableName,catName);
            score = scorePHYSICS;
        } else if (b.containsKey("scoreEnglish")) {
            scoreENGLISH = b.getInt("scoreEnglish");
            tableName=b.getString("section");
            catName=b.getString("category");
            dbHelper.insertScoreEnglish(scoreENGLISH,tableName,catName);
            score = scoreENGLISH;
        } else if (b.containsKey("scoreRandom")){
            scoreRandom = b.getInt("scoreRandom");
            tableName=b.getString("section");
            dbHelper.insertScoreFinal(scoreRandom,tableName);
            score = scoreRandom;

        }

        txtCorrectAns.setText("Total Answered : 30" + "\n" + "Correct Answered : " + score + "\nWrong Answered : " + (30 - score));

        wrongQuests = getIntent().getStringArrayListExtra("wrongQuestions");
        selectedAnswers = getIntent().getStringArrayListExtra("selectedAnswer");
        actualAnswers = getIntent().getStringArrayListExtra("actualAnswer");

        double perc = score*3.33;
        DecimalFormat df = new DecimalFormat("##.###");
        tvPerc = (TextView) findViewById(R.id.tvPerc);
        tvPerc.setText(""+df.format(perc)+" %");

        btnWrongQstns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, WrongQuestion.class);
                //Bundle b = new Bundle();
                intent.putStringArrayListExtra("wrongQuestions", wrongQuests);
                intent.putStringArrayListExtra("selectedAnswer", selectedAnswers);
                intent.putStringArrayListExtra("actualAnswer", actualAnswers);
                startActivity(intent);
                finish();
                if (admobAds.isLoaded()) {
                    admobAds.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        setUserDetails(user);

    }

    private void setUserDetails(FirebaseUser user) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personPhoto = acct.getPhotoUrl();

            Glide.with(this).load(personPhoto).fitCenter().into(yourScore);
            //Picasso.get().load(personPhoto.toString()).resize(100,100).centerCrop().into(userPhoto);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
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
