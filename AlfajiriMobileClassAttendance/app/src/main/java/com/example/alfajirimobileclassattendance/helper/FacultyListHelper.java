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

public class FacultyListHelper extends RecyclerView.Adapter<FacultyListHelper.FacultyListHolder> {

    private List<FacultyConstructor> facList;
    private Context context;

    public FacultyListHelper(Context context, List<FacultyConstructor> facList){
        this.context = context;
        this.facList = facList;
    }

    public FacultyListHelper(){

    }

    @NonNull
    @Override
    public FacultyListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.per_student_layout, parent, false);
        return new FacultyListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyListHolder holder, int position) {
        holder.studentName.setText(facList.get(holder.getBindingAdapterPosition()).getName());
        holder.unitCode.setText(facList.get(holder.getBindingAdapterPosition()).getDesignation());
        holder.facMail.setText(facList.get(holder.getBindingAdapterPosition()).getEmail());
    }

    @Override
    public int getItemCount() {
        return facList.size();
    }

    class FacultyListHolder extends RecyclerView.ViewHolder {

        TextView studentName, facMail;
        TextView unitCode;

        public FacultyListHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.txtName);
            facMail = itemView.findViewById(R.id.txtAddress);
            unitCode = itemView.findViewById(R.id.txtAddress1);

        }
    }

    public void updateCollection(List<FacultyConstructor> studentList){
        this.facList = studentList;
        notifyDataSetChanged();
    }
}
