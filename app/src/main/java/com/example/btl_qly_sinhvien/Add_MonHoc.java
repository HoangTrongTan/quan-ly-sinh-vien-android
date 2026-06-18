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

public class Add_MonHoc extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    Button btnLuu, btnHuy;
    EditText editMaMon, editTenMon, editSoTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mon_hoc);

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

        editMaMon = (EditText) findViewById(R.id.edt_t_MaMon);
        editTenMon = (EditText) findViewById(R.id.edt_t_TenMon);
        editSoTiet = (EditText) findViewById(R.id.edt_t_SoTiet);
    }

    private void insert(){
        String mamon = editMaMon.getText().toString();
        String tenmon = editTenMon.getText().toString();
        String sotiet = editSoTiet.getText().toString();

        if(mamon.equals("") || tenmon.equals("") || sotiet.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else{
            database = DataBase.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM tbl_monhoc WHERE mamon =?", new String[]{mamon+""});
            if (cursor.moveToNext()){
                Toast.makeText(this, "Trùng mã môn học", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("mamon", mamon);
                contentValues.put("tenmon", tenmon);
                contentValues.put("sotiet", sotiet);

                SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
                database.insert("tbl_monhoc", null, contentValues);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity_MonHoc.class);
                startActivity(intent);
            }
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_MonHoc.class);
        startActivity(intent);
    }
}