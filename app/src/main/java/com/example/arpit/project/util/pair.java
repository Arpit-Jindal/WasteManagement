package com.example.arpit.project.util;

public class pair {
    public double lat;
    public double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public pair(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
