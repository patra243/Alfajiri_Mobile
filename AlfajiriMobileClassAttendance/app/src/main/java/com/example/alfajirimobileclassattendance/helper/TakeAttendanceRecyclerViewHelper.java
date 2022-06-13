package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceRecyclerViewHelper extends RecyclerView.Adapter<TakeAttendanceRecyclerViewHelper.TakeAttendanceViewHolder> {

    private List<StudentConstructor> studentList = new ArrayList<>();
    private Context context;
    public static List<String> presentList = new ArrayList<>();
    public static List<String> absentList = new ArrayList<>();

    public TakeAttendanceRecyclerViewHelper(Context context, List<StudentConstructor> studentList){
        this.context = context;
        this.studentList = studentList;
    }

    public TakeAttendanceRecyclerViewHelper(){

    }

    @NonNull
    @Override
    public TakeAttendanceRecyclerViewHelper.TakeAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.take_attendance_layout, parent, false);
        return new TakeAttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeAttendanceRecyclerViewHelper.TakeAttendanceViewHolder holder, int position) {
        holder.studentName.setText(studentList.get(holder.getBindingAdapterPosition()).getName());
        holder.studentID.setText(studentList.get(holder.getBindingAdapterPosition()).getId());
        holder.presentBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (!presentList.contains(studentList.get(holder.getBindingAdapterPosition()).getId())){
                        presentList.add(studentList.get(holder.getBindingAdapterPosition()).getId());
                    }
                }
                else{
                    presentList.remove(studentList.get(holder.getBindingAdapterPosition()).getId());
                }
            }
        });
        holder.absentBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    absentList.add(studentList.get(holder.getBindingAdapterPosition()).getId());
                }
                else{
                    absentList.remove(studentList.get(holder.getBindingAdapterPosition()).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TakeAttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView studentName;
        TextView studentID;
        RadioButton presentBtn;
        RadioButton absentBtn;

        public TakeAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.takeAttendanceStudent1);
            studentID = itemView.findViewById(R.id.takeAttendanceStudent2);
            presentBtn = itemView.findViewById(R.id.presentRadio);
            absentBtn = itemView.findViewById(R.id.absentRadio);
        }
    }

    public void updateCollection(List<StudentConstructor> studentList){
        this.studentList = studentList;
        notifyDataSetChanged();
    }
}
