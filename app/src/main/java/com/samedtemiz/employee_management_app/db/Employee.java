package com.samedtemiz.employee_management_app.db;

import java.io.Serializable;

public class Employee implements Serializable {

    private int id;
    private String ad;
    private String soyad;
    private String pozisyon;
    private String departman;
    private String telNo;
    private String eposta;
    private byte[] gorsel;



    public Employee(String ad, String soyad, String pozisyon, String departman, String telNo, String eposta, byte[] gorsel) {
        this.ad = ad;
        this.soyad = soyad;
        this.pozisyon = pozisyon;
        this.departman = departman;
        this.telNo = telNo;
        this.eposta = eposta;
        this.gorsel = gorsel;
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

    public byte[] getGorsel() {
        return gorsel;
    }

    public void setGorsel(byte[] gorsel) {
        this.gorsel = gorsel;
    }
}
