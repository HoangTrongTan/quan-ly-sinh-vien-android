package com.example.btl_qly_sinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_qly_sinhvien.Controller.controll;
import com.example.btl_qly_sinhvien.Database.DataBase;
import com.example.btl_qly_sinhvien.Model.ChuyenNganh;

public class Activity_DangNhap extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    Button btnDN, btnDK;
    EditText editUser, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        controll.turn_off_SSL();

        addControls();
        addEvents();
    }

    //ÁNH XẠ
    private void addControls() {
        btnDN = (Button) findViewById(R.id.btn_login);
        btnDK = (Button) findViewById(R.id.btn_register);

        editUser = (EditText) findViewById(R.id.edt_user);
        editPassword = (EditText) findViewById(R.id.edt_password);
    }

    //oclick đăng ký
    private void addEvents() {
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll.Login(Activity_DangNhap.this,editUser.getText().toString(),editPassword.getText().toString());

            }
        });

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_DangNhap.this, Activity_DangKy.class);
                startActivity(intent);
            }
        });
    }

}