package com.jpblo19.me.coreapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jpblo19.me.coreapp.R;

public class MainActivity extends CoreActivity {

    private static String TAG_CLASS = "MAIN ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    private TextView textview_info_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;
        progress = new ProgressDialog(this);

        textview_info_version = (TextView) findViewById(R.id.textview_info_version);
        textview_info_version.setText(getResources().getString(R.string.psvtxt_version) + " " + VERSION__APP_STRING + " (" + VERSION__CODE_STRING + ")" + "\nRuta: " + tools.getServer(PREF_MODE_AUX_SERVER));
    }

    /////---[DIALOGS]-------------------------------------------------------------------------------

    /////---[GRAPHICS]------------------------------------------------------------------------------

    /////---[ASYNC METHODS]-------------------------------------------------------------------------

    /////---[LAUNCHERS]-----------------------------------------------------------------------------

    /////---[EVT ACTIONS----------------------------------------------------------------------------

    public void evt_start_gmaps(View view){
        tools.Log_i("EVT GMAPS ACTIVITY",TAG_CLASS);
        Intent intent = new Intent(view.getContext(), MapsActivity.class);
        startActivity(intent);
    }

    public void evt_endpoint_get(View view){
        tools.Log_i("EVT ENDPOINT GET ACTIVITY",TAG_CLASS);
        Intent intent = new Intent(view.getContext(), EndpointGetActivity.class);
        startActivity(intent);
    }

    public void evt_endpoint_post(View view){
        tools.Log_i("EVT ENDPOINT POST ACTIVITY",TAG_CLASS);
        Intent intent = new Intent(view.getContext(), EndpointPostActivity.class);
        startActivity(intent);
    }

    public void evt_print(View view){
        tools.Log_i("EVT PRINT ACTIVITY",TAG_CLASS);
        Intent intent = new Intent(view.getContext(), PrintActivity.class);
        startActivity(intent);
    }

    public void evt_start_geoloc(View view){
        tools.Log_i("EVT GEOLOC ACTIVITY",TAG_CLASS);
        Intent intent = new Intent(view.getContext(), GeolocActivity.class);
        startActivity(intent);
    }
}
