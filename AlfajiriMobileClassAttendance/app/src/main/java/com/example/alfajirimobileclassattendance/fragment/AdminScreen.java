package com.example.alfajirimobileclassattendance.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.alfajirimobileclassattendance.FacultyListActivity;
import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.StudentListActivity;
import com.example.alfajirimobileclassattendance.UnitListActivity;

public class AdminScreen extends Fragment {

    private CardView cardStudent, cardLec, cardUnit;

    public AdminScreen(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_admin_screen, viewGroup, false);

        cardStudent = view.findViewById(R.id.cardStudent);
        cardLec = view.findViewById(R.id.cardLec);
        cardUnit = view.findViewById(R.id.cardUnit);

        cardStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentListActivity.class);
                startActivity(intent);
            }
        });

        cardLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FacultyListActivity.class);
                startActivity(intent);
            }
        });

        cardUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UnitListActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
