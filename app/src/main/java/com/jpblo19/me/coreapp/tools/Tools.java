package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class Tools extends LogMessage{

    private static String TAG_CLASS = "TOOLS CLASS";

    ///////--------[CONSTANTS AND FLAGS]-------///////


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

    public int toDPI(int metric){
        final float scale = ctx.getResources().getDisplayMetrics().density;
        int value = (int) (metric * scale + 0.5f);
        return value;
    }

    //////////-------[PREFERENCES]------------------------------------------------------------------

    public SharedPreferences getPref(){
        return pref;
    }

    public SharedPreferences.Editor getEditor(){
        return edit;
    }

    public void MakeToast(String text){
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }
}
