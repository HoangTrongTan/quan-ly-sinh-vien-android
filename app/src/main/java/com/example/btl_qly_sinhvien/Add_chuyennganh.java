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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl_qly_sinhvien.Database.DataBase;

import java.util.Locale;

public class Add_chuyennganh extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    Button btnLuu, btnHuy;
    EditText editMaNganh, editTenNganh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chuyennganh);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
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

    //ÁNH XẠ
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        editMaNganh = (EditText) findViewById(R.id.edt_t_MaNganh);
        editTenNganh = (EditText) findViewById(R.id.edt_t_TenNganh);
    }

    private void insert(){
        String ma = editMaNganh.getText().toString();
        String tennganh = editTenNganh.getText().toString();

        if(ma.equals("") || tennganh.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else{
            database = DataBase.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM tbl_chuyennganh WHERE manganh =? ", new String[]{ma+""});
            if (cursor.moveToNext()){
                Toast.makeText(this, "Trùng mã ngành", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("manganh", ma);
                contentValues.put("tennganh", tennganh);

                SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
                database.insert("tbl_chuyennganh", null, contentValues);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity_ChuyenNganh.class);
                startActivity(intent);
            }
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_ChuyenNganh.class);
        startActivity(intent);
    }
}