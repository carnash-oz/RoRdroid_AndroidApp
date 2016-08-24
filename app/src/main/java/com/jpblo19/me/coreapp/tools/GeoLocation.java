package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class GeoLocation extends LogMessage implements LocationListener {

    private static String TAG_CLASS = "GEOLOCATION";

    protected static float GEO_LATITUD = 0.0f;
    protected static float GEO_LONGITUD = 0.0f;

    protected LocationManager locationManager;
    private static boolean isGeoLocEnabled;
    private static boolean isRunning;

    private Context ctx;

    public GeoLocation(Context ctx) {
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        this.ctx = ctx;

        isRunning = false;
        isGeoLocEnabled = false;
    }

    public boolean isReady() {
        if (GEO_LATITUD == 0.0f && GEO_LONGITUD == 0.0f)
            return false;
        else
            return true;
    }

    public boolean isGeoLocationAvailable() {
        isGeoLocEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log_i("Geo Location Available: " + isGeoLocEnabled, TAG_CLASS);
        return isGeoLocEnabled;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (GEO_LATITUD != location.getLatitude() && GEO_LONGITUD != location.getLongitude()) {
            GEO_LATITUD = (float) location.getLatitude();
            GEO_LONGITUD = (float) location.getLongitude();
            Log_i("COORDENADA GENERADA: " + GEO_LATITUD + "," + GEO_LONGITUD, TAG_CLASS);
        }
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

    public void StartGeoLoc(boolean high_accuaracy) {
        Log_i("StartGeoLoc. HIGH_ACCU:[" + high_accuaracy + "]", TAG_CLASS);
        if (isGeoLocationAvailable()) {
            GEO_LATITUD = 0.0f;
            GEO_LONGITUD = 0.0f;
            isRunning = true;
            if (high_accuaracy) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
            }
        }
    }

    public void StopGeoLoc() {
        Log_i("StartGeoLoc. Shut Down GeoLoc.", TAG_CLASS);
        isRunning = false;
        locationManager.removeUpdates(this);
    }

    public float getLatitud(){
        return GEO_LATITUD;
    }

    public float getLongitud(){
        return GEO_LONGITUD;
    }

    public LatLng getGeoPosResult(){
        LatLng geocood = new LatLng(GEO_LATITUD,GEO_LONGITUD);
        return geocood;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
