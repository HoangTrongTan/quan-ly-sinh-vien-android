package com.example.btl_qly_sinhvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //onclick btn chuyên ngành
    public void chuyennganh (View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_ChuyenNganh.class);
        startActivity(intent);
    }
    public void khoahoc (View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_KhoaHoc.class);
        startActivity(intent);
    }
    public void lophoc (View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_Lop.class);
        intent.putExtra("email",this.getIntent().getStringExtra("email"));
        startActivity(intent);
    }
    public void monhoc (View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_MonHoc.class);
        startActivity(intent);
    }
    public void sinhvien (View view){
//        Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MainActivity_SinhVien.class);
        startActivity(intent);
    }
    public void diem (View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_Diem.class);
        startActivity(intent);
    }

    //gọi activity menu
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //sự kiện click vào item đăng xuất
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_login) {
            Intent intent = new Intent(MainActivity.this, Activity_DangNhap.class);
            startActivity(intent);
        }
        return true;
    }
}