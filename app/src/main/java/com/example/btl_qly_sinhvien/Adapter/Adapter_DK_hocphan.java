package com.example.btl_qly_sinhvien.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_qly_sinhvien.Controller.controll;
import com.example.btl_qly_sinhvien.MainActivity;
import com.example.btl_qly_sinhvien.MainActivity_MonHoc;
import com.example.btl_qly_sinhvien.R;
import com.example.btl_qly_sinhvien.model_dkhp;

import java.util.ArrayList;

public class Adapter_DK_hocphan extends RecyclerView.Adapter<Adapter_DK_hocphan.DKHP_Holder>{

    private ArrayList<model_dkhp> list_dkhp;

    public Adapter_DK_hocphan(ArrayList<model_dkhp> list_dkhp) {
        this.list_dkhp = list_dkhp;
    }

    @NonNull
    @Override
    public DKHP_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DKHP_Holder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dangkyhocphan_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DKHP_Holder holder, int position) {
        holder.tenmon.setText(list_dkhp.get(position).getTenmon());
        holder.tinchi.setText("Tín chỉ: "+list_dkhp.get(position).getTinchi());
        holder.lop.setText("lớp: "+list_dkhp.get(position).getLop());
        holder.phong.setText(list_dkhp.get(position).getPhong());
        holder.thoigian.setText(list_dkhp.get(position).getThoigian());
        holder.giatien.setText("giá tiền: "+list_dkhp.get(position).getGiatien() +" $");
        holder.giaovien.setText(list_dkhp.get(position).getGiaovien());

        int vitri = position;
        holder.expandblehuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll.Delete_dkhop(view.getContext(), list_dkhp.get(vitri).getTenmon(), MainActivity_MonHoc.recyckle_dangky);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list_dkhp != null){
            return list_dkhp.size();
        }
        return 0;
    }

    public class DKHP_Holder extends RecyclerView.ViewHolder{
        private TextView tenmon,tinchi,lop,phong,thoigian,giatien,giaovien;
        private Button expandblehuy;
        public DKHP_Holder(@NonNull View itemView) {
            super(itemView);
            tenmon = itemView.findViewById(R.id.tenmon);
            tinchi = itemView.findViewById(R.id.tinchi);
            lop = itemView.findViewById(R.id.lop);
            phong = itemView.findViewById(R.id.phong);
            thoigian = itemView.findViewById(R.id.thoigian);
            giatien = itemView.findViewById(R.id.giatien);
            giaovien = itemView.findViewById(R.id.giaovien);

            expandblehuy = itemView.findViewById(R.id.expandblehuy);
        }
    }
}
