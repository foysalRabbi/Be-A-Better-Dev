package com.momentum.coaching.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.momentum.coaching.Adapter.PageAdapter;
import com.momentum.coaching.R;
import com.momentum.coaching.allmodeltest.ModelFirstActivity;
import com.momentum.coaching.authentication.LoginActivity;
import com.momentum.coaching.bcspreliminary.BCSActivity;
import com.momentum.coaching.civiljob.CivilJobSolution;
import com.momentum.coaching.diploma.DiplomaActivity;
import com.momentum.coaching.externalfile.ExternalFileListActivity;
import com.momentum.coaching.notification.NotificationActivity;
import com.momentum.coaching.notification.NotificationAdapter;
import com.momentum.coaching.notification.NotificationModel;
import com.momentum.coaching.webview.WebViewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
    /*
License for everything not in third_party and not otherwise marked:

Copyright 2014 Google, Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
         conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
         of conditions and the following disclaimer in the documentation and/or other materials
         provided with the distribution.

THIS SOFTWARE IS PROVIDED BY GOOGLE, INC. ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GOOGLE, INC. OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Google, Inc.
---------------------------------------------------------------------------------------------
License for third_party/disklrucache:

Copyright 2012 Jake Wharton
Copyright 2011 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
---------------------------------------------------------------------------------------------
License for third_party/gif_decoder:

Copyright (c) 2013 Xcellent Creations, Inc.

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
---------------------------------------------------------------------------------------------
License for third_party/gif_encoder/AnimatedGifEncoder.java and
third_party/gif_encoder/LZWEncoder.java:

No copyright asserted on the source code of this class. May be used for any
purpose, however, refer to the Unisys LZW patent for restrictions on use of
the associated LZWEncoder class. Please forward any corrections to
kweiner@fmsware.com.

-----------------------------------------------------------------------------
License for third_party/gif_encoder/NeuQuant.java

Copyright (c) 1994 Anthony Dekker

NEUQUANT Neural-Net quantization algorithm by Anthony Dekker, 1994. See
"Kohonen neural networks for optimal colour quantization" in "Network:
Computation in Neural Systems" Vol. 5 (1994) pp 351-367. for a discussion of
the algorithm.

Any party obtaining a copy of these files from the author, directly or
indirectly, is granted, free of charge, a full and unrestricted irrevocable,
world-wide, paid up, royalty-free, nonexclusive right and license to deal in
this software and documentation files (the "Software"), including without
limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons who
receive copies from any such party to do so, with the only requirement being
that this copyright notice remain intact.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private InterstitialAd mInterstitialAd;
    private Boolean exit = false;
    private NavigationView navigationView;
    private View mHeaderView;
    private static int cart_count = 0;
    private DatabaseReference notificationDataBase;
    private NotificationAdapter notificationAdapter;
    private List<NotificationModel> modelList;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private boolean mGranted;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean exit2 = false;
    private FirebaseAuth mAuth;
    private Uri personPhoto;
    private CircleImageView userPhoto;
    private TextView nevName, nevGmail;
    private String personName;
    private String name;
    private ConnectivityManager connectivityManager;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("MOMENTUM");
        setSupportActionBar(toolbar);

        //Firebase Instance
        mAuth = FirebaseAuth.getInstance();

        tabLayout = findViewById(R.id.tabLayout_id);
        tabLayout.addTab(tabLayout.newTab().setText("CSE"));
        tabLayout.addTab(tabLayout.newTab().setText("NON"));
        tabLayout.addTab(tabLayout.newTab().setText("FORMULA"));
        tabLayout.addTab(tabLayout.newTab().setText("QUIZ"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = findViewById(R.id.ViewPager_id);
        //get from user storage permission
        getPermission();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mHeaderView = navigationView.getHeaderView(0);

        //Page Adapter purpose of swap the tap
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseUser user = mAuth.getCurrentUser();
        setUserDetails(user);

        modelList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(MainActivity.this, modelList);
        notificationDataBase = FirebaseDatabase.getInstance().getReference("Notification");
        FetchNotesBookName();
        //initiateExpander();

    }

    private void setUserDetails(FirebaseUser user) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            personPhoto = acct.getPhotoUrl();
            userPhoto = mHeaderView.findViewById(R.id.nev_ImageView);
            Glide.with(this).load(personPhoto).fitCenter().into(userPhoto);


            nevName = mHeaderView.findViewById(R.id.nev_UserName);
            nevGmail = mHeaderView.findViewById(R.id.nev_UserGmail);

            nevName.setText(personName);
            nevGmail.setText(personEmail);
            name = nevName.getText().toString();

        }
    }

    public void FetchNotesBookName() {
        notificationDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelList.clear();
                for (DataSnapshot pstSnapshot : dataSnapshot.getChildren()) {
                    NotificationModel obj = pstSnapshot.getValue(NotificationModel.class);
                    modelList.add(obj);
                }
                cart_count = modelList.size();
                invalidateOptionsMenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Cancelled!! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.notificationMenu);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.notification_out));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.notificationMenu) {

            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                startActivity(new Intent(MainActivity.this, NotificationActivity.class));

                //Interstitial Ads
                mInterstitialAd = new InterstitialAd(this);
                mInterstitialAd.setAdUnitId(getString(R.string.mInterstitialAd));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                mInterstitialAd.setAdListener(new AdListener() {
                                                  @Override
                                                  public void onAdLoaded() {
                                                      if (mInterstitialAd.isLoaded()) {
                                                          mInterstitialAd.show();
                                                      }
                                                      super.onAdLoaded();
                                                  }

                                              }
                );


            } else {
                Snackbar.make(findViewById(R.id.parentContentLayoutMain), "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.diploma:
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    startActivity(new Intent(MainActivity.this, DiplomaActivity.class));
                    //Interstitial Ads

                    mInterstitialAd = new InterstitialAd(this);
                    mInterstitialAd.setAdUnitId(getString(R.string.mInterstitialAd));
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    mInterstitialAd.setAdListener(new AdListener() {
                                                      @Override
                                                      public void onAdLoaded() {
                                                          if (mInterstitialAd.isLoaded()) {
                                                              mInterstitialAd.show();
                                                          }
                                                          super.onAdLoaded();
                                                      }

                                                  }
                    );


                } else {

                    Snackbar.make(findViewById(R.id.parentContentLayoutMain), "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                }

                break;
            case R.id.bcsPreliminary:
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    startActivity(new Intent(MainActivity.this, BCSActivity.class));
                } else {
                    Snackbar.make(findViewById(R.id.parentContentLayoutMain), "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                }

                break;

            case R.id.civil:
                startActivity(new Intent(MainActivity.this, CivilJobSolution.class));
                break;

            case R.id.Model_Test:
                startActivity(new Intent(MainActivity.this, ModelFirstActivity.class));
                break;

            case R.id.downloads:

                File filePath = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/");
                if (filePath.exists()) {
                    File[] addFile = filePath.listFiles();
                    if (addFile.length != 0) {
                        Intent intent2 = new Intent(MainActivity.this, ExternalFileListActivity.class);
                        startActivity(intent2);
                    } else {
                        Snackbar.make(findViewById(R.id.parentContentLayoutMain), "You don't have any downloaded file..", Snackbar.LENGTH_LONG).show();
                    }

                } else {
                    Snackbar.make(findViewById(R.id.parentContentLayoutMain), "File Path is incorrect", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.developer_item:
                Intent developerActivity = new Intent(MainActivity.this, DeveloperActivity.class);
                startActivity(developerActivity);

                break;
            case R.id.Feed_back:
                try {
                    Intent inten = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "creativedevelopers2019@gmail.com"));
                    inten.putExtra(Intent.EXTRA_SUBJECT, "");
                    inten.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(inten);
                } catch (Exception e) {
                    Toast.makeText(this, "Please install gmail app", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.share_item:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                intent.putExtra(Intent.EXTRA_TEXT, "Share this app with your friends and help them get opportunity for admission in DUET.\n" + "https://play.google.com/store/apps/details?id=com.momentum.coaching&hl=en");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share Engg. Guide with"));
                break;
            case R.id.rateus_item:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.momentum.coaching&hl=en"); // missing 'http://' will cause crashed
                Intent intentRate = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentRate);
                break;

            case R.id.privacy_policy:
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    String topicName = item.getTitle().toString();
                    Intent intentprivacy = new Intent(MainActivity.this, WebViewActivity.class);
                    intentprivacy.putExtra("Momentum", topicName);
                    startActivity(intentprivacy);

                } else {

                    Snackbar.make(findViewById(R.id.parentContentLayoutMain), "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
        } else {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Setting message manually and performing action on button click
                builder.setCancelable(false)
                        .setPositiveButton(Html.fromHtml("<font color=\"#283754\" >" + "Yes" + "</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color=\"#283754\" >" + "No" + "</font>"), null);
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(Html.fromHtml("<font color=\"#283754\" >" + "<b>" + "CLOSE APP" + "</b>" + "</font>"));
                alert.setMessage("Are you sure you want to exit?");
                alert.show();
            }
        }
    }

}

