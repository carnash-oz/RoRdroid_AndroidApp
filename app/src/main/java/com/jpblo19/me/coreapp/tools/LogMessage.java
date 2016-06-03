package com.jpblo19.me.coreapp.tools;

import android.util.Log;

/**
 * Created by jpblo19 on 6/2/16.
 */
public class LogMessage {

    private final boolean DEBUG_LOG = true;

    public LogMessage(){}

    public void Log_i(final String text, final String TAG_CLASS){
        if(DEBUG_LOG)
            Log.i("DEBUG_LOG - " + TAG_CLASS, text);
    }

    public void Log_d(final String text, final String TAG_CLASS){
        if(DEBUG_LOG)
            Log.d("DEBUG_LOG - " + TAG_CLASS, text);
    }

    public void Log_e(final String text, final String TAG_CLASS){
        if(DEBUG_LOG)
            Log.e("DEBUG_LOG - " + TAG_CLASS, text);
    }
}
