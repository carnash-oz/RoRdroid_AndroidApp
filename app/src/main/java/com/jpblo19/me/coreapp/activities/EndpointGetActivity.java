package com.jpblo19.me.coreapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.json.decoders.DecodeDemoObject;
import com.jpblo19.me.coreapp.json.decoders.DecodeHttpResponse;
import com.jpblo19.me.coreapp.models.DemoObject;
import com.jpblo19.me.coreapp.models.HttpResponseObject;

import java.util.ArrayList;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class EndpointGetActivity extends CoreActivity {

    private static String TAG_CLASS = "ENDPOINT GET ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    private LinearLayout pivot_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endpoint_get);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;
        progress = new ProgressDialog(this);

        pivot_layout = (LinearLayout) findViewById(R.id.pivot_layout);

    }

    /////---[DIALOGS]-------------------------------------------------------------------------------

    private void DialogNoElements(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_NEUTRAL:
                        //DO NOTHING
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        //DO NOTHING
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //DO NOTHING
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.dialog_noelements));
        builder.setNeutralButton(getString(R.string.options_accept),dialogClickListener);
        builder.show();
    }

    public void ConfirmationCancelSend(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){

                    case DialogInterface.BUTTON_NEUTRAL:
                        //DO NOTHING
                        break;
                }
            }
        };
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.dialog_canceltasksend));
        builder.setNeutralButton(getResources().getString(R.string.options_accept), dialogClickListener);
        builder.show();
    }

    /////---[GRAPHICS]------------------------------------------------------------------------------

    private void ConstructPool(){
        pivot_layout.removeAllViews();

        ArrayList<DemoObject> pool_data = qkcache.LIST_DEMO;
        tools.Log_i("Pool Size: " + pool_data.size(), TAG_CLASS);

        if(pool_data.size() == 0){
            DialogNoElements();
        }else{
            for(int i = 0; i < pool_data.size(); i++){
                final DemoObject this_item = pool_data.get(i);
                pivot_layout.addView(graf.CellDemoObjectView(this_item));
            }
        }
    }

    /////---[ASYNC METHODS]-------------------------------------------------------------------------

    private void ASYNC_GET_DEMO(){

        class asycn_method extends AsyncTask<String, String, Void> {

            private boolean complete_fetch;
            private HttpResponseObject content;

            protected void forceCancel(){
                tools.Log_e("ASYNC_GET_DEMO [[FORCE CANCEL EVENT]]", TAG_CLASS);
                tools.networking.DestroyActualSocket();
                this.cancel(true);
                ConfirmationCancelSend();
            }

            @Override
            protected void onCancelled(){
                tools.Log_i("ASYNC_GET_DEMO [ONCANCEL] - END ASYNC",TAG_CLASS);
            }

            protected void onPreExecute(){
                tools.Log_i("ASYNC_GET_DEMO [PRE] - GET DEMO",TAG_CLASS);

                complete_fetch = false;
                content = new HttpResponseObject();

                progress.setTitle("Descargando");
                progress.setMessage("favor esperar...");
                progress.setCancelable(true);
                progress.setIndeterminate(true);
                progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        forceCancel();
                    }
                });
                progress.show();

                tools.Log_i("ASYNC_GET_DEMO [PRE] - GET DEMO (END)", TAG_CLASS);
            }

            @Override
            protected Void doInBackground(String... params) {
                tools.Log_i("ASYNC_GET_DEMO [BACKGROUND] - GET DEMO",TAG_CLASS);

                try{
                    String s_url = tools.getServer(PREF_MODE_AUX_SERVER)+"api/get_demoobjects.json";

                    String fetch_response = tools.networking.GET_HTTP_REQUEST(s_url,20,35);
                    tools.Log_i("ASYNC_GET_DEMO [BACKGROUND] - Fetch Response: "+fetch_response,TAG_CLASS);

                    if(!fetch_response.equals(getString(R.string.FAIL_RESPONSE))){
                        content = (new DecodeHttpResponse()).Decode(fetch_response);
                        complete_fetch = true;
                        tools.Log_i("ASYNC_GET_DEMO [BACKGROUND] - POST REGISTRODESPACHO (COMMAND) : "+content.getCommand(),TAG_CLASS);
                    }

                }catch (Exception e){
                    tools.Log_e("ASYNC_GET_DEMO [BACKGROUND] - Catch Error. Reason: " + e, TAG_CLASS);
                }
                tools.Log_i("ASYNC_GET_DEMO [BACKGROUND] - (END)",TAG_CLASS);
                return null;
            }

            protected void onPostExecute(Void v){
                tools.Log_i("ASYNC_GET_DEMO [POST] - GET DEMO",TAG_CLASS);

                progress.dismiss();
                if(complete_fetch){
                    tools.MakeToast(content.getInfo());
                    if(content.isSuccess()){
                        qkcache.LIST_DEMO = (new DecodeDemoObject()).DecodeList(content.getData());
                        ConstructPool();
                    }else{
                        //DO SOMETHING WITH SUCCESS FALSE
                    }
                }else{
                    tools.MakeToast(getString(R.string.psvtxt_error_badconnection));
                }

                tools.Log_i("ASYNC_GET_DEMO [POST] - GET DEMO (END)",TAG_CLASS);
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

    public void evt_async_get(View view){
        tools.Log_i("EVT ASYNC DEMO LAUNCH",TAG_CLASS);
        LaunchDemo();
    }
}
