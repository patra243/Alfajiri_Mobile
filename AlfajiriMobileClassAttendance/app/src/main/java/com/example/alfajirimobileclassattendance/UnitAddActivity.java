package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alfajirimobileclassattendance.constructor.FacultyConstructor;
import com.example.alfajirimobileclassattendance.constructor.UnitConstructor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UnitAddActivity extends AppCompatActivity {

    private Spinner spnTitle, spnFaculty, spnLecturer;
    private EditText txtUnitCode;
    private DatabaseReference lecListRef, facultyListRef, unitReference, unitListRef, unitCodeRef;
    private List<String> lecList, facList, lecIDList, unitNameList, unitCodeList;
    private String intendDepartment, intendMode;
    private Button addUnit;
    private String selectFac, selectLec, selectLecID, selectUnitName;
    private String[] unitList, facultyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_add);

        Toolbar toolbar = findViewById(R.id.toolUnit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spnFaculty = findViewById(R.id.spinnerFaculty);
        spnLecturer = findViewById(R.id.spinnerLec);
        spnTitle = findViewById(R.id.spinnerTitle);
        txtUnitCode = findViewById(R.id.txtUnitCode);
        addUnit = findViewById(R.id.btnSubmitUnit);

        Intent intent = getIntent();
        intendDepartment = intent.getStringExtra("DEPARTMENT");
        intendMode = intent.getStringExtra("MODE");

        lecList = new ArrayList<>();
        facList = new ArrayList<>();
        lecIDList = new ArrayList<>();
        unitNameList = new ArrayList<>();
        unitCodeList = new ArrayList<>();

        unitList = getResources().getStringArray(R.array.Unit_name);
        facultyList = getResources().getStringArray(R.array.department);

        unitReference = FirebaseDatabase.getInstance().getReference();
        unitReference.child("Department").child("Unit");

        unitListRef = FirebaseDatabase.getInstance().getReference();
        unitListRef.child("Department").child("unitList");

        unitListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unitNameList.clear();
                unitNameList.add(0, "Select unit");
                unitCodeList.add(0, "Unit code");
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        String key = snapshot1.getKey();
                        String key1 = snapshot1.getValue().toString();
                        unitNameList.add(key);
                        unitCodeList.add(key1);
                    }
                    ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(UnitAddActivity.this, android.R.layout.simple_list_item_1, unitNameList);
                    spnTitle.setAdapter(myArrayAdapter);
                    spnTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectUnitName = parent.getItemAtPosition(position).toString();
                            txtUnitCode.setText(unitCodeList.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lecListRef = FirebaseDatabase.getInstance().getReference().child("Department").child("Lecturer");
        lecListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lecList.clear();
                lecIDList.clear();
                lecList.add(0, "Select lecturer");
                lecIDList.add(0, "id");

                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            FacultyConstructor constructor = snapshot1.getValue(FacultyConstructor.class);
                            String name = constructor.getName();
                            String id = constructor.getId();
                            lecList.add(name);
                            lecIDList.add(id);
                        }
                    }
                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(UnitAddActivity.this, android.R.layout.simple_list_item_1, lecList);
                    spnLecturer.setAdapter(arrayAdapter1);
                    spnLecturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectLec = parent.getItemAtPosition(position).toString();
                            selectLecID = lecIDList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        facultyListRef = FirebaseDatabase.getInstance().getReference().child("Department").child("Student");
        facultyListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                facList.clear();
                facList.add("Select faculty");
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            String key = snapshot1.getKey();
                            facList.add(key);
                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UnitAddActivity.this, android.R.layout.simple_list_item_1, facList);
                    spnFaculty.setAdapter(arrayAdapter);
                    spnFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectFac = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        addUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeacherUnit();
            }
        });
    }

    private void addTeacherUnit() {
        
        String unitCode = txtUnitCode.getText().toString();
        if (selectUnitName.equals("Select unit")){
            Toast.makeText(getApplicationContext(), "Select unit", Toast.LENGTH_SHORT).show();
        }
        else if (unitCode.isEmpty()){
            txtUnitCode.setError("Give unit code");
        }
        else if (selectFac.equals("Select faculty")){
            Toast.makeText(getApplicationContext(), "Select faculty", Toast.LENGTH_SHORT).show();
        }
        else if (selectLec.equals("Select lecturer")){
            Toast.makeText(getApplicationContext(), "Select lecturer", Toast.LENGTH_SHORT).show();
        }
        else if (selectLecID.equals("ID")){
            
        }
        else{
            unitReference = FirebaseDatabase.getInstance().getReference().child("Department").child("Unit");
            String key = unitReference.push().getKey();

            UnitConstructor unit = new UnitConstructor("", selectUnitName, unitCode, selectLec, selectLecID, selectFac);
            unitReference.child(key).setValue(unit).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Unit added successfully", Toast.LENGTH_SHORT).show();
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