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

import com.example.alfajirimobileclassattendance.constructor.UnitConstructor;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectUnitViewAttendanceActivity extends AppCompatActivity {

    private Spinner selectUnit;
    private Button nextBtn;
    private String intendID, intendMode;
    private List<UnitConstructor> unitList = new ArrayList<>();
    private List<String> unitNameList = new ArrayList<>();
    private DatabaseReference unitReference;
    private String department;
    private String selectSUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_unit_view_attendance);

        Toolbar toolbar = findViewById(R.id.selectUnitToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intendID = new SaveUser().Faculty_EmailLoadData(getApplicationContext());
        selectUnit = findViewById(R.id.selectUnitViewSpn);
        nextBtn = findViewById(R.id.btnNextView);
        intendMode = new SaveUser().Faculty_ModeLoadData(getApplicationContext());

        fetchUnit();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectSUnit.equals("Select unit")){
                    new SaveUser().Faculty_CourseSaveData(getApplicationContext(), selectSUnit);
                    Intent intent = new Intent(SelectUnitViewAttendanceActivity.this, ViewAttendanceActivity.class);
                    intent.putExtra("SU", selectSUnit);
                    startActivity(intent);
                }
            }
        });
    }

    private void fetchUnit() {

        DatabaseReference departmentRef = FirebaseDatabase.getInstance().getReference().child("Department").child(new SaveUser().getFaculty(SelectUnitViewAttendanceActivity.this).getFaculty()).child("Unit").child(intendMode);
        departmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unitList.clear();
                unitNameList.clear();
                unitNameList.add(0, "Select unit");
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            UnitConstructor unit = snapshot1.getValue(UnitConstructor.class);
                            if (unit.getLecturerID().equals(intendID)){
                                String name = unit.getUnitName();
                                unitNameList.add(name);
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectUnitViewAttendanceActivity.this, android.R.layout.simple_list_item_1, unitNameList);
                        selectUnit.setAdapter(adapter);
                        selectUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selectSUnit = parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}