package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentActivity extends AppCompatActivity {
    int id, idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        id = getIntent().getExtras().getInt("id");
        idUser = getIntent().getExtras().getInt("id");
        new Async().execute();
    }

    class Async extends AsyncTask<Void, Void, Void> {
        String records = "", records2 = "";
        String name;

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT id_student FROM isu.student WHERE id_user="+id+" LIMIT 1");
                if(resultSet.next())
                    id=resultSet.getInt(1);

                resultSet = statement.executeQuery("SELECT fan.`subject_name`, grade.`ball` FROM fan, grade WHERE grade.`id_student`=" + id + " AND fan.`id_fan`=grade.`id_fan` ");
                while (resultSet.next()) {
                    records += resultSet.getString(1) + ":\n";
                    records2 += resultSet.getString(2) + " \n";
                }
                ResultSet resultSet2 = statement.executeQuery("SELECT fio FROM student WHERE id_user=" + idUser);
                if (resultSet2.next()) {
                    name = resultSet2.getString("fio");
                }

                connection.close();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TextView textView = findViewById(R.id.textRes);
            textView.setText(records);
            TextView textView2 = findViewById(R.id.textRes2);
            textView2.setText(records2);
            TextView textView1 = findViewById(R.id.textName);
            textView1.setText("Добро пожаловать:\n" + name + "!");
            super.onPostExecute(aVoid);
        }
    }
}