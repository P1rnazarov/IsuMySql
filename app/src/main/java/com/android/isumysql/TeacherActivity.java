package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {
    Spinner selStudent, selBall;
    ArrayList aStudent, aStudentId;
    String ball, nameStudent;
    Integer idFan, idTeacher, idUser, idStudent, positionIdStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        aStudent = new ArrayList<String>();
        aStudentId = new ArrayList<Integer>();
        selStudent = findViewById(R.id.spinnerSelectStudent);
        selBall = findViewById(R.id.spinnerSelectBall);
        String[] aBall = {"A", "B", "C", "D", "F"};

        ArrayAdapter<String> adSelBall = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, aBall);
        selBall.setAdapter(adSelBall);

        idUser = getIntent().getIntExtra("idTeacher", 0);
        new Async().execute();
    }

    public void add(View view) {
        ball=selBall.getSelectedItem().toString();
        nameStudent=selBall.getSelectedItem().toString();
        positionIdStudent=selStudent.getSelectedItemPosition();
        idStudent= (Integer) aStudentId.get(positionIdStudent);
        new Insert().execute();

    }
    class Insert extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                Integer idGrade;
                ResultSet resultSet = statement.executeQuery("SELECT * FROM isu.grade where id_fan="+idFan+" and id_teacher = "+idTeacher+" and id_student = "+idStudent+";");
                if (resultSet.next()) {
                    idGrade=resultSet.getInt("id_grade");
                    statement.executeUpdate("DELETE FROM `isu`.`grade` WHERE (`id_grade` = '"+idGrade+"')");
                }
                statement.executeUpdate("INSERT INTO `isu`.`grade` (`ball`, `id_fan`, `id_teacher`, `id_student`) VALUES ('"+ball+"', '"+idFan+"', '"+idTeacher+"', '"+idStudent+"')");
                connection.close();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
            super.onPostExecute(aVoid);
        }
    }

    class Async extends AsyncTask<Void, Void, Void> {
        String records = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM student");
                while (resultSet.next()) {
                    aStudent.add(resultSet.getString("fio"));
                    aStudentId.add(resultSet.getInt("id_student"));
                }
                resultSet = statement.executeQuery("SELECT id_teacher FROM isu.teacher where id_user="+idUser+"");
                if (resultSet.next())
                    idTeacher = resultSet.getInt("id_teacher");
                resultSet = statement.executeQuery("SELECT id_fan FROM isu.timetable where id_teacher=" + idTeacher + "");
                if (resultSet.next())
                    idFan = resultSet.getInt("id_fan");
                connection.close();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            show();
            super.onPostExecute(aVoid);
        }
    }
    private void show() {
        ArrayAdapter<String> adSelStudent = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, aStudent);
        selStudent.setAdapter(adSelStudent);

    }
}