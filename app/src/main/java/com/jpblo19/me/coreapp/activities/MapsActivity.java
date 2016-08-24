package com.jpblo19.me.coreapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jpblo19.me.coreapp.R;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class MapsActivity extends CoreActivity implements OnMapReadyCallback {

    private static String TAG_CLASS = "MAPS ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;
        progress = new ProgressDialog(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        tools.Log_i("Init Gmaps", TAG_CLASS);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);             ///CAMBIA LA MODALIDAD DEL MAPA
        mMap.setTrafficEnabled(true);                           ///PONE O QUITA ESTIMACION DE TRAFICO
        mMap.setMyLocationEnabled(true);                        ///PONE O QUITA EL GEOLOC PROPIO DE GMAPS

        tools.Log_i("Init Point", TAG_CLASS);
        LatLng point = new LatLng(14.655183f, -90.536476f);     ///INSTANCIA UN PUNTO

        tools.Log_i("Add Marker", TAG_CLASS);
        mMap.addMarker(new MarkerOptions().position(point).title("This is the default point"));         ///AGREGA MARCADOR EN BASE AL PUNTO
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15.0f));
    }
}
