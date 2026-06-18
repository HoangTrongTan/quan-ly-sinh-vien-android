package com.example.btl_qly_sinhvien.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_qly_sinhvien.Controller.controll;
import com.example.btl_qly_sinhvien.Database.DataBase;
import com.example.btl_qly_sinhvien.MainActivity;
import com.example.btl_qly_sinhvien.MainActivity_MonHoc;
import com.example.btl_qly_sinhvien.Model.KhoaHoc;
import com.example.btl_qly_sinhvien.Model.MonHoc;
import com.example.btl_qly_sinhvien.R;
import com.example.btl_qly_sinhvien.Sua_MonHoc;
import com.example.btl_qly_sinhvien.Sua_khoahoc;
import com.example.btl_qly_sinhvien.model_dkhp;

import java.util.ArrayList;

public class Adapter_MonHoc extends BaseAdapter implements Filterable {
    Activity context;
    ArrayList<MonHoc> list;
    ArrayList<MonHoc> listOld;


    public Adapter_MonHoc(Activity context, ArrayList<MonHoc> list) {
        this.context = context;
        this.list = list;
        this.listOld = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Lấy thông tin từ database vừa lấy bên manin đổ vào text hiển thị lên
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.show_monhoc, null);
        TextView txtMaMon = (TextView) row.findViewById(R.id.txtMaMon);
        TextView txtTenMon = (TextView) row.findViewById(R.id.txtTenMon);
        TextView txtSoTiet = (TextView) row.findViewById(R.id.txtSoTiet);
        ImageView btnsua = (ImageView) row.findViewById(R.id.btnSua);
        ImageView btnxoa = (ImageView) row.findViewById(R.id.btnXoa);
        LinearLayout linearlayout = row.findViewById(R.id.linearlayout);
        Button expandble_dangky = row.findViewById(R.id.expandble_dangky);



        MonHoc monHoc = list.get(i);
        txtMaMon.setText(monHoc.mamon);
        txtTenMon.setText(monHoc.tenmon);
        txtSoTiet.setText(monHoc.sotiet);

//        expandble_dangky.setVisibility(monHoc.isExpandble() == 1 ? View.VISIBLE : View.GONE);

        //Đăng ký
        expandble_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = DataBase.initDatabase(context, "btl_qly_sinhvien.sqlite");
                ContentValues values = new ContentValues();
                values.put("check_dangky", 0);

                String selection = "mamon = ?";
                String[] selectionArgs = {list.get(i).getMamon()};

                int count = db.update("tbl_monhoc", values, selection, selectionArgs);
                if(count > 0){
                    monHoc.setExpandble(0);
                    Toast.makeText(view.getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                }

                int isExpandble = monHoc.isExpandble();
                if(isExpandble == 0){
                    expandble_dangky.setVisibility(View.GONE);
                    MonHoc monHoc1 = list.get(i);
                    monHoc1.setExpandble(0);
                }
                ArrayList<model_dkhp> list_dk = new ArrayList<>();
                controll.dangky_json(view.getContext(), monHoc.tenmon, MainActivity_MonHoc.recyckle_dangky,list_dk);
            }
        });

        //Button Sửa
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sua_MonHoc.class);
                intent.putExtra("mamon", monHoc.mamon);
                context.startActivity(intent);
            }
        });

        //Button Xóa
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa sách này không?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(monHoc.mamon);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String sql = "ALTER TABLE tbl_monhoc ADD COLUMN check_dangky INTEGER DEFAULT 1;";
//                        SQLiteDatabase sqLiteDatabase = DataBase.initDatabase(context, "btl_qly_sinhvien.sqlite");
//                        sqLiteDatabase.execSQL(sql);
                        return;

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(String idmamon){
        SQLiteDatabase database = DataBase.initDatabase(context, "btl_qly_sinhvien.sqlite");
        database.delete("tbl_monhoc", "mamon = ?",new String[]{idmamon + ""});
        list.clear();

        Cursor cursor = database.rawQuery("SELECT * FROM tbl_monhoc",null);
        while (cursor.moveToNext()){
            String mamon = cursor.getString(0);
            String tenmon = cursor.getString(1);
            String sotiet = cursor.getString(2);

            list.add(new MonHoc(mamon, tenmon, sotiet,1));
        }
        notifyDataSetChanged();
    }

    //tìm kiếm
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                } else {
                    ArrayList<MonHoc> mlist = new ArrayList<>();
                    for (MonHoc monHoc : listOld){
                        // tên của sách chứa các ký tự mà search sẽ add vào list rồi hiển thị
                        if (monHoc.getTenmon().toLowerCase().contains(strSearch.toLowerCase())){
                            mlist.add(monHoc);
                        }
                    }
                    list = mlist;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<MonHoc>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
