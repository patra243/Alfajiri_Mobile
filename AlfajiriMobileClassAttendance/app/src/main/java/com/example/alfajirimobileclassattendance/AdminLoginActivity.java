package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.alfajirimobileclassattendance.constructor.AdminConstructor;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText username, passwd;
    private Button blogin;
    private FirebaseAuth auth;
    private SaveUser saveUser = new SaveUser();
    private LinearLayout signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        username = findViewById(R.id.txtUsername);
        passwd = findViewById(R.id.txtPassword);
        blogin = findViewById(R.id.txtLogin);
        auth = FirebaseAuth.getInstance();

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        /*signUp = findViewById(R.id.signUpBtn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminRegisterActivity.class));
                finish();
            }
        });*/
    }

    private void login() {
        final String email = username.getText().toString().trim();
        final String password2 = passwd.getText().toString().trim();

        if (email.isEmpty()){
            username.setError("Enter Username");
            username.requestFocus();
        }

        else if (password2.isEmpty()){
            passwd.setError("Enter Password");
            passwd.requestFocus();
        }

        else {
            DatabaseReference adminLogin = FirebaseDatabase.getInstance().getReference().child("Admin");
            adminLogin.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            AdminConstructor adminConstructor = snapshot1.getValue(AdminConstructor.class);;
                            if (adminConstructor.getName().equals(email) && adminConstructor.getPassword().equals(password2)){
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminLoginActivity.this, AdminActivity.class));
                                saveUser.Admin_saveData(getApplicationContext(), true);
                                finish();
                            }
                            /*else {
                                Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            auth.signInWithEmailAndPassword(email, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminLoginActivity.this, AdminActivity.class));
                        saveUser.Admin_saveData(getApplicationContext(), true);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}