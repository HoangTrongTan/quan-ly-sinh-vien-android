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

public class Add_khoahoc extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    Button btnLuu, btnHuy;
    EditText editMaKhoa, editTenKhoa, editNienKhoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_khoahoc);

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

        editMaKhoa = (EditText) findViewById(R.id.edt_t_MaKhoa);
        editTenKhoa = (EditText) findViewById(R.id.edt_t_KhoaHoc);
        editNienKhoa = (EditText) findViewById(R.id.edt_t_NienKhoa);
    }

    private void insert(){
        String makhoa = editMaKhoa.getText().toString();
        String tenkhoa = editTenKhoa.getText().toString();
        String nienkhoa = editNienKhoa.getText().toString();

        if(makhoa.equals("") || tenkhoa.equals("") || nienkhoa.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else{
            database = DataBase.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM tbl_khoa WHERE makhoa =? ", new String[]{makhoa+""});
            if (cursor.moveToNext()){
                Toast.makeText(this, "Trùng mã khóa học", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("makhoa", makhoa);
                contentValues.put("tenkhoa", tenkhoa);
                contentValues.put("nienkhoa", nienkhoa);

                SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
                database.insert("tbl_khoa", null, contentValues);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity_KhoaHoc.class);
                startActivity(intent);
            }
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_KhoaHoc.class);
        startActivity(intent);
    }
}