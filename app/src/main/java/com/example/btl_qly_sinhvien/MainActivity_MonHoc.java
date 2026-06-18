package com.example.btl_qly_sinhvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.example.btl_qly_sinhvien.Adapter.Adapter_DK_hocphan;
import com.example.btl_qly_sinhvien.Adapter.Adapter_KhoaHoc;
import com.example.btl_qly_sinhvien.Adapter.Adapter_MonHoc;
import com.example.btl_qly_sinhvien.Database.DataBase;

import com.example.btl_qly_sinhvien.Model.MonHoc;

import java.util.ArrayList;

public class MainActivity_MonHoc extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    com.google.android.material.floatingactionbutton.FloatingActionButton btnAdd;
    com.google.android.material.floatingactionbutton.FloatingActionButton btnHome;
    ListView listView;
    ArrayList<MonHoc> list;
    Adapter_MonHoc adapter_monHoc;
    SearchView searchView;
    public static RecyclerView recyckle_dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mon_hoc);
        recyckle_dangky = findViewById(R.id.recyckle_dangky);


        listView = findViewById(R.id.listview_monhoc);
        list = new ArrayList<>();
        adapter_monHoc = new Adapter_MonHoc(this, list);
        listView.setAdapter(adapter_monHoc);



        Click_home();
        Chick_Them();
        redata();
    }

    private void Click_home() {
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_MonHoc.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Chick_Them() {
        btnAdd = findViewById(R.id.btn_themmonhoc);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_MonHoc.this, Add_MonHoc.class);
                startActivity(intent);
            }
        });
    }

    //lấy dữ liệu từ database đổ lên listview
    private void redata(){
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_monhoc ",null);
        list.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String mamon = cursor.getString(0);
            String tenmon = cursor.getString(1);
            String sotiet = cursor.getString(2);
            list.add(new MonHoc(mamon, tenmon, sotiet,1));
        }
        adapter_monHoc.notifyDataSetChanged(); //adapter vẽ lại giao diện
    }

    //thay menu cũ thành menu search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.btn_search).getActionView();
        //căn chỉnh menu cho đẹp (thanh search ôm chọn máy)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter_monHoc.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_monHoc.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}