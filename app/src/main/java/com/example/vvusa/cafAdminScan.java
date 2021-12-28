package com.example.vvusa;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import com.google.zxing.Result;

import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;

public class cafAdminScan extends AppCompatActivity implements
        ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView ScannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }
    @Override
    public void handleResult(Result result) {
        cafAdmin.resultTExtView.setText(result.getText());
        onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }
    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}