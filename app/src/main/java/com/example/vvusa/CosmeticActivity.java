package com.example.vvusa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.net.Uri;
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

public class CosmeticActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    myProductAdapter myProductAdapter;
    ArrayList<Products> productList;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading cosmetics, please wait...");
        pd.show();

        recyclerView = findViewById(R.id.Cosmetic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productList = new ArrayList<>();
        myProductAdapter = new myProductAdapter(this, productList);
        recyclerView.setAdapter(myProductAdapter);

        EventChangeListener();

    }

    private void EventChangeListener() {
        db.collection("Cosmetic").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null){
                    pd.dismiss();
                    Log.e("Error", error.getMessage());
                    Toast.makeText(CosmeticActivity.this, "Error loading sosmetics, please try again", Toast.LENGTH_SHORT).show();
                    return;
                }



                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){


                        productList.add(dc.getDocument().toObject(Products.class));

                    }

                    myProductAdapter.notifyDataSetChanged();
                    pd.dismiss();
                }
            }
        });
    }

}