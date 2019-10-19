package com.example.arpit.project.Models;

import com.example.arpit.project.util.BinMarker;
import com.example.arpit.project.util.DriverMarker;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {
    private LatLng position;
    private String title;
    private String snippet;
    private int iconPicture;
    private BinMarker bin;
    private DriverMarker driver;

    @Override
    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getIconPicture() {
        return iconPicture;
    }

    public void setIconPicture(int iconPicture) {
        this.iconPicture = iconPicture;
    }

    public BinMarker getBin() {
        return bin;
    }

    public void setBin(BinMarker bin) {
        this.bin = bin;
    }

    public DriverMarker getDriver() {
        return driver;
    }

    public void setDriver(DriverMarker driver) {
        this.driver = driver;
    }

    public ClusterMarker() {
    }

    public ClusterMarker(LatLng position, String title, String snippet, int iconPicture, BinMarker bin, DriverMarker driver) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.iconPicture = iconPicture;
        this.bin = bin;
        this.driver = driver;
    }
}
