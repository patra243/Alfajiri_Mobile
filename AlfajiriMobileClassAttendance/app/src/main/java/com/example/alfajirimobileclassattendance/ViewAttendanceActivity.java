package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;
import com.example.alfajirimobileclassattendance.helper.ViewAttendanceHelper;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String intendUnit, intendDate;
    private DatabaseReference studentRef, departmentRef, facultyRef, attendanceRef;
    private String department, faculty;
    private List<StudentConstructor> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        Toolbar toolbar = findViewById(R.id.toolbarViewAttendance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewAttendance);
        Intent intent = getIntent();
        intendUnit = intent.getStringExtra("SU");
        studentRef = FirebaseDatabase.getInstance().getReference().child("Department");
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        department = snapshot1.getKey();
                        departmentRef = studentRef.child(new SaveUser().getFaculty(ViewAttendanceActivity.this).getFaculty()).child("Student");
                        departmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                studentList.clear();
                                if (snapshot.exists()){
                                    for (DataSnapshot snapshot2:snapshot.getChildren()){
                                        for (DataSnapshot snapshot3:snapshot2.child("allStudent").child(new SaveUser().Faculty_ModeLoadData(getApplicationContext())).getChildren()){
                                            if (snapshot3.hasChildren()){
                                                StudentConstructor student = snapshot3.getValue(StudentConstructor.class);
                                                if (student.getUnit().contains(intendUnit)){
                                                    studentList.add(student);
                                                }
                                            }
                                        }
                                    }
                                    ViewAttendanceHelper viewAttendanceHelper = new ViewAttendanceHelper(ViewAttendanceActivity.this, studentList);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewAttendanceActivity.this));
                                    viewAttendanceHelper.notifyDataSetChanged();
                                    recyclerView.setAdapter(viewAttendanceHelper);
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