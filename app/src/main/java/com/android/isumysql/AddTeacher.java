package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AddTeacher extends AppCompatActivity {
    TextInputEditText login, pass, fio;
    String s_login, s_pass, s_fio;
    String insert_1, insert_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        login = findViewById(R.id.editTeacherLogin);
        pass = findViewById(R.id.editTeacherPass);
        fio = findViewById(R.id.editTeacherFio);
    }

    public void addTeacher(View view) {
        s_login = login.getText().toString();
        s_pass = pass.getText().toString();
        s_fio = fio.getText().toString();
        if (s_login.equals("")) {
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
        } else if (s_fio.isEmpty()) {
            Toast.makeText(this, "Введите Фамилю Имю", Toast.LENGTH_SHORT).show();
        } else {
            insert_1 = "INSERT INTO `isu`.`users` (`login`, `pass`, `status`) VALUES ('" + s_login + "', '" + s_pass + "', 'teacher');";
            insert_2 = "INSERT INTO `isu`.`teacher` (`id_user`, `fio`) VALUES ((SELECT id_user FROM isu.users where login='" + s_login + "'), '" + s_fio + "');";
            new Insert().execute();
        }
    }


    class Insert extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                statement.executeUpdate(insert_1);
                statement.executeUpdate(insert_2);
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
}