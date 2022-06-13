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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectUnitActivity extends AppCompatActivity {

    private Spinner selectUnit;
    private Button nxtBtn;
    private String intendID, intendMode;
    private List<UnitConstructor> unitList = new ArrayList<>();
    private List<String> unitNameList = new ArrayList<>();
    private DatabaseReference reference;
    private String department;
    private String selectSUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_unit);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intendID = new SaveUser().Faculty_EmailLoadData(getApplicationContext());
        selectUnit = findViewById(R.id.selectUnitSpn);
        nxtBtn = findViewById(R.id.btnSelectNext);

        intendMode = new SaveUser().Faculty_ModeLoadData(getApplicationContext());

        fetchUnit();
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectSUnit.equals("Select unit")){
                    Intent intent = new Intent(SelectUnitActivity.this, DatePickerActivity.class);
                    intent.putExtra("SU", selectSUnit);
                    startActivity(intent);
                }
            }
        });
    }

    private void fetchUnit() {

        reference = FirebaseDatabase.getInstance().getReference().child("Department");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unitList.clear();
                unitNameList.clear();
                unitNameList.add(0, "Select unit");
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        department = snapshot1.getKey();
                        Query departmentRef = reference.child("Unit").orderByChild("LecID").equalTo(intendID);
                        departmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    for (DataSnapshot snapshot2:snapshot.getChildren()){
                                        if (snapshot2.hasChildren()){
                                            UnitConstructor unitConstructor = snapshot2.getValue(UnitConstructor.class);
                                            String name = unitConstructor.getUnitName();
                                            unitNameList.add(name);
                                        }

                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectUnitActivity.this, android.R.layout.simple_list_item_1, unitNameList);
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