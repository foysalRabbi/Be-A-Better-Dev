package com.momentum.coaching.allmodeltest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.momentum.coaching.bcspreliminary.FileUploadModel;
import com.momentum.coaching.R;

import java.util.ArrayList;
import java.util.List;

public class ModelTestActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Toolbar toolbar;
    public static RecyclerView modelListrecyclerView;
    private List<FileUploadModel> mListItem;
    public static DatabaseReference modelListDataBase;
    private long downloadID;
    public static ModelTestAdapter modelTestAdapter;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private boolean mGranted;
    private ConnectivityManager connectivityManager;
    private boolean connected;
    public static String modelTestDepartment;
    private AdView adView;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test_list);

        toolbar = findViewById(R.id.modelTestToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Facebook Banner ads
        adView = new AdView(this, getResources().getString(R.string.facebookBannerAds), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();

        modelListrecyclerView = findViewById(R.id.recyclerViewModelTest);
        progressBar = findViewById(R.id.progressbar_modelTest);
        modelListrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getPermission();
        mListItem = new ArrayList<>();

        modelTestDepartment = getIntent().getStringExtra("Momentum");
        setTitle(modelTestDepartment);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        if (connected == true) {
            progressBar.setVisibility(View.VISIBLE);
            switch (modelTestDepartment) {
                case "Computer":
                    modelListDataBase = FirebaseDatabase.getInstance().getReference("Computer Model Test");
                    FetchModelTest();
                    break;
                case "Civil":
                    modelListDataBase = FirebaseDatabase.getInstance().getReference("Civil Model Test");
                    FetchModelTest();
                    break;
                case "Electrical":
                    modelListDataBase = FirebaseDatabase.getInstance().getReference("Electrical Model Test");
                    FetchModelTest();
                    break;
                case "Mechanical":
                    modelListDataBase = FirebaseDatabase.getInstance().getReference("Mechanical Model Test");
                    FetchModelTest();
                    break;
                case "All Model Test":
                    modelListDataBase = FirebaseDatabase.getInstance().getReference("Non Model Test");
                    FetchModelTest();
                    break;
            }

        } else {
            Toast.makeText(this, "Please, Check your internet connection !!.", Toast.LENGTH_LONG).show();
        }
    }

    public void FetchModelTest() {
        try {

            modelListDataBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mListItem.clear();
                    for (DataSnapshot pstSnapshot : dataSnapshot.getChildren()) {
                        FileUploadModel fileUploadModel = pstSnapshot.getValue(FileUploadModel.class);
                        mListItem.add(fileUploadModel);
                    }

                    modelTestAdapter = new ModelTestAdapter(ModelTestActivity.this, mListItem);
                    modelListrecyclerView.setAdapter(modelTestAdapter);
                    progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ModelTestActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
        Parcelable listState = modelListrecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            modelListrecyclerView.getLayoutManager().onRestoreInstanceState(listState);
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