package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddStudent extends AppCompatActivity {
    TextInputEditText login, pass, fio, group, address, tel;
    String s_login, s_pass, s_fio, s_group, s_address, s_tel, s_status = "student";
    String insert_1, insert_2;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        login = findViewById(R.id.editTextTextPersonName2);
        pass = findViewById(R.id.editTextTextPassword2);
        fio = findViewById(R.id.editTextFio);
        group = findViewById(R.id.editTextGroup);
        address = findViewById(R.id.editTextAddress);
        tel = findViewById(R.id.editTextTel);


    }

    public void addClick(View view) {
        s_login = login.getText().toString();
        s_pass = pass.getText().toString();
        s_fio = fio.getText().toString();
        s_group = group.getText().toString();
        s_address = address.getText().toString();
        s_tel = tel.getText().toString();
        insert_1 = "INSERT INTO `isu`.`users` (`login`, `pass`, `status`) VALUES ('" + s_login + "', '" + s_pass + "', 'student');";
        insert_2 = "INSERT INTO `isu`.`student` (`id_user`, `fio`, `group`, `adress`, `tel`) VALUES ((SELECT id_user FROM isu.users where login='" + s_login + "'), '" + s_fio + "', '" + s_group + "', '" + s_address + "', '" + s_tel + "');";
        new Insert().execute();
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