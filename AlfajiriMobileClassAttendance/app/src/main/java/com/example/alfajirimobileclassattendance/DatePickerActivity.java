package com.example.alfajirimobileclassattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class DatePickerActivity extends AppCompatActivity {

    private String intendUnit;
    private Button nextBtn;
    private EditText dateField;
    private ImageButton dateBtn;
    private String date;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        Toolbar toolbar = findViewById(R.id.toolbarDate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dateField = findViewById(R.id.datePicker);
        dateBtn = findViewById(R.id.dateImg);
        nextBtn = findViewById(R.id.btnDate);
        intendUnit = getIntent().getStringExtra("SU");

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(DatePickerActivity.this);
                int currentDay = datePicker.getDayOfMonth();
                int currentMonth = datePicker.getMonth();
                int currentYear = datePicker.getYear();

                datePickerDialog = new DatePickerDialog(DatePickerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(dayOfMonth+"-");
                        stringBuilder.append((month+1)+"-");
                        stringBuilder.append(year);
                        dateField.setText(stringBuilder.toString());
                    }
                }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = dateField.getText().toString();
                if (!date.isEmpty()){
                    Intent intent = new Intent(DatePickerActivity.this, TakeAttendanceActivity.class);
                    intent.putExtra("SU", intendUnit);
                    intent.putExtra("DATE", date);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Pick a date", Toast.LENGTH_SHORT).show();
                }
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