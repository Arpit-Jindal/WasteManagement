package com.example.arpit.project;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arpit.project.Adapter.CustomInfoWindowAdapter;
import com.example.arpit.project.util.DriverMarker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private Button bin,home;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        bin = findViewById(R.id.Bin);
        home = findViewById(R.id.Home);
        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, HomeActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                finish();
            }
        });
    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),"Driver Map",Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment =
                            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        double tempLat = 28.711834; //use currentLocation.getLatitude() in real
        double tempLong = 77.1133729; //use currentLocation.getLongitude() in real
        DriverMarker currDriver =
                new DriverMarker(1,100,0,tempLat,tempLong);

        LatLng latLng = new LatLng(currDriver.latitude,currDriver.longitude);

//        DriverMarker currDriver =
//                new DriverMarker(0,100,0,currentLocation.getLatitude(),currentLocation.getLongitude());

//        LatLng latLng = new LatLng(currDriver.latitude,currDriver.longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are Here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .snippet("Current Weight = " + currDriver.currentWeight + "\nCapacity = " + currDriver.capacity);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        googleMap.addMarker(markerOptions);

        googleMap.addCircle(
                new CircleOptions()
                        .center(latLng)
                        .radius(500.0)
                        .strokeWidth(3f)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.argb(50,50,50,150))
        );

        googleMap.getUiSettings().isCompassEnabled();
        int noOfDrivers = 20;
        DriverMarker[] driver = new DriverMarker[noOfDrivers];


        Random gen = new Random(4);


        for (int i=0;i<noOfDrivers;i+=1) {
            driver[i] = new DriverMarker
                    (i+1,100,0,
                            tempLat + (2*gen.nextDouble() - 1)/10,
                            tempLong + (2*gen.nextDouble() - 1)/10);
            if(i!= 10 && i!=19 &&i!=12 && i!=8) {
                LatLng latLng2 = new LatLng(driver[i].latitude, driver[i].longitude);
                MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2).title("Driver " + driver[i].ID)
                        .snippet("Current Weight = " + driver[i].currentWeight + "\nCapacity = " + driver[i].capacity);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
                googleMap.addMarker(markerOptions2);
            }
        }
        LatLng l1 = new LatLng(driver[10].latitude,driver[10].longitude);
        MarkerOptions m1 = new MarkerOptions().position(l1).title("Cluster 1").snippet("Driver 1\nDriver 2\nDriver 4\nDriver 7\nDriver 12\nDriver 15\nDriver 19")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l1,12));
        googleMap.addMarker(m1);

        LatLng l2 = new LatLng(driver[12].latitude,driver[12].longitude);
        MarkerOptions m2 = new MarkerOptions().position(l2).title("Cluster 2").snippet("Driver 3\nDriver 6\nDriver 8\nDriver 10\nDriver 17\nDriver 18")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l2,12));
        googleMap.addMarker(m2);

        LatLng l3 = new LatLng(driver[8].latitude,driver[8].longitude);
        MarkerOptions m3 = new MarkerOptions().position(l3).title("Cluster 3").snippet("Driver 0\nDriver 5\nDriver 14\nDriver 16")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l3,12));
        googleMap.addMarker(m3);



        LatLng dump = new LatLng(28.740848,77.1538945);
        MarkerOptions mo = new MarkerOptions().position(dump).title("Dumping Ground")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet("House of Trash");
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dump,12));
        googleMap.addMarker(mo);

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MainActivity.this));

//        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                LatLng temp = marker.getPosition();
//                Toast.makeText(MainActivity.this, "Latitude = " + temp.latitude + "\nLongitude = " + temp.longitude, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
}
