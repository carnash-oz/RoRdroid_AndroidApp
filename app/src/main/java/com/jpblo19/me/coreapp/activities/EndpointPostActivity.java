package com.jpblo19.me.coreapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.jpblo19.me.coreapp.R;

public class EndpointPostActivity extends CoreActivity {

    private static String TAG_CLASS = "ENDPOINT POST ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endpoint_post);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;
        progress = new ProgressDialog(this);
    }

    /////---[DIALOGS]-------------------------------------------------------------------------------

    /////---[GRAPHICS]------------------------------------------------------------------------------

    /////---[ASYNC METHODS]-------------------------------------------------------------------------

    /////---[LAUNCHERS]-----------------------------------------------------------------------------

    /////---[EVT ACTIONS----------------------------------------------------------------------------
}
