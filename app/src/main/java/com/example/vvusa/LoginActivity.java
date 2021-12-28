package com.example.vvusa;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView txt_signup;

    private FirebaseAuth auth;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //This redirects if the user isnt null, to make sure the user is always signed in
        if (firebaseUser!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        txt_signup = findViewById(R.id.txt_signup);
        login = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();


        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }});



            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage("Please wait...");
                    pd.show();

                    String str_email = email.getText().toString();
                    String str_password = password.getText().toString();

                    if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "All fields required!", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.signInWithEmailAndPassword(str_email, str_password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            pd.dismiss();
                                            Intent intent;
                                            if (str_email.equalsIgnoreCase("kio@st.vvu.edu.gh")){
                                                intent = new Intent(LoginActivity.this, MainActivity.class);
//                                                intent.putExtra("userType", "Workstudy Admin");
                                                UserType type = new UserType();
                                                type.UserType = "Workstudy Admin";
                                                startActivity(intent);
                                            }
                                            else if (str_email.equalsIgnoreCase("kio.favour@st.vvu.edu.gh")){
                                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("userType", "Nortey Admin");
//                                                UserType type = new UserType();
//                                                type.setUserType("Nortey Admin");
////                                                type.UserType = "Nortey Admin";
                                                startActivity(intent);
                                            }
                                            else{
                                                intent = new Intent(LoginActivity.this, MainActivity.class);
//                                                intent.putExtra("userType", "Student");
                                                UserType type = new UserType();
                                                type.UserType = "Student";
                                                startActivity(intent);
                                            }
                                        }
                                        else{
                                            pd.dismiss();
                                            Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
        }
}