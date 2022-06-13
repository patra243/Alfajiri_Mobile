package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.alfajirimobileclassattendance.fragment.FacultyScreen;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.android.material.navigation.NavigationView;

public class FacultyActivity extends AppCompatActivity {

    private NavigationView facNav;
    private DrawerLayout facDrawer;
    private Toolbar facTool;
    private String intentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        Intent intent1 = getIntent();
        intentID = intent1.getStringExtra("FACULTYID");

        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.facultyFrame, new FacultyScreen()).commit();
        }

        facTool = findViewById(R.id.facultyToolbar);
        setSupportActionBar(facTool);

        facNav = findViewById(R.id.facultyNav);
        facDrawer = findViewById(R.id.facultyDrawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, facDrawer, facTool, R.string.drawer_open, R.string.drawer_close);

        facDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        facNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawerLogout:
                        Intent intent = new Intent(FacultyActivity.this, MainActivity.class);
                        SaveUser saveUser = new SaveUser();
                        saveUser.Faculty_saveData(FacultyActivity.this, false);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                }
                return true;
            }
        });
    }
    public String getData(){
        return intentID;
    }
}