package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void open(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnAddStudent:
                i = new Intent(this, AddStudent.class);
                break;
            case R.id.btnAddTeacher:
                i = new Intent(this, AddTeacher.class);
                break;
            case R.id.btnAddTimeTable:
                i = new Intent(this, AddTimeTable.class);
                break;
            case R.id.btnShowListTeacher:
                i = new Intent(this, ShowTeacherList.class);
                break;
            case R.id.btnAddSubject:
                i = new Intent(this, AddSubject.class);
                break;
            default:
                i = new Intent(this, ShowStudentList.class);
                break;
        }
        startActivity(i);
    }
}