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

public class Sua_khoahoc extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    String makhoa = "";

    Button btnLuu, btnHuy;
    EditText editMaKhoa, editTenKhoa, editNienKhoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_khoahoc);

        addControls();
        addEvents();
        laydulieu();
    }

    //ÁNH XẠ
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        editMaKhoa = (EditText) findViewById(R.id.edt_s_MaKhoa);
        editTenKhoa = (EditText) findViewById(R.id.edt_s_KhoaHoc);
        editNienKhoa = (EditText) findViewById(R.id.edt_s_NienKhoa);
    }

    //lấy dữ liệu đổ vào texr để sửa
    private void laydulieu() {
        Intent intent = getIntent();
        makhoa = intent.getStringExtra("makhoa");
        SQLiteDatabase database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_khoa WHERE makhoa = ? ",new String[]{makhoa+""});
        cursor.moveToFirst();
        String makhoa = cursor.getString(0);
        String tenkhoa = cursor.getString(1);
        String nienkhoa = cursor.getString(2);

        editMaKhoa.setText(makhoa);
        editTenKhoa.setText(tenkhoa);
        editNienKhoa.setText(nienkhoa);
    }

    private void addEvents(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private void update(){
        String tenkhoa = editTenKhoa.getText().toString();
        String nienkhoa = editNienKhoa.getText().toString();

        if(tenkhoa.equals("") || nienkhoa.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tenkhoa", tenkhoa);
            contentValues.put("nienkhoa", nienkhoa);

            SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
            database.update("tbl_khoa", contentValues, "makhoa=?", new String[]{makhoa + ""});
            Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity_KhoaHoc.class);
            startActivity(intent);
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_KhoaHoc.class);
        startActivity(intent);
    }
}