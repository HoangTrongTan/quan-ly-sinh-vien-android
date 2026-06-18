package com.example.btl_qly_sinhvien.Model;

public class KhoaHoc {
    public String makhoa;
    public String tenkhoa;
    public String nienkhoa;

    public KhoaHoc(String makhoa, String tenkhoa, String nienkhoa) {
        this.makhoa = makhoa;
        this.tenkhoa = tenkhoa;
        this.nienkhoa = nienkhoa;
    }

    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    public String getTenkhoa() {
        return tenkhoa;
    }

    public void setTenkhoa(String tenkhoa) {
        this.tenkhoa = tenkhoa;
    }

    public String getNienkhoa() {
        return nienkhoa;
    }

    public void setNienkhoa(String nienkhoa) {
        this.nienkhoa = nienkhoa;
    }
}
