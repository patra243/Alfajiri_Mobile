package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;
import com.example.alfajirimobileclassattendance.helper.TakeAttendanceRecyclerViewHelper;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceActivity extends AppCompatActivity {

    private String intendUnit, intendDate;
    private DatabaseReference studentRef, departmentRef, attendanceRef, presentRef, absentRef;
    private String department, faculty;
    private List<StudentConstructor> studentList = new ArrayList<>();
    private ListView listView;
    private RecyclerView recyclerView;
    private TakeAttendanceRecyclerViewHelper recyclerViewHelper;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        Toolbar toolbar = findViewById(R.id.toolbarTake);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        intendUnit = intent.getStringExtra("SU");
        intendDate = intent.getStringExtra("DATE");
        btnSubmit = findViewById(R.id.btnTakeSubmit);
        recyclerView = findViewById(R.id.recyclerTakeAttendance);
        studentRef = FirebaseDatabase.getInstance().getReference().child("Department");
        attendanceRef = FirebaseDatabase.getInstance().getReference().child("Department").child("Attendance");
        presentRef = attendanceRef.child("Present");
        absentRef = attendanceRef.child("Absent");
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        department = snapshot1.getKey();
                        departmentRef = studentRef.child("Student");
                        departmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                studentList.clear();
                                if (snapshot.exists()){
                                    for (DataSnapshot snapshot2:snapshot.getChildren()){
                                        for (DataSnapshot snapshot3:snapshot2.child("allStudent").getChildren()){
                                            if (snapshot3.hasChildren()){
                                                StudentConstructor student = snapshot3.getValue(StudentConstructor.class);
                                                if (student.getUnit().contains(intendDate)){
                                                    studentList.add(student);
                                                }
                                            }
                                        }
                                    }
                                    recyclerViewHelper = new TakeAttendanceRecyclerViewHelper(getApplicationContext(), studentList);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(TakeAttendanceActivity.this));
                                    recyclerViewHelper.notifyDataSetChanged();
                                    recyclerView.setAdapter(recyclerViewHelper);
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

        TakeAttendanceRecyclerViewHelper.presentList.clear();
        TakeAttendanceRecyclerViewHelper.absentList.clear();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(TakeAttendanceActivity.this).create();
                View view = LayoutInflater.from(TakeAttendanceActivity.this).inflate(R.layout.attendance_popup, null);
                TextView total, present, absent;
                Button btnConfirm, btnCancel;
                total = view.findViewById(R.id.totalStudent);
                present = view.findViewById(R.id.presentStudent);
                absent = view.findViewById(R.id.absentStudent);
                btnConfirm = view.findViewById(R.id.btnConfirm);
                btnCancel = view.findViewById(R.id.btnCancel);

                total.setText(Integer.toString(studentList.size()));
                present.setText(Integer.toString(TakeAttendanceRecyclerViewHelper.presentList.size()));
                absent.setText(Integer.toString(TakeAttendanceRecyclerViewHelper.absentList.size()));
                dialog.setCancelable(true);
                dialog.setView(view);
                
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String presentStudentID = "";
                        for (int i = 0; i < TakeAttendanceRecyclerViewHelper.presentList.size(); i++){
                            presentStudentID = TakeAttendanceRecyclerViewHelper.presentList.get(i);
                            presentRef.child(presentStudentID).setValue(presentStudentID);
                        }
                        String absentStudentID = "";
                        for (int i = 0; i < TakeAttendanceRecyclerViewHelper.absentList.size(); i++){
                            absentStudentID = TakeAttendanceRecyclerViewHelper.absentList.get(i);
                            absentRef.child(absentStudentID).setValue(absentStudentID);
                        }
                        TakeAttendanceRecyclerViewHelper.presentList.clear();
                        TakeAttendanceRecyclerViewHelper.absentList.clear();
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Attendance taken successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
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