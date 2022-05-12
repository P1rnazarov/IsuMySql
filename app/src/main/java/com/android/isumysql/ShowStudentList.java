package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShowStudentList extends AppCompatActivity {

    TextView fio, tel, univer, adres, num;
    String ft = "Фамиля Имя", tt = "Телфон", ut = "Университет", at = "Адрес", nt="№";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_list);
        fio = findViewById(R.id.textFio);
        tel = findViewById(R.id.textTelfon);
        univer = findViewById(R.id.textUniversitet);
        adres = findViewById(R.id.textAdres);
        num=findViewById(R.id.textNum);
        new Async().execute();
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
                int i = 1;
                while (resultSet.next()) {
                    ft += "\n" + resultSet.getString("fio");
                    ut += "\n" + resultSet.getString("group");
                    at += "\n" + resultSet.getString("adress");
                    tt += "\n" + resultSet.getString("tel");
                    nt += "\n" + i;
                    ++i;
                }
                connection.close();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            num.setText(nt);
            fio.setText(ft);
            univer.setText(ut);
            adres.setText(at);
            tel.setText(tt);
            super.onPostExecute(aVoid);
        }
    }
}