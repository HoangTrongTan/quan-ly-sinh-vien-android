package com.example.btl_qly_sinhvien.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_qly_sinhvien.Model.model_danhsachcongno;
import com.example.btl_qly_sinhvien.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_danhsach_congno extends RecyclerView.Adapter<Adapter_danhsach_congno.Congnos_Holder>{

    List<model_danhsachcongno> danhsanh;

    public Adapter_danhsach_congno(List<model_danhsachcongno> danhsanh) {
        this.danhsanh = danhsanh;
    }

    @NonNull
    @Override
    public Congnos_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Congnos_Holder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ds_conno,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull Congnos_Holder holder, int position) {

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        holder.tenmon.setText(danhsanh.get(position).getTenmon());
        holder.sotinchi.setText(danhsanh.get(position).getSotinchi());
        holder.sotien.setText(numberFormat.format(Integer.parseInt(danhsanh.get(position).getSotien())) + " đ");
        holder.tongtien.setText(numberFormat.format(Integer.parseInt(danhsanh.get(position).getTongtien())) + " đ");
        holder.trangthai.setText(danhsanh.get(position).getTrangthai());


    }

    @Override
    public int getItemCount() {
        if(danhsanh != null){
            return danhsanh.size();
        }
        return 0;
    }

    public class Congnos_Holder extends RecyclerView.ViewHolder{
        private TextView tenmon,sotinchi,sotien,tongtien,trangthai;

        public Congnos_Holder(@NonNull View itemView) {
            super(itemView);
            tenmon = itemView.findViewById(R.id.temon);
            sotinchi = itemView.findViewById(R.id.sotinchi);
            sotien = itemView.findViewById(R.id.sotien);
            tongtien = itemView.findViewById(R.id.tongtien);
            trangthai = itemView.findViewById(R.id.trangthai);

        }
    }
}
