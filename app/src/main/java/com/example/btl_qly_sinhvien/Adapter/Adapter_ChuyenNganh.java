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
import com.example.btl_qly_sinhvien.R;
import com.example.btl_qly_sinhvien.Sua_chuyennganh;

import java.util.ArrayList;

public class Adapter_ChuyenNganh extends BaseAdapter implements Filterable {

    Activity context;
    ArrayList<ChuyenNganh> list;
    ArrayList<ChuyenNganh> listOld;

    public Adapter_ChuyenNganh(Activity context, ArrayList<ChuyenNganh> list) {
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
        View row = inflater.inflate(R.layout.show_chuyennganh, null);
        TextView txtMaNganh = (TextView) row.findViewById(R.id.txtMaNganh);
        TextView txtTenNganh = (TextView) row.findViewById(R.id.txtTenNganh);
        ImageView btnsua = (ImageView) row.findViewById(R.id.btnSua);
        ImageView btnxoa = (ImageView) row.findViewById(R.id.btnXoa);

        ChuyenNganh chuyenNganh = list.get(i);
        txtMaNganh.setText(chuyenNganh.manganh);
        txtTenNganh.setText(chuyenNganh.tennganh);

        //Button Sửa
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sua_chuyennganh.class);
                intent.putExtra("manganh", chuyenNganh.manganh);
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
                                delete(chuyenNganh.manganh);
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

    //sự kiện xóa
    private void delete(String idmanganh){
        SQLiteDatabase database = DataBase.initDatabase(context, "btl_qly_sinhvien.sqlite");
        database.delete("tbl_chuyennganh", "manganh = ?",new String[]{idmanganh + ""});
        list.clear();

        Cursor cursor = database.rawQuery("SELECT * FROM tbl_chuyennganh",null);
        while (cursor.moveToNext()){
            String manganh = cursor.getString(0);
            String tennganh = cursor.getString(1);

            list.add(new ChuyenNganh(manganh, tennganh));
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
                    ArrayList<ChuyenNganh> mlist = new ArrayList<>();
                    for (ChuyenNganh chuyenNganh : listOld){
                        // tên của sách chứa các ký tự mà search sẽ add vào list rồi hiển thị
                        if (chuyenNganh.getTennganh().toLowerCase().contains(strSearch.toLowerCase())){
                            mlist.add(chuyenNganh);
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
                list = (ArrayList<ChuyenNganh>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
