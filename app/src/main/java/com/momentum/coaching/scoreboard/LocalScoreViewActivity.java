package com.momentum.coaching.scoreboard;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class LocalScoreViewActivity extends AppCompatActivity {

    private Uri personPhoto;
    private Toolbar toolbar;
    private String subjectName;
    private FirebaseAuth mAuth;
    private Bundle bundle;
    private CircleImageView circleImageView1, circleImageView2, circleImageView3;
    private DbHelper dbHelper = new DbHelper(this);
    private TextView score1, score2, score3;
    private int physicsB, physicsI, physicsE;
    private int chemistryB, chemistryI, chemistryE;
    private int englishB, englishI, englishE;
    private AdView mAdView;
    private TextView comment_B, comment_I, comment_E;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_score_view);

        bundle = getIntent().getExtras();
        subjectName = bundle.getString("MOMENTUM");

        toolbar = findViewById(R.id.localScore_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //circleImageView1 = findViewById(R.id.userBeginnerImage);
        circleImageView2 = findViewById(R.id.userInterImage);
        //circleImageView3 = findViewById(R.id.userExpertImage);

        score1 = findViewById(R.id.txt1);
        score2 = findViewById(R.id.txt2);
        score3 = findViewById(R.id.txt3);

        comment_B = findViewById(R.id.physicsBeginner_Comment);
        comment_I = findViewById(R.id.physicsIntermediate_Comment);
        comment_E = findViewById(R.id.physicsExpert_Comment);

        //Admob Banner Ad
        mAdView = findViewById(R.id.local_score_adView);
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView.loadAd(new AdRequest.Builder().build());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //set user image
        setUserDetails(user);

        if (subjectName.equals("Physics Score")) {
            physicsB = physicsI = physicsE = 0;
            physicsB = dbHelper.getScorePhysicB();
            physicsI = dbHelper.getScorePhysicI();
            physicsE = dbHelper.getScorePhysicE();

            if (physicsB == 0)
            {
                comment_B.setText("You haven't taken test yet.");
            }else
            {
                if (physicsB < 20) {
                    comment_B.setText("Poor performance. Study hard and try again.");
                    comment_B.setTextColor(Color.RED);
                } else if (physicsB >= 20 && physicsB < 35) {
                    comment_B.setText("Good performance. Try hard to get more marks.");
                    comment_B.setTextColor(Color.BLUE);
                } else {
                    comment_B.setText("Excellent performance.");
                    comment_B.setTextColor(Color.GREEN);
                }
            }


            if (physicsI == 0)
            {
                comment_I.setText("You haven't taken test yet.");
            }else {
                if (physicsI < 20) {
                    comment_I.setText("Poor performance. Study hard and try again.");
                    comment_I.setTextColor(Color.RED);
                } else if (physicsI >= 20 && physicsB < 35) {
                    comment_I.setText("Good performance. Try hard to get more marks.");
                    comment_I.setTextColor(Color.BLUE);
                } else {
                    comment_I.setText("Excellent performance.");
                    comment_I.setTextColor(Color.GREEN);
                }
            }

            if (physicsE == 0)
            {
                comment_E.setText("You haven't taken test yet.");
            }else {
                if (physicsE < 20) {
                    comment_E.setText("Poor performance. Study hard and try again.");
                    comment_E.setTextColor(Color.RED);
                } else if (physicsE >= 20 && physicsB < 35) {
                    comment_E.setText("Good performance. Try hard to get more marks.");
                    comment_E.setTextColor(Color.BLUE);
                } else {
                    comment_E.setText("Excellent performance.");
                    comment_E.setTextColor(Color.GREEN);
                }
            }

            if (physicsB < 10) {
                score1.setText("0" + physicsB);
            } else {
                score1.setText("" + physicsB);
            }
            if (physicsI < 10) {
                score2.setText("0" + physicsI);
            } else {
                score2.setText("" + physicsI);
            }
            if (physicsE < 10) {
                score3.setText("0" + physicsE);
            } else {
                score3.setText("" + physicsE);
            }


        } else if (subjectName.equals("Chemistry Score")) {
            chemistryB = chemistryI = chemistryE = 0;
            chemistryB = dbHelper.getScoreChemistryB();
            chemistryI = dbHelper.getScoreChemistryI();
            chemistryE = dbHelper.getScoreChemistryE();


            if (chemistryB == 0)
            {
                comment_B.setText("You haven't taken test yet.");
            }else {
                if (chemistryB < 20) {
                    comment_B.setText("Poor performance. Study hard and try again.");
                    comment_B.setTextColor(Color.RED);
                } else if (chemistryB >= 20 && chemistryB < 35) {
                    comment_B.setText("Good performance. Try hard to get more marks.");
                    comment_B.setTextColor(Color.BLUE);
                } else {
                    comment_B.setText("Excellent performance.");
                    comment_B.setTextColor(Color.GREEN);
                }
            }

            if (chemistryI == 0)
            {
                comment_I.setText("You haven't taken test yet.");
            }else {
                if (chemistryI < 20) {
                    comment_I.setText("Poor performance. Study hard and try again.");
                    comment_I.setTextColor(Color.RED);
                } else if (chemistryI >= 20 && chemistryI < 35) {
                    comment_I.setText("Good performance. Try hard to get more marks.");
                    comment_I.setTextColor(Color.BLUE);
                } else {
                    comment_I.setText("Excellent performance.");
                    comment_I.setTextColor(Color.GREEN);
                }
            }

            if (chemistryE == 0)
            {
                comment_E.setText("You haven't taken test yet.");
            }else {
                if (chemistryE < 20) {
                    comment_E.setText("Poor performance. Study hard and try again.");
                    comment_E.setTextColor(Color.RED);
                } else if (chemistryE >= 20 && chemistryE < 35) {
                    comment_E.setText("Good performance. Try hard to get more marks.");
                    comment_E.setTextColor(Color.BLUE);
                } else {
                    comment_E.setText("Excellent performance.");
                    comment_E.setTextColor(Color.GREEN);
                }
            }

            if (chemistryB < 10) {
                score1.setText("0" + chemistryB);
            } else {
                score1.setText("" + chemistryB);
            }
            if (chemistryI < 10) {
                score2.setText("0" + chemistryI);
            } else {
                score2.setText("" + chemistryI);
            }
            if (chemistryE < 10) {
                score3.setText("0" + chemistryE);
            } else {
                score3.setText("" + chemistryE);
            }


        } else if (subjectName.equals("English Score")) {
            englishB = englishI = englishE = 0;
            englishB = dbHelper.getScoreEnglishB();
            englishI = dbHelper.getScoreEnglishI();
            englishE = dbHelper.getScoreEnglishE();

            if (englishB == 0)
            {
                comment_B.setText("You haven't taken test yet.");
            }else {
                if (englishB < 20) {
                    comment_B.setText("Poor performance. Study hard and try again.");
                    comment_B.setTextColor(Color.RED);
                } else if (englishB >= 20 && englishB < 35) {
                    comment_B.setText("Good performance. Try hard to get more marks.");
                    comment_B.setTextColor(Color.BLUE);
                } else {
                    comment_B.setText("Excellent performance.");
                    comment_B.setTextColor(Color.GREEN);
                }
            }

            if (englishI == 0)
            {
                comment_I.setText("You haven't taken test yet.");
            }else {
                if (englishI < 20) {
                    comment_I.setText("Poor performance. Study hard and try again.");
                    comment_I.setTextColor(Color.RED);
                } else if (englishI >= 20 && englishB < 35) {
                    comment_I.setText("Good performance. Try hard to get more marks.");
                    comment_I.setTextColor(Color.BLUE);
                } else {
                    comment_I.setText("Excellent performance.");
                    comment_I.setTextColor(Color.GREEN);
                }
            }

            if (englishE == 0)
            {
                comment_E.setText("You haven't taken test yet.");
            }else {

                if (englishE < 20) {
                    comment_E.setText("Poor performance. Study hard and try again.");
                    comment_E.setTextColor(Color.RED);
                } else if (englishE >= 20 && englishB < 35) {
                    comment_E.setText("Good performance. Try hard to get more marks.");
                    comment_E.setTextColor(Color.BLUE);
                } else {
                    comment_E.setText("Excellent performance.");
                    comment_E.setTextColor(Color.GREEN);
                }
            }

            if (englishB < 10) {
                score1.setText("0" + englishB);
            } else {
                score1.setText("" + englishB);
            }
            if (englishI < 10) {
                score2.setText("0" + englishI);
            } else {
                score2.setText("" + englishI);
            }
            if (englishE < 10) {
                score3.setText("0" + englishE);
            } else {
                score3.setText("" + englishE);
            }
        }

    }

    public void setUserDetails(FirebaseUser user) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personPhoto = acct.getPhotoUrl();
            //Glide.with(this).asBitmap().load(personPhoto).fitCenter().into(circleImageView1);
            Glide.with(this).load(personPhoto).fitCenter().into(circleImageView2);
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
