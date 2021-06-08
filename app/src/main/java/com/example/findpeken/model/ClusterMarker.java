package com.example.findpeken.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {
    public LatLng position;
    public String title;
    public String snippet;
    public int image;
    public PasarModel pasarModel;

    public ClusterMarker() {
    }

    public ClusterMarker(LatLng position, String title, String snippet, int image, PasarModel pasarModel) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.image = image;
        this.pasarModel = pasarModel;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public PasarModel getPasarModel() {
        return pasarModel;
    }

    public void setPasarModel(PasarModel pasarModel) {
        this.pasarModel = pasarModel;
    }


    @Override
    public LatLng getPosition() {
        return this.position;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getSnippet() {
        return this.snippet;
    }
}
