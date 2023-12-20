package com.samedtemiz.employee_management_app.db;

public class Employee {

    private int id;
    private String ad;
    private String soyad;
    private String pozisyon;
    private String departman;
    private String telNo;
    private String eposta;
//    private String gorselYolu;

    public Employee(int id, String ad, String soyad, String pozisyon, String departman, String telNo, String eposta) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.pozisyon = pozisyon;
        this.departman = departman;
        this.telNo = telNo;
        this.eposta = eposta;
//        this.gorselYolu = gorselYolu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getPozisyon() {
        return pozisyon;
    }

    public void setPozisyon(String pozisyon) {
        this.pozisyon = pozisyon;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

//    public String getGorselYolu() {
//        return gorselYolu;
//    }
//
//    public void setGorselYolu(String gorselYolu) {
//        this.gorselYolu = gorselYolu;
//    }
}
