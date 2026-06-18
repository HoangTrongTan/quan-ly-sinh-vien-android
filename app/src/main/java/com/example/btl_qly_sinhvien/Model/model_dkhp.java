package com.example.btl_qly_sinhvien;

public class model_dkhp {
    private String tenmon,tinchi,lop,phong,thoigian,giatien,giaovien;

    public model_dkhp(String tenmon, String tinchi, String lop, String phong, String thoigian, String giatien, String giaovien) {
        this.tenmon = tenmon;
        this.tinchi = tinchi;
        this.lop = lop;
        this.phong = phong;
        this.thoigian = thoigian;
        this.giatien = giatien;
        this.giaovien = giaovien;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getTinchi() {
        return tinchi;
    }

    public void setTinchi(String tinchi) {
        this.tinchi = tinchi;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }

    public String getGiaovien() {
        return giaovien;
    }

    public void setGiaovien(String giaovien) {
        this.giaovien = giaovien;
    }
}
