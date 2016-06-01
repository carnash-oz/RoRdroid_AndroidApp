package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class GeoLocation implements LocationListener{

    protected LocationManager locationManager;
    private boolean isGeoLocEnabled;

    public GeoLocation(Context ctx){
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        isGeoLocationAvailable();
    }

    public boolean isGeoLocationAvailable(){
        isGeoLocEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGeoLocEnabled;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
