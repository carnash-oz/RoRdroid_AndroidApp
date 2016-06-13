package com.jpblo19.me.coreapp.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class Tools extends LogMessage{

    private static String TAG_CLASS = "TOOLS CLASS";

    ///////--------[CONSTANTS AND FLAGS]-------///////


    //////---------[SHARE]---------------------///////
    private Context ctx;
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    /////----------[ENGINES]------------------///////
    public static Networking networking;
    public static GeoLocation geolocation;

    public Tools(){}

    public Tools(Context ctx){
        this.ctx = ctx;

        pref = ctx.getSharedPreferences("DATAPREF",0);
        edit = pref.edit();

        networking = new Networking(ctx);
        geolocation = new GeoLocation(ctx);
    }

    ////OBTIENE URL DE SERVIDOR
    public String getServer(boolean flag_aux_server){
        String url_server = KeyRoutes.PATH_SERVER;

        if(flag_aux_server)
            url_server = KeyRoutes.AUX_PATH_SERVER;

        return url_server;
    }

    ////GENERA LA FECHA DE HOY (EN BASE AL CELULAR)
    public String getToday(){
        SimpleDateFormat sdfdate  = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        String str_date  = sdfdate.format(now);
        return  str_date;
    }

    ////GENERA UNA CADENA STRING EN BASE AL HORARIO LOCAL DEL CELULAR ddMMyyyhhmmss
    public String NowKey(){
        SimpleDateFormat sdfdate  = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date now = new Date();
        String str_date  = sdfdate.format(now);
        String rev_a = str_date.replace("-", "");
        String rev_b = rev_a.replace(":","");
        String rev_c = rev_b.replace(" ", "");
        return  rev_c;
    }

    ////LIMPIA FORMATO DE RAILS DE DATETIME 2016-06-07T16:32:54-06:00 >>> 07-06-2016 (FECHA)
    public String RailsFormatCleanDate(String date){
        String value;
        try {
            String[] a = date.split("T");
            value = a[0];
        }catch(Exception e){
            Log_e("Error Clean Date (Phase 1). Reason: "+e,TAG_CLASS);
            value = date;
        }

        String new_value;
        try{
            String[] a = value.split("-");
            new_value = a[2]+"-"+a[1]+"-"+a[0];
        }catch (Exception e){
            Log_e("Error Clean Date (Phase 2). Reason: "+e,TAG_CLASS);
            new_value = value;
        }
        return new_value;
    }

    ////LIMPIA FORMATO DE RAILS DE DATETIME 2016-06-07T16:32:54-06:00 >>> 07-06-2016 16:32:54 (FECHA TIEMPO)
    public String RailsFormatCleanDatetime(String fdate){
        String date ="";
        String time ="";

        try {
            String[] a = fdate.split("T");
            date = a[0];
            time = a[1];
        }catch(Exception e){
            Log_e("Error Clean DateTime (Phase 1). Reason: "+e,TAG_CLASS);
            date = fdate;
        }

        String new_date;
        try{
            String[] a = date.split("-");
            new_date = a[2]+"-"+a[1]+"-"+a[0];
        }catch (Exception e){
            Log_e("Error Clean DateTime (Phase 2). Reason: "+e,TAG_CLASS);
            new_date = date;
        }

        String new_time;
        try{
            String[] a = time.split("-");
            new_time = a[0];
        }catch (Exception e){
            Log_e("Error Clean DateTime (Phase 3). Reason: "+e,TAG_CLASS);
            new_time = time;
        }

        String new_value = new_date + " " +new_time;
        return new_value;
    }

    ////FORMATOS NUMERICOS CON COMAS Y DOBLE DECIMAL PARA VALORES PRECIOS FLOTANTES 10000 >>> 10,000.00
    public String FormatPrice(float v){
        String r = "";
        try{
            DecimalFormat form = new DecimalFormat("#,###.00");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            form.setDecimalFormatSymbols(dfs);
            r =  form.format(v);

            if (r.equals(".00"))
                r = "0";

            if (r.indexOf(0) == '.')
                r = '0'+r;

        }catch (Exception e){
            Log_e("Error Formating Duo Value. Reason: "+e,TAG_CLASS);
        }
        return r;
    }

    ////FORMATOS NUMERICOS CON COMAS PARA VALORES ENTEROS 10000 >>> 10,000
    public String FormatIntegerComas(float v){
        String r = "";
        try{
            DecimalFormat form = new DecimalFormat("#,###");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            form.setDecimalFormatSymbols(dfs);
            r =  form.format(v);

        }catch (Exception e){
            Log_e("Error Formating Integer Comas Value. Reason: "+e,TAG_CLASS);
        }
        return r;
    }

    ////CONVERTIDOR DE DPI A DIMENSION
    public int toDPI(int metric){
        final float scale = ctx.getResources().getDisplayMetrics().density;
        int value = (int) (metric * scale + 0.5f);
        return value;
    }

    ////CONVIERTE LOS DIALOGS EN VERTICAL, RECOMENDADO EN VERSION 5.0 ANDROID
    public AlertDialog ShowVerticalDialog(AlertDialog.Builder builder){
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        try{
            final Button tmp_bto = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            LinearLayout linearLayout = (LinearLayout) tmp_bto.getParent();
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }catch (Exception e){
            Log_e("Dialog vertical convert not supported", TAG_CLASS);
        }
        return alertDialog;
    }

    ////VALIDADOR DE EMAIL
    public boolean RegexEmail(String s){
        boolean flag;
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = s;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            flag = true;
        else
            flag = false;
        return flag;
    }

    ////ELIMINA ESPACIOS EN BLANCO EXISTENTE
    public String cleanWhiteSpaces(String s){
        String r = s.replaceAll("\\s", "");
        return r;
    }

    ////ELIMINA GUIONES DENTRO DE LA CADENA
    public String cleanGuions(String s){
        String r = s.replaceAll("-", "");
        return r;
    }

    ////ELIMINA CARACTERES ESPECIALES O RAROS
    public String cleanSpecialString(String s){
        String r1 = s.replaceAll("'","");
        String r2 = r1.replaceAll("_"," ");
        String r3 = r2.replaceAll("´","");
        String r4 = r3.replaceAll("`", "");

        String r = r4;

        return r;
    }

    ////ELIMINA ESPACIO EN BLACO PRESENTE EN EL LADO DERECHO DE LA CADENA
    public String cleanRightWhiteSpaces(String  s){
        String r = "";
        int cc  = 0;
        for (int i = s.length()-1; i >= 0; i--){
            if (s.charAt(i) != ' ') {
                cc = i;
                break;
            }
        }
        r = s.substring(0,cc+1);
        return r;
    }

    ////CALCULA LA DISTANCIA ENTRE DOS PUNTOS GEO EN LINEA RECTA (EN KM)
    public float GeoDistance(float point_ax, float point_ay, float point_bx, float point_by){
        float dist = 0.0f;

        Location loc1 = new Location("");
        loc1.setLatitude(point_ax);
        loc1.setLongitude(point_ay);
        Location loc2 = new Location("");
        loc2.setLatitude(point_bx);
        loc2.setLongitude(point_by);

        dist = loc1.distanceTo(loc2);
        Log_i("GEO DISTANCE: "+dist,TAG_CLASS);

        return dist;
    }

    //////////-------[PREFERENCES]------------------------------------------------------------------

    public SharedPreferences getPref(){
        return pref;
    }

    public SharedPreferences.Editor getEditor(){
        return edit;
    }

    public void MakeToast(String text){
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }
}
