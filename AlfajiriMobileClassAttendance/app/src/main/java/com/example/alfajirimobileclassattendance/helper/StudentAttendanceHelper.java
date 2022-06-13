package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.FacultyConstructor;

import java.util.List;

public class StudentAttendanceHelper extends RecyclerView.Adapter<StudentAttendanceHelper.FacultyListHolder> {

    private List<FacultyConstructor> lecList;
    private Context context;

    public StudentAttendanceHelper(Context context, List<FacultyConstructor> lecList){
        this.context = context;
        this.lecList = lecList;
    }

    public StudentAttendanceHelper(){

    }

    @NonNull
    @Override
    public StudentAttendanceHelper.FacultyListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.per_student_layout, parent, false);
        return new FacultyListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAttendanceHelper.FacultyListHolder holder, int position) {
        holder.studentName.setText(lecList.get(holder.getBindingAdapterPosition()).getName());
        holder.unitCode.setText(lecList.get(holder.getBindingAdapterPosition()).getDesignation());
    }

    @Override
    public int getItemCount() {
        return lecList.size();
    }

    class FacultyListHolder extends RecyclerView.ViewHolder {

        TextView studentName;
        TextView unitCode;

        public FacultyListHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.txtName);
            unitCode = itemView.findViewById(R.id.txtAddress1);
        }
    }
    public void updateCollection(List<FacultyConstructor> studentList){
        this.lecList = studentList;
        notifyDataSetChanged();
    }
}
