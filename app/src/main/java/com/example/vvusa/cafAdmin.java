package com.example.vvusa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Scanner;

public class cafAdmin extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static TextView resultTExtView;
    Button scan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caf_admin);
        resultTExtView = findViewById(R.id.result_Text);
        scan_btn = findViewById(R.id.btn_scan);



        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        cafAdminScan.class));
            }
        });
    }
}