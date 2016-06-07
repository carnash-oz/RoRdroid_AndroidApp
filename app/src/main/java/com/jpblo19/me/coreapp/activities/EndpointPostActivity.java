package com.jpblo19.me.coreapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.json.decoders.DecodeHttpResponse;
import com.jpblo19.me.coreapp.json.encoders.EncodeDemoObject;
import com.jpblo19.me.coreapp.models.DemoObject;

import org.json.JSONObject;

import java.util.ArrayList;

public class EndpointPostActivity extends CoreActivity {

    private static String TAG_CLASS = "ENDPOINT POST ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    private EditText form_demoobject_title;
    private EditText form_demoobject_value;
    private EditText form_demoobject_descripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endpoint_post);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;
        progress = new ProgressDialog(this);

        form_demoobject_title = (EditText) findViewById(R.id.form_demoobject_title);
        form_demoobject_value = (EditText) findViewById(R.id.form_demoobject_value);
        form_demoobject_descripcion = (EditText) findViewById(R.id.form_demoobject_descripcion);
    }

    /////---[DIALOGS]-------------------------------------------------------------------------------

    /////---[GRAPHICS]------------------------------------------------------------------------------

    /////---[ASYNC METHODS]-------------------------------------------------------------------------

    private void ASYNC_POST_DEMO(final DemoObject this_item){

        class asycn_method extends AsyncTask<String, String, Void> {

            private boolean complete_fetch;
            private boolean response_info;
            private ArrayList<String> content;

            protected void onPreExecute(){
                tools.Log_i("ASYNC [PRE] - GET DEMO",TAG_CLASS);

                complete_fetch = false;
                response_info = false;
                content = new ArrayList<String>();

                progress.setTitle("Enviando");
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
                    String s_url = tools.getServer(PREF_MODE_AUX_SERVER)+"api/post_demoobjects";

                    JSONObject json = (new EncodeDemoObject()).Encode(this_item);
                    String fetch_response = tools.networking.POST_HTTP_REQUEST(s_url, json, 20, 35);
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
                    tools.MakeToast(content.get(1));
                    if(response_info){
                        //DO SOMETHING WITH SUCCESS TRUE
                    }else{
                        //DO SOMETHING WITH SUCCESS FALSE
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
        tools.Log_i("LAUNCH DEMO", TAG_CLASS);
        DemoObject this_item = new DemoObject();

        if(!form_demoobject_title.getText().equals("") && !form_demoobject_value.getText().equals("") && !form_demoobject_descripcion.getText().equals("")) {

            try {
                this_item.setTitle(form_demoobject_title.getText().toString());
                this_item.setValue(Integer.parseInt(form_demoobject_value.getText().toString()));
                this_item.setDescription(form_demoobject_descripcion.getText().toString());
                ASYNC_POST_DEMO(this_item);
            } catch (Exception e) {
                tools.Log_e("Some error. Reason: " + e, TAG_CLASS);
                tools.MakeToast(getResources().getString(R.string.psvtxt_error_badform));
            }
        }else{
            tools.Log_e("Error: Empty fields", TAG_CLASS);
            tools.MakeToast(getResources().getString(R.string.psvtxt_error_emptyform));
        }
    }

    /////---[EVT ACTIONS----------------------------------------------------------------------------

    public void evt_async_post(View view){
        tools.Log_i("EVT ASYNC DEMO LAUNCH",TAG_CLASS);
        LaunchDemo();
    }
}
