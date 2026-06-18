package com.example.btl_qly_sinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_qly_sinhvien.Database.DataBase;

public class Sua_chuyennganh extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    String manganh = "";

    Button btnLuu, btnHuy;
    EditText editMaNganh, editTenNganh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_chuyennganh);

        addControls();
        addEvents();
        laydulieu();
    }

    //ÁNH XẠ
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        editMaNganh = (EditText) findViewById(R.id.edt_s_MaNganh);
        editTenNganh = (EditText) findViewById(R.id.edt_s_TenNganh);
    }

    private void laydulieu() {
        Intent intent = getIntent();
        manganh = intent.getStringExtra("manganh");
        SQLiteDatabase database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_chuyennganh WHERE manganh = ? ",new String[]{manganh+""});
//  2 trở lên      Cursor cursor = database.rawQuery("SELECT * FROM tt_sach WHERE id = ?, sdt = ?",new String[]{id + "", sdt});
        cursor.moveToFirst();
        String manganh = cursor.getString(0);
        String tennganh = cursor.getString(1);

        editMaNganh.setText(manganh);
        editTenNganh.setText(tennganh);
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
        String tennganh = editTenNganh.getText().toString();

        if(tennganh.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tennganh", tennganh);

            SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
            database.update("tbl_chuyennganh", contentValues, "manganh=?", new String[]{manganh + ""});
            Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity_ChuyenNganh.class);
            startActivity(intent);
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_ChuyenNganh.class);
        startActivity(intent);
    }
}