package com.jpblo19.me.coreapp.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.tools.Graphics;
import com.jpblo19.me.coreapp.tools.QuickCache;
import com.jpblo19.me.coreapp.tools.Tools;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */
public class CoreActivity extends AppCompatActivity {

    private static String TAG_CLASS = "CORE ACTIVITY";

    public Tools tools;
    public QuickCache qkcache;
    public Graphics graf;

    public static String VERSION__APP_STRING = "0.0.0";
    public static String VERSION__CODE_STRING = "0";

    public static boolean PREF_MODE_AUX_SERVER = false;             //BANDERA PARA INDICAR A QUE SERVER CONECTAR

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //---[INIT MAIN OBJECTS]---//
        tools = new Tools(this);
        qkcache = new QuickCache();
        graf = new Graphics(this);

        //---[RELOAD PREF DATA]---//
        PREF_MODE_AUX_SERVER = tools.getPref().getBoolean(getString(R.string.pref_modeaux_server),false);

        PackageInfo pInfo;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            VERSION__APP_STRING  = pInfo.versionName+"";
            VERSION__CODE_STRING  = pInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {}
    }

    /////---[OVERRIDES]-----------------------------------------------------------------------------

    @Override
    public void onBackPressed(){
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            tools.Log_d("RESULT CODE: "+resultCode,TAG_CLASS);
        } catch (Exception ex) {
            tools.Log_e("ERROR Result activity. Reason: " + ex, TAG_CLASS);
        }
    }

}
