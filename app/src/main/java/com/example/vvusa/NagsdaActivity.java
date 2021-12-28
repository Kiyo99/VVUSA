package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NagsdaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button Apply;
    List<String> numbers = new ArrayList<>();
    Spinner spinnerNum;
    ProgressDialog pd;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagsda);

        Apply = findViewById(R.id.apply);


        //Spinner
        Spinner spinnerFloor = findViewById(R.id.spinnerFloor);
        spinnerNum = findViewById(R.id.spinnerNum);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.block, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFloor.setAdapter(adapter);

        spinnerFloor.setOnItemSelectedListener(this);




        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();
        if (selectedItem.equalsIgnoreCase("A")){
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Rooms for A block, please wait...");
            pd.show();
            numbers.clear();

            db.collection("Hostel")
                    .document("Nagsda")
                    .collection("A")
                    .whereEqualTo("Availability", true)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("Name");
                                    numbers.add(name);
                                }
                                ArrayAdapter<String> arrayAdapterGF = new ArrayAdapter<>(NagsdaActivity.this, android.R.layout.simple_spinner_item, numbers);
                                arrayAdapterGF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerNum.setAdapter(arrayAdapterGF);
                                pd.dismiss();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(NagsdaActivity.this, "Error loading Rooms, please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
        else if (selectedItem.equalsIgnoreCase("B")){

            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Rooms for B block, please wait...");
            pd.show();
            numbers.clear();

            db.collection("Hostel")
                    .document("Nagsda")
                    .collection("B")
                    .whereEqualTo("Availability", true)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("Name");
                                    numbers.add(name);
                                }
                                ArrayAdapter<String> arrayAdapterSF = new ArrayAdapter<>(NagsdaActivity.this, android.R.layout.simple_spinner_item, numbers);
                                arrayAdapterSF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerNum.setAdapter(arrayAdapterSF);
                                pd.dismiss();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(NagsdaActivity.this, "Error loading Rooms, please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        else if (selectedItem.equalsIgnoreCase("C")){
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Rooms for C block, please wait...");
            pd.show();
            numbers.clear();

            db.collection("Hostel")
                    .document("Nagsda")
                    .collection("C")
                    .whereEqualTo("Availability", true)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("Name");
                                    numbers.add(name);
                                }
                                ArrayAdapter<String> arrayAdapterSF = new ArrayAdapter<>(NagsdaActivity.this, android.R.layout.simple_spinner_item, numbers);
                                arrayAdapterSF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerNum.setAdapter(arrayAdapterSF);
                                pd.dismiss();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(NagsdaActivity.this, "Error loading Rooms, please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}