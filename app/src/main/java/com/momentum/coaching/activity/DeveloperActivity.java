package com.momentum.coaching.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.momentum.coaching.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeveloperActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView circleImageView1,circleImageView2;
    private TextView ohidText1,ohidText2,shivaText1,shivaText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        toolbar = findViewById(R.id.developerToolbar);
        setSupportActionBar(toolbar);
        setTitle("Developers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        circleImageView1 = findViewById(R.id.ohid_photo);
        try {
            circleImageView1.setImageResource(R.drawable.ohid2);
        }catch (Exception e)
        {
            Toast.makeText(this, "Image not loaded..", Toast.LENGTH_SHORT).show();
        }
        ohidText1 = findViewById(R.id.ohid_name);
        ohidText2 = findViewById(R.id.ohi_description);
        circleImageView2 = findViewById(R.id.shiva_photo);
        circleImageView2.setImageResource(R.drawable.shiu);
        shivaText1 = findViewById(R.id.shiva_name);
        shivaText2 = findViewById(R.id.shiva_description);
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
