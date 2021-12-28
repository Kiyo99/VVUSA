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

public class RepairActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    myServiceAdapter myServiceAdapter;
    ArrayList<Service> serviceList;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading Gadget repair posts, please wait...");
        pd.show();

        recyclerView = findViewById(R.id.Repair);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        serviceList = new ArrayList<>();
        myServiceAdapter = new myServiceAdapter(this, serviceList);
        recyclerView.setAdapter(myServiceAdapter);

        EventChangeListener();

    }

    private void EventChangeListener() {
        db.collection("Gadget Repairs").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null){
                    pd.dismiss();
                    Log.e("Error", error.getMessage());
                    Toast.makeText(RepairActivity.this, "Error loading gadget repair posts, please try again", Toast.LENGTH_SHORT).show();
                    return;
                }



                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){


                        serviceList.add(dc.getDocument().toObject(Service.class));

                    }

                    myServiceAdapter.notifyDataSetChanged();
                    pd.dismiss();
                }
            }
        });
    }

}