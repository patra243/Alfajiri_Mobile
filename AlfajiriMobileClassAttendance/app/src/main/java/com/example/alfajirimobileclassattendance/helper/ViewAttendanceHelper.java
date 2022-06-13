package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;
import com.example.alfajirimobileclassattendance.store.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendanceHelper extends RecyclerView.Adapter<ViewAttendanceHelper.ViewAttendanceHolder> {

    private List<StudentConstructor> studentList = new ArrayList<>();
    private List<String> presentList = new ArrayList<>();
    private List<String> absentList = new ArrayList<>();
    private Context context;
    private DatabaseReference presentRef, absentRef;

    public ViewAttendanceHelper(Context context, List<StudentConstructor> studentList){

        this.context = context;
        this.studentList = studentList;
    }

    public ViewAttendanceHelper(){

    }

    @NonNull
    @Override
    public ViewAttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.per_student_layout, parent, false);
        return new ViewAttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAttendanceHolder holder, int position) {
        holder.studentName.setText(studentList.get(holder.getBindingAdapterPosition()).getName());
        holder.unitCode.setText(studentList.get(holder.getBindingAdapterPosition()).getUnitCode());
        holder.id.setText(studentList.get(holder.getBindingAdapterPosition()).getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentRef = FirebaseDatabase.getInstance().getReference().child("Department").child(studentList.get(holder.getBindingAdapterPosition()).getDepartment())
                        .child("Attendance").child(new SaveUser().getFaculty(context).getMode()).child(new SaveUser().Faculty_CourseLoadData(context));
                presentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        presentList.clear();
                        absentList.clear();
                        if (snapshot.exists()){
                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                for (DataSnapshot snapshot2:snapshot1.child("Present").getChildren()){
                                    String key = snapshot2.getKey();
                                    if (holder.getBindingAdapterPosition() != -1){
                                        if (key.equals(studentList.get(position).getId())){
                                            presentList.add(key);
                                        }
                                    }
                                }
                                for (DataSnapshot snapshot2:snapshot1.child("Absent").getChildren()){
                                    String key = snapshot2.getValue().toString();
                                    if (key.equals(studentList.get(holder.getBindingAdapterPosition()).getId())){
                                        absentList.add(key);
                                    }
                                }
                            }

                            final AlertDialog dialog = new AlertDialog.Builder(context).create();
                            View view = LayoutInflater.from(context).inflate(R.layout.view_attendance, null);

                            TextView present, absent, name, id;
                            Button button;

                            present = view.findViewById(R.id.presentVStudent);
                            absent = view.findViewById(R.id.absentVStudent);
                            name = view.findViewById(R.id.txtViewName);
                            id = view.findViewById(R.id.txtViewID);

                            name.setText(studentList.get(holder.getBindingAdapterPosition()).getName());
                            id.setText(studentList.get(holder.getBindingAdapterPosition()).getId());

                            button = view.findViewById(R.id.proceedBtn);

                            present.setText(Integer.toString(presentList.size()));
                            absent.setText(Integer.toString(absentList.size()));

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setView(view);
                            dialog.setCancelable(true);
                            dialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewAttendanceHolder extends RecyclerView.ViewHolder {

        TextView studentName;
        TextView unitCode;
        TextView id;

        public ViewAttendanceHolder(@NonNull View itemView) {

            super(itemView);
            studentName = itemView.findViewById(R.id.txtName);
            unitCode = itemView.findViewById(R.id.txtAddress1);
            id = itemView.findViewById(R.id.txtAddress);
        }
    }

    public void updateCollection(List<StudentConstructor> studentList){

        this.studentList = studentList;
        notifyDataSetChanged();
    }
}
