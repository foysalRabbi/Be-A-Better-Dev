package com.momentum.coaching.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.momentum.coaching.activity.MainActivity;
import com.momentum.coaching.R;
import com.momentum.coaching.model.UserModel;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private boolean isTaken=true;
    private LinearLayout signIn;
    public static final int RC_SIGN_IN = 1;
    private ProgressDialog progressDialog;
    private String userName;
    private String userEmail;
    private String personEmail;
    private FirebaseUser firebaseUser;
    private ConnectivityManager connectivityManager;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    openSigin();
                }
                else {
                    connected = false;
                    Snackbar.make(view, "Please check your internet connection.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openSigin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                fireBaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void fireBaseAuthWithGoogle(GoogleSignInAccount acct) {
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            // Sign in success, update UI with the signed-in user's information
                            firebaseUser = mAuth.getCurrentUser();
                            updateUI(firebaseUser);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this,"you are not able to log in to google",Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            personEmail= acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            FirebaseDatabase.getInstance().getReference("userNames").child(personName).setValue(true);
        }
    }

    private void onAuthSuccess(FirebaseUser user) {
        userName = user.getDisplayName();
        userEmail = user.getEmail();
        UserModel userModel = buildNewUser();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).setValue(userModel);

    }


    private String getUserDisplayName() {
        return userName;
    }
    private String getUserEmail() {
        return userEmail;    }

    public int getPhysicMarksB(){
        return 0;
    }
    public int getPhysicMarksI(){
        return 0;
    }
    public int getPhysicMarksE(){
        return 0;
    }

    private int getChemistryMarksB() {
        return 0;
    }
    private int getChemistryMarksI() {
        return 0; }
    private int getChemistryMarksE() {
        return 0; }

    private int getEnglishMarksB() {
        return 0; }
    private int getEnglishMarksI() {
        return 0;
    }
    private int getEnglishMarksE() {
        return 0;
    }

    private int getFinalMarks()
    {
        return 0;
    }


    private UserModel buildNewUser() {
        return new UserModel(
                getUserDisplayName(),
                getUserEmail(),
                new Date().getTime(),
                getPhysicMarksB(),
                getPhysicMarksI(),
                getPhysicMarksE(),
                getChemistryMarksB(),
                getChemistryMarksI(),
                getChemistryMarksE(),
                getEnglishMarksB(),
                getEnglishMarksI(),
                getEnglishMarksE(),
                getFinalMarks()
        );
    }
}
