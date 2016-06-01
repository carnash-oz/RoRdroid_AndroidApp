package com.jpblo19.me.coreapp.json.decoders;

import com.jpblo19.me.coreapp.tools.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jpblo19 on 5/31/16.
 */
public class DecodeHttpResponse {

    private static String TAG_CLASS = "DECODER HTTP_RESPONSE";
    private Tools tools = new Tools();

    public DecodeHttpResponse(){}

    public ArrayList<String> Decode(String s_json){
        ArrayList<String> content = new ArrayList<String>();

        boolean success;
        String info;
        String data;

        try{
            JSONObject entry_data = new JSONObject(s_json);

            success = entry_data.getBoolean("success");
            info = entry_data.getString("info");
            data = entry_data.getString("data");

            content.add(success+"");
            content.add(info);
            content.add(data);

        }catch (JSONException e){
            tools.Log_e("DECODE ERROR Decoding Response. Reason: " + e, TAG_CLASS);
            content.add("false");
            content.add("Error - Fallo en el paquete de respuesta");
            content.add("N/A");
        }

        return content;
    }

}
