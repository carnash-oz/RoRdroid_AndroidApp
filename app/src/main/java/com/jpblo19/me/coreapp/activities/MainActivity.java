package com.jpblo19.me.coreapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.json.decoders.DecodeDemoObject;
import com.jpblo19.me.coreapp.json.decoders.DecodeHttpResponse;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends CoreActivity {

    private static String TAG_CLASS = "MAIN ACTIVITY";
    private Context ctx;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;

        progress = new ProgressDialog(this);
    }

    /////---[ASYNC METHODS]-----------------------------------------------------------------------------

    private void ASYNC_GET_DEMO(){

        class asycn_method extends AsyncTask<String, String, Void>{

            private boolean complete_fetch;
            private boolean response_info;
            private ArrayList<String> content;

            protected void onPreExecute(){
                tools.Log_i("ASYNC [PRE] - GET DEMO",TAG_CLASS);

                complete_fetch = false;
                response_info = false;
                content = new ArrayList<String>();

                progress.setTitle("Descargando");
                progress.setMessage("favor esperar...");
                progress.setCancelable(true);
                progress.setIndeterminate(false);
                progress.show();

                tools.Log_i("ASYNC [PRE] - GET DEMO (END)", TAG_CLASS);
            }

            @Override
            protected Void doInBackground(String... params) {
                tools.Log_i("ASYNC [BACKGROUND] - GET DEMO",TAG_CLASS);

                try{
                    String s_url = tools.getServer(PREF_MODE_AUX_SERVER)+"api/points.json";

                    String fetch_response = tools.networking.GET_HTTP_RESQUEST(s_url,20,35);
                    tools.Log_i("ASYNC [BACKGROUND] - Fetch Response: "+fetch_response,TAG_CLASS);

                    if(!fetch_response.equals(getString(R.string.FAIL_RESPONSE))){
                        content = (new DecodeHttpResponse()).Decode(fetch_response);
                        response_info = Boolean.parseBoolean(content.get(0));
                        complete_fetch = true;
                    }

                }catch (Exception e){
                    tools.Log_e("ASYNC [BACKGROUND] - Catch Error. Reason: " + e, TAG_CLASS);
                }
                tools.Log_i("ASYNC [BACKGROUND] - GET DEMO (END)",TAG_CLASS);
                return null;
            }

            protected void onPostExecute(Void v){
                tools.Log_i("ASYNC [POST] - GET DEMO",TAG_CLASS);

                progress.dismiss();
                if(complete_fetch){
                    if(response_info){
                        tools.MakeToast(content.get(1));
                        qkcache.LIST_DEMO = (new DecodeDemoObject()).DecodeList(content.get(2));
                    }else{
                        tools.MakeToast(content.get(1));
                    }
                }else{
                    tools.MakeToast(getString(R.string.psvtxt_error_badconnection));
                }

                tools.Log_i("ASYNC [POST] - GET DEMO (END)",TAG_CLASS);
            }
        }

        if(tools.networking.isNetworkAvailable()){
            asycn_method am = new asycn_method();
            try{
                am.execute();
            }catch (Exception e){
                am.cancel(true);
            }
        }else{
            tools.MakeToast(getString(R.string.psvtxt_error_nointernet_conn));
        }

    }

    /////---[LAUNCHERS]-----------------------------------------------------------------------------

    private void LaunchDemo(){
        tools.Log_i("LAUNCH DEMO",TAG_CLASS);
        ASYNC_GET_DEMO();

    }

    /////---[EVT ACTIONS----------------------------------------------------------------------------

    public void evt_demo(View view){
        tools.Log_i("EVT DEMO LAUNCH",TAG_CLASS);
        LaunchDemo();
    }
}
