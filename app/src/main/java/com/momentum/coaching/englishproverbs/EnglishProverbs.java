package com.momentum.coaching.englishproverbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.momentum.coaching.R;
import com.momentum.coaching.bcspreliminary.BCSActivity;
import com.momentum.coaching.bcspreliminary.FileUploadModel;
import com.momentum.coaching.model.ProverbsModel;

import java.util.ArrayList;
import java.util.List;


public class EnglishProverbs extends AppCompatActivity {
    private List<ProverbsModel> mListItem;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private AdView mAdView;
    private Toolbar toolbar;
    private String subjectName;
    private ProgressBar progressBar;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_proverbs);

        Bundle bundle2 = getIntent().getExtras();
        subjectName = bundle2.getString("Momentum");
        toolbar = findViewById(R.id.englishProverbs_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Admob Banner Ad
        MobileAds.initialize(this,getString(R.string.admobBanneradsMain));
        mAdView = findViewById(R.id.englishProverbs_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        progressBar = findViewById(R.id.progressbar_proverbs);
        recyclerView = findViewById(R.id.proverb_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListItem = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("English Proverb");

        try {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mListItem.clear();
                    for (DataSnapshot pstSnapshot : dataSnapshot.getChildren()) {
                        ProverbsModel fileUploadModel = pstSnapshot.getValue(ProverbsModel.class);
                        mListItem.add(fileUploadModel);
                    }

                    ProverbsAdapter  adapter = new ProverbsAdapter(EnglishProverbs.this,mListItem);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(EnglishProverbs.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }
            });
        }catch (Exception e)
        {
            Toast.makeText(this, "Today Limit Expired..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
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
