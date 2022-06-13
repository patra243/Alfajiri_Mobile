package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyViewActivity extends AppCompatActivity {

    private Spinner spMode, spFac;
    private Button nextBtn;
    private DatabaseReference lecReference;
    private List<String> facultyList = new ArrayList<>();
    private String selectMode, selectFac;
    private String[] modeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view);

        Toolbar toolbar = findViewById(R.id.toolFac);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        modeList = getResources().getStringArray(R.array.mode);
        spMode = findViewById(R.id.spinnerMode);
        spFac = findViewById(R.id.spinnerFac);
        nextBtn = findViewById(R.id.btnNext);

        lecReference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        lecReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                facultyList.clear();
                facultyList.add("Select Faculty");
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            String key = snapshot1.getKey();
                            facultyList.add(key);
                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(FacultyViewActivity.this, android.R.layout.simple_list_item_1, facultyList);
                    spMode.setAdapter(arrayAdapter);
                    spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectMode = parent.getItemAtPosition(position).toString();
                            if (!selectMode.isEmpty()){
                                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(FacultyViewActivity.this, android.R.layout.simple_list_item_1, modeList);
                                spFac.setAdapter(arrayAdapter1);
                                spFac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectFac != null && !selectFac.equals("Select Faculty") && selectMode != null && !selectMode.equals("Select Mode")){
                    Intent intent = new Intent(FacultyViewActivity.this, FacultyListActivity.class);
                    intent.putExtra("FACULTY", selectFac);
                    intent.putExtra("MODE", selectMode);
                    startActivity(intent);
                }
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

}