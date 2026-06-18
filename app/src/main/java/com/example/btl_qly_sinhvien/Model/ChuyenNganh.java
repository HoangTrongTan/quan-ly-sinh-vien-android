package com.example.btl_qly_sinhvien.Model;

public class ChuyenNganh {
    public String manganh;
    public String tennganh;

    public ChuyenNganh(String manganh, String tennganh) {
        this.manganh = manganh;
        this.tennganh = tennganh;
    }

    public String getManganh() {
        return manganh;
    }

    public void setManganh(String manganh) {
        this.manganh = manganh;
    }

    public String getTennganh() {
        return tennganh;
    }

    public void setTennganh(String tennganh) {
        this.tennganh = tennganh;
    }
}
