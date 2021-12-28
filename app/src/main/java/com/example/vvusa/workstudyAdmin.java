package com.example.vvusa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class workstudyAdmin extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    myAdapter myAdapter;
    ArrayList<User> list;
    ProgressDialog pd;

    int backButtonCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstudy_admin);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Loading Applications, please wait...");
        pd.show();

        recyclerView = findViewById(R.id.Applicants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new myAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

    }

    @Override
    public void onBackPressed() {

            if(backButtonCount>= 1)
        {
            finishAffinity();
            finish();
        }else {
                Toast.makeText(this, "Press the back button once again to exit the application.", Toast.LENGTH_SHORT).show();
                backButtonCount++;
            }
        }

    private void EventChangeListener() {
        db.collection("Work-study Applications").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null){
                    pd.dismiss();
                    Log.e("Error", error.getMessage());
                    Toast.makeText(workstudyAdmin.this, "Error loading applications, please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){

                        list.add(dc.getDocument().toObject(User.class));

                    }

                    myAdapter.notifyDataSetChanged();
                    pd.dismiss();
                }
            }
        });
    }

}