package com.example.alfajirimobileclassattendance.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.ShowAttendanceActivity;
import com.example.alfajirimobileclassattendance.store.SaveUser;

import java.util.ArrayList;
import java.util.List;

public class StudentScreen extends Fragment {

    private TextView name, id, faculty,email, mode;
    private CardView cardViewAttendance;
    private String units;
    private List<String> unitList = new ArrayList<>();

    public StudentScreen(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.student_screen, viewGroup, false);
        name = view.findViewById(R.id.txtStName);
        id = view.findViewById(R.id.txtStID);
        faculty = view.findViewById(R.id.txtStFac);
        email = view.findViewById(R.id.txtStEmail);
        mode = view.findViewById(R.id.txtStMode);
        cardViewAttendance = view.findViewById(R.id.cardStVAttendance);

        SaveUser saveUser = new SaveUser();
        units = saveUser.getStudent(getContext()).getUnit();

        name.setText(saveUser.getStudent(getContext()).getName());
        id.setText(saveUser.getStudent(getContext()).getId());
        faculty.setText(saveUser.getStudent(getContext()).getFaculty());
        email.setText(saveUser.getStudent(getContext()).getEmail());
        mode.setText(saveUser.getStudent(getContext()).getMode());

        cardViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAttendanceActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
