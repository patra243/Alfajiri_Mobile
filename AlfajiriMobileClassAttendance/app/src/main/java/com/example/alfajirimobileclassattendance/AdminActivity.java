package com.example.alfajirimobileclassattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.alfajirimobileclassattendance.fragment.AdminScreen;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.adminNavDrawer);
        navigationView = findViewById(R.id.adminNavView);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.adminFragment,
                    new AdminScreen()).commit();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.drawerLogout:
                    Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                        SaveUser saveUser = new SaveUser();
                        saveUser.Admin_saveData(AdminActivity.this, false);
                        startActivity(intent);
                        finish();
                }
                return true;
            }
        });
    }
}