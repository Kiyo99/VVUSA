package com.example.vvusa;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CafFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CafFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CafFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CafFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CafFragment newInstance(String param1, String param2) {
        CafFragment fragment = new CafFragment();
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
        View v = inflater.inflate(R.layout.fragment_caf, container, false);

        Button Breakfast = (Button) v.findViewById(R.id.bf);
        Button Lunch = (Button) v.findViewById(R.id.lh);
        Button Supper = (Button) v.findViewById(R.id.sp);
        TextView Time = (TextView) v.findViewById(R.id.logoN);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
        String strDate = simpleDateFormat.format(calendar.getTime());
        Time.setText(strDate);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        Breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeOfDay >= 0 && timeOfDay < 12) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), cafQR.class);
                    getActivity().startActivity(intent);
                } else {
                    Toast.makeText(getActivity(),
                            "It isn't time for breakfast yet", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(v.findViewById(R.id.caffrag), "It is not time for breakfast yet", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timeOfDay >= 12 && timeOfDay < 17){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), cafQR.class);
                    getActivity().startActivity(intent);
                } else {
                    Toast.makeText(getActivity(),
                            "It is not time for lunch yet", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(v.findViewById(R.id.caffrag), "It is not time for lunch yet", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Supper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timeOfDay >= 17 && timeOfDay < 24){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), cafQR.class);
                    getActivity().startActivity(intent);
                } else {
                    Toast.makeText(getActivity(),
                            "It is not time for supper yet", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(v.findViewById(R.id.caffrag), "It is not time for supper yet", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return v;

    }
}