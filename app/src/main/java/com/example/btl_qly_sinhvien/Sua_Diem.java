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

public class Sua_Diem extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;
    String masv = "";
    String mamon = "";

    Button btnLuu, btnHuy;
    EditText editMaSV, editHoTen, editDiemCC, editDiemGK, editDiemThi, editDiemTB, editMaMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_diem);

        addControls();
        addEvents();
        laydulieu();
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

        editMaSV= (EditText) findViewById(R.id.edt_S_MaSV);
        editHoTen= (EditText) findViewById(R.id.edt_s_HoTen);
        editMaMon = (EditText) findViewById(R.id.edt_s_MaMon);
        editDiemCC = (EditText) findViewById(R.id.edt_s_DiemCC);
        editDiemGK = (EditText) findViewById(R.id.edt_s_DiemGK);
        editDiemThi = (EditText) findViewById(R.id.edt_s_DiemThi);
        editDiemTB = (EditText) findViewById(R.id.edt_s_DiemTB);
    }

    //lấy dữ liệu đổ vào text để sửa
    private void laydulieu() {
        Intent intent = getIntent();
        masv = intent.getStringExtra("masv");
        mamon = intent.getStringExtra("mamon");
        SQLiteDatabase database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_diem WHERE masv = ? AND mamon = ?",new String[]{masv+"",mamon});
        cursor.moveToFirst();
        String masv = cursor.getString(0);
        String hoten = cursor.getString(1);
        String mamon = cursor.getString(2);
        String diemcc = cursor.getString(3);
        String diemgk = cursor.getString(4);
        String diemthi = cursor.getString(5);
        String diemtb = cursor.getString(6);

        editMaSV.setText(masv);
        editHoTen.setText(hoten);
        editMaMon.setText(mamon);
        editDiemCC.setText(diemcc);
        editDiemGK.setText(diemgk);
        editDiemThi.setText(diemthi);
        editDiemTB.setText(diemtb);
    }


    private void update(){
        String mamon = editMaMon.getText().toString();
        String hoten = editHoTen.getText().toString();
        String diemcc = editDiemCC.getText().toString();
        String diemgk = editDiemGK.getText().toString();
        String diemthi = editDiemThi.getText().toString();

        if(diemcc.equals("") || diemgk.equals("") || diemthi.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
        }
        try {
            // điều kiện điểm -----------------------------------
            double cc = Double.parseDouble(diemcc);
            if (cc < 0 || cc > 10) {
                Toast.makeText(this, "Điểm CC không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            double gk = Double.parseDouble(diemgk);
            if (gk < 0 || gk > 10) {
                Toast.makeText(this, "Điểm GK không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            double thi = Double.parseDouble(diemthi);
            if (thi < 0 || thi > 10) {
                Toast.makeText(this, "Điểm Thi không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            //tính điểm tb môn---------------------------------------
            double tb = cc*0.1+ gk*0.2+ thi*0.7;

            ContentValues contentValues = new ContentValues();
            contentValues.put("hoten", hoten);
            contentValues.put("diemcc", diemcc);
            contentValues.put("diemgk", diemgk);
            contentValues.put("diemthi", diemthi);
            contentValues.put("diemtb", tb);

            SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
            database.update("tbl_diem", contentValues, "masv=? and mamon=?", new String[]{masv + "",mamon});
            Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity_Diem.class);startActivity(intent);
        }
        catch (Exception e) {
            Toast.makeText(this, "Điểm chỉ nhận giá trị số", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_Diem.class);
        startActivity(intent);
    }
}