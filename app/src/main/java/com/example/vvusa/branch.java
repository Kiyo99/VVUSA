package com.example.vvusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class branch extends AppCompatActivity {


    String userType;

    @Override
    protected void onStart() {
        super.onStart();
//        Bundle users = getIntent().getExtras();
//        if (users != null){
//            userType = users.getString("userType");
//            final String decision = userType;
//            Toast.makeText(branch.this, userType, Toast.LENGTH_LONG).show();
//            if (userType.equalsIgnoreCase("Nortey Admin")){
//                Intent kio = new Intent(branch.this, NorteyAdmin.class);
//                startActivity(kio);
//                finish();
//            }
//            else if (userType.equalsIgnoreCase("Workstudy Admin")){
//                Intent kio = new Intent(branch.this, workstudyAdmin.class);
//                startActivity(kio);
//            }
//            else if (userType.equalsIgnoreCase("Bediako Admin")){
//                Intent kio = new Intent(branch.this, BediakoAdmin.class);
//                startActivity(kio);
//            }
//            else if (userType.equalsIgnoreCase("Student")){
//                Intent kio = new Intent(branch.this, MainActivity.class);
//                startActivity(kio);
//            }
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
//        UserType type = new UserType();
//        String userType = type.UserType;
//
//        if (userType.equalsIgnoreCase("Nortey Admin")){
//            Intent kio = new Intent(branch.this, NorteyAdmin.class);
//            startActivity(kio);
//            finish();
//        }
//        else if (userType.equalsIgnoreCase("Workstudy Admin")){
//            Intent kio = new Intent(branch.this, workstudyAdmin.class);
//            startActivity(kio);
//        }
//        else if (userType.equalsIgnoreCase("Bediako Admin")){
//            Intent kio = new Intent(branch.this, BediakoAdmin.class);
//            startActivity(kio);
//        }
//        else if (userType.equalsIgnoreCase("Student")){
//            Intent kio = new Intent(branch.this, MainActivity.class);
//            startActivity(kio);
//        }
        Bundle users = getIntent().getExtras();
        if (users != null) {
            userType = users.getString("userType");
            final String decision = userType;
            Toast.makeText(branch.this, decision, Toast.LENGTH_LONG).show();
            if (decision.equalsIgnoreCase("Nortey Admin")) {
                Intent kio = new Intent(branch.this, NorteyAdmin.class);
                startActivity(kio);
                finish();
            } else if (userType.equalsIgnoreCase("Workstudy Admin")) {
                Intent kio = new Intent(branch.this, workstudyAdmin.class);
                startActivity(kio);
            } else if (userType.equalsIgnoreCase("Bediako Admin")) {
                Intent kio = new Intent(branch.this, BediakoAdmin.class);
                startActivity(kio);
            } else if (userType.equalsIgnoreCase("Student")) {
                Intent kio = new Intent(branch.this, MainActivity.class);
                startActivity(kio);
            }
        }
    }
}