package com.example.alfajirimobileclassattendance;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alfajirimobileclassattendance.store.SaveUser;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (new SaveUser().wlcmLoad(getApplicationContext())){
            if (new SaveUser().Admin_loadData(getApplicationContext())){
                startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                finish();
            }
            else if (new SaveUser().Faculty_loadData(getApplicationContext())){
                startActivity(new Intent(SplashActivity.this, FacultyActivity.class));
                finish();
            }
            else if (new SaveUser().Student_loadData(getApplicationContext())){
                startActivity(new Intent(SplashActivity.this, StudentActivity.class));
                finish();
            }
            else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
        else {
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}