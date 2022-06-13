package com.example.alfajirimobileclassattendance;

import static android.os.Build.ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alfajirimobileclassattendance.constructor.FacultyConstructor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacultyAddActivity extends AppCompatActivity {

    private Toolbar addFacTool;
    private EditText facName, facEmail, facPhone, facID, facAddress;
    private Spinner facSpinner;
    private String[] designList;
    private String selectDesign, userID;
    private Button addFacBtn;
    private String intentMode, intentFac;
    private DatabaseReference facReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_add);

        addFacTool = findViewById(R.id.toolAdd);
        setSupportActionBar(addFacTool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        intentFac = intent.getStringExtra("FACULTY");
        intentMode = intent.getStringExtra("MODE");

        mAuth = FirebaseAuth.getInstance();

        facName = findViewById(R.id.txtLName);
        facEmail = findViewById(R.id.txtLEmail);
        facAddress = findViewById(R.id.txtLAddress);
        facPhone = findViewById(R.id.txtLPhone);
        facID = findViewById(R.id.txtLID);
        addFacBtn = findViewById(R.id.btnLSubmit);
        facSpinner = findViewById(R.id.spnDes);

        designList = getResources().getStringArray(R.array.designation);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(FacultyAddActivity.this, android.R.layout.simple_list_item_1, designList);
        facSpinner.setAdapter(arrayAdapter);
        facSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectDesign = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addFacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFaculty();
            }
        });
    }

    private void addFaculty() {
        String name = facName.getText().toString();
        String email = facEmail.getText().toString();
        String address = facAddress.getText().toString();
        String phone = facPhone.getText().toString();
        String ID = facID.getText().toString();

        if (name.isEmpty()){
            facName.setError("Enter lecturer name");
            facName.requestFocus();
        }
        else if (email.isEmpty()){
            facEmail.setError("Enter lecturer email");
            facEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            facEmail.setError("Enter lecturer email");
            facEmail.requestFocus();
        }
        else if (address.isEmpty()){
            facAddress.setError("Enter lecturer address");
            facAddress.requestFocus();
        }
        else if (phone.isEmpty()){
            facPhone.setError("Enter lecturer phone number");
            facPhone.requestFocus();
        }
        else if (selectDesign.isEmpty() && selectDesign.equals("Select Designation")){
            Toast.makeText(getApplicationContext(), "Select Designation", Toast.LENGTH_SHORT).show();
        }
        else if (ID.isEmpty()){
            facID.setError("Enter lecturer ID");
            facID.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, ID).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                        facReference = FirebaseDatabase.getInstance().getReference().child("Department").child("Lecturer").child(userID);
                        FacultyConstructor constructor = new FacultyConstructor(ID, name, intentFac, selectDesign, phone, email, address, "123456", intentMode);

                        facReference.setValue(constructor).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    mAuth.signOut();
                                    Toast.makeText(getApplicationContext(), "Lecturer added successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    mAuth.signOut();
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
            });

            /*facReference = FirebaseDatabase.getInstance().getReference().child("Department").child("Lecturer");
            String key = facReference.push().getKey();

            FacultyConstructor facultyConstructor = new FacultyConstructor(ID, name, intentFac, selectDesign, phone, email, address, "123456", intentMode);
            facReference.child(key).setValue(facultyConstructor).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Lecturer added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}