package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.jpblo19.me.coreapp.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class Networking {


    private static String TAG_CLASS = "NETWORKING CLASS";
    private Context ctx;

    public Networking(Context ctx){
        this.ctx = ctx;
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if(netinfo != null && netinfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()){
          return true;
        }else{
            return false;
        }

    }

    ///////////[ASYNC CONNECTIONS]////////////////////////[ASYNC CONNECTIONS]///////////////////////

    public String POST_HTTP_REQUEST(final String s_url, final JSONObject json, final int timeout_connection, final int timeout_response){
        String RESPONSE_DATA = "";

        final int TIMEOUT_CONNECTION = timeout_connection * 1000;
        final int TIMEOUT_RESPONSE = timeout_response * 1000;

        Log.i("DEBUG_LOG - "+TAG_CLASS, "[POST] URL to: "+s_url);

        try {
            URL url = new URL(s_url);
            Log.i("DEBUG_LOG - " + TAG_CLASS, "[POST]  Starting URL Connection...");

            URLConnection uc = url.openConnection();
            uc.setDoInput(true);
            uc.setDoOutput(true);
            uc.setConnectTimeout(TIMEOUT_CONNECTION);
            uc.setReadTimeout(TIMEOUT_RESPONSE);
            uc.setRequestProperty("Accept-Charset", "UTF-8");
            uc.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter wr = new OutputStreamWriter(uc.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            InputStreamReader is = new InputStreamReader(uc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            Log.i("DEBUG_LOG - " + TAG_CLASS, "[POST] Reading Package...");
            String line = null;
            while((line = br.readLine()) != null)
                RESPONSE_DATA += line;
            Log.i("DEBUG_LOG - " + TAG_CLASS, "[POST] Package Data: "+RESPONSE_DATA);

            wr.close();
            is.close();
        }catch (Exception e){
            RESPONSE_DATA = ctx.getString(R.string.FAIL_RESPONSE);
            Log.e("DEBUG_LOG - " + TAG_CLASS, "[POST] ERROR TRY. Razon: " + e);
        }

        return RESPONSE_DATA;
    }

    public String GET_HTTP_RESQUEST(final String s_url, final int timeout_connection, final int timeout_response){
        String RESPONSE_DATA = "";

        final int TIMEOUT_CONNECTION = timeout_connection * 1000;
        final int TIMEOUT_RESPONSE = timeout_response * 1000;

        Log.i("DEBUG_LOG - "+TAG_CLASS, "[GET] URL to: "+s_url);

        try{
            URL url = new URL(s_url);

            URLConnection uc = url.openConnection();
            uc.setConnectTimeout(TIMEOUT_CONNECTION);
            uc.setReadTimeout(TIMEOUT_RESPONSE);

            InputStreamReader is = new InputStreamReader(uc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            Log.i("DEBUG_LOG - " + TAG_CLASS, "[GET] Reading Package...");
            String line = null;
            while((line = br.readLine()) != null)
                RESPONSE_DATA += line;
            Log.i("DEBUG_LOG - " + TAG_CLASS, "[GET] Package Data: "+RESPONSE_DATA);

            is.close();
        }catch (Exception e){
            RESPONSE_DATA = ctx.getString(R.string.FAIL_RESPONSE);
            Log.e("DEBUG_LOG - " + TAG_CLASS, "[GET] ERROR TRY. Razon: " + e);
        }

        return RESPONSE_DATA;
    }


}
