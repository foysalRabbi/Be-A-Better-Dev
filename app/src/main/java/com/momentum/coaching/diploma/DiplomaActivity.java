package com.momentum.coaching.diploma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.momentum.coaching.R;

public class DiplomaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView diplomaRecycle;
    private DiplomaAdapter adapter;
    private String[] diplomaSubjectName;
    private int [] diplomaSubjectImage = {R.drawable.c,R.drawable.c, R.drawable.system_analysis, R.drawable.microprocessor,
            R.drawable.data_structure, R.drawable.windows,R.drawable.network,R.drawable.network};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma_acitivity);

        toolbar = findViewById(R.id.diplomaToolbar_id);
        setSupportActionBar(toolbar);
        setTitle("Diploma Book");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        diplomaSubjectName = getResources().getStringArray(R.array.Diploma_Name);
        diplomaRecycle = findViewById(R.id.diplomaRecycler_id);
        diplomaRecycle.setHasFixedSize(true);
        diplomaRecycle.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiplomaAdapter(DiplomaActivity.this,diplomaSubjectName,diplomaSubjectImage);
        diplomaRecycle.setAdapter(adapter);

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
