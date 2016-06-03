package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class GeoLocation extends LogMessage implements LocationListener{

    private static String TAG_CLASS = "GEOLOCATION";

    protected float GEO_LATITUD = 0.0f;
    protected float GEO_LONGITUD = 0.0f;

    protected LocationManager locationManager;
    private boolean isGeoLocEnabled;

    public GeoLocation(Context ctx){
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        GEO_LATITUD = 0.0f;
        GEO_LONGITUD = 0.0f;
    }

    public boolean isGeoLocationAvailable(){
        isGeoLocEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log_i("Geo Location Available: "+isGeoLocEnabled,TAG_CLASS);
        return isGeoLocEnabled;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(GEO_LATITUD != location.getLatitude() && GEO_LONGITUD != location.getLongitude()){
            GEO_LATITUD = (float) location.getLatitude();
            GEO_LONGITUD = (float) location.getLongitude();
            Log_i("COORDENADA GENERADA: "+GEO_LATITUD+","+GEO_LONGITUD,TAG_CLASS);
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

    public void StartGeoLoc(boolean high_accuaracy){
        Log_i("StartGeoLoc. HIGH_ACCU:["+high_accuaracy+"]",TAG_CLASS);
        if(isGeoLocationAvailable()){
            GEO_LATITUD = 0.0f;
            GEO_LONGITUD = 0.0f;
            if(high_accuaracy){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
            }else{
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,this);
            }
        }
    }

    public void StopGeoLoc(){
        Log_i("StartGeoLoc. Shut Down GeoLoc.",TAG_CLASS);
        locationManager.removeUpdates(this);
    }

    public float getLatitud(){
        return GEO_LATITUD;
    }

    public float getLongitud(){
        return GEO_LONGITUD;
    }

    public String getGeoPosResult(){
        String s = GEO_LATITUD+","+GEO_LONGITUD;
        return s;
    }
}
