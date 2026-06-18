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

public class Sua_MonHoc extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    String mamon = "";

    Button btnLuu, btnHuy;
    EditText editMaMon, editTenMon, editSoTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_hoc);

        addControls();
        addEvents();
        laydulieu();
    }

    //ÁNH XẠ
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        editMaMon = (EditText) findViewById(R.id.edt_s_MaMon);
        editTenMon = (EditText) findViewById(R.id.edt_s_MonHoc);
        editSoTiet = (EditText) findViewById(R.id.edt_s_SoTiet);
    }

    //lấy dữ liệu đổ vào texr để sửa
    private void laydulieu() {
        Intent intent = getIntent();
        mamon = intent.getStringExtra("mamon");
        SQLiteDatabase database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_monhoc WHERE mamon = ? ",new String[]{mamon+""});
        cursor.moveToFirst();
        String mamon = cursor.getString(0);
        String tenmon = cursor.getString(1);
        String sotiet = cursor.getString(2);

        editMaMon.setText(mamon);
        editTenMon.setText(tenmon);
        editSoTiet.setText(sotiet);
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
        String tenmon = editTenMon.getText().toString();
        String sotiet = editSoTiet.getText().toString();

        if(tenmon.equals("") || sotiet.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tenmon", tenmon);
            contentValues.put("sotiet", sotiet);

            SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
            database.update("tbl_monhoc", contentValues, "mamon=?", new String[]{mamon + ""});
            Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity_MonHoc.class);
            startActivity(intent);
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_MonHoc.class);
        startActivity(intent);
    }
}