package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AddTimeTable extends AppCompatActivity {
    Spinner s1, s2;
    ArrayList<String> a1, a2;
    String str1, str2, fio = "Учитель", name = "Предмет", num = "№";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);
        s1 = findViewById(R.id.spinner);
        s2 = findViewById(R.id.spinner2);
        a1 = new ArrayList<>();
        a2 = new ArrayList<>();
        new Async().execute();
    }

    public void addTimeTable(View view) {
        str1 = s1.getSelectedItem().toString();
        str2 = s2.getSelectedItem().toString();
        new Insert().execute();
        new Async().execute();
    }

    class Async extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT fio FROM isu.teacher;");
                while (resultSet.next()) {
                    a1.add(resultSet.getString("fio"));
                }
                resultSet = statement.executeQuery("SELECT subject_name FROM isu.fan;");
                while (resultSet.next()) {
                    a2.add(resultSet.getString("subject_name"));
                }
                resultSet = statement.executeQuery("SELECT fio, subject_name FROM teacher, fan, timetable where timetable.id_teacher=teacher.id_teacher and fan.id_fan=timetable.id_fan;");
                int i = 1;
                fio = "Учитель";
                name = "Предмет";
                num = "№";
                while (resultSet.next()) {
                    fio += "\n" + resultSet.getString("fio");
                    name += "\n" + resultSet.getString("subject_name");
                    num += "\n" + i;
                    i++;
                }
                connection.close();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TextView teacherNeme, subjectName, numT;
            teacherNeme = findViewById(R.id.textTeacherTime);
            teacherNeme.setText(fio);
            subjectName = findViewById(R.id.textSubjectTime);
            subjectName.setText(name);
            numT=findViewById(R.id.textNumTime);
            numT.setText(num);
            show();
            super.onPostExecute(aVoid);
        }
    }

    private void show() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, a1);
        s1.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, a2);
        s2.setAdapter(adapter2);
    }

    class Insert extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO `isu`.`timetable` (`id_teacher`, `id_fan`) VALUES ((SELECT id_teacher FROM isu.teacher where fio = '" + str1 + "'), (SELECT id_fan FROM isu.fan where subject_name='" + str2 + "'));");
                connection.close();
            } catch (Exception e) {
            }
            return null;

        }
    }


}