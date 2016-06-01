package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class Tools {

    private static String TAG_CLASS = "TOOLS CLASS";

    ///////--------[CONSTANTS AND FLAGS]-------///////
    private final boolean DEBUG_LOG = true;


    //////---------[SHARE]---------------------///////
    private Context ctx;
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    /////----------[ENGINES]------------------///////
    public static Networking networking;
    public static GeoLocation geolocation;

    public Tools(){}

    public Tools(Context ctx){
        this.ctx = ctx;

        pref = ctx.getSharedPreferences("DATAPREF",0);
        edit = pref.edit();

        networking = new Networking(ctx);
        geolocation = new GeoLocation(ctx);
    }

    public String getServer(boolean flag_aux_server){
        String url_server = KeyRoutes.PATH_SERVER;

        if(flag_aux_server)
            url_server = KeyRoutes.AUX_PATH_SERVER;

        return url_server;
    }

    //////////-------[PREFERENCES]------------------------------------------------------------------

    public SharedPreferences getPref(){
        return pref;
    }

    public SharedPreferences.Editor getEditor(){
        return edit;
    }

    public void Log_i(final String text, final String TAG_CLASS){
        if(DEBUG_LOG)
            Log.i("DEBUG_LOG - " + TAG_CLASS, text);
    }

    public void Log_d(final String text, final String TAG_CLASS){
        if(DEBUG_LOG)
            Log.d("DEBUG_LOG - " + TAG_CLASS, text);
    }

    public void Log_e(final String text, final String TAG_CLASS){
        if(DEBUG_LOG)
            Log.e("DEBUG_LOG - " + TAG_CLASS, text);
    }

    public void MakeToast(String text){
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }
}
