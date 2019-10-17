package com.example.arpit.project.util;

public class BinMarker {
    public int ID;
    public double capacity;
    public double latitude;
    public double longitude;
    public boolean done;

    public BinMarker(int ID, double capacity, double latitude, double longitude, boolean done) {
        this.ID = ID;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.done = done;
    }
}
