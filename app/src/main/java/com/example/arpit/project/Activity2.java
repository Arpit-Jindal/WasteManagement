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
import com.example.arpit.project.util.BinMarker;
import com.example.arpit.project.util.DriverMarker;
import com.example.arpit.project.util.pair;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Activity2 extends FragmentActivity implements OnMapReadyCallback {
    private Button driver,home;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        home = findViewById(R.id.Home);
        driver = findViewById(R.id.Driver);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Activity2.this, MainActivity.class);
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
                    Toast.makeText(getApplicationContext(),"Bin Map",Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment =
                            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(Activity2.this);
                }
            }
        });
    }

    public pair getll(int i){
        pair p = new pair(28.7166278,77.1139979);
        switch(i){
            case 1:
                p.lat = 28.714322;
                p.lon = 77.116545;
                break;
            case 2:
                p.lat = 28.7172435;
                p.lon = 77.1190887;
                break;
            case 3:
                p.lat = 28.7182435;
                p.lon = 77.1195979;
                break;
            case 4:
                p.lat = 28.7204488;
                p.lon = 77.1199755;
                break;
            case 5:
                p.lat = 28.725456;
                p.lon = 77.126711;
                break;
            case 6:
                p.lat = 28.7256676;
                p.lon = 77.1271328;
                break;
            case 7:
                p.lat = 28.7291465;
                p.lon = 77.1305471;
                break;
            case 8:
                p.lat = 28.734726;
                p.lon = 77.137525;
                break;
            case 9:
                p.lat = 28.7298339;
                p.lon = 77.1400939;
                break;
            case 10:
                p.lat = 28.7305858;
                p.lon = 77.1419342;
                break;
        }
        return p;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        double tempLat = 28.711834; //use currentLocation.getLatitude() in real
        double tempLong = 77.1133729; //use currentLocation.getLongitude() in real
        DriverMarker currDriver =
                new DriverMarker(1,100,0,tempLat,tempLong);

        LatLng latLng = new LatLng(currDriver.latitude,currDriver.longitude);

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are Here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .snippet("Current Weight = " + currDriver.currentWeight+ "\nCapacity = " + currDriver.capacity);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
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



//        int noOfBins = 100;
//        BinMarker bins[] = new BinMarker[noOfBins];
//
//        for (int i=0;i<noOfBins;i+=1) {
//            bins[i] = new BinMarker(i+1,5*Math.random() + 5,
//                    currentLocation.getLatitude() + (2*Math.random() - 1)/10,
//                    currentLocation.getLongitude() + (2*Math.random() - 1)/10,
//                    false);
//
//            LatLng latLng2 = new LatLng(bins[i].latitude,bins[i].longitude);
//            MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2).title("BIN " + bins[i].ID)
//                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
//                                            .snippet("Picked Up : " + bins[i].done);
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
//            googleMap.addMarker(markerOptions2);
//        }

        int noOfBins = 10;
        BinMarker bins[] = new BinMarker[noOfBins];

        for (int i=0;i<noOfBins;i+=1) {
            pair p = new pair(getll(i+1).lat,getll(i+1).lon);
            bins[i] = new BinMarker(i+1,5*Math.random() + 5,
                    p.lat, p.lon,
                    false);
            // currentLocation.getLatitude() + (2*Math.random() - 1)/10, currentLocation.getLongitude() + (2*Math.random() - 1)/10,
            LatLng latLng2 = new LatLng(bins[i].latitude,bins[i].longitude);
            MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2).title("BIN " + bins[i].ID)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    .snippet("Picked Up : " + bins[i].done);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
            googleMap.addMarker(markerOptions2);
        }




        LatLng dump = new LatLng(28.740848,77.1538945);
        MarkerOptions mo = new MarkerOptions().position(dump).title("Dumping Ground")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .snippet("House of Trash");
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dump,12));
        googleMap.addMarker(mo);

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(Activity2.this));

//        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                LatLng temp = marker.getPosition();
//                Toast.makeText(Activity2.this, "Latitude = " + temp.latitude + "\nLongitude = " + temp.longitude, Toast.LENGTH_SHORT).show();
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
