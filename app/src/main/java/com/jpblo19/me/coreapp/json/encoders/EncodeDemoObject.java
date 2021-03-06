package com.jpblo19.me.coreapp.json.encoders;

import com.jpblo19.me.coreapp.models.DemoObject;
import com.jpblo19.me.coreapp.tools.LogMessage;

import org.json.JSONObject;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class EncodeDemoObject extends LogMessage {

    private static String TAG_CLASS = "ENCODER_DEMOOBJECT CLASS";

    public EncodeDemoObject(){}

    public JSONObject Encode(DemoObject this_item){
        JSONObject json = new JSONObject();
        try {
            json.put("title", this_item.getTitle());
            json.put("value", this_item.getValue());
            json.put("descripcion", this_item.getDescription());
        }catch (Exception e){
            Log_e("Encoder ERROR: " + e, TAG_CLASS);
        }
        return  json;
    }
}
