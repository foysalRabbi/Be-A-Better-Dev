package com.momentum.coaching.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.momentum.coaching.R;

import java.io.File;


       /*Copyright 2017 Bartosz Schiller

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.*/

public class PdfView extends AppCompatActivity {

    private PDFView pdfView;
    private Toolbar toolbar;
    private String imageName;
    private String file2;
    private File storageFile;
    private File storageFile2;
    private String momentumURL;
    private String fileName = "null";
    private String FIRST_ITEM_SET;
    private ProgressDialog pDialog;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        Bundle bundle = getIntent().getExtras();
        Bundle bundle2 = getIntent().getExtras();
        file2 = bundle2.getString("fileNameNet");
        imageName = bundle.getString("Momentum");
        //imageName = getIntent().getStringExtra("Momentum");

        toolbar = findViewById(R.id.pdfView_toolbar);
        toolbar.setTitle(imageName);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pdfView = findViewById(R.id.pdfView_id);

        //Admob Banner Ad
        MobileAds.initialize(this, getString(R.string.admobBanneradsMain));
        mAdView = findViewById(R.id.banner_pdfView);
        mAdView.loadAd(new AdRequest.Builder().build());

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Depends on your network connection and file size.");
        pDialog.setTitle("Loading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);



        storageFile = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/" + imageName + ".pdf");
        if (storageFile.exists()) {
            pdfView.fromFile(storageFile)
                    .enableDoubletap(true)
                    .enableSwipe(true)
                    .onRender(new OnRenderListener() {
                        @Override
                        public void onInitiallyRendered(int pages, float pageWidth, float pageHeight) {
                            pdfView.fitToWidth(); // optionally pass page number
                        }
                    })
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        } else {
            switch (imageName) {
                case "Physics":
                    fileName = "physics.pdf";
                    break;
                case "Chemistry":
                    fileName = "Chemistry.pdf";
                    break;
                case "Mathematics":
                    fileName = "math.pdf";
                    break;
                case "English":
                    fileName = "English.pdf";
                    break;
                case "C Programming":
                    fileName = "cprogramsolution.pdf";
                    break;
                case "Data Structure":
                    fileName = "data structure.pdf";
                    break;
                case "Discrete Math":
                    fileName = "discrete math.pdf";
                    break;
                case "Digital Electronics":
                    fileName = "digital electronics.pdf";
                    break;
                case "Operating System":
                    fileName = "operating system.pdf";
                    break;
                case "System Analysis":
                    fileName = "system analysis.pdf";
                    break;
                case "Networking":
                    fileName = "networking.pdf";
                    break;
                case "Microprocessor":
                    fileName = "microprocessor.pdf";
                    break;
                case "DBMS":
                    fileName = "dbms.pdf";
                    break;
                case "Transistor":
                    fileName = "transistor.pdf";
                    break;
                case "Basic Electricity":
                    fileName = "basic electricity.pdf";
                    break;
                case "Previous Question":
                    fileName = "previous question.pdf";
                    break;
                case "All Formulas":
                    fileName = "attach.pdf";
                    break;
                case "Trigonometric":
                    fileName = "trigonometric.pdf";
                    break;
                case "Differentiation":
                    fileName = "differentiation.pdf";
                    break;
                case "Matrix+Determinants":
                    fileName = "matrix+determinants.pdf";
                    break;
                case "Unit Conversion":
                    fileName = "physics formula.pdf";
                    break;
                case "Chemistry Formula":
                    fileName = "chemistryformula.pdf";
                    break;
                case "Periodic Table":
                    fileName = "periodic Table.pdf";
                    break;
            }

            pdfView.fromAsset(fileName)
                    .enableDoubletap(true)
                    .enableSwipe(true)
                    .onRender(new OnRenderListener() {
                        @Override
                        public void onInitiallyRendered(int pages, float pageWidth, float pageHeight) {
                            pdfView.fitToWidth(); // optionally pass page number
                        }
                    })
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
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