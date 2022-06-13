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
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectStudentActivity extends AppCompatActivity {

    private Spinner spinner1, spinner2, spinner3;
    private Button nxtBtn;
    private List<String> departmentList = new ArrayList<>();
    private List<String> facultyList = new ArrayList<>();
    private String selectDepartment = new String();
    private String selectFac, selectMode;
    private ArrayAdapter<String> departmentAdapter, facAdapter, modeAdapter;
    private DatabaseReference departmentRef, facReference;
    private String[] mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);

        Toolbar toolbar = findViewById(R.id.toolbarView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner1 = findViewById(R.id.snpdepart);
        spinner2 = findViewById(R.id.snpdepart1);
        spinner3 = findViewById(R.id.snpMode);
        nxtBtn = findViewById(R.id.btnSelectStudent);
        mode = getResources().getStringArray(R.array.mode);

        departmentRef = FirebaseDatabase.getInstance().getReference().child("Department");
        departmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                departmentList.clear();
                departmentList.add("Select Department");
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            String key = snapshot1.getKey();
                            departmentList.add(key);
                        }
                    }

                    departmentAdapter = new ArrayAdapter<>(SelectStudentActivity.this, android.R.layout.simple_list_item_1, departmentList);
                    spinner1.setAdapter(departmentAdapter);
                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectDepartment = parent.getItemAtPosition(position).toString();
                            if (selectDepartment!=null){
                                facReference = departmentRef.child("Student");
                                facReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        facultyList.clear();
                                        facultyList.add("Select faculty");
                                        if (snapshot.exists()){
                                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                                if (snapshot1.hasChildren()){
                                                    String fac = snapshot1.getKey();
                                                    facultyList.add(fac);
                                                }
                                            }

                                            facAdapter = new ArrayAdapter<>(SelectStudentActivity.this, android.R.layout.simple_list_item_1, facultyList);
                                            spinner2.setAdapter(facAdapter);
                                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    selectFac = parent.getItemAtPosition(position).toString();
                                                    if (selectFac!=null){
                                                        modeAdapter = new ArrayAdapter<>(SelectStudentActivity.this, android.R.layout.simple_list_item_1, mode);
                                                        spinner3.setAdapter(modeAdapter);
                                                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                selectMode = parent.getItemAtPosition(position).toString();
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> parent) {

                                                            }
                                                        });
                                                    }
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
                            }
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

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDepartment!=null && !selectDepartment.equals("Select department") && selectFac!=null && !selectFac.equals("Select faculty") && selectMode!=null && !selectMode.equals("Select mode")){
                    Intent intent = new Intent(SelectStudentActivity.this, StudentListActivity.class);
                    intent.putExtra("DEPARTMENT", selectDepartment);
                    intent.putExtra("FACULTY", selectFac);
                    intent.putExtra("MODE", selectMode);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}