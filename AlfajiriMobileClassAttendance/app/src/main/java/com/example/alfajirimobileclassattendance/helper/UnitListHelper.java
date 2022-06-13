package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.UnitConstructor;

import java.util.List;


public class UnitListHelper extends RecyclerView.Adapter<UnitListHelper.UnitViewholder> {

    private List<UnitConstructor> unitConstructorList;
    private Context context;

    public UnitListHelper(Context context, List<UnitConstructor> unitConstructorList){
        this.context = context;
        this.unitConstructorList = unitConstructorList;
    }

    public UnitListHelper(){

    }

    @NonNull
    @Override
    public UnitViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.per_student_layout, parent, false);
        return new UnitViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewholder holder, int position) {
        holder.unitName.setText(unitConstructorList.get(holder.getBindingAdapterPosition()).getUnitName());
        holder.unitCode.setText(unitConstructorList.get(holder.getBindingAdapterPosition()).getUnitCode());
        holder.lecName.setText(unitConstructorList.get(holder.getBindingAdapterPosition()).getLecturer());
    }

    @Override
    public int getItemCount() {
        return unitConstructorList.size();
    }

    class UnitViewholder extends RecyclerView.ViewHolder {

        TextView unitName, lecName;
        TextView unitCode;

        public UnitViewholder(@NonNull View itemView) {
            super(itemView);
            unitName = itemView.findViewById(R.id.txtName);
            unitCode = itemView.findViewById(R.id.txtAddress);
            lecName = itemView.findViewById(R.id.txtAddress1);
        }
    }

    public void updateCollection(List<UnitConstructor> unitList){
        this.unitConstructorList = unitList;
        notifyDataSetChanged();
    }
}
