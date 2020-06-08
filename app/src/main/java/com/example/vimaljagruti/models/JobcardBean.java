package com.example.vimaljagruti.models;

public class JobcardBean {
    int JCID;
    String DATE,PHONE,ISSUES,PRICES,TOTAL;

    public JobcardBean(int JCID, String DATE, String PHONE, String ISSUES, String PRICES, String TOTAL) {
        this.JCID = JCID;
        this.DATE = DATE;
        this.PHONE = PHONE;
        this.ISSUES = ISSUES;
        this.PRICES = PRICES;
        this.TOTAL = TOTAL;
    }


    public int getJCID() {
        return JCID;
    }

    public void setJCID(int JCID) {
        this.JCID = JCID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getISSUES() {
        return ISSUES;
    }

    public void setISSUES(String ISSUES) {
        this.ISSUES = ISSUES;
    }

    public String getPRICES() {
        return PRICES;
    }

    public void setPRICES(String PRICES) {
        this.PRICES = PRICES;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }
}
