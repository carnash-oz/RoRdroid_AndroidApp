package com.jpblo19.me.coreapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.tools.Graphics;
import com.jpblo19.me.coreapp.tools.QuickCache;
import com.jpblo19.me.coreapp.tools.Tools;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class CoreActivity extends AppCompatActivity {

    public static int PREF_FONT_SIZE = 13;

    public Tools tools;
    public QuickCache qkcache;
    public Graphics graf;

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

    }

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

}