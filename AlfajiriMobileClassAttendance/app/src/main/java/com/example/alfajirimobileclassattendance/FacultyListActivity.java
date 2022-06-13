package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.alfajirimobileclassattendance.constructor.FacultyConstructor;
import com.example.alfajirimobileclassattendance.helper.FacultyListHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyListActivity extends AppCompatActivity {

    private Toolbar lecTool;
    private FloatingActionButton addLec;
    private RecyclerView recyclerLec;
    private String lMode, lFac;
    private DatabaseReference facListRef;
    private List<FacultyConstructor> facultyConstructorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);

        lecTool = findViewById(R.id.listTool);
        setSupportActionBar(lecTool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerLec = findViewById(R.id.lecRV);
        final Intent intent = getIntent();
        lMode = intent.getStringExtra("MODE");
        lFac = intent.getStringExtra("FACULTY");

        facListRef = FirebaseDatabase.getInstance().getReference().child("Department").child("Lecturer");
        facListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            FacultyConstructor facultyConstructor = snapshot1.getValue(FacultyConstructor.class);
                            facultyConstructorList.add(facultyConstructor);
                        }
                    }

                    FacultyListHelper facultyListHelper = new FacultyListHelper(FacultyListActivity.this, facultyConstructorList);
                    recyclerLec.setLayoutManager(new LinearLayoutManager(FacultyListActivity.this));
                    facultyListHelper.notifyDataSetChanged();
                    recyclerLec.setAdapter(facultyListHelper);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addLec = findViewById(R.id.addLec);
        addLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FacultyListActivity.this, FacultyAddActivity.class);
                intent1.putExtra("FACULTY", lFac);
                intent1.putExtra("MODE", lMode);
                startActivity(intent1);
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