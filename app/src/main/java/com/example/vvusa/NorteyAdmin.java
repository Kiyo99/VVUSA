package com.example.vvusa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class NorteyAdmin extends AppCompatActivity {
    int backButtonCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nortey_admin);
    }

    @Override
    public void onBackPressed() {

        if(backButtonCount >= 1)
        {
            finishAffinity();
            finish();
        }else {
            Toast.makeText(this, "Press the back button once again to exit the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}