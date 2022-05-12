package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddSubject extends AppCompatActivity {
    String s_name = "Назавание предмета", s_num = "№";
    String subject_string = "";
    TextInputEditText subjectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        subjectText = findViewById(R.id.editTextSubjectName);
        new Async().execute();
    }

    public void insert_subject(View view) {
        subject_string = subjectText.getText().toString();
        new Insert().execute();
        s_name = "Назавание предмета";
        s_num = "№";
        new Async().execute();
    }

    class Async extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM isu.fan;");
                int i = 1;
                while (resultSet.next()) {
                    s_num += "\n" + i;
                    s_name += "\n" + resultSet.getString("subject_name");
                    ++i;
                }
                connection.close();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TextView num = findViewById(R.id.textNumSunject);
            TextView name = findViewById(R.id.textNameSubject);
            num.setText(s_num);
            name.setText(s_name);
            super.onPostExecute(aVoid);
        }
    }

    class Insert extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO `isu`.`fan` (`subject_name`) VALUES ('"+subject_string+"');");
                connection.close();
            } catch (Exception e) {
            }
            return null;

        }
    }
}