package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String gender;

    EditText SID, firstName, lastName, email, password, cpassword, major, phoneNumber;
    Button register;
    TextView txt_login;

    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SID = findViewById(R.id.SID);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        register = findViewById(R.id.register);
        txt_login = findViewById(R.id.txt_login);
        major = findViewById(R.id.major);
        phoneNumber = findViewById(R.id.phoneNumber);
//        gender = findViewById(R.id.gender);

        auth = FirebaseAuth.getInstance();

        Spinner spinner = findViewById(R.id.spinnerGen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_SID = SID.getText().toString();
                String str_firstName = firstName.getText().toString();
                String str_lastName = lastName.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_cpassword = cpassword.getText().toString();
                String str_major = major.getText().toString();
                String str_phoneNumber = phoneNumber.getText().toString();


                if(TextUtils.isEmpty(str_SID) || TextUtils.isEmpty(str_firstName) || TextUtils.isEmpty(str_lastName) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)
                        || TextUtils.isEmpty(str_cpassword) || TextUtils.isEmpty(str_major) || TextUtils.isEmpty(str_phoneNumber))
                {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                else if (!str_email.endsWith("vvu.edu.gh")){
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "You must have a vvu email", Toast.LENGTH_SHORT).show();
                }
                else if (!str_password.equals(str_cpassword))
                {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if (str_phoneNumber.length() < 10)
                {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please have a valid phone number", Toast.LENGTH_SHORT).show();
                }
                else if (str_password.length() < 6) {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Your password must have more than 6 characters.", Toast.LENGTH_SHORT).show();
                }
                else {
                    register(str_SID, str_lastName, str_firstName, str_email, str_password, str_major, str_phoneNumber, gender);
                }
            }
        });

    }

    private void register(final String SID,final String lastName, final String firstName, final String email, String password, final String major, final String phoneNumber, final String gender){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            String isFeeder = "False";
                            String position = "none";
                            String hostel = "";
                            String roomNumber = "";

//                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            Map<String, Object> User = new HashMap<>();
                            User.put("Student ID", SID);
                            User.put("UID", userid);
                            User.put("email", email);
                            User.put("Last name", lastName);
                            User.put("First name", firstName);
                            User.put("Phone Number", phoneNumber);
                            User.put("Major", major);
                            User.put("Gender", gender);
                            User.put("Password", password);
                            User.put("isFeeder", isFeeder);
                            User.put("Workstudy Position", position);
                            User.put("Hostel", hostel);
                            User.put("Room Number", roomNumber);
                            User.put("Title1", "");
                            User.put("Message1", "");
                            User.put("Progress1", "");
                            User.put("Title2", "");
                            User.put("Message2", "");
                            User.put("Progress2", "");
                            User.put("Title3", "");
                            User.put("Message3", "");
                            User.put("Progress3", "");


                            //Adding a new document with the userid as the document id
                            db.collection("Users").document(userid)
                                    .set(User)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Save successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Error saving your details", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "You can't be registered with this email or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();
        if (selectedItem.equalsIgnoreCase("Male")){
            gender = "Male";

        }
        else if (selectedItem.equalsIgnoreCase("Female")){
            gender = "Female";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
