package com.example.vvusa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HostelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HostelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HostelFragment newInstance(String param1, String param2) {
        HostelFragment fragment = new HostelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hostel, container, false);

        Button paybtn4 = (Button) v.findViewById(R.id.paybtn4);
        Button paybtn2 = (Button) v.findViewById(R.id.paybtn2);
        Button bookroom = (Button) v.findViewById(R.id.bookroom);
        Button roomStatus = (Button) v.findViewById(R.id.roomStatus);

        bookroom.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Validating room status
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = auth.getCurrentUser();
            String userid = firebaseUser.getUid();
            DocumentReference userRef = db.collection("Users").document(userid);
            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            String isHostel = document.getString("Hostel");
                            if (!isHostel.equalsIgnoreCase("")) {
                                Toast.makeText(getActivity(), "You are already in a room, please wait until next semester to apply again", Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), bookRoom.class);
                                getActivity().startActivity(intent);
                            }

                        } else {
                            Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
            }
        });

        paybtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), hostelPay4.class);
                getActivity().startActivity(intent);
            }
        });
        paybtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), hostelPay2.class);
                getActivity().startActivity(intent);
            }
        });
        roomStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

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
                                String isHostel = document.getString("Hostel");
//                                String roomNumber = document.getString("Room Number");
                                if (isHostel.equalsIgnoreCase("J.J.Nortey")){
                                    intent.setClass(getActivity(), NorteyDashboard.class);
//                                    intent.putExtra("Room Number", roomNumber);
                                    getActivity().startActivity(intent);
                                }
                                else if (isHostel.equalsIgnoreCase("EGW")){
                                    intent.setClass(getActivity(), EGWAdmin.class);
                                    getActivity().startActivity(intent);
                                }
                                else if (isHostel.equalsIgnoreCase("Nagsda")){
                                    intent.setClass(getActivity(), NagsdaAdmin.class);
                                    getActivity().startActivity(intent);
                                }
                                else if (isHostel.equalsIgnoreCase("Bediako")){
                                    intent.setClass(getActivity(), BediakoAdmin.class);
                                    getActivity().startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getActivity(), "You are not registered into any hostel, please book a room first", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });



                //If hostelName = "" then this//
            }
        });

        return v;
    }
}