package com.example.alfajirimobileclassattendance.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.TakeAttendanceActivity;
import com.example.alfajirimobileclassattendance.ViewAttendanceActivity;
import com.example.alfajirimobileclassattendance.constructor.FacultyConstructor;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyScreen extends Fragment {

    private List<FacultyConstructor> facultyConstructors = new ArrayList<>();
    private DatabaseReference reference;
    private String faculty;
    private String intendID, intendMode;
    private TextView name, designation, id, faculty1;
    private CardView cardViewAttendance, cardTakeAttendance;

    public FacultyScreen(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.faculty_screen, viewGroup, false);
        SaveUser saveUser = new SaveUser();
        intendID = saveUser.Faculty_EmailLoadData(getContext());

        name = view.findViewById(R.id.txtTtName);
        designation = view.findViewById(R.id.txtTtDesign);
        id = view.findViewById(R.id.txtTtID);
        faculty1 = view.findViewById(R.id.txtTtFac);
        cardViewAttendance = view.findViewById(R.id.cardVAttendance);
        cardTakeAttendance = view.findViewById(R.id.cardTAttendance);

        name.setText(saveUser.getFaculty(getContext()).getName());
        designation.setText(saveUser.getFaculty(getContext()).getDesignation());
        id.setText(saveUser.getFaculty(getContext()).getId());
        faculty1.setText(saveUser.getFaculty(getContext()).getFaculty());

        cardTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TakeAttendanceActivity.class);
                intent.putExtra("FID", intendID);
                startActivity(intent);
            }
        });

        cardViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAttendanceActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void fetchFaculty(){
        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                facultyConstructors.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        faculty = snapshot1.getKey();
                        DatabaseReference databaseReference = reference.child(faculty).child("Faculty");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    for (DataSnapshot snapshot2:snapshot.getChildren()){
                                        for (DataSnapshot snapshot3:snapshot2.getChildren()){
                                            if (snapshot3.hasChildren()){
                                                FacultyConstructor facultyConstructor = snapshot3.getValue(FacultyConstructor.class);
                                                if (facultyConstructor.getId().equals(intendID)){
                                                    String name1 = facultyConstructor.getName();
                                                    String id1 = facultyConstructor.getId();
                                                    String design1 = facultyConstructor.getDesignation();
                                                    String faculty2 = facultyConstructor.getFaculty();
                                                    intendMode = facultyConstructor.getMode();
                                                    name.setText(name1);
                                                    id.setText(id1);
                                                    designation.setText(design1);
                                                    faculty1.setText(faculty2);
                                                }
                                            }
                                        }
                                    }
                                    cardTakeAttendance.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (!intendMode.equals("")){
                                                Intent intent = new Intent(getContext(), TakeAttendanceActivity.class);
                                                intent.putExtra("FID", intendID);
                                                intent.putExtra("MODE", intendMode);
                                                startActivity(intent);
                                            }
                                        }
                                    });
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
    }
}
