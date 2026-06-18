package com.example.btl_qly_sinhvien.Model;

public class MonHoc {
    public String mamon;
    public String tenmon;
    public String sotiet;
    private int isExpandble;

    public MonHoc(String mamon, String tenmon, String sotiet, int isExpandble) {
        this.mamon = mamon;
        this.tenmon = tenmon;
        this.sotiet = sotiet;
        this.isExpandble = isExpandble;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getSotiet() {
        return sotiet;
    }

    public void setSotiet(String sotiet) {
        this.sotiet = sotiet;
    }

    public int isExpandble() {
        return isExpandble;
    }

    public void setExpandble(int expandble) {
        isExpandble = expandble;
    }
}
