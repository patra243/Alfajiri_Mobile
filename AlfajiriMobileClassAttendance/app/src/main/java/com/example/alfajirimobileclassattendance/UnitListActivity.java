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

import com.example.alfajirimobileclassattendance.constructor.UnitConstructor;
import com.example.alfajirimobileclassattendance.helper.UnitListHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UnitListActivity extends AppCompatActivity {

    private FloatingActionButton addUnitButton;
    private RecyclerView unitRv;
    private String intendDepartment, intendMode;
    private List<UnitConstructor> unitConstructorList = new ArrayList<>();
    private DatabaseReference unitReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);

        Toolbar unitTool = findViewById(R.id.unitToolbar);
        setSupportActionBar(unitTool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Intent intent = getIntent();
        intendDepartment = intent.getStringExtra("DEPARTMENT");
        intendMode = intent.getStringExtra("MODE");

        addUnitButton = findViewById(R.id.btnAddUnit);
        unitRv = findViewById(R.id.unitRecycler);

        unitReference = FirebaseDatabase.getInstance().getReference().child("Department").child("Unit");
        unitReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unitConstructorList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        if (snapshot1.hasChildren()){
                            UnitConstructor unitConstructor = snapshot1.getValue(UnitConstructor.class);
                            unitConstructorList.add(unitConstructor);
                        }
                    }
                    UnitListHelper unitListHelper = new UnitListHelper(UnitListActivity.this, unitConstructorList);
                    unitRv.setLayoutManager(new LinearLayoutManager(UnitListActivity.this));
                    unitListHelper.notifyDataSetChanged();
                    unitRv.setAdapter(unitListHelper);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UnitListActivity.this, UnitAddActivity.class);
                i.putExtra("DEPARTMENT", intendDepartment);
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