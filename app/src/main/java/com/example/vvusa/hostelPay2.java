package com.example.vvusa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class hostelPay2 extends AppCompatActivity {

    WebView wb2;
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_pay2);

        wb2=(WebView)findViewById(R.id.webView2);
        wb2.getSettings().setJavaScriptEnabled(true);
        wb2.getSettings().setLoadWithOverviewMode(true);
        wb2.getSettings().setUseWideViewPort(true);
        wb2.getSettings().setBuiltInZoomControls(true);
        wb2.getSettings().setPluginState(WebSettings.PluginState.ON);
//        wb.getSettings().setPluginsEnabled(true);
        wb2.setWebViewClient(new hostelPay2.HelloWebViewClient());
        wb2.loadUrl("https://paystack.com/pay/j-1gz4a21v");
    }
}