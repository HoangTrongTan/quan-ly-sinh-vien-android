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

public class Add_lophoc extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    Button btnLuu, btnHuy;
    EditText editMaLop, editTenLop, editGVCN;
    Spinner spinnerMaKhoa, spinnerMaNganh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lophoc);

        addControls();
        addEvents();

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

    // even sự kiện click---------------------------------------------------------------
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

    //ÁNH XẠ-----------------------------------------------------------------------
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        editMaLop = (EditText) findViewById(R.id.edt_t_malop);
        spinnerMaKhoa = (Spinner) findViewById(R.id.spinner_t_MaKhoa);
        spinnerMaNganh = (Spinner) findViewById(R.id.spinner_t_MaNganh);
        editTenLop = (EditText) findViewById(R.id.edt_t_TenLop);
        editGVCN = (EditText) findViewById(R.id.edt_t_GVCN);
    }

    private void insert(){
        String malop = editMaLop.getText().toString();
        String makhoa = spinnerMaKhoa.getSelectedItem().toString();
        String manganh = spinnerMaNganh.getSelectedItem().toString();
        String tenlop = editTenLop.getText().toString();
        String gvcn = editGVCN.getText().toString();

        if(malop.equals("") || tenlop.equals("") || gvcn.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else {
            database = DataBase.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM tbl_lop WHERE malop =? ", new String[]{malop+""});
            if (cursor.moveToNext()){
                Toast.makeText(this, "Trùng mã lớp học", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("malop", malop);
                contentValues.put("makhoa", makhoa);
                contentValues.put("manganh", manganh);
                contentValues.put("tenlop", tenlop);
                contentValues.put("gvcn", gvcn);

                SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
                database.insert("tbl_lop", null, contentValues);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity_Lop.class);
                startActivity(intent);
            }
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_Lop.class);
        startActivity(intent);
    }
}