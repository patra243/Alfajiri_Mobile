package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class DepartmentActivity extends AppCompatActivity {

    private Spinner spnDept, spnDeptMode;
    private Button nxtBtn;
    private DatabaseReference reference;
    private List<String> departmentList = new ArrayList<>();
    private String selectDepartment, selectMode;
    private String[] modeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_activity);

        Toolbar toolbar = findViewById(R.id.toolDepart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        modeList = getResources().getStringArray(R.array.mode);

        spnDept = findViewById(R.id.spnDepartment);
        spnDeptMode = findViewById(R.id.spnDepartment1);
        nxtBtn = findViewById(R.id.btnNextDepart);

        reference = FirebaseDatabase.getInstance().getReference().child("Department");
        reference.addValueEventListener(new ValueEventListener() {
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

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(DepartmentActivity.this, android.R.layout.simple_list_item_1, departmentList);
                    spnDept.setAdapter(arrayAdapter);
                    spnDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectDepartment = parent.getItemAtPosition(position).toString();
                            if (!selectDepartment.isEmpty()){
                                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(DepartmentActivity.this, android.R.layout.simple_list_item_1, modeList);
                                spnDeptMode.setAdapter(arrayAdapter1);
                                spnDeptMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}