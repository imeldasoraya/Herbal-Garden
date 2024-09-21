package com.example.herbalgarden.model;

public class Data {
    private String id, nama, manfaat;

    public Data() {

    }

    public Data(String id, String nama, String manfaat) {
        this.id = id;
        this.nama = nama;
        this.manfaat = manfaat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getManfaat() {
        return manfaat;
    }

    public void setManfaat(String manfaat) {
        this.manfaat = manfaat;
    }
}
