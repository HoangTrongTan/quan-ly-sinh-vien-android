package com.example.btl_qly_sinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl_qly_sinhvien.Adapter.Adapter_Diem;
import com.example.btl_qly_sinhvien.Database.DataBase;
import com.example.btl_qly_sinhvien.Model.Diem;

import java.util.ArrayList;

public class Add_Diem extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    ListView listView_them;
    ArrayList<Diem> list_them;
    Adapter_Diem adapter_diem;

    Button btnLuu, btnHuy;
    EditText editHoTen, editDiemCC, editDiemGK, editDiemThi;
    Spinner spinnerMaSV, spinnerMaMon, spinnerMaLOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diem);

        addControls();
        addEvents();

        Spinner_MaMon();
        Spinner_Malop();
        //sự kiện click lấy thông mã sv-------------------------------------------
        spinnerMaLOP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editHoTen.setText("");
                Spinner_MaSV();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //sự kiện click lấy thông tin họ tên------------------------------------------------
        spinnerMaSV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LayNguonHoTen();
                load_diem_masv();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    //ÁNH XẠ-----------------------------------------------------------------------------------------------------
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        spinnerMaLOP = (Spinner) findViewById(R.id.spinner_t_MaLop);
        spinnerMaSV = (Spinner) findViewById(R.id.spinner_t_MaSV);
        editHoTen = (EditText) findViewById(R.id.edt_t_HoTen);
        spinnerMaMon = (Spinner) findViewById(R.id.spinner_t_MaMon);
        editDiemCC = (EditText) findViewById(R.id.edt_t_DiemCC);
        editDiemGK = (EditText) findViewById(R.id.edt_t_DiemGK);
        editDiemThi = (EditText) findViewById(R.id.edt_t_DiemThi);

        listView_them = findViewById(R.id.listview_them_monhoc);
        list_them = new ArrayList<>();
        adapter_diem = new Adapter_Diem(this, list_them);
        listView_them.setAdapter(adapter_diem);
    }

    // lấy nguồn combobox mã lớp học------------------------------------------------------------------------------
    private void Spinner_Malop() {
        ArrayList<String> arrayMaLOP =new ArrayList<String>();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT malop FROM tbl_lop ",null);
        arrayMaLOP.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            arrayMaLOP.add(ten);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayMaLOP);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaLOP.setAdapter(arrayAdapter);
    }

    // lấy nguồn combobox mã sv-----------------------------------------------------------------------------------
    private void Spinner_MaSV() {
        String laynguon_malop = spinnerMaLOP.getSelectedItem().toString();
        ArrayList<String> arrayMaSV =new ArrayList<String>();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT masv FROM tbl_sinhvien WHERE malop =? ", new String[]{laynguon_malop+""});
        arrayMaSV.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            arrayMaSV.add(ten);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayMaSV);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaSV.setAdapter(arrayAdapter);
    }

    // lấy nguồn combobox mã môn----------------------------------------------------------------------------------
    private void Spinner_MaMon() {
        ArrayList<String> arrayMaMon =new ArrayList<String>();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT mamon FROM tbl_monhoc ",null);
        arrayMaMon.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            arrayMaMon.add(ten);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayMaMon);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaMon.setAdapter(arrayAdapter);
    }

    //lấy thông tin họ tên-----------------------------------------------------------------------------
    private void LayNguonHoTen() {
        String laynguon_masv = spinnerMaSV.getSelectedItem().toString();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_sinhvien WHERE masv =? ", new String[]{laynguon_masv+""});
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String hoten = cursor.getString(2);
            editHoTen.setText(hoten);
        }
    }

    //lấy dữ liệu từ database đổ lên listview trong activity thêm điểm-------------------------------------------
    private void load_diem_masv(){
        String laynguon_listview_masv = spinnerMaSV.getSelectedItem().toString();
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_diem WHERE masv =? ", new String[]{laynguon_listview_masv+""});
        list_them.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String masv = cursor.getString(0);
            String hoten = cursor.getString(1);
            String mamon = cursor.getString(2);
            String diemcc = cursor.getString(3);
            String diemgk = cursor.getString(4);
            String diemthi = cursor.getString(5);
            String diemtb = cursor.getString(6);
            list_them.add(new Diem(masv, hoten, mamon, diemcc, diemgk, diemthi, diemtb));
        }
        adapter_diem.notifyDataSetChanged(); //adapter vẽ lại giao diện
    }

    // even sự kiện click-----------------------------------------------------------------------------------
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


    private void insert(){
        String hoten = editHoTen.getText().toString();
        String mamon = spinnerMaMon.getSelectedItem().toString();
        String diemcc = editDiemCC.getText().toString();
        String diemgk = editDiemGK.getText().toString();
        String diemthi = editDiemThi.getText().toString();

        if(hoten.equals("") || diemcc.equals("") || diemgk.equals("") || diemthi.equals("")){
            Toast.makeText(this, "Các ô text không đc để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // điều kiện
            double cc = Double.parseDouble(diemcc);
            if(cc < 0 || cc >10) {
                Toast.makeText(this, "Điểm CC không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            double gk = Double.parseDouble(diemgk);
            if(gk < 0 || gk >10) {
                Toast.makeText(this, "Điểm GK không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            double thi = Double.parseDouble(diemthi);
            if(thi < 0 || thi >10) {
                Toast.makeText(this, "Điểm Thi không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            String masv = spinnerMaSV.getSelectedItem().toString();
            database = DataBase.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM tbl_diem WHERE masv =? and mamon=?", new String[]{masv+"",mamon});
            if (cursor.moveToNext()){
                Toast.makeText(this, "Trùng mã môn học", Toast.LENGTH_SHORT).show();
                return;
            } else {
                //tính điểm tb môn---------------------------------------
                double tb = cc*0.1+ gk*0.2+ thi*0.7;

                ContentValues contentValues = new ContentValues();//chèn 1 hàng mới vào bảng trong csdl
                contentValues.put("masv", masv);
                contentValues.put("hoten", hoten);
                contentValues.put("mamon", mamon);
                contentValues.put("diemcc", diemcc);
                contentValues.put("diemgk", diemgk);
                contentValues.put("diemthi", diemthi);
                contentValues.put("diemtb", tb);

                SQLiteDatabase database = DataBase.initDatabase(this, "btl_qly_sinhvien.sqlite");
                database.insert("tbl_diem", null, contentValues);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity_Diem.class);
                startActivity(intent);
            }
        }catch (Exception e) {
            Toast.makeText(this, "Điểm chỉ nhận giá trị số", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity_Diem.class);
        startActivity(intent);
    }
}