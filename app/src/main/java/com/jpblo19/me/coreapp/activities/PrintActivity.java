package com.jpblo19.me.coreapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.tools.engines.PrinterDatamax.Printer;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class PrintActivity extends CoreActivity {

    private static String TAG_CLASS = "PRINT ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        tools.Log_i("Instances Activity", TAG_CLASS);
        ctx = this;
        progress = new ProgressDialog(this);
    }

    /////---[LAUNCHERS]-----------------------------------------------------------------------------

    private void LaunchPrint(){
        tools.Log_i("LAUNCH PRINT",TAG_CLASS);
        new Printer();
    }

    /////---[EVT ACTIONS----------------------------------------------------------------------------

    public void evt_send_print(View view){
        tools.Log_i("EVT SEND PRINT LAUNCH",TAG_CLASS);
        LaunchPrint();
    }
}
