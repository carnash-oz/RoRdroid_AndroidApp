package com.jpblo19.me.coreapp.json.decoders;

import com.jpblo19.me.coreapp.models.DemoObject;
import com.jpblo19.me.coreapp.tools.LogMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class DecodeDemoObject extends LogMessage {

    private static String TAG_CLASS = "DECODER DEMOOBJECT";

    public DecodeDemoObject(){
        //DO NOTHING
    }

    public ArrayList<DemoObject> DecodeList(String s_json){
        ArrayList<DemoObject> content = new ArrayList<DemoObject>();

        try{
            JSONObject jsonObject = new JSONObject(s_json);
            Log_i("DECODE DEBUG " + s_json, TAG_CLASS);

            String param_arr = jsonObject.getString("listdemo");
            JSONArray arr = new JSONArray(param_arr);

            for(int i = 0; i < arr.length(); i++)
                content.add(DecodeItem(arr.getJSONObject(i).toString()));
        }catch (Exception e){
            Log_e("DECODE ERROR Decoding List. Reason: "+e,TAG_CLASS);
        }

        return content;
    }

    public DemoObject DecodeItem(String s_json){
        DemoObject this_item = new DemoObject();

        try{
            JSONObject jsonObject = new JSONObject(s_json);
            Log_i("DECODE DEBUG "+s_json,TAG_CLASS);

            String param_title = jsonObject.getString("title");
            String param_value = jsonObject.getString("value");
            String param_description = jsonObject.getString("descripction");
            String param_fechacreado = jsonObject.getString("created_at");

            this_item.setTitle(param_title);
            this_item.setValue(Integer.parseInt(param_value));
            this_item.setDescription(param_description);
            this_item.setFecha_creado(param_fechacreado);

        }catch (Exception e){
            Log_e("DECODE ERROR Decoding Item. Reason: "+e,TAG_CLASS);

            this_item.setTitle("ENTRADA ERRONEA");
            this_item.setValue(0);
            this_item.setDescription("");
            this_item.setFecha_creado("");
        }

        return this_item;
    }

}
