package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceHelper extends BaseAdapter {

    Context context;
    List<StudentConstructor> studentList = new ArrayList<>();
    LayoutInflater inflater;
    public static List<String> presentList = new ArrayList<>();
    public static List<String> absentList = new ArrayList<>();

    public TakeAttendanceHelper(Context context, List<StudentConstructor> studentList){
        this.context = context;
        this.studentList = studentList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.take_attendance_layout, null);
        TextView studentName = convertView.findViewById(R.id.takeAttendanceStudent1);
        TextView studentID = convertView.findViewById(R.id.takeAttendanceStudent2);
        final RadioButton present = convertView.findViewById(R.id.presentRadio);
        final RadioButton absent = convertView.findViewById(R.id.absentRadio);

        studentName.setText(studentList.get(position).getName());
        studentID.setText(studentList.get(position).getId());

        present.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (!presentList.contains(studentList.get(position).getId())){
                        presentList.add(studentList.get(position).getId());
                    }
                }
                else {
                    absentList.add(studentList.get(position).getId());
                }
            }
        });
        return convertView;
    }
}
