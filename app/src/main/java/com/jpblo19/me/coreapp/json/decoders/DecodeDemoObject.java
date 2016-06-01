package com.jpblo19.me.coreapp.json.decoders;

import com.jpblo19.me.coreapp.models.DemoObject;
import com.jpblo19.me.coreapp.tools.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class DecodeDemoObject {

    private static String TAG_CLASS = "DECODER DEMOOBJECT";
    private Tools tools = new Tools();

    public DecodeDemoObject(){
        //DO NOTHING
    }

    public ArrayList<DemoObject> DecodeList(String s_json){
        ArrayList<DemoObject> content = new ArrayList<DemoObject>();

        try{
            JSONArray arr = new JSONArray(s_json);
            tools.Log_e("DECODE DEBUG "+s_json,TAG_CLASS);

            for(int i = 0; i < arr.length(); i++)
                content.add(DecodeItem(arr.getJSONObject(i).toString()));
        }catch (Exception e){
            tools.Log_e("DECODE ERROR Decoding List. Reason: "+e,TAG_CLASS);
        }

        return content;
    }

    public DemoObject DecodeItem(String s_json){
        DemoObject this_item = new DemoObject();

        try{
            JSONObject jsonObject = new JSONObject(s_json);

            String param_gpslatitud = jsonObject.getString("gpslatitud");
            String param_gpslongitud = jsonObject.getString("gpslatitud");
            String param_title = jsonObject.getString("title");
            String param_info = jsonObject.getString("info");
            String param_address = jsonObject.getString("address");

            this_item.setGpslatitud(Float.parseFloat(param_gpslatitud));
            this_item.setGpslongitud(Float.parseFloat(param_gpslongitud));
            this_item.setTitle(param_title);
            this_item.setInfo(param_info);
            this_item.setAddress(param_address);

        }catch (Exception e){
            tools.Log_e("DECODE ERROR Decoding Item. Reason: "+e,TAG_CLASS);
        }

        return this_item;
    }

}
