package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfajirimobileclassattendance.constructor.FacultyConstructor;
import com.example.alfajirimobileclassattendance.store.SaveUser;
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

import java.util.ArrayList;
import java.util.List;

public class FacultyLoginActivity extends AppCompatActivity {

    public static Activity fa;
    private EditText lecUser, lecPass;
    private List<String> lecEmailList = new ArrayList<>();
    private List<String> lecPassList = new ArrayList<>();
    private Button lecLog;
    private FirebaseAuth fAuth;
    private DatabaseReference facReference, facultyRef;
    private String fac, mode, depart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa = this;
        setContentView(R.layout.activity_faculty_login);

        lecUser = findViewById(R.id.txtUsername);
        lecPass = findViewById(R.id.txtPassword);
        lecLog = findViewById(R.id.btnLogin);

        fAuth = FirebaseAuth.getInstance();

        lecLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facLogin();
            }
        });
    }

    private void facLogin() {
        final String lecturerEmail = lecUser.getText().toString();
        final String lecturerPass = lecPass.getText().toString();

        if (lecturerEmail.isEmpty()){
            lecUser.setError("Enter Lecturer Email");
            lecUser.requestFocus();
        }
        else if (lecturerPass.isEmpty()){
            lecPass.setError("Enter Password");
            lecPass.requestFocus();
        }
        else {
            facReference = FirebaseDatabase.getInstance().getReference().child("Department").child("Lecturer");
            facReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    lecEmailList.clear();
                    lecPassList.clear();

                    if (snapshot.exists()){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            fac = snapshot1.getKey();
                            facultyRef = facReference.child("Lecturer");
                            facultyRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        for (DataSnapshot snapshot2:snapshot.getChildren()){
                                            for (DataSnapshot snapshot3:snapshot2.getChildren()){
                                                if (snapshot3.hasChildren()){
                                                    FacultyConstructor facultyConstructor = snapshot3.getValue(FacultyConstructor.class);
                                                    if (facultyConstructor.getEmail().equals(lecturerEmail)){
                                                        String email = facultyConstructor.getEmail();
                                                        String password = facultyConstructor.getPassword();
                                                        lecEmailList.add(email);
                                                        lecPassList.add(password);
                                                        mode = facultyConstructor.getMode();
                                                        depart = facultyConstructor.getFaculty();
                                                        SaveUser saveUser = new SaveUser();
                                                        saveUser.saveFaculty(FacultyLoginActivity.this, facultyConstructor);
                                                    }
                                                }
                                            }
                                        }
                                        if (lecEmailList.contains(lecturerEmail) && lecPassList.contains(lecturerPass)){
                                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(FacultyLoginActivity.this, FacultyActivity.class);
                                            intent.putExtra("LECTUREREMAIL", lecturerEmail);

                                            SaveUser saveUser = new SaveUser();
                                            saveUser.Faculty_EmailSaveData(getApplicationContext(), lecturerEmail);
                                            saveUser.Faculty_ModeSaveData(getApplicationContext(), mode);
                                            saveUser.Faculty_saveData(FacultyLoginActivity.this, true);
                                            saveUser.Faculty_DeptSaveData(getApplicationContext(), depart);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}