package com.example.arpit.project.util;

public class DriverMarker {
    public int ID;
    public int capacity;
    public int currentWeight;
    public double latitude;
    public double longitude;

    public DriverMarker(int ID, int capacity, int currentWeight, double latitude, double longitude) {
        this.ID = ID;
        this.capacity = capacity;
        this.currentWeight = currentWeight;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
