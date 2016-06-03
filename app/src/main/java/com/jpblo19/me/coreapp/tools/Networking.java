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
public class Networking extends LogMessage{


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

        Log_i("[POST] URL to: "+s_url,TAG_CLASS);

        try {
            URL url = new URL(s_url);
            Log_i("[POST]  Starting URL Connection...", TAG_CLASS);

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

            Log_i("[POST] Reading Package...", TAG_CLASS);
            String line = null;
            while((line = br.readLine()) != null)
                RESPONSE_DATA += line;
            Log_i("[POST] Package Data: " + RESPONSE_DATA, TAG_CLASS);

            wr.close();
            is.close();
        }catch (Exception e){
            RESPONSE_DATA = ctx.getString(R.string.FAIL_RESPONSE);
            Log_e("[POST] ERROR TRY. Razon: " + e, TAG_CLASS);
        }

        return RESPONSE_DATA;
    }

    public String GET_HTTP_RESQUEST(final String s_url, final int timeout_connection, final int timeout_response){
        String RESPONSE_DATA = "";

        final int TIMEOUT_CONNECTION = timeout_connection * 1000;
        final int TIMEOUT_RESPONSE = timeout_response * 1000;

        Log_i("[GET] URL to: " + s_url, TAG_CLASS);

        try{
            URL url = new URL(s_url);

            URLConnection uc = url.openConnection();
            uc.setConnectTimeout(TIMEOUT_CONNECTION);
            uc.setReadTimeout(TIMEOUT_RESPONSE);

            InputStreamReader is = new InputStreamReader(uc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            Log_i("[GET] Reading Package...",TAG_CLASS);
            String line = null;
            while((line = br.readLine()) != null)
                RESPONSE_DATA += line;
            Log_i("[GET] Package Data: "+RESPONSE_DATA,TAG_CLASS);

            is.close();
        }catch (Exception e){
            RESPONSE_DATA = ctx.getString(R.string.FAIL_RESPONSE);
            Log_e("[GET] ERROR TRY. Razon: " + e,TAG_CLASS);
        }

        return RESPONSE_DATA;
    }


}
