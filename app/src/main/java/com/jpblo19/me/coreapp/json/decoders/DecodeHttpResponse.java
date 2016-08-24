package com.jpblo19.me.coreapp.json.decoders;

import com.jpblo19.me.coreapp.models.HttpResponseObject;
import com.jpblo19.me.coreapp.tools.LogMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class DecodeHttpResponse extends LogMessage {

    private static String TAG_CLASS = "DECODER HTTP_RESPONSE";

    public DecodeHttpResponse(){}

    public HttpResponseObject Decode(String s_json){
        HttpResponseObject content = new HttpResponseObject();

        boolean success;
        String info;
        String command;
        String data;

        try{
            JSONObject entry_data = new JSONObject(s_json);

            success = entry_data.getBoolean("success");
            info = entry_data.getString("info");
            command = entry_data.getString("command");
            data = entry_data.getString("data");

            content.setSuccess(success);
            content.setInfo(info);
            content.setCommand(command);
            content.setData(data);

        }catch (JSONException e){
            Log_e("DECODE ERROR Decoding Response. Reason: " + e, TAG_CLASS);
        }

        return content;
    }

}
