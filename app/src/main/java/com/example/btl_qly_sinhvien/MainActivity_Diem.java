package com.example.btl_qly_sinhvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.btl_qly_sinhvien.Adapter.Adapter_Diem;
import com.example.btl_qly_sinhvien.Adapter.Adapter_Lop;
import com.example.btl_qly_sinhvien.Database.DataBase;
import com.example.btl_qly_sinhvien.Model.Diem;
import com.example.btl_qly_sinhvien.Model.Lop;

import java.util.ArrayList;

public class MainActivity_Diem extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    com.google.android.material.floatingactionbutton.FloatingActionButton btnAdd;
    com.google.android.material.floatingactionbutton.FloatingActionButton btnHome;
    Button btn_tk_all;

    ListView listView;
    ArrayList<Diem> list;
    Adapter_Diem adapter_diem;
    Spinner spinner_tk_MaSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_diem);

        listView = findViewById(R.id.listview_diem);
        list = new ArrayList<>();
        adapter_diem = new Adapter_Diem(this, list);
        listView.setAdapter(adapter_diem);

        Spinner_MaSV();
        Click_home();
        Chick_Them();
        redata();

        //tìm kiếm theo masv--------------------------------------------
        spinner_tk_MaSV = (Spinner) findViewById(R.id.timkiem_masv);
        spinner_tk_MaSV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                load_tk_masv();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //tìm kiếm tất cả--------------------------------------------------
        btn_tk_all = (Button) findViewById(R.id.btn_tk_all);
        btn_tk_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redata();
            }
        });
    }

    // lấy nguồn combobox mã sv---------------------------------------------------------
    private void Spinner_MaSV() {
        spinner_tk_MaSV = (Spinner) findViewById(R.id.timkiem_masv);
        ArrayList<String> arrayMaSV =new ArrayList<String>();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT masv FROM tbl_sinhvien ",null);
        arrayMaSV.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            arrayMaSV.add(ten);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayMaSV);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tk_MaSV.setAdapter(arrayAdapter);
    }

    //lấy dữ liệu từ database đổ lên listview trong activity thêm điểm
    private void load_tk_masv(){
        String laynguon_listview_masv = spinner_tk_MaSV.getSelectedItem().toString();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_diem WHERE masv =? ", new String[]{laynguon_listview_masv+""});
        list.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String masv = cursor.getString(0);
            String hoten = cursor.getString(1);
            String mamon = cursor.getString(2);
            String diemcc = cursor.getString(3);
            String diemgk = cursor.getString(4);
            String diemthi = cursor.getString(5);
            String diemtb = cursor.getString(6);
            list.add(new Diem(masv, hoten, mamon, diemcc, diemgk, diemthi, diemtb));
        }
        adapter_diem.notifyDataSetChanged(); //adapter vẽ lại giao diện
    }

    private void Click_home() {
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_Diem.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //sự kiện onclik nút thêm---------------------------------------
    private void Chick_Them() {
        btnAdd = findViewById(R.id.btn_themDiem);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_Diem.this, Add_Diem.class);
                startActivity(intent);
            }
        });
    }

    //lấy dữ liệu từ database đổ lên listview
    private void redata(){
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_diem ",null);
        list.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String masv = cursor.getString(0);
            String hoten = cursor.getString(1);
            String mamon = cursor.getString(2);
            String diemcc = cursor.getString(3);
            String diemgk = cursor.getString(4);
            String diemthi = cursor.getString(5);
            String diemtb = cursor.getString(6);
            list.add(new Diem(masv, hoten, mamon, diemcc, diemgk, diemthi, diemtb));
        }
        adapter_diem.notifyDataSetChanged(); //adapter vẽ lại giao diện
    }
}