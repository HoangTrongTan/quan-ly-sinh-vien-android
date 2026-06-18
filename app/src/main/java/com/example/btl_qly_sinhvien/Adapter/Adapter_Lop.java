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
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl_qly_sinhvien.Database.DataBase;
import com.example.btl_qly_sinhvien.Model.KhoaHoc;
import com.example.btl_qly_sinhvien.Model.Lop;
import com.example.btl_qly_sinhvien.R;
import com.example.btl_qly_sinhvien.Sua_khoahoc;
import com.example.btl_qly_sinhvien.Sua_lophoc;

import java.util.ArrayList;

public class Adapter_Lop extends BaseAdapter implements Filterable {
    Activity context;
    ArrayList<Lop> list;
    ArrayList<Lop> listOld;

    public Adapter_Lop(Activity context, ArrayList<Lop> list) {
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
        View row = inflater.inflate(R.layout.show_lophoc, null);
        TextView txt_MaLop = (TextView) row.findViewById(R.id.txtMaLop);
        TextView txt_MaKhoa = (TextView) row.findViewById(R.id.txtMaKhoa);
        TextView txt_MaNganh = (TextView) row.findViewById(R.id.txtMaNganh);
        TextView txt_TenLop = (TextView) row.findViewById(R.id.txtTenLop);
        TextView txt_GVCN = (TextView) row.findViewById(R.id.txtGVCN);
        Button btnsua = (Button) row.findViewById(R.id.btnSua);
        Button btnxoa = (Button) row.findViewById(R.id.btnXoa);

        Lop mlop = list.get(i);
        txt_MaLop.setText(mlop.malop);
        txt_MaKhoa.setText(mlop.makhoa);
        txt_MaNganh.setText(mlop.manganh);
        txt_TenLop.setText(mlop.tenlop);
        txt_GVCN.setText(mlop.gvcn);

        //Button Sửa lấy id để sang main sửa để sửa
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sua_lophoc.class);
                intent.putExtra("malop", mlop.malop);
                context.startActivity(intent);
            }
        });

        //Button Xóa (thực hiện nút xóa)
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
                        delete(mlop.malop);
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

    private void delete(String idmalop){
        SQLiteDatabase database = DataBase.initDatabase(context, "btl_qly_sinhvien.sqlite");
        database.delete("tbl_lop", "malop = ?",new String[]{idmalop + ""});
        list.clear();

        //xóa xong load lại dữ liệu
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_lop",null);
        while (cursor.moveToNext()){
            String malop = cursor.getString(0);
            String makhoa = cursor.getString(1);
            String manganh = cursor.getString(2);
            String tenlop = cursor.getString(3);
            String gvcn = cursor.getString(4);

            list.add(new Lop(malop, makhoa, manganh, tenlop, gvcn));
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                } else {
                    ArrayList<Lop> mlist = new ArrayList<>();
                    for (Lop lop : listOld){
                        // tên của sách chứa các ký tự mà search sẽ add vào list rồi hiển thị
                        if (lop.getTenlop().toLowerCase().contains(strSearch.toLowerCase())){
                            mlist.add(lop);
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
                list = (ArrayList<Lop>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
