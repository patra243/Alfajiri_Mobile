package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alfajirimobileclassattendance.constructor.AdminConstructor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegisterActivity extends AppCompatActivity {

    private EditText adminName, adminEmail, adminPassword, adminConfirm;
    private Button adminBtn;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private DatabaseReference reference;
    private ProgressBar progressBar;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        adminName = findViewById(R.id.txtAName);
        adminEmail = findViewById(R.id.txtAEmail);
        adminPassword = findViewById(R.id.txtAPassword);
        adminConfirm = findViewById(R.id.txtACPassword);
        adminBtn = findViewById(R.id.btnAdminSign);
        progressBar = findViewById(R.id.progressAdmin);

        layout = findViewById(R.id.adminSign);
        firebaseAuth = FirebaseAuth.getInstance();

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    private void signUp() {
        final String name = adminName.getText().toString().trim();
        final String email = adminEmail.getText().toString().trim();
        String password = adminPassword.getText().toString().trim();
        String confirmPassword = adminConfirm.getText().toString().trim();

        if (name.isEmpty()){
            adminName.setError("Enter admin name");
            adminName.requestFocus();
        }
        else if (email.isEmpty()){
            adminEmail.setError("Enter admin email");
            adminEmail.requestFocus();
        }
        else if (password.isEmpty()){
            adminPassword.setError("Enter password");
            adminPassword.requestFocus();
        }
        else if (confirmPassword.isEmpty()){
            adminConfirm.setError("Confirm password");
            adminConfirm.requestFocus();
        }
        else if (confirmPassword.equals(password)){
            adminConfirm.setError("Password does not match");
            adminConfirm.requestFocus();
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                userID = firebaseAuth.getCurrentUser().getUid();
                                reference = FirebaseDatabase.getInstance().getReference().child("Admin").child(userID);
                                AdminConstructor adminConstructor = new AdminConstructor(
                                        name,
                                        email,
                                        "",
                                        password
                                );

                                reference.setValue(adminConstructor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            firebaseAuth.signOut();
                                            Intent intent = new Intent(AdminRegisterActivity.this, AdminLoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(getApplicationContext(), "Sign up successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            firebaseAuth.signOut();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                layout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            layout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}