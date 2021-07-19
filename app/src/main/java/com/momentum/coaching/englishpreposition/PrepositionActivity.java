package com.momentum.coaching.englishpreposition;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.momentum.coaching.R;
import com.momentum.coaching.model.ProverbsModel;
import com.momentum.coaching.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrepositionActivity extends AppCompatActivity {

    private List<ProverbsModel> mListItem;
    private RecyclerView recyclerView;
    private AdView mAdView;
    private Toolbar toolbar;
    private String subjectName;
    private ProgressBar progressBar;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private RequestQueue mRequestQueue;
    private PrepositionAdapter prepositionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priposition);

        Bundle bundle2 = getIntent().getExtras();
        subjectName = bundle2.getString("Momentum");
        toolbar = findViewById(R.id.englishPreposition_toolbar);
        toolbar.setTitle(subjectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Facebook Banner ads
        mAdView = new AdView(this, getResources().getString(R.string.facebookBannerAds), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.englishPreposition_adView);
        adContainer.addView(mAdView);
        mAdView.loadAd();

        progressBar = findViewById(R.id.progressbar_preposition);
        recyclerView = findViewById(R.id.preposition_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRequestQueue = Volley.newRequestQueue(this);
        mListItem = new ArrayList<>();
        parseJSON();

    }

    private void parseJSON() {
        String url = Constants.BASE_URL;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Preposition");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String question = hit.getString("question");
                                String answer = hit.getString("answer");
                                mListItem.add(new ProverbsModel(question, answer));
                            }
                            prepositionAdapter = new PrepositionAdapter(PrepositionActivity.this, mListItem);
                            recyclerView.setAdapter(prepositionAdapter);
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        mRequestQueue.add(objectRequest);
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

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
