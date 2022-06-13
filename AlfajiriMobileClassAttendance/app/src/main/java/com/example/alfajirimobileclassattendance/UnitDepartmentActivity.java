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

public class UnitDepartmentActivity extends AppCompatActivity {

    private Spinner deptSpn, modeSpn;
    private Button nxtBtn;
    private DatabaseReference deptReference;
    private List<String> deptList = new ArrayList<>();
    private String selectDept, selectMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_department);

        Toolbar toolbar = findViewById(R.id.toolUnitDep);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deptSpn = findViewById(R.id.deptSpinner);
        modeSpn = findViewById(R.id.modeSpinner);
        nxtBtn = findViewById(R.id.icNextBtn);

        deptReference = FirebaseDatabase.getInstance().getReference().child("Department");
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deptList.clear();
                deptList.add("Select Department");
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            String key = snapshot1.getKey();
                            deptList.add(key);
                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UnitDepartmentActivity.this, android.R.layout.simple_list_item_1, deptList);
                    deptSpn.setAdapter(arrayAdapter);
                    deptSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectDept = parent.getItemAtPosition(position).toString();

                            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(UnitDepartmentActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mode));
                            modeSpn.setAdapter(arrayAdapter1);
                            modeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectMode = parent.getItemAtPosition(position).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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
                if (selectDept != null && !selectDept.equals("Select Department") && selectMode != null && !selectMode.equals("Select Mode")){
                    Intent intent = new Intent(UnitDepartmentActivity.this, UnitListActivity.class);
                    intent.putExtra("DEPARTMENT", selectDept);
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