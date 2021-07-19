package com.momentum.coaching.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momentum.coaching.R;
import com.momentum.coaching.authentication.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class Splash_Screen extends AppCompatActivity {

    private CircleImageView circleImageView;
    private TextView textView1, textView2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        mAuth = FirebaseAuth.getInstance();
        circleImageView = findViewById(R.id.circleImageView);
        textView1 = findViewById(R.id.splashText1);
        textView2 = findViewById(R.id.splashText2);
        final FirebaseUser user = mAuth.getCurrentUser();

        new Thread((new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ae) {
                    Toast.makeText(Splash_Screen.this, "Error while Opening the App", Toast.LENGTH_SHORT).show();
                }
                if (user != null) {
                    startActivity(new Intent(Splash_Screen.this, MainActivity.class));
                    finish();

                } else {
                    //check user login or not
                    Intent i = new Intent(Splash_Screen.this, LoginActivity.class);
                    startActivity(i);
                    //When going to login activity than splash screen is close for these finish.
                    finish();
                }

            }
        })).start();
    }
}
