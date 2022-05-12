package com.android.isumysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    TextInputEditText login, pass;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.editTextTextPersonName);
        pass=findViewById(R.id.editTextTextPassword);
    }

    public void singIn(View view) {
        new Async().execute();
    }
    class Async extends AsyncTask<Void, Void, Void> {
        String loginTxt=login.getText().toString();
        String passTxt=pass.getText().toString();
        private boolean res=false;
        private String status="";
        private int id=0;
        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbInfo.url, dbInfo.user, dbInfo.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM isu.users where login=\""+loginTxt+"\" and pass=\""+passTxt+"\";");
                if (resultSet.next()) {
                    res=true;
                    status=resultSet.getString("status");
                    id=resultSet.getInt("id_user");
                }
                connection.close();
            }
            catch(Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            //text.setText(records);
            //Toast.makeText(MainActivity.this, ""+res+" "+status, Toast.LENGTH_SHORT).show();
            Intent i;
            if(res==true){
                open(res, status, id);
            }
            super.onPostExecute(aVoid);
        }
    }
    private void open(boolean res, String status, int id){
        Intent i=new Intent(this, StudentActivity.class);
        switch (status){
            case "teacher":
                i=new Intent(this, TeacherActivity.class);
                i.putExtra("idTeacher",id);
                break;
            case "admin":
                i=new Intent(this, AdminActivity.class);
                break;
            default:
                i=new Intent(this, StudentActivity.class);
                break;
        }
        i.putExtra("id",id);
        startActivity(i);
    }
}