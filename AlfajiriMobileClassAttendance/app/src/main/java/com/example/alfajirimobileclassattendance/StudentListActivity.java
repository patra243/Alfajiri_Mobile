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

import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;
import com.example.alfajirimobileclassattendance.fragment.StudentScreen;
import com.example.alfajirimobileclassattendance.helper.StudentHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    private Toolbar stdToolbar;
    private FloatingActionButton addStudent;
    private DatabaseReference stdReference;
    private List<StudentConstructor> studentList = new ArrayList<>();
    private RecyclerView studentRecycler;
    private String intendDepartment;
    private String intendFaculty;
    private String intendMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        stdToolbar = findViewById(R.id.stdBarTool);
        addStudent = findViewById(R.id.studentAdd);
        studentRecycler = findViewById(R.id.stdRecycler);

        Intent intent = getIntent();
        intendDepartment = intent.getStringExtra("DEPARTMENT");
        intendFaculty = intent.getStringExtra("FACULTY");
        intendMode = intent.getStringExtra("MODE");

        setSupportActionBar(stdToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        stdReference = FirebaseDatabase.getInstance().getReference();
        stdReference.child("Department").child("Student").child("allstudent")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            StudentConstructor student = snapshot1.getValue(StudentConstructor.class);
                            studentList.add(student);
                        }

                        StudentHelper studentHelper = new StudentHelper(StudentListActivity.this, studentList);
                        studentRecycler.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
                        studentHelper.notifyDataSetChanged();
                        studentRecycler.setAdapter(studentHelper);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentListActivity.this, StudentAddActivity.class);
                i.putExtra("DEPARTMENT", intendDepartment);
                i.putExtra("FACULTY", intendFaculty);
                i.putExtra("MODE", intendMode);
                startActivity(i);
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