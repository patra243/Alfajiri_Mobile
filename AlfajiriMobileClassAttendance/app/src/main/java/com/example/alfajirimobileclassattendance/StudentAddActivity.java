package com.example.alfajirimobileclassattendance;

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

import com.example.alfajirimobileclassattendance.constructor.SpinnerConstructor;
import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;
import com.example.alfajirimobileclassattendance.constructor.UnitConstructor;
import com.example.alfajirimobileclassattendance.helper.SpinnerHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class StudentAddActivity extends AppCompatActivity {

    private Toolbar studentToolbar;
    private Spinner spinner1, spinner2, spinnerUnit;
    private EditText name, email12, phone, password;
    private String[] department;
    private String[] semester;
    private String[] year;
    private ArrayList<String> unitName = new ArrayList<>();
    private ArrayList<String> unitCode = new ArrayList<>();
    private ArrayAdapter<String> departmentAd, semesterAd, yearAd, unitAd;
    private Button submitButton;
    private String selectDepartment, selectYear, selectSemester, intendDepartment,
                    intendFaculty, intendMode;
    private DatabaseReference stdReference, unitReference, attendanceReference;

    private final List<SpinnerHelper.SpinnerItem<SpinnerConstructor>> unitSpinnerItem = new ArrayList<>();
    private final List<SpinnerHelper.SpinnerCode<SpinnerConstructor>> unitSpinnerCode = new ArrayList<>();
    private final Set<SpinnerConstructor> unitSelectItem = new HashSet<>();
    private final Set<SpinnerConstructor> selectItem = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);

        studentToolbar = findViewById(R.id.addStdToolbar);
        setSupportActionBar(studentToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        intendDepartment = intent.getStringExtra("Department");
        intendFaculty = intent.getStringExtra("Faculty");
        intendMode = intent.getStringExtra("Mode");

        name = findViewById(R.id.txtStdName);
        email12 = findViewById(R.id.txtStdEmail);
        phone = findViewById(R.id.txtStdPhone);
        password = findViewById(R.id.txtStdPass);
        submitButton = findViewById(R.id.btnStdSubmit);
        spinner1 = findViewById(R.id.spn1);
        spinner2 = findViewById(R.id.spn2);
        spinnerUnit = findViewById(R.id.spnUnit);

        stdReference = FirebaseDatabase.getInstance().getReference().child("Department").child("Student").child("allStudent");
        department = getResources().getStringArray(R.array.department);
        semester = getResources().getStringArray(R.array.semester);
        year = getResources().getStringArray(R.array.year);

        unitReference = FirebaseDatabase.getInstance().getReference().child("Department").child("Unit");
        unitReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unitName.clear();
                unitCode.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            UnitConstructor unitConstructor = snapshot1.getValue(UnitConstructor.class);
                            String name = unitConstructor.getUnitName();
                            String code = unitConstructor.getUnitCode();
                            unitName.add(name);
                            unitCode.add(code);
                        }
                    }
                    List<SpinnerConstructor> allObjects = new ArrayList<>();
                    for (int i = 0; i < unitName.size(); i++){
                        SpinnerConstructor spinnerConstructor = new SpinnerConstructor();
                        spinnerConstructor.setUnitName(unitName.get(i));
                        spinnerConstructor.setUnitCode(unitCode.get(i));
                        allObjects.add(spinnerConstructor);
                    }
                    for (SpinnerConstructor constructor : allObjects){
                        unitSpinnerItem.add(new SpinnerHelper.SpinnerItem<>(constructor, constructor.getUnitName()));
                        unitSpinnerCode.add(new SpinnerHelper.SpinnerCode<>(constructor, constructor.getUnitCode()));
                    }
                    String headerText = "Select Units";
                    //SpinnerHelper spinnerHelper = new SpinnerHelper<>(StudentAddActivity.this, headerText ,unitSpinnerItem, unitSpinnerCode, unitSelectItem, selectItem);
                    SpinnerHelper spinnerHelper = new SpinnerHelper<>(StudentAddActivity.this, headerText, unitSpinnerItem, unitSpinnerCode, unitSelectItem, selectItem);
                    spinnerUnit.setAdapter(spinnerHelper);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        semesterAd = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, semester);
        yearAd = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, year);
        spinner1.setAdapter(yearAd);
        spinner2.setAdapter(semesterAd);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectSemester = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectYear = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
    }

    private void addStudent() {

        StringBuilder stringBuilder = new StringBuilder();
        for (SpinnerConstructor sc : unitSelectItem){
            stringBuilder.append(sc.getUnitName().concat(", "));
        }
        StringBuilder stringBuilder1 = new StringBuilder();
        for (SpinnerConstructor sc1 : selectItem){
            stringBuilder1.append(sc1.getUnitCode().concat(", "));
        }

        String name1 = name.getText().toString();
        String email1 = email12.getText().toString();
        String phone1 = phone.getText().toString();
        String ID = password.getText().toString();

        if (name1.isEmpty()){
            name.setError("Enter student name");
            name.requestFocus();
        }
        else if (email1.isEmpty()){
            email12.setError("Enter student email");
            email12.requestFocus();
        }
        else if (phone1.isEmpty()){
            phone.setError("Enter student phone");
            phone.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email12.setError("Enter valid email");
            email12.requestFocus();
        }
        else if (selectSemester.equals("Select Semester")){
            Toast.makeText(getApplicationContext(), "Select Semester", Toast.LENGTH_SHORT).show();
        }
        else if (selectYear.equals("Select Year")){
            Toast.makeText(getApplicationContext(), "Select Year", Toast.LENGTH_SHORT).show();
        }
        else if (ID.isEmpty()){
            password.setError("Enter password");
            password.requestFocus();
        }
        else if (stringBuilder.toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Select Units", Toast.LENGTH_SHORT).show();
        }
        else{
            String key = stdReference.push().getKey();
            StudentConstructor studentConstructor = new StudentConstructor(name1, ID, selectYear, selectSemester, intendDepartment, intendFaculty, email1, phone1, stringBuilder.toString(), stringBuilder1.toString(), intendMode, "123456");
            stdReference.child(key).setValue(studentConstructor).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Student added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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