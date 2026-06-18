package com.example.btl_qly_sinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl_qly_sinhvien.Database.DataBase;

import java.util.ArrayList;

public class Sua_lophoc extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;
    String malop = "";

    Button btnLuu, btnHuy;
    EditText editMaLop, editTenLop, editGVCN;
    Spinner spinnerMaKhoa, spinnerMaNganh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_lophoc);

        addControls();
        addEvents();
        laydulieu();
        Spinner_MaKhoa();
        Spinner_MaNganh();
    }

    // lấy nguồn combobox mã khoa---------------------------------------------------------
    private void Spinner_MaKhoa() {
        ArrayList<String> arrayMaKhoa =new ArrayList<String>();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT makhoa FROM tbl_khoa ",null);
        arrayMaKhoa.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            arrayMaKhoa.add(ten);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayMaKhoa);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaKhoa.setAdapter(arrayAdapter);
    }

    // lấy nguồn combobox mã nganh------------------------------------------------------
    private void Spinner_MaNganh() {
        ArrayList<String> arrayMaNganh =new ArrayList<String>();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT manganh FROM tbl_chuyennganh ",null);
        arrayMaNganh.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            arrayMaNganh.add(ten);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayMaNganh);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaNganh.setAdapter(arrayAdapter);
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

    //ÁNH XẠ
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        editMaLop = (EditText) findViewById(R.id.edt_s_malop);
        spinnerMaKhoa = (Spinner) findViewById(R.id.spinner_s_MaKhoa);
        spinnerMaNganh = (Spinner) findViewById(R.id.spinner_st_MaNganh);
        editTenLop = (EditText) findViewById(R.id.edt_s_TenLop);
        editGVCN = (EditText) findViewById(R.id.edt_s_GVCN);
    }

    //lấy dữ liệu đổ vào texr để sửa
    private void laydulieu() {
        Intent intent = getIntent();
        malop = intent.getStringExtra("malop");
        SQLiteDatabase database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_lop WHERE malop = ? ",new String[]{malop+""});
        cursor.moveToFirst();
        String malop = cursor.getString(0);
//        String makhoa = cursor.getString(1);
//        String manganh = cursor.getString(2);
        String tenlop = cursor.getString(3);
        String gvcn = cursor.getString(4);

        editMaLop.setText(malop);
        editTenLop.setText(tenlop);
        editGVCN.setText(gvcn);
    }


    private void update(){
        String makhoa = spinnerMaKhoa.getSelectedItem().toString();
        String manganh = spinnerMaNganh.getSelectedItem().toString();
        String tenlop = editTenLop.getText().toString();
        String gvcn = editGVCN.getText().toString();

        if(tenlop.equals("") || gvcn.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("makhoa", makhoa);
            contentValues.put("manganh", manganh);
            contentValues.put("tenlop", tenlop);
            contentValues.put("gvcn", gvcn);

            SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
            database.update("tbl_lop", contentValues, "malop=?", new String[]{malop + ""});
            Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity_Lop.class);
            startActivity(intent);
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_Lop.class);
        startActivity(intent);
    }
}