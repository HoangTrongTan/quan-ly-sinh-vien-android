package com.example.btl_qly_sinhvien.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl_qly_sinhvien.Database.DataBase;
import com.example.btl_qly_sinhvien.Model.ChuyenNganh;
import com.example.btl_qly_sinhvien.Model.KhoaHoc;
import com.example.btl_qly_sinhvien.R;
import com.example.btl_qly_sinhvien.Sua_chuyennganh;
import com.example.btl_qly_sinhvien.Sua_khoahoc;

import java.util.ArrayList;

public class Adapter_KhoaHoc extends BaseAdapter implements Filterable {

    Activity context;
    ArrayList<KhoaHoc> list;
    ArrayList<KhoaHoc> listOld;

    public Adapter_KhoaHoc(Activity context, ArrayList<KhoaHoc> list) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Lấy thông tin từ database vừa lấy bên manin đổ vào text hiển thị lên
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.show_khoahoc, null);
        TextView txtMaKhoa = (TextView) row.findViewById(R.id.txtMaKhoa);
        TextView txtTenKhoa = (TextView) row.findViewById(R.id.txtTenKhoa);
        TextView txtNienKhoa = (TextView) row.findViewById(R.id.txtNienKhoa);
        ImageView btnsua = (ImageView) row.findViewById(R.id.btnSua);
        ImageView btnxoa = (ImageView) row.findViewById(R.id.btnXoa);

        KhoaHoc khoaHoc = list.get(i);
        txtMaKhoa.setText(khoaHoc.makhoa);
        txtTenKhoa.setText(khoaHoc.tenkhoa);
        txtNienKhoa.setText(khoaHoc.nienkhoa);

        //Button Sửa
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sua_khoahoc.class);
                intent.putExtra("makhoa", khoaHoc.makhoa);
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
                        delete(khoaHoc.makhoa);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(String idmakhoa){
        SQLiteDatabase database = DataBase.initDatabase(context, "btl_qly_sinhvien.sqlite");
        database.delete("tbl_khoa", "makhoa = ?",new String[]{idmakhoa + ""});
        list.clear();

        Cursor cursor = database.rawQuery("SELECT * FROM tbl_khoa",null);
        while (cursor.moveToNext()){
            String makhoa = cursor.getString(0);
            String tenkhoa = cursor.getString(1);
            String nienkhoa = cursor.getString(2);

            list.add(new KhoaHoc(makhoa, tenkhoa, nienkhoa));
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
                    ArrayList<KhoaHoc> mlist = new ArrayList<>();
                    for (KhoaHoc khoaHoc : listOld){
                        // tên của sách chứa các ký tự mà search sẽ add vào list rồi hiển thị
                        if (khoaHoc.getTenkhoa().toLowerCase().contains(strSearch.toLowerCase())){
                            mlist.add(khoaHoc);
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
                list = (ArrayList<KhoaHoc>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
