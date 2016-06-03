package com.jpblo19.me.coreapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.json.decoders.DecodeDemoObject;
import com.jpblo19.me.coreapp.json.decoders.DecodeHttpResponse;
import com.jpblo19.me.coreapp.models.DemoObject;
import com.jpblo19.me.coreapp.tools.Printer;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends CoreActivity {

    private static String TAG_CLASS = "MAIN ACTIVITY";
    private Context ctx;

    private ProgressDialog progress;

    private TextView textview_cood;
    private LinearLayout pivot_layout;

    private TimerTask Parallel_task_Geoloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;

        progress = new ProgressDialog(this);

        textview_cood = (TextView) findViewById(R.id.textview_cood);
        pivot_layout = (LinearLayout) findViewById(R.id.pivot_layout);
    }

    /////---[DIALOGS]-------------------------------------------------------------------------

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

    /////---[GRAPHICS]------------------------------------------------------------------------------

    private void ContructPool(){
        pivot_layout.removeAllViews();

        ArrayList<DemoObject> pool_data = qkcache.LIST_DEMO;
        tools.Log_i("Pool Size: "+pool_data.size(),TAG_CLASS);

        if(pool_data.size() == 0){
            DialogNoElements();
        }else{
            for(int i = 0; i < pool_data.size(); i++){

                final DemoObject this_item = pool_data.get(i);

                final LinearLayout row_layout = new LinearLayout(this);
                ViewGroup.LayoutParams row_layout_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                row_layout.setLayoutParams(row_layout_params);
                row_layout.setOrientation(LinearLayout.VERTICAL);
                row_layout.setPadding(tools.toDPI(5), tools.toDPI(5), tools.toDPI(5), tools.toDPI(5));
                {
                    TextView txt_title = new TextView(this);
                    txt_title.setText(this_item.getTitle());
                    txt_title.setSingleLine();

                    TextView txt_value = new TextView(this);
                    txt_value.setText(this_item.getValue()+"");
                    txt_value.setSingleLine();

                    TextView txt_description = new TextView(this);
                    txt_description.setText(this_item.getDescription());
                    txt_description.setSingleLine();

                    row_layout.addView(txt_title);
                    row_layout.addView(txt_value);
                    row_layout.addView(txt_description);

                }
                pivot_layout.addView(row_layout);
            }
        }
    }

    /////---[ASYNC METHODS]-------------------------------------------------------------------------

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
                    String s_url = tools.getServer(PREF_MODE_AUX_SERVER)+"api/demoobject.json";

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
                        ContructPool();
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

    private void TASK_GEOLOC_INTERVAL(){
        final Handler handler = new Handler();
        Timer timer = new Timer();

        Parallel_task_Geoloc = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textview_cood.setText(tools.geolocation.getGeoPosResult());
                    }
                });
            }
        };
        timer.schedule(Parallel_task_Geoloc,0,1000);  ///[Task,Long Delay (ms), Long Period (ms)]
    }

    /////---[LAUNCHERS]-----------------------------------------------------------------------------

    private void LaunchDemo(){
        tools.Log_i("LAUNCH DEMO",TAG_CLASS);
        ASYNC_GET_DEMO();
    }

    private void LaunchPrint(){
        tools.Log_i("LAUNCH PRINT",TAG_CLASS);

        Printer printer = new Printer();
        printer.printMessage("olakease popo");
    }

    private void LaunchGeoLoc(boolean flag_action, boolean flag_type){
        tools.Log_i("LAUNCH GEOLOC", TAG_CLASS);
        if(flag_action){
            if(flag_type) {
                tools.geolocation.StartGeoLoc(true);
                textview_cood.setTextColor(getResources().getColor(R.color.color_blue));
            }else {
                tools.geolocation.StartGeoLoc(false);
                textview_cood.setTextColor(getResources().getColor(R.color.color_green));
            }
            TASK_GEOLOC_INTERVAL();
        } else {
            tools.geolocation.StopGeoLoc();
            textview_cood.setTextColor(getResources().getColor(R.color.color_red));
            Parallel_task_Geoloc.cancel();
        }
    }

    /////---[EVT ACTIONS----------------------------------------------------------------------------

    public void evt_async_demo(View view){
        tools.Log_i("EVT ASYNC DEMO LAUNCH",TAG_CLASS);
        LaunchDemo();
    }

    public void evt_send_print(View view){
        tools.Log_i("EVT SEND PRINT LAUNCH",TAG_CLASS);
        LaunchPrint();
    }

    public void evt_start_geoloc_g(View view){
        tools.Log_i("EVT GEOLOC LAUNCH",TAG_CLASS);
        LaunchGeoLoc(true,true);
    }

    public void evt_start_geoloc_n(View view){
        tools.Log_i("EVT GEOLOC LAUNCH",TAG_CLASS);
        LaunchGeoLoc(true,false);
    }

    public void evt_stop_geoloc(View view){
        tools.Log_i("EVT GEOLOC LAUNCH",TAG_CLASS);
        LaunchGeoLoc(false,false);
    }
}
