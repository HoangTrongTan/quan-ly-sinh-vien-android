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
import com.example.btl_qly_sinhvien.Model.Diem;
import com.example.btl_qly_sinhvien.Model.Lop;
import com.example.btl_qly_sinhvien.R;
import com.example.btl_qly_sinhvien.Sua_Diem;
import com.example.btl_qly_sinhvien.Sua_lophoc;

import java.util.ArrayList;

public class Adapter_Diem extends BaseAdapter{
    Activity context;
    ArrayList<Diem> list;

    public Adapter_Diem(Activity context, ArrayList<Diem> list) {
        this.context = context;
        this.list = list;
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
        View row = inflater.inflate(R.layout.show_diemthi, null);
        TextView txt_MaSV = (TextView) row.findViewById(R.id.txtmasv);
        TextView txt_TenSV = (TextView) row.findViewById(R.id.txttensv);
        TextView txt_MaMon = (TextView) row.findViewById(R.id.txtmonhoc);
        TextView txt_DiemCC = (TextView) row.findViewById(R.id.txtCC);
        TextView txt_DiemGK = (TextView) row.findViewById(R.id.txtGK);
        TextView txt_DiemThi = (TextView) row.findViewById(R.id.txtThi);
        TextView txt_DiemTB = (TextView) row.findViewById(R.id.txtTB);
        ImageView btnsua = (ImageView) row.findViewById(R.id.btnSua);
        ImageView btnxoa = (ImageView) row.findViewById(R.id.btnXoa);

        Diem mdiem = list.get(i);
        txt_MaSV.setText(mdiem.masv);
        txt_TenSV.setText(mdiem.hoten);
        txt_MaMon.setText(mdiem.mamon);
        txt_DiemCC.setText(mdiem.diemcc);
        txt_DiemGK.setText(mdiem.diemgk);
        txt_DiemThi.setText(mdiem.diemthi);
        txt_DiemTB.setText(mdiem.diemtb);

        //Button Sửa lấy id để sang main sửa để sửa
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sua_Diem.class);
                intent.putExtra("masv", mdiem.masv);
                intent.putExtra("mamon", mdiem.mamon);
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
                        delete(mdiem.masv, mdiem.mamon);
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

    private void delete(String idmasv, String idmamon){
        SQLiteDatabase database = DataBase.initDatabase(context, "btl_qly_sinhvien.sqlite");
        database.delete("tbl_diem", "masv = ? and mamon =?",new String[]{idmasv + "",idmamon + ""});
        list.clear();

        //xóa xong load lại dữ liệu
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_diem",null);
        while (cursor.moveToNext()){
            String masv = cursor.getString(0);
            String hoten = cursor.getString(1);
            String mamon = cursor.getString(2);
            String diemcc = cursor.getString(3);
            String diemgk = cursor.getString(4);
            String diemthi = cursor.getString(5);
            String diemtb = cursor.getString(6);

            list.add(new Diem(masv, hoten, mamon, diemcc, diemgk, diemthi, diemtb));
        }
        notifyDataSetChanged();
    }
}
