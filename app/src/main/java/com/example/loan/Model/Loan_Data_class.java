package com.example.loan.Model;

public class Loan_Data_class {
    int ic_app_icon;
    String interset;
    String month;
    String ruppes;
    String sweetRupee;

    public Loan_Data_class(String ruppes2, int ic_app_icon2, String sweetRupee2, String interset2, String month2) {
        this.ruppes = ruppes2;
        this.ic_app_icon = ic_app_icon2;
        this.sweetRupee = sweetRupee2;
        this.interset = interset2;
        this.month = month2;
    }

    public String getRuppes() {
        return this.ruppes;
    }

    public void setRuppes(String ruppes2) {
        this.ruppes = ruppes2;
    }

    public int getIc_app_icon() {
        return this.ic_app_icon;
    }

    public void setIc_app_icon(int ic_app_icon2) {
        this.ic_app_icon = ic_app_icon2;
    }

    public String getSweetRupee() {
        return this.sweetRupee;
    }

    public void setSweetRupee(String sweetRupee2) {
        this.sweetRupee = sweetRupee2;
    }

    public String getInterset() {
        return this.interset;
    }

    public void setInterset(String interset2) {
        this.interset = interset2;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month2) {
        this.month = month2;
    }
}
