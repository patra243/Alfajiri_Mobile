package com.example.alfajirimobileclassattendance.store;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alfajirimobileclassattendance.constructor.FacultyConstructor;
import com.example.alfajirimobileclassattendance.constructor.StudentConstructor;

public class SaveUser {

    private Boolean value;

    public void Admin_saveData (Context context, Boolean b){
        SharedPreferences preferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isUserLogin", b);
        editor.commit();
    }

    public boolean Admin_loadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        value = preferences.getBoolean("isUserLogin", false);
        return value;
    }

    public void wlcmSave(Context context, Boolean b){
        SharedPreferences preferences = context.getSharedPreferences("intro", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("intro", b);
        editor.commit();
    }

    public boolean wlcmLoad(Context context){
        SharedPreferences preferences = context.getSharedPreferences("myPreferences", MODE_PRIVATE);
        value = preferences.getBoolean("intro", false);
        return value;
    }

    public void Faculty_saveData(Context context, Boolean b){
        SharedPreferences preferences = context.getSharedPreferences("myPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isUserLogin", b);
        editor.commit();
    }

    public boolean Faculty_loadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("myPreferences", MODE_PRIVATE);
        value = preferences.getBoolean("isUserLogin", false);
        return value;
    }

    public void Student_saveData(Context context, Boolean b){
        SharedPreferences preferences = context.getSharedPreferences("saveStudent", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isUserLogin", b);
        editor.commit();
    }

    public boolean Student_loadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("saveStudent", MODE_PRIVATE);
        value = preferences.getBoolean("isUserLogin", false);
        return value;
    }

    public void Faculty_EmailSaveData(Context context, String b){
        SharedPreferences preferences = context.getSharedPreferences("myPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("isUserLogin", b);
        editor.commit();
    }

    public String Faculty_EmailLoadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("myPreference", MODE_PRIVATE);
        String values = preferences.getString("isUserLogin", null);
        return values;
    }

    public void Faculty_NameSaveData(Context context, String b){
        SharedPreferences preferences = context.getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("isUserlogin", b);
        editor.commit();
    }

    public String Faculty_NameLoadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("name", MODE_PRIVATE);
        String values = preferences.getString("isUserLogin", null);
        return values;
    }

    public void Faculty_ModeSaveData(Context context, String b){
        SharedPreferences preferences = context.getSharedPreferences("shift", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("isUserlogin", b);
        editor.commit();
    }

    public String Faculty_ModeLoadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("shift", MODE_PRIVATE);
        String values = preferences.getString("isUserLogin", null);
        return values;
    }

    public void Faculty_DeptSaveData(Context context, String b){
        SharedPreferences preferences = context.getSharedPreferences("department", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("isUserLogin", b);
        editor.commit();
    }

    public String Faculty_DeptLoadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("department", MODE_PRIVATE);
        String values = preferences.getString("isUserLogin", null);
        return values;
    }

    public void Faculty_CourseSaveData(Context context, String b){
        SharedPreferences preferences = context.getSharedPreferences("course", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("isUserLogin", b);
        editor.commit();
    }

    public String Faculty_CourseLoadData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("course", MODE_PRIVATE);
        String values = preferences.getString("isUserLogin", null);
        return values;
    }

    public void saveFaculty(Context context, FacultyConstructor facultyConstructor){
        SharedPreferences sharedPreferences = context.getSharedPreferences("FACULTY", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", facultyConstructor.getId());
        editor.putString("name", facultyConstructor.getName());
        editor.putString("designation", facultyConstructor.getDesignation());
        editor.putString("faculty", facultyConstructor.getFaculty());
        editor.putString("mode", facultyConstructor.getMode());
        editor.apply();
    }

    public FacultyConstructor getFaculty(Context context){
        SharedPreferences preferences = context.getSharedPreferences("FACULTY", MODE_PRIVATE);
        FacultyConstructor faculty = new FacultyConstructor(preferences.getString("id", null),
                preferences.getString("name", null),
                preferences.getString("faculty", null),
                preferences.getString("designation", null),
                "", "", "", "",
                preferences.getString("mode", null)
        );
        return faculty;
    }

    public  void saveStudent(Context context, StudentConstructor studentConstructor){
        SharedPreferences preferences = context.getSharedPreferences("STUDENT", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", studentConstructor.getId());
        editor.putString("name", studentConstructor.getName());
        editor.putString("year", studentConstructor.getYear());
        editor.putString("faculty", studentConstructor.getFaculty());
        editor.putString("mode", studentConstructor.getMode());
        editor.putString("unit", studentConstructor.getUnit());
        editor.putString("unit_code", studentConstructor.getUnitCode());
        editor.putString("password", studentConstructor.getPassword());
        editor.apply();
    }

    public StudentConstructor getStudent(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("STUDENT", MODE_PRIVATE);
        StudentConstructor studentConstructor = new StudentConstructor(preferences.getString("name", null),
                preferences.getString("id", null),
                "", "",
                preferences.getString("faculty", null),
                preferences.getString("year", null),
                "", "", "",
                preferences.getString("unit", null),
                preferences.getString("unit_code", null),
                preferences.getString("password", null)
        );
        return studentConstructor;
    }

}
