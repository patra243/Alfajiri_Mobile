package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;

import java.util.List;

public class StudentHelper extends RecyclerView.Adapter<StudentHelper.StudentViewHolder> {

    private List<StudentConstructor> studentConstructors;
    private Context context;

    public StudentHelper(Context context, List<StudentConstructor> studentConstructors){
        this.context = context;
        this.studentConstructors = studentConstructors;
    }

    public StudentHelper(){

    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.per_student_layout, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.studentName.setText(studentConstructors.get(holder.getBindingAdapterPosition()).getName());
        holder.unitCode.setText(studentConstructors.get(holder.getBindingAdapterPosition()).getUnitCode());
        holder.studentID.setText(studentConstructors.get(holder.getBindingAdapterPosition()).getId());
    }

    @Override
    public int getItemCount() {
        return studentConstructors.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentName;
        TextView unitCode;
        TextView studentID;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.txtName);
            unitCode = itemView.findViewById(R.id.txtAddress1);
            studentID = itemView.findViewById(R.id.txtAddress);

        }
    }

    public void updateCollection(List<StudentConstructor> studentConstructors){
        this.studentConstructors = studentConstructors;
        notifyDataSetChanged();
    }

}
