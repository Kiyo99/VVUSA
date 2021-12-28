package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class bookRoom extends AppCompatActivity {
    CardView nortey, bediako, EGW, nagsda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);

         nortey = findViewById(R.id.nortey);
         bediako = findViewById(R.id.bediako);
         EGW = findViewById(R.id.EGW);
         nagsda = findViewById(R.id.nagsda);

        //Querying the database for the gender of the user
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
                        String gender = document.getString("Gender");
                        if (gender.equalsIgnoreCase("Male")){
                            EGW.setVisibility(View.GONE);
                            nagsda.setVisibility(View.GONE);
                        }
                        else if (gender.equalsIgnoreCase("Female")){
                            nortey.setVisibility(View.GONE);
                            bediako.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d(TAG, "No such document");

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());

                }
            }
        });

        nortey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bookRoom.this, NorteyActivity.class);
                startActivity(intent);

            }
        });
        bediako.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bookRoom.this, BediakoActivity.class);
                startActivity(intent);

            }
        });
        EGW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bookRoom.this, EgwActivity.class);
                startActivity(intent);

            }
        });
        nagsda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bookRoom.this, NagsdaActivity.class);
                startActivity(intent);

            }
        });
    }
}