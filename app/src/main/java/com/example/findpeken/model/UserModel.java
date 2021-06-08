package com.example.findpeken.model;

public class UserModel {
    private String name, email, gambar;

    public UserModel(String name, String email, String gambar) {
        this.name = name;
        this.email = email;
        this.gambar = gambar;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGambar() {
        return gambar;
    }

}
