package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShowTeacherList extends AppCompatActivity {
    TextView fio;
    String ft = "Фамиля Имя", nt = "№";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_list);
        fio = findViewById(R.id.textFio);
        new Async().execute();
    }

    class Async extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM isu.teacher");
                int i = 1;
                while (resultSet.next()) {
                    ft += "\n" + resultSet.getString("fio");
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
            TextView num=findViewById(R.id.textNum);
            num.setText(nt);
            fio.setText(ft);
            super.onPostExecute(aVoid);
        }
    }
}