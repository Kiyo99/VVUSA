package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class thirdCard extends AppCompatActivity {
    WebView wb;
    ProgressDialog pd;
    SwipeRefreshLayout refreshScreen;

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_card);

        refreshScreen = findViewById(R.id.refreshScreen);
        pd = new ProgressDialog(thirdCard.this);
        pd.setMessage("Loading, please wait...");
        pd.show();
        showWebView();

        refreshScreen.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshScreen.setRefreshing(true);
                showWebView();
                refreshScreen.setRefreshing(false);
            }
        });
    }

    private void showWebView() {
        //Pulling the third content from firebase into the third cardView
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("news").document("News3");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String content = document.getString("content");
                        String url = content;
                        wb=(WebView)findViewById(R.id.webView1);
                        wb.getSettings().setJavaScriptEnabled(true);
                        wb.getSettings().setLoadWithOverviewMode(true);
                        wb.getSettings().setUseWideViewPort(true);
                        wb.getSettings().setBuiltInZoomControls(true);
                        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
                        wb.setWebViewClient(new thirdCard.HelloWebViewClient());
                        wb.loadUrl(url);

                        wb.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                pd.dismiss();
                            }
                        });

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (wb.isFocused() && wb.canGoBack()) {
            wb.goBack();
        } else {
            super.onBackPressed();
        }
    }
}