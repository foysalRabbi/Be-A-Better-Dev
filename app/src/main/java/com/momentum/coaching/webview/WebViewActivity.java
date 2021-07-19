package com.momentum.coaching.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.momentum.coaching.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView mwebview;
    private ProgressBar progressBar;
    private String sb = "";
    private ConnectivityManager connectivityManager;
    private boolean connected = true;
    private Toolbar toolbar;
    private String topicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Bundle bundle = getIntent().getExtras();
        topicName = bundle.getString("Momentum");
        toolbar = findViewById(R.id.webView_toolbar);
        toolbar.setTitle(topicName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mwebview = findViewById(R.id.webView_id);
        progressBar = findViewById(R.id.progressBar_webView);
        progressBar.setMax(100);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            connected = true;
            if (savedInstanceState == null && connected == true)
                if (topicName.equals("বাংলাদেশ সিভিল সার্ভিস"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/15aPR9ydaT59itEYEJY7HT1G9_SODWs-k/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("বাংলাদেশ পল্লী বিদ্যুতায়ন বোর্ড(1)"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1AhLUfyq2OEBjAqzI6ScTyo_33fUf0wAf/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("বাংলাদেশ পল্লী বিদ্যুতায়ন বোর্ড(2)"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/145kEb7OqP8BxXyCyH4Ey4o8BpRbRhqxQ/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("রাজউক"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1E21Y2FCm10k83cDZAqs0iQs2h0T_NmSX/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("টি-টি-সি"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1AEyMZhtUMQKnLp32jGs39HlBGxpKivWw/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("Programming-2"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/13LSxqPo7dtnDAvd9MT_iajZBLROYiS-S/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("Programming-3"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1u9lRlL6k7rmYD0KkJS4fGD31a9irb6Na/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("System Analysis"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1WERdyeKr2RPEYtMsUmdsnRrllve34Ftm/view?usp=sharing");
                    webViewMethod();

                }else if (topicName.equals("Microprocessor"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1gMgw28CJFWJ14BxS_-JlCayLSsjhTKBP/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("Data Structure"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1NzIE7jCSDPDiI0A2b9t2ENIW_-4W0zMH/view?usp=sharing");
                    webViewMethod();

                }else if (topicName.equals("Operating System"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1tnybfXyUVMT0lo57rD8M6x3hOGHvG-vK/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("Networking-1"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/1ltA-hwjylt7yAbbjUUU2TxOxF1H8dGbS/view?usp=sharing");
                    webViewMethod();
                }else if (topicName.equals("Networking-2"))
                {
                    mwebview.loadUrl("https://drive.google.com/file/d/19Rnx4IbgUlxow7d2AYkWe4rRtMftHb3g/view?usp=sharing");
                    webViewMethod();
                } else if (topicName.equals("Privacy Policy"))
                {
                    mwebview.loadUrl("https://momentumduetcoaching.blogspot.com/p/privacy-policy-creatives-developers.html");
                    webViewMethod();
                }
                else {
                    Toast.makeText(this, "File no available", Toast.LENGTH_SHORT).show();
                }

        } else {
            Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //getSupportActionBar().setTitle(view.getTitle());
        }
    }

    class MyWebCromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            sb = title;
            //getSupportActionBar().setTitle(title);

        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);

        }
    }

    @Override
    public void onBackPressed() {
        //getSupportActionBar().setTitle(sb);
        if (mwebview.canGoBack()) {
            mwebview.goBack();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mwebview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mwebview.restoreState(savedInstanceState);
    }

    public void webViewMethod()
    {
        mwebview.getSettings().setJavaScriptEnabled(true);
        mwebview.setWebViewClient(new MyWebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });  //to fetch WebViewClient class
        mwebview.setWebChromeClient(new MyWebCromeClient());
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
