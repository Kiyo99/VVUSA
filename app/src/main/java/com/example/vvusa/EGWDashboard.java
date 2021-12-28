package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EGWDashboard extends AppCompatActivity {

    TextView rNumber, welcome, title1, title2, title3;
    EditText title, message;
    SwipeRefreshLayout refreshScreen;
    Button send;
    ProgressBar pb1, pb2, pb3;
    LinearLayout first_field, second_field, third_field;
    ProgressDialog pd;

    String rNum, ID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = auth.getCurrentUser();
    String userid = firebaseUser.getUid();
    DocumentReference docRef = db.collection("Users").document(userid);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egwdashboard);

        rNumber = findViewById(R.id.rNumber);
        welcome = findViewById(R.id.welcome);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        pb1 = findViewById(R.id.pb1);
        title2 = findViewById(R.id.title2);
        pb2 = findViewById(R.id.pb2);
        title3 = findViewById(R.id.title3);
        pb3 = findViewById(R.id.pb3);
        refreshScreen = findViewById(R.id.refeshScreen);

        //Initialising and hiding the progress bars incase there are no complaints
        first_field = findViewById(R.id.first_field);
        second_field = findViewById(R.id.second_field);
        third_field = findViewById(R.id.third_field);

        first_field.setVisibility(View.GONE);
        second_field.setVisibility(View.GONE);
        third_field.setVisibility(View.GONE);

        //Calling the method for the db
        enterdb();


        //Creating the method for the send button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling the method for the send button
                send();
            }
        });

        refreshScreen.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshScreen.setRefreshing(true);
                enterdb();
                refreshScreen.setRefreshing(false);
            }
        });
    }

    private void send() {
        pd = new ProgressDialog(EGWDashboard.this);
        pd.setTitle("Sending your complaint");
        pd.setMessage("Please wait...");
        pd.show();

        String str_title = title.getText().toString();
        String str_message = message.getText().toString();

        if(TextUtils.isEmpty(str_title) || TextUtils.isEmpty(str_message))
        {
            pd.dismiss();
            Toast.makeText(EGWDashboard.this, "All fields are required!", Toast.LENGTH_SHORT).show();
        }else{
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            String T1 = document.getString("Title1");
                            String T2 = document.getString("Title2");
                            String T3 = document.getString("Title3");

                            if (T1.equalsIgnoreCase("")) {

                                Map<String, Object> complaint = new HashMap<>();
                                complaint.put("Title1", str_title);
                                complaint.put("Message1", str_message);
                                complaint.put("ID", ID);
                                complaint.put("Progress1", "25");

                                db.collection("Users").document(userid)
                                        .update(complaint);

                                db.collection("Hostel")
                                        .document("EGW")
                                        .collection("Complaints")
                                        .document(rNum)
                                        .collection(ID)
                                        .document("First Complaint")
                                        .set(complaint)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(EGWDashboard.this, "Successfully sent your complaint", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(EGWDashboard.this, "Error sending your complaint, please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else if (T2.equalsIgnoreCase("")) {

                                Map<String, Object> complaint = new HashMap<>();
                                complaint.put("Title2", str_title);
                                complaint.put("Message2", str_message);
                                complaint.put("ID", ID);
                                complaint.put("Progress2", "25");

                                db.collection("Users").document(userid)
                                        .update(complaint);

                                db.collection("Hostel")
                                        .document("EGW")
                                        .collection("Complaints")
                                        .document(rNum)
                                        .collection(ID)
                                        .document("Second Complaint")
                                        .set(complaint)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(EGWDashboard.this, "Successfully sent your complaint", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(EGWDashboard.this, "Error sending your complaint, please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }else if (T3.equalsIgnoreCase("")) {

                                Map<String, Object> complaint = new HashMap<>();
                                complaint.put("Title3", str_title);
                                complaint.put("Message3", str_message);
                                complaint.put("ID", ID);
                                complaint.put("Progress3", "25");

                                db.collection("Users").document(userid)
                                        .update(complaint);

                                db.collection("Hostel")
                                        .document("EGW")
                                        .collection("Complaints")
                                        .document(rNum)
                                        .collection(ID)
                                        .document("Third Complaint")
                                        .set(complaint)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(EGWDashboard.this, "Successfully sent your complaint", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(EGWDashboard.this, "Error sending your complaint, please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {
                                pd.dismiss();
                                Toast.makeText(EGWDashboard.this, "You have 3 active complaints, please wait until they are resolved or ask a roommate to send the complaint.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "No such document");

                        }
                    }else{
                        Log.d(TAG, "get failed with ", task.getException());

                    }
                }
            });

        }
    }

    private void enterdb() {
        //        Entering database
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        rNum = document.getString("Room Number");
                        String name = document.getString("First name");
                        String t1 = document.getString("Title1");
                        String t2 = document.getString("Title2");
                        String t3 = document.getString("Title3");
                        String p1 = document.getString("Progress1");
                        String p2 = document.getString("Progress2");
                        String p3 = document.getString("Progress3");
                        ID = document.getString("Student ID");
                        welcome.setText(String.format("Welcome %s", name));
                        rNumber.setText(String.format("Room %s", rNum));

                        //Checking for existing complaints

                        assert t1 != null;
                        if (!t1.equalsIgnoreCase("")) {

                            int intProgress = 0;
                            if (p1 != null) {
                                intProgress = Integer.parseInt(p1);
                                pb1.setProgress(intProgress);
                            }
                            title1.setText(t1);
                            first_field.setVisibility(View.VISIBLE);
//                            Toast.makeText(EGWDashboard.this, "Found document", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(EGWDashboard.this, "No active complaints", Toast.LENGTH_SHORT).show();
                        }
                        if (!t2.equalsIgnoreCase("")) {

                            int intProgress2 = 0;
                            if (p2 != null) {
                                intProgress2 = Integer.parseInt(p2);
                                pb2.setProgress(intProgress2);
                            }
                            title2.setText(t2);
                            second_field.setVisibility(View.VISIBLE);
//                            Toast.makeText(EGWDashboard.this, "Found 2nd document", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(EGWDashboard.this, "1 active complaints", Toast.LENGTH_SHORT).show();
                        }

                        if (!t3.equalsIgnoreCase("")) {

                            int intProgress3 = 0;
                            if (p3 != null) {
                                intProgress3 = Integer.parseInt(p3);
                                pb3.setProgress(intProgress3);
                            }
                            title3.setText(t3);
                            third_field.setVisibility(View.VISIBLE);
//                            Toast.makeText(EGWDashboard.this, "Found document", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(EGWDashboard.this, "Two active complaints", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Log.d(TAG, "No such document");
                        rNumber.setText("Document not found");
                    }
                }

                else{
                    Log.d(TAG, "get failed with ", task.getException());
                    rNumber.setText("failed");
                }
            }
        });

    }
}//Refresh
//Methods
//to EGW
//Tracking complaints by ID? OR?