package com.example.btl_qly_sinhvien.Model;

public class Lop {
    public String malop;
    public String makhoa;
    public String manganh;
    public String tenlop;
    public String gvcn;

    public Lop(String malop, String makhoa, String manganh, String tenlop, String gvcn) {
        this.malop = malop;
        this.makhoa = makhoa;
        this.manganh = manganh;
        this.tenlop = tenlop;
        this.gvcn = gvcn;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    public String getManganh() {
        return manganh;
    }

    public void setManganh(String manganh) {
        this.manganh = manganh;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getGvcn() {
        return gvcn;
    }

    public void setGvcn(String gvcn) {
        this.gvcn = gvcn;
    }
}
