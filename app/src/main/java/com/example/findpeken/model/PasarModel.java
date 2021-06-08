package com.example.findpeken.model;


import java.io.Serializable;

public class PasarModel implements Serializable {
    private int pasar_id;
    private String pasar_nama;
    private String pasar_deskripsi;
    private String pasar_alamat;
    private double latitude;
    private double longitude;
    private String pasar_gambar;


    public PasarModel(int pasar_id, String pasar_nama, String pasar_deskripsi, String pasar_alamat,
                      double latitude, double longitude, String pasar_gambar) {
        this.pasar_id = pasar_id;
        this.pasar_nama = pasar_nama;
        this.pasar_deskripsi = pasar_deskripsi;
        this.pasar_alamat = pasar_alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pasar_gambar = pasar_gambar;
    }

    public int getPasar_id() {
        return pasar_id;
    }

    public String getPasar_nama() {
        return pasar_nama;
    }

    public String getPasar_deskripsi() {
        return pasar_deskripsi;
    }

    public String getPasar_alamat() {
        return pasar_alamat;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPasar_gambar() {
        return pasar_gambar;
    }
}
