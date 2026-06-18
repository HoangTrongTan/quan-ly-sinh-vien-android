package com.example.btl_qly_sinhvien.Model;

public class model_danhsachcongno {
    private String tenmon,sotinchi,sotien,tongtien,trangthai;

    public model_danhsachcongno(String tenmon, String sotinchi, String sotien, String tongtien, String trangthai) {
        this.tenmon = tenmon;
        this.sotinchi = sotinchi;
        this.sotien = sotien;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getSotinchi() {
        return sotinchi;
    }

    public void setSotinchi(String sotinchi) {
        this.sotinchi = sotinchi;
    }

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
