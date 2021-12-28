package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class cafQR extends AppCompatActivity {

    String isFeeder, sText, result, eligibilty;
    EditText Input;
    Button Generate;
    ImageView Output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caf_qr);

        Input = findViewById(R.id.SID);
        Generate = findViewById(R.id.generate);
        Output = findViewById(R.id.output);


        //Getting the User's ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userid = firebaseUser.getUid();
        DocumentReference docRef = db.collection("Users").document(userid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String SID = document.getString("Student ID");
                        Input.setText(SID);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        isFeeder = document.getString("isFeeder");
                    } else {
                        Log.d(TAG, "No such document");

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());

                }
            }
        });


        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sText = Input.getText().toString();

                if (sText.isEmpty()) {
                    Toast.makeText (cafQR.this,
                            "ENTER YOUR STUDENT ID", Toast.LENGTH_SHORT).show();

                } else {
                    if (isFeeder.equalsIgnoreCase("True")){
                        eligibilty = "Eligible";
                    }
                    else {
                         eligibilty = "Not eligible";
                    }
//                    String date = String.valueOf(android.text.format.DateFormat("dd-MM-yyyy", new java.util.Date()));
                    Date date = Calendar.getInstance().getTime();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String fDate = simpleDateFormat.format(date);
//                    Toast.makeText(cafQR.this, fDate, Toast.LENGTH_LONG).show();
                    result = fDate + ", " + sText + ", " + eligibilty;
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        BitMatrix matrix = writer.encode(result, BarcodeFormat.QR_CODE, 350, 350);
                        BarcodeEncoder encoder = new BarcodeEncoder();

                        Bitmap bitmap = encoder.createBitmap(matrix);

                        Output.setImageBitmap(bitmap);

                        InputMethodManager manager = (InputMethodManager) getSystemService(
                                Context.INPUT_METHOD_SERVICE
                        );

                        manager.hideSoftInputFromWindow(Input.getApplicationWindowToken()
                                ,0);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }



            }
        });


    }
}
