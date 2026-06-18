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

import com.example.btl_qly_sinhvien.Database.DataBase;

public class Activity_DangKy extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    Button btnDK, btnHuy;
    EditText editUser, editPassword, editGmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        addControls();
        addEvents();
    }

    //ÁNH XẠ
    private void addControls() {
        btnDK = (Button) findViewById(R.id.btn_dangky);
        btnHuy = (Button) findViewById(R.id.btn_Huy);

        editUser = (EditText) findViewById(R.id.edt_user);
        editPassword = (EditText) findViewById(R.id.edt_password);
        editGmail = (EditText) findViewById(R.id.edt_gmail);
    }

    private void addEvents() {
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }


    private void insert(){
        String user = editUser.getText().toString();
        String pass = editPassword.getText().toString();
        String gmail = editGmail.getText().toString();

        if(user.equals("") || pass.equals("") || gmail.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else{
            database = DataBase.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM tbl_dangnhap WHERE username =? ", new String[]{user+""});
            if (cursor.moveToNext()){
                Toast.makeText(this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("username", user);
                contentValues.put("password", pass);
                contentValues.put("email", gmail);

                SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
                database.insert("tbl_dangnhap", null, contentValues);
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Activity_DangNhap.class);
                startActivity(intent);
            }
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, Activity_DangNhap.class);
        startActivity(intent);
    }
}