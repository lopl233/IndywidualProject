package com.example.mikolaj.pi;

import android.annotation.SuppressLint;
        import android.content.Context;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.util.Log;

public class LocationTracker implements LocationListener {

    private LocationManager locationManager;
    private final int MILISECONDS_BETWEEN_UPDATES = 0;
    private final int MIN_DISTANCE = 0;
    private double longitude = 0;
    private double latitude = 0;
    private static LocationTracker instance;

    public static LocationTracker getInstance(Context context) {
        if (instance != null) {
            return instance;
        }
        return instance = new LocationTracker(context);
    }

    @SuppressLint("MissingPermission")
    private LocationTracker(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MILISECONDS_BETWEEN_UPDATES, MIN_DISTANCE, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }

    @SuppressLint("MissingPermission")
    public void startUpdates() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MILISECONDS_BETWEEN_UPDATES, 5, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }

    public void stopUpdates() {
        locationManager.removeUpdates(this);
    }

    private void updateLoc() {
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }

    public double getLongitude() {
        updateLoc();
        return longitude;
    }

    public double getLatitude() {
        updateLoc();
        return latitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("TRACKER", "Location changed");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
        isLocationEnabled();
    }

    boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}