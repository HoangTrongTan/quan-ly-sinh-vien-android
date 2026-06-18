package com.example.btl_qly_sinhvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_qly_sinhvien.Adapter.Adapter_KhoaHoc;
import com.example.btl_qly_sinhvien.Adapter.Adapter_Lop;
import com.example.btl_qly_sinhvien.Controller.controll;
import com.example.btl_qly_sinhvien.Database.DataBase;
import com.example.btl_qly_sinhvien.Model.KhoaHoc;
import com.example.btl_qly_sinhvien.Model.Lop;

import java.util.ArrayList;

public class MainActivity_Lop extends AppCompatActivity {
    final String DATABASE_NAME = "btl_qly_sinhvien.sqlite";
    SQLiteDatabase database;

    com.google.android.material.floatingactionbutton.FloatingActionButton btnAdd;
    com.google.android.material.floatingactionbutton.FloatingActionButton btnHome;
    ListView listView;
    ArrayList<Lop> list;
    Adapter_Lop adapter_lop;
    SearchView searchView;
    RecyclerView listview_danhsachcongno;
    LinearLayout linearlayout_expandble;
    boolean isExpandble = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lop);
        Toast.makeText(this, getIntent().getStringExtra("email"), Toast.LENGTH_SHORT).show();
        TextView tonghocphi = findViewById(R.id.tonghocphi);
        TextView dadong = findViewById(R.id.dadong);
        TextView conno = findViewById(R.id.conno);
        controll.setCongno(this,tonghocphi,dadong,conno,getIntent().getStringExtra("email"));
        listview_danhsachcongno = findViewById(R.id.listview_danhsachcongno);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        listview_danhsachcongno.setLayoutManager(linearLayoutManager);
        controll.setDanhsachCongNo(MainActivity_Lop.this,listview_danhsachcongno,getIntent().getStringExtra("email"));
        Click_home();
        Chick_Them();
        linearlayout_expandble = findViewById(R.id.linearlayout_expandble);
        listview_danhsachcongno.setVisibility(isExpandble ? View.VISIBLE : View.GONE);

        linearlayout_expandble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet set = new AnimatorSet();
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(listview_danhsachcongno,"scaleY",0f,1f);
                scaleY.setDuration(500);

                set.playTogether(scaleY);
                set.start();

                isExpandble = !isExpandble;
                listview_danhsachcongno.setVisibility(isExpandble ? View.VISIBLE : View.GONE);
                Toast.makeText(MainActivity_Lop.this, String.valueOf(isExpandble), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Click_home() {
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_Lop.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //sự kiện onclik nút thêm---------------------------------------
    private void Chick_Them() {
        btnAdd = findViewById(R.id.btn_themlophoc);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_Lop.this, Add_lophoc.class);
                startActivity(intent);
            }
        });
    }

    //lấy dữ liệu từ database đổ lên listview
    private void redata(){
        database = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_lop ",null);
        list.clear();
        for (int i = 0; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String malop = cursor.getString(0);
            String makhoa = cursor.getString(1);
            String manganh = cursor.getString(2);
            String tenlop = cursor.getString(3);
            String gvcn = cursor.getString(4);
            list.add(new Lop(malop, makhoa, manganh, tenlop, gvcn));
        }
        adapter_lop.notifyDataSetChanged(); //adapter vẽ lại giao diện
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
                adapter_lop.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_lop.getFilter().filter(newText);
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