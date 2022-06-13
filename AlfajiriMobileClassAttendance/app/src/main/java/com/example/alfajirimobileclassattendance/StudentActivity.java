package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.alfajirimobileclassattendance.fragment.StudentScreen;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.android.material.navigation.NavigationView;

public class StudentActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout layout;
    private Toolbar toolbar;
    private String intentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.studentFrame, new StudentScreen()).commit();
        }
        toolbar = findViewById(R.id.studentToolbar);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.studentDrawer);
        navigationView = findViewById(R.id.studentNav);

        ActionBarDrawerToggle actionBar = new ActionBarDrawerToggle(this, layout, toolbar, R.string.drawer_open, R.string.drawer_close);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawerLogout:
                        new SaveUser().Student_saveData(getApplicationContext(), false);
                        Intent intent = new Intent(StudentActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                }
                return true;
            }
        });
    }
}