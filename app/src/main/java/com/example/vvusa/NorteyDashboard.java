package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class NorteyDashboard extends AppCompatActivity {

    TextView rNumber, welcome, title1, title2, title3, norteyTitle1, norteySub1, topic2, subtitle2,
            topic3, subtitle3, topic4, subtitle4;
    EditText title, message;
    SwipeRefreshLayout refreshScreen;
    Button send;
    ProgressBar pb1, pb2, pb3;
    LinearLayout first_field, second_field, third_field;
    ProgressDialog pd;
    CardView firstCard, secondCard, thirdCard, fourthCard;

    String rNum, ID, room;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = auth.getCurrentUser();
    String userid = firebaseUser.getUid();
    DocumentReference docRef = db.collection("Users").document(userid);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nortey_dashboard);

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
        norteyTitle1 = findViewById(R.id.norteyTitle1);
        norteySub1 = findViewById(R.id.norteySub1);
        topic2 = findViewById(R.id.topic2);
        subtitle2 = findViewById(R.id.subtitle2);
        topic3 = findViewById(R.id.topic3);
        subtitle3 = findViewById(R.id.subtitle3);
        topic4 = findViewById(R.id.topic4);
        subtitle4 = findViewById(R.id.subtitle4);
        firstCard = findViewById(R.id.first_card);
        secondCard = findViewById(R.id.second_card);
        thirdCard = findViewById(R.id.third_card);
        fourthCard = findViewById(R.id.fourth_card);


        //Initialising and hiding the progress bars incase there are no complaints
        first_field = findViewById(R.id.first_field);
        second_field = findViewById(R.id.second_field);
        third_field = findViewById(R.id.third_field);

        first_field.setVisibility(View.GONE);
        second_field.setVisibility(View.GONE);
        third_field.setVisibility(View.GONE);

        //Calling the method for the db
        enterdb();
        //Calling the method to update the cards
        updateCards();

        //Onclick for cards
        firstCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NorteyDashboard.this, NorteyCard1.class);
                startActivity(intent);
            }
        });

        secondCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NorteyDashboard.this, NorteyCard2.class);
                startActivity(intent);
            }
        });

        thirdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NorteyDashboard.this, NorteyCard3.class);
                startActivity(intent);
            }
        });

        //Onclick for TextViews
        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NorteyDashboard.this, Complaint1.class);
                startActivity(intent);
            }
        });

        title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NorteyDashboard.this, Complaint2.class);
                startActivity(intent);
            }
        });

        title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NorteyDashboard.this, Complaint3.class);
                startActivity(intent);
            }
        });


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
                updateCards();
                refreshScreen.setRefreshing(false);
            }
        });
    }

    private void updateCards() {

        DocumentReference docRef = db.collection("Hostel")
                .document("J.J.Nortey")
                .collection("News")
                .document("news1");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String topic = document.getString("Title");
                        String subtitle = document.getString("Subtitle");
                        norteyTitle1.setText(topic);
                        norteySub1.setText(subtitle);

                    } else {
                        Log.d(TAG, "No such document");
                        norteyTitle1.setText("Nothing yet, check again later");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    norteyTitle1.setText("failed");
                }
            }
        });

        //Second news card
        DocumentReference docRef2 = db.collection("Hostel")
                .document("J.J.Nortey")
                .collection("News")
                .document("news2");
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String topic = document.getString("Title");
                        String subtitle = document.getString("Subtitle");
                        topic2.setText(topic);
                        subtitle2.setText(subtitle);

                    } else {
                        Log.d(TAG, "No such document");
                        topic2.setText("Nothing yet, check again later");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    topic2.setText("failed");
                }
            }
        });

        //Third news card
        DocumentReference docRef3 = db.collection("Hostel")
                .document("J.J.Nortey")
                .collection("News")
                .document("news3");
        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String topic = document.getString("Title");
                        String subtitle = document.getString("Subtitle");
                        topic3.setText(topic);
                        subtitle3.setText(subtitle);

                    } else {
                        Log.d(TAG, "No such document");
                        topic3.setText("Nothing yet, check again later");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    topic3.setText("failed");
                }
            }
        });

        //fourth news card
        DocumentReference docRef4 = db.collection("Hostel")
                .document("J.J.Nortey")
                .collection("News")
                .document("news4");
        docRef4.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String topic = document.getString("Title");
                        String subtitle = document.getString("Subtitle");
                        topic4.setText(topic);
                        subtitle4.setText(subtitle);

                    } else {
                        Log.d(TAG, "No such document");
                        topic4.setText("Nothing yet, check again later");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    norteyTitle1.setText("failed");
                }
            }
        });

    }

    private void send() {
        pd = new ProgressDialog(NorteyDashboard.this);
        pd.setTitle("Sending your complaint");
        pd.setMessage("Please wait...");
        pd.show();

        String str_title = title.getText().toString();
        String str_message = message.getText().toString();

        if(TextUtils.isEmpty(str_title) || TextUtils.isEmpty(str_message))
        {
            pd.dismiss();
            Toast.makeText(NorteyDashboard.this, "All fields are required!", Toast.LENGTH_SHORT).show();
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
                                        .document("J.J.Nortey")
                                        .collection("Complaints")
                                        .document(rNum)
                                        .collection(ID)
                                        .document("First Complaint")
                                        .set(complaint)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(NorteyDashboard.this, "Successfully sent your complaint", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(NorteyDashboard.this, "Error sending your complaint, please try again", Toast.LENGTH_SHORT).show();
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
                                        .document("J.J.Nortey")
                                        .collection("Complaints")
                                        .document(rNum)
                                        .collection(ID)
                                        .document("Second Complaint")
                                        .set(complaint)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(NorteyDashboard.this, "Successfully sent your complaint", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(NorteyDashboard.this, "Error sending your complaint, please try again", Toast.LENGTH_SHORT).show();
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
                                        .document("J.J.Nortey")
                                        .collection("Complaints")
                                        .document(rNum)
                                        .collection(ID)
                                        .document("Third Complaint")
                                        .set(complaint)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(NorteyDashboard.this, "Successfully sent your complaint", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(NorteyDashboard.this, "Error sending your complaint, please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {
                                pd.dismiss();
                                Toast.makeText(NorteyDashboard.this, "You have 3 active complaints, please wait until they are resolved or ask a roommate to send the complaint.", Toast.LENGTH_LONG).show();
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
//                            Toast.makeText(NorteyDashboard.this, "Found document", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(NorteyDashboard.this, "No active complaints", Toast.LENGTH_SHORT).show();
                        }
                        if (!t2.equalsIgnoreCase("")) {

                            int intProgress2 = 0;
                            if (p2 != null) {
                                intProgress2 = Integer.parseInt(p2);
                                pb2.setProgress(intProgress2);
                            }
                            title2.setText(t2);
                            second_field.setVisibility(View.VISIBLE);
//                            Toast.makeText(NorteyDashboard.this, "Found 2nd document", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(NorteyDashboard.this, "1 active complaints", Toast.LENGTH_SHORT).show();
                        }

                        if (!t3.equalsIgnoreCase("")) {

                            int intProgress3 = 0;
                            if (p3 != null) {
                                intProgress3 = Integer.parseInt(p3);
                                pb3.setProgress(intProgress3);
                            }
                            title3.setText(t3);
                            third_field.setVisibility(View.VISIBLE);
//                            Toast.makeText(NorteyDashboard.this, "Found document", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(NorteyDashboard.this, "Two active complaints", Toast.LENGTH_SHORT).show();
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
}