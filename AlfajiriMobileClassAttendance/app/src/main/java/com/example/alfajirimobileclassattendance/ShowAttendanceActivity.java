package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowAttendanceActivity extends AppCompatActivity {

    private Spinner showAttendance;
    private Button btnShow;
    private String units;
    private String unitCode;
    private List<String> unitList = new ArrayList<>();
    private List<String> unitCodeList = new ArrayList<>();
    private List<String> presentList = new ArrayList<>();
    private List<String> absentList = new ArrayList<>();
    private String selectUnit;
    private DatabaseReference presentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

        Toolbar toolbar = findViewById(R.id.toolbarShowAttendance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        showAttendance = findViewById(R.id.spnShowAttendance);
        btnShow = findViewById(R.id.btnShowAttendance);

        SaveUser saveUser = new SaveUser();
        units = saveUser.getStudent(getApplicationContext()).getUnit();
        unitCode = saveUser.getStudent(getApplicationContext()).getUnitCode();
        unitList = Arrays.asList(units.split(", "));
        unitCodeList = Arrays.asList(unitCode.split(", "));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ShowAttendanceActivity.this, android.R.layout.simple_list_item_1, unitList);
        showAttendance.setAdapter(arrayAdapter);
        showAttendance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectUnit!=null){
                    presentRef = FirebaseDatabase.getInstance().getReference().child("Department").child(new SaveUser().getStudent(getApplicationContext()).getDepartment())
                            .child("Attendance").child(new SaveUser().getStudent(getApplicationContext()).getMode()).child(selectUnit);
                    presentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            presentList.clear();
                            absentList.clear();
                            if (snapshot.exists()){
                                for (DataSnapshot snapshot1:snapshot.getChildren()){
                                    for (DataSnapshot snapshot2:snapshot1.child("Present").getChildren()){
                                        String key = snapshot2.getKey();
                                        if (key.equals(new SaveUser().getStudent(getApplicationContext()).getId())){
                                            presentList.add(key);
                                        }
                                    }

                                    for (DataSnapshot snapshot2:snapshot1.child("Absent").getChildren()){
                                        String key = snapshot2.getValue().toString();
                                        if (key.equals(new SaveUser().getStudent(getApplicationContext()).getId())){
                                            absentList.add(key);
                                        }
                                    }
                                }
                                final AlertDialog dialog = new AlertDialog.Builder(ShowAttendanceActivity.this).create();
                                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_attendance, null);
                                TextView presentV, absentV, nameV, idV;
                                Button button;

                                presentV = view.findViewById(R.id.presentVStudent);
                                absentV = view.findViewById(R.id.absentVStudent);
                                nameV = view.findViewById(R.id.txtViewName);
                                idV = view.findViewById(R.id.txtViewID);
                                button = view.findViewById(R.id.proceedBtn);
                                presentV.setText(Integer.toString(presentList.size()));
                                absentV.setText(Integer.toString(absentList.size()));

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.setView(view);
                                dialog.setCancelable(true);
                                dialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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