package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText studentUsername, studentPassword;
    private Button studentLogin;
    private DatabaseReference studentReference;
    private List<StudentConstructor> studentConstructors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        studentUsername = findViewById(R.id.txtStUsername);
        studentPassword = findViewById(R.id.txtStPassword);
        studentLogin = findViewById(R.id.btnStLogin);
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginStudent();
            }
        });
    }

    private void loginStudent() {
        final String username = studentUsername.getText().toString();
        final String password = studentPassword.getText().toString();
        if (username.isEmpty()){
            studentUsername.setError("Enter student username");
            studentUsername.requestFocus();
        }
        else if (password.isEmpty()){
            studentPassword.setError("Enter student password");
            studentPassword.requestFocus();
        }
        else{
            studentReference = FirebaseDatabase.getInstance().getReference().child("Faculty");
            studentReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    studentConstructors.clear();
                    if (snapshot.exists()){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            String fac = snapshot1.getKey();
                            DatabaseReference facReference = studentReference.child(fac).child("Student");
                            facReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot2:snapshot.getChildren()){
                                        for (DataSnapshot snapshot3:snapshot2.getChildren()){
                                            for (DataSnapshot snapshot4:snapshot3.getChildren()){
                                                for (DataSnapshot snapshot5:snapshot4.getChildren()){
                                                    if (snapshot5.hasChildren()){
                                                        StudentConstructor student = snapshot5.getValue(StudentConstructor.class);
                                                        if (student.getEmail().equals(username)){
                                                            new SaveUser().saveStudent(getApplicationContext(), student);
                                                            studentConstructors.add(student);
                                                            if (studentConstructors.get(0).getEmail().equals(username) && studentConstructors.get(0).getPassword().equals(password)){
                                                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                                                new SaveUser().Student_saveData(getApplicationContext(), true);
                                                                Intent intent = new Intent(StudentLoginActivity.this, StudentActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            else{
                                                                Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
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