package com.momentum.coaching.externalfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.momentum.coaching.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExternalFileListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Toolbar toolbar;
    public static RecyclerView externalFileRecyclerView;
    private List<String> externalList;
    private AdView adView;
    private ExternalFile_Adapter externalFile_adapter;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private boolean mGranted;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_filelist);

        toolbar = findViewById(R.id.externalFileToolbar);
        setSupportActionBar(toolbar);
        setTitle("All Downloads File");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adView = new AdView(this, getResources().getString(R.string.facebookBannerAds), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_external);
        adContainer.addView(adView);
        adView.loadAd();
        getPermission();

        externalFileRecyclerView = findViewById(R.id.recyclerViewExternal);
        progressBar = findViewById(R.id.progressbar_External);
        externalFileRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        externalList = new ArrayList<>();

        File filePath = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/");
        if (filePath.exists()) {
            File[] addFile = filePath.listFiles();
            externalList.clear();
            for (File stringFile : addFile) {
                externalList.add(stringFile.getName());
            }
            externalFile_adapter = new ExternalFile_Adapter(ExternalFileListActivity.this, externalList);
            externalFileRecyclerView.setAdapter(externalFile_adapter);
            progressBar.setVisibility(View.GONE);
        }else
        {
            Toast.makeText(this, "You don't have any downloaded file..", Toast.LENGTH_SHORT).show();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
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
        Parcelable listState = externalFileRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            externalFileRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
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
