package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class NorteyCard1 extends AppCompatActivity {

    //    TextView content2;
    org.sufficientlysecure.htmltextview.HtmlTextView content2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nortey_card1);

        content2 = findViewById(R.id.content2);



        //Pulling the second content from firebase into the third cardView
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef3 = db.collection("Hostel")
                .document("J.J.Nortey")
                .collection("News")
                .document("news1");
        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String content = document.getString("Content");
                        content2.setHtml(content);

                    } else {
                        Log.d(TAG, "No such document");
                        content2.setText("Document not found");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    content2.setText("failed");
                }
            }
        });
    }
}