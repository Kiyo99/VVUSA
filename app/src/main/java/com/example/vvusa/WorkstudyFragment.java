package com.example.vvusa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkstudyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkstudyFragment extends Fragment {

    String Applicant, lastName, firstName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkstudyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkstudyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkstudyFragment newInstance(String param1, String param2) {
        WorkstudyFragment fragment = new WorkstudyFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_workstudy, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userid = firebaseUser.getUid();
        DocumentReference docRef = db.collection("Users").document(userid);



        TextView applyLab = (TextView) v.findViewById(R.id.applyLab);
        TextView applySec = (TextView) v.findViewById(R.id.applySec);
        TextView applySweeper = (TextView) v.findViewById(R.id.applySweeper);
        TextView applyCaf = (TextView) v.findViewById(R.id.applyCaf);
        TextView applyWeed = (TextView) v.findViewById(R.id.applyWeeding);

        applyLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                lastName = document.getString("Last name");
                                firstName = document.getString("First name");
                                String ID = document.getString("Student ID");

                                Applicant = lastName + " " + firstName;
                                Map<String, Object> applicant = new HashMap<>();
                                applicant.put("Applicant", Applicant);
                                applicant.put("ID", ID);
                                applicant.put("Position", "Lab Assistant");

                                db.collection("Work-study Applications").document(ID)
                                        .set(applicant)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Successfully saved your application", Toast.LENGTH_SHORT).show();
                                                applyLab.setText("Applied!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Error processing your application", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No such document");


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });
            }
        });
        applySec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                lastName = document.getString("Last name");
                                firstName = document.getString("First name");
                                String ID = document.getString("Student ID");

                                Applicant = lastName + " " + firstName;
                                Map<String, Object> applicant = new HashMap<>();
                                applicant.put("Applicant", Applicant);
                                applicant.put("ID", ID);
                                applicant.put("Position", "Secretary");

                                db.collection("Work-study Applications").document(ID)
                                        .set(applicant)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Successfully saved your application", Toast.LENGTH_SHORT).show();
                                                applySec.setText("Applied!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Error processing your application", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No such document");


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });
            }
        });
        applySweeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                lastName = document.getString("Last name");
                                firstName = document.getString("First name");
                                String ID = document.getString("Student ID");

                                Applicant = lastName + " " + firstName;
                                Map<String, Object> applicant = new HashMap<>();
                                applicant.put("Applicant", Applicant);
                                applicant.put("ID", ID);
                                applicant.put("Position", "Sweeper");

                                db.collection("Work-study Applications").document(ID)
                                        .set(applicant)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Successfully saved your application", Toast.LENGTH_SHORT).show();
                                                applySweeper.setText("Applied!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Error processing your application", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No such document");


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });
            }
        });

        applyWeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                lastName = document.getString("Last name");
                                firstName = document.getString("First name");
                                String ID = document.getString("Student ID");

                                Applicant = lastName + " " + firstName;
                                Map<String, Object> applicant = new HashMap<>();
                                applicant.put("Applicant", Applicant);
                                applicant.put("ID", ID);
                                applicant.put("Position", "Weeder");

                                db.collection("Work-study Applications").document(ID)
                                        .set(applicant)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Successfully saved your application", Toast.LENGTH_SHORT).show();
                                                applyWeed.setText("Applied!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Error processing your application", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No such document");


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });
            }
        });
        applyCaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                lastName = document.getString("Last name");
                                firstName = document.getString("First name");
                                String ID = document.getString("Student ID");

                                Applicant = lastName + " " + firstName;
                                Map<String, Object> applicant = new HashMap<>();
                                applicant.put("Applicant", Applicant);
                                applicant.put("ID", ID);
                                applicant.put("Position", "Cafeteria Assistant");

                                db.collection("Work-study Applications").document(ID)
                                        .set(applicant)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Successfully saved your application", Toast.LENGTH_SHORT).show();
                                                applyCaf.setText("Applied!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Error processing your application", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No such document");


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });
            }
        });

        return v;
    }
}