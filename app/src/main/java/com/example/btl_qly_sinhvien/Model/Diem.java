package com.example.btl_qly_sinhvien.Model;

public class Diem {
    public String masv;
    public String hoten;
    public String mamon;
    public String diemcc;
    public String diemgk;
    public String diemthi;
    public String diemtb;

    public Diem(String masv, String hoten, String mamon, String diemcc, String diemgk, String diemthi, String diemtb) {
        this.masv = masv;
        this.hoten = hoten;
        this.mamon = mamon;
        this.diemcc = diemcc;
        this.diemgk = diemgk;
        this.diemthi = diemthi;
        this.diemtb = diemtb;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getDiemcc() {
        return diemcc;
    }

    public void setDiemcc(String diemcc) {
        this.diemcc = diemcc;
    }

    public String getDiemgk() {
        return diemgk;
    }

    public void setDiemgk(String diemgk) {
        this.diemgk = diemgk;
    }

    public String getDiemthi() {
        return diemthi;
    }

    public void setDiemthi(String diemthi) {
        this.diemthi = diemthi;
    }

    public String getDiemtb() {
        return diemtb;
    }

    public void setDiemtb(String diemtb) {
        this.diemtb = diemtb;
    }
}
