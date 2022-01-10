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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

import static android.content.ContentValues.TAG;
import static android.content.Intent.getIntent;

public class HomeFragment extends Fragment {

//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private DocumentReference nameref = db.collection("Users").document("Last name");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
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
//        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_homefragment, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView welcome = (TextView) v.findViewById(R.id.welcome);
        TextView tempTV = (TextView) v.findViewById(R.id.temp);
        TextView topic2 = (TextView) v.findViewById(R.id.topic2);
        TextView subtitle2 = (TextView) v.findViewById(R.id.subtitle2);
        CardView first_card = (CardView) v.findViewById(R.id.first_card);
        CardView second_card = (CardView) v.findViewById(R.id.second_card);
        CardView third_card = (CardView) v.findViewById(R.id.third_card);
        CardView fourth_card = (CardView) v.findViewById(R.id.fourth_card);
//        CardView hostel = (CardView) v.findViewById(R.id.hostel);
//        CardView cafeteria = (CardView) v.findViewById(R.id.cafeteria);
//        CardView workstudy = (CardView) v.findViewById(R.id.workstudy);
//        CardView campusMarket = (CardView) v.findViewById(R.id.campusMarket);

        final String url = "https://api.openweathermap.org/data/2.5/weather?q=Oyibi&units=metric&appid=ab14f74264477285ae20a75ed821de46";
        DecimalFormat df = new DecimalFormat("#.##");

        //Going into the weatherAPI
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("onResponse: ", response);
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
//                    JSONArray jsonArray = jsonResponse.getJSONArray("Weather");
//                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp");
                    tempTV.setText(temp + "C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

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
//                        String name = document.getData().toString();
                        String name = document.getString("First name");
                        welcome.setText(String.format("Welcome, %s", name));
                    } else {
                        Log.d(TAG, "No such document");
                        welcome.setText("Document not found");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    welcome.setText("failed");
                }
            }
        });

        //Fetching the various components from firestore
        DocumentReference docRef2 = db.collection("news").document("News2");
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String topic = document.getString("topic");
                        String subtitle = document.getString("subtitle");
                        topic2.setText(topic);
                        subtitle2.setText(subtitle);

                    } else {
                        Log.d(TAG, "No such document");
                        subtitle2.setText("Document not found");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    subtitle2.setText("failed");
                }
            }
        });

        first_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), firstCard.class);
                getActivity().startActivity(intent);
            }
        });

        second_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), secondCard.class);
                getActivity().startActivity(intent);
            }
        });
        third_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), thirdCard.class);
                getActivity().startActivity(intent);
            }
        });
        fourth_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), branch.class);
                getActivity().startActivity(intent);
            }
        });



//        hostel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.body_container, new HostelFragment()).commit();
//            }
//        });
//        cafeteria.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.body_container, new CafFragment()).commit();
//            }
//        });
//        workstudy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.body_container, new WorkstudyFragment()).commit();
//            }
//        });
//        campusMarket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.body_container, new CampusMarketFragment()).commit();
//            }
//        });
        return v;
   }
}