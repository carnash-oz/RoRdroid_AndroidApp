package com.jpblo19.me.coreapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.jpblo19.me.coreapp.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class GeolocActivity extends CoreActivity {

    private static String TAG_CLASS = "GEOLOC ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    private TextView textview_cood;

    private TimerTask Parallel_task_Geoloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geoloc);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;
        progress = new ProgressDialog(this);

        textview_cood = (TextView) findViewById(R.id.textview_cood);
    }

    /////---[ASYNC METHODS]-------------------------------------------------------------------------

    private void TASK_GEOLOC_INTERVAL(){
        final Handler handler = new Handler();
        Timer timer = new Timer();

        Parallel_task_Geoloc = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LatLng tmp_latlng = tools.geolocation.getGeoPosResult();
                        textview_cood.setText(tmp_latlng.latitude + ","+tmp_latlng.longitude);
                    }
                });
            }
        };
        timer.schedule(Parallel_task_Geoloc, 0, 1000);  ///[Task,Long Delay (ms), Long Period (ms)]
    }

    /////---[LAUNCHERS]-----------------------------------------------------------------------------

    private void LaunchGeoLoc(boolean flag_action, boolean flag_type){
        tools.Log_i("LAUNCH GEOLOC", TAG_CLASS);
        if(flag_action){
            if(flag_type) {
                tools.geolocation.StartGeoLoc(true);
                textview_cood.setTextColor(getResources().getColor(R.color.color_blue));
            }else {
                tools.geolocation.StartGeoLoc(false);
                textview_cood.setTextColor(getResources().getColor(R.color.color_green));
            }
            TASK_GEOLOC_INTERVAL();
        } else {
            tools.geolocation.StopGeoLoc();
            textview_cood.setTextColor(getResources().getColor(R.color.color_red));
            Parallel_task_Geoloc.cancel();
        }
    }

    /////---[EVT ACTIONS----------------------------------------------------------------------------

    public void evt_start_geoloc_g(View view){
        tools.Log_i("EVT GEOLOC LAUNCH",TAG_CLASS);
        LaunchGeoLoc(true,true);
    }

    public void evt_start_geoloc_n(View view){
        tools.Log_i("EVT GEOLOC LAUNCH",TAG_CLASS);
        LaunchGeoLoc(true,false);
    }

    public void evt_stop_geoloc(View view){
        tools.Log_i("EVT GEOLOC LAUNCH",TAG_CLASS);
        LaunchGeoLoc(false,false);
    }
}
