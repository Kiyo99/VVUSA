package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class BediakoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Name, ID, acNo, acaYear, sem, address, nationality, PhoneNumber;
    Button Apply;
    String block, roomNo, room, capacity, finalCapacity;
    int capacityInt, updatedCapacity;
    List<String> numbers = new ArrayList<>();
    Spinner spinnerNum;
    ProgressDialog pd;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = auth.getCurrentUser();
    String userid = firebaseUser.getUid();
    DocumentReference userRef = db.collection("Users").document(userid);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bediako);

        Name = findViewById(R.id.name);
        ID = findViewById(R.id.ID);
        acNo = findViewById(R.id.acNo);
        acaYear = findViewById(R.id.acaYear);
        sem = findViewById(R.id.sem);
        address = findViewById(R.id.address);
        nationality = findViewById(R.id.nationality);
        PhoneNumber = findViewById(R.id.phoneNumber);
        Apply = findViewById(R.id.apply);


        //Spinner
        Spinner spinnerblock = findViewById(R.id.spinnerblock);
        spinnerNum = findViewById(R.id.spinnerNum);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.block, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerblock.setAdapter(adapter);

        spinnerblock.setOnItemSelectedListener(this);

        //Going into the database to populate some of the fields
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        String lastName = document.getString("Last name");
                        String firstName = document.getString("First name");
                        String name = lastName + " " + firstName;
                        String iD = document.getString("Student ID");
                        String accNo = "S" + iD;
                        String phoneNumber = document.getString("Phone Number");

                        //Setting the textViews
                        Name.setText(name);
                        ID.setText(iD);
                        acNo.setText(accNo);
                        PhoneNumber.setText(phoneNumber);


                    } else {
                        Toast.makeText(BediakoActivity.this, "Document does not exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(BediakoActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });


        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room = spinnerNum.getSelectedItem().toString();
                roomNo = room + block;

                //Declaring the strings
                pd = new ProgressDialog(BediakoActivity.this);
                pd.setTitle("Setting up your dashboard");
                pd.setMessage("Please wait...");
                pd.show();

                String str_ID = ID.getText().toString();
                String str_name = Name.getText().toString();
                String str_acNo = acNo.getText().toString();
                String str_acaYear = acaYear.getText().toString();
                String str_sem = sem.getText().toString();
                String str_address = address.getText().toString();
                String str_nationality = nationality.getText().toString();
                String str_phoneNumber = PhoneNumber.getText().toString();


                if(TextUtils.isEmpty(str_ID) || TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_acNo) || TextUtils.isEmpty(str_acaYear) || TextUtils.isEmpty(str_sem)
                        || TextUtils.isEmpty(str_address) || TextUtils.isEmpty(str_nationality) || TextUtils.isEmpty(str_phoneNumber))
                {
                    pd.dismiss();
                    Toast.makeText(BediakoActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                else if (!str_acNo.equals("S" + str_ID)){
                    pd.dismiss();
                    Toast.makeText(BediakoActivity.this, "Your Account number and ID number do not correlate", Toast.LENGTH_SHORT).show();
                }
                else if (str_phoneNumber.length() < 10)
                {
                    pd.dismiss();
                    Toast.makeText(BediakoActivity.this, "Please have a valid phone number", Toast.LENGTH_SHORT).show();
                }
                else{

                    //Updating room count and room database
                    db.collection("Hostel")
                            .document("Bediako")
                            .collection(block)
                            .document(room)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
//                                                        Toast.makeText(BediakoActivity.this, "Found document", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    capacity = document.getString("Capacity");
                                    capacityInt = Integer.parseInt(capacity);
                                    updatedCapacity = capacityInt - 1;
                                    finalCapacity = String.valueOf(updatedCapacity);

                                    if (updatedCapacity == 0) {

                                        //Do a dependency test on -1
                                        Map<String, Object> Room = new HashMap<>();
                                        Room.put("Capacity", finalCapacity);
                                        Room.put("Availability", false);

                                        db.collection("Hostel")
                                                .document("Bediako")
                                                .collection(block)
                                                .document(room)
                                                .update(Room);
                                    } else {
                                        Map<String, Object> Room = new HashMap<>();
                                        Room.put("Capacity", finalCapacity);
                                        db.collection("Hostel")
                                                .document("Bediako")
                                                .collection(block)
                                                .document(room)
                                                .update(Room);
                                    }
                                }
                                else {
                                    Log.d(TAG, "No such document");
                                    Toast.makeText(BediakoActivity.this, "Document not found", Toast.LENGTH_LONG).show();
                                }

                            }
                            else {
                                Log.d(TAG, "No such document");
                                Toast.makeText(BediakoActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }

                    });


                    //Updating the Hostel Database
                    Map<String, Object> Room = new HashMap<>();
                    Room.put("Name", str_name);
                    Room.put("ID", str_ID);
                    Room.put("Account Number", str_acNo);
                    Room.put("Year", str_acaYear);
//                                        Room.put("Date", Date);
                    Room.put("Semester", str_sem);
                    Room.put("Home address", str_address);
                    Room.put("Nationality", str_nationality);
                    Room.put("Phone Number", str_phoneNumber);


                    db.collection("Hostel")
                            .document("Bediako")
                            .collection(block + " block")
                            .document(room)
                            .collection("Room Members")
                            .document(str_ID)
                            .set(Room);

                    //Updating the User database
                    userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                    Map<String, Object> Hostel = new HashMap<>();
                                    Hostel.put("Hostel", "Bediako");
                                    Hostel.put("Room Number", roomNo);

                                    db.collection("Users").document(userid)
                                            .update(Hostel);

                                    Intent intent = new Intent(BediakoActivity.this, BediakoDashboard.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(BediakoActivity.this, "Document does not exist", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                                Toast.makeText(BediakoActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }

        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();
        if (selectedItem.equalsIgnoreCase("A")){
            block = "A";
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Rooms for A block, please wait...");
            pd.show();
            numbers.clear();

            db.collection("Hostel")
                    .document("Bediako")
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
                                ArrayAdapter<String> arrayAdapterA = new ArrayAdapter<>(BediakoActivity.this, android.R.layout.simple_spinner_item, numbers);
                                arrayAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerNum.setAdapter(arrayAdapterA);
                                pd.dismiss();
//                                room = spinnerNum.getSelectedItem().toString();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(BediakoActivity.this, "Error loading Rooms, please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
        else if (selectedItem.equalsIgnoreCase("B")){
            block = "B";
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Rooms for B block, please wait...");
            pd.show();
            numbers.clear();

            db.collection("Hostel")
                    .document("Bediako")
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
                                ArrayAdapter<String> arrayAdapterB = new ArrayAdapter<>(BediakoActivity.this, android.R.layout.simple_spinner_item, numbers);
                                arrayAdapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerNum.setAdapter(arrayAdapterB);
                                pd.dismiss();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(BediakoActivity.this, "Error loading Rooms, please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        else if (selectedItem.equalsIgnoreCase("C")){
            block = "C";
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Rooms for C block, please wait...");
            pd.show();
            numbers.clear();

            db.collection("Hostel")
                    .document("Bediako")
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
                                ArrayAdapter<String> arrayAdapterC = new ArrayAdapter<>(BediakoActivity.this, android.R.layout.simple_spinner_item, numbers);
                                arrayAdapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerNum.setAdapter(arrayAdapterC);
                                pd.dismiss();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(BediakoActivity.this, "Error loading Rooms, please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}