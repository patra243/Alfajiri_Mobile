package com.example.alfajirimobileclassattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cardA = findViewById(R.id.cardAdmin);
        cardA.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
            finish();
        });

        CardView cardlec = findViewById(R.id.cardLec1);
        cardlec.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FacultyLoginActivity.class);
            startActivity(intent);
            finish();
        });

        CardView cardStudent = findViewById(R.id.cardStd);
        cardStudent.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}