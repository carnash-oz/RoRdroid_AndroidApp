package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.models.DemoObject;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class Graphics {

    private static String TAG_CLASS = "GRAPHICS CLASS";

    public static int PREF_FONTSIZE_SMALLTEXT;
    public static int PREF_FONTSIZE_SIMPLETEXT;
    public static int PREF_FONTSIZE_HEADERTEXT;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    private Context ctx;
    private Tools tools;

    public Graphics(Context ctx){
        this.ctx = ctx;

        pref = ctx.getSharedPreferences("VISUAL", 0);
        edit = pref.edit();

        tools = new Tools(ctx);

        //---[RELOAD PREF DATA]---//
        PREF_FONTSIZE_SMALLTEXT = pref.getInt(ctx.getString(R.string.pref_fontsize_smalltext), 9);
        PREF_FONTSIZE_SIMPLETEXT = pref.getInt(ctx.getString(R.string.pref_fontsize_simpletext), 10);
        PREF_FONTSIZE_HEADERTEXT = pref.getInt(ctx.getString(R.string.pref_fontsize_headertext), 11);
    }

    //-----------[AREAS]----------------------------------------------------------------------------

    /*SEPARADOR SIMPLE */
    public LinearLayout AddSeparator(){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, tools.toDPI(12));
        _layout.setLayoutParams(_layout_params);
        _layout.setPadding(tools.toDPI(5), tools.toDPI(5), tools.toDPI(5), tools.toDPI(5));
        _layout.setOrientation(LinearLayout.VERTICAL);
        {
            FrameLayout _frame = new FrameLayout(ctx);
            FrameLayout.LayoutParams _frame_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            _frame.setLayoutParams(_frame_params);
            _frame.setBackgroundColor(ctx.getResources().getColor(R.color.color_purplecheese));

            _layout.addView(_frame);
        }
        return _layout;
    }

    /*SEPARADOR CON COLOR MODIFICABLE */
    public LinearLayout AddSeparatorColor(int color){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, tools.toDPI(12));
        _layout.setLayoutParams(_layout_params);
        _layout.setPadding(tools.toDPI(5), tools.toDPI(5), tools.toDPI(5), tools.toDPI(5));
        _layout.setOrientation(LinearLayout.VERTICAL);
        {
            FrameLayout _frame = new FrameLayout(ctx);
            FrameLayout.LayoutParams _frame_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            _frame.setLayoutParams(_frame_params);
            _frame.setBackgroundColor(color);

            _layout.addView(_frame);
        }
        return _layout;
    }

    /*LINEAR LAYOUT SIMPLE (DEF. VERTICAL)*/
    public LinearLayout AddLayout(){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        _layout.setLayoutParams(_layout_params);
        _layout.setOrientation(LinearLayout.VERTICAL);

        return _layout;
    }

    /*LINEAR LAYOUT MOD ORIENTACION*/
    public LinearLayout AddLayout(int mode){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        _layout.setLayoutParams(_layout_params);
        _layout.setOrientation(mode);

        return _layout;
    }

    /*LINEAR LAYOUT MOD PESO*/
    public LinearLayout AddLayout(float weitht){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        _layout_params.weight = weitht;
        _layout.setLayoutParams(_layout_params);
        _layout.setOrientation(LinearLayout.VERTICAL);

        return _layout;
    }

    /*LINEAR LAYOUT MOD ORIENTACION Y PESO*/
    public LinearLayout AddLayout(int mode, float weitht){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        _layout_params.weight = weitht;
        _layout.setLayoutParams(_layout_params);
        _layout.setOrientation(mode);

        return _layout;
    }

    /*ESPACIADOR VERTICAL*/
    public LinearLayout AddSpaceVertical(int dpi){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,tools.toDPI(dpi));
        _layout.setLayoutParams(_layout_params);

        return _layout;
    }

    /*ESPACIADOR HORIZONAL*/
    public LinearLayout AddSpaceHorizontal(int dpi){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(tools.toDPI(dpi),LinearLayout.LayoutParams.MATCH_PARENT);
        _layout.setLayoutParams(_layout_params);

        return _layout;
    }

    //-----------[IMAGENES]-------------------------------------------------------------------------

    /* IMAGEN */
    public ImageView AddImage(final String s_icon, final int w, final int h){
        final int img_icon = ctx.getResources().getIdentifier(s_icon, "drawable", ctx.getPackageName());
        ImageView image_delivery = new ImageView(ctx);
        LinearLayout.LayoutParams image_delivery_params = new LinearLayout.LayoutParams(tools.toDPI(h), tools.toDPI(w));
        image_delivery.setLayoutParams(image_delivery_params);
        image_delivery.setAdjustViewBounds(true);
        image_delivery.setImageResource(img_icon);

        return image_delivery;
    }

    /* AGREGA A UNA IMAGEN UN WEIGHT */
    public LinearLayout AddWeightImage(ImageView imgview){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        _layout_params.weight = 1.0f;
        _layout.setLayoutParams(_layout_params);
        _layout.setOrientation(LinearLayout.VERTICAL);
        _layout.setBackgroundColor(ctx.getResources().getColor(R.color.color_white));
        _layout.setPadding(tools.toDPI(5), tools.toDPI(5), tools.toDPI(5), tools.toDPI(5));
        _layout.addView(imgview);
        imgview.setAdjustViewBounds(true);
        return _layout;
    }

    /* MOTIFICACION EN IMAGEN CON TEXTO A COLOR */
    public LinearLayout AddNotiWithImage(String s_img, int w, int h, String s_txt, int color){
        LinearLayout _layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams _layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        _layout_params.weight = 1.0f;
        _layout.setLayoutParams(_layout_params);
        _layout.setGravity(Gravity.CENTER);
        _layout.setOrientation(LinearLayout.VERTICAL);
        _layout.setBackgroundColor(ctx.getResources().getColor(R.color.color_whitesoft));
        _layout.setPadding(tools.toDPI(5), tools.toDPI(5), tools.toDPI(5), tools.toDPI(5));

        TextView tmp_txt = AddHeaderTextColor(s_txt, color);
        tmp_txt.setLayoutParams(_layout_params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tmp_txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        _layout.addView(AddImage(s_img,w,h));
        _layout.addView(tmp_txt);

        return _layout;
    }

    //-----------[TEXTOS]---------------------------------------------------------------------------

    /* TEXTO SIMPLE */
    public TextView AddSimpleText(String msg){
        TextView txt_view = new TextView(ctx);
        LinearLayout.LayoutParams txt_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        txt_view.setLayoutParams(txt_view_params);
        txt_view.setText(msg);
        txt_view.setSingleLine();
        txt_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, PREF_FONTSIZE_SIMPLETEXT);

        return txt_view;
    }

    /* TEXTO SIMPLE CON COLOR */
    public TextView AddSimpleTextColor(String msg, int color){
        TextView txt_view = AddSimpleText(msg);
        txt_view.setTextColor(color);
        return txt_view;
    }

    /* TEXTO CABECERA */
    public TextView AddHeaderText(String msg){
        TextView txt_view = new TextView(ctx);
        LinearLayout.LayoutParams txt_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        txt_view.setLayoutParams(txt_view_params);
        txt_view.setText(msg);
        txt_view.setSingleLine();
        txt_view.setEllipsize(TextUtils.TruncateAt.END);
        txt_view.setHorizontallyScrolling(false);
        txt_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, PREF_FONTSIZE_HEADERTEXT);
        txt_view.setTypeface(null, Typeface.BOLD);

        return txt_view;
    }

    /* TEXTO CABECERA CON ALINEACION */
    public TextView AddHeaderText(String msg, int align){
        TextView txt_view = AddHeaderText(msg);
        txt_view.setGravity(align);
        return txt_view;
    }

    /* TEXTO CABECERA CON COLOR */
    public TextView AddHeaderTextColor(String msg, int color){
        TextView txt_view = AddHeaderText(msg);
        txt_view.setTextColor(color);
        return txt_view;
    }

    /* TEXTO CABECERA CON COLOR Y ALINEACION */
    public TextView AddHeaderTextColor(String msg, int color, int align){
        TextView txt_view = AddHeaderText(msg);
        txt_view.setTextColor(color);
        txt_view.setGravity(align);
        return txt_view;
    }

    //-----------[GUI]------------------------------------------------------------------------------

    /* CHECKBOX SIMPLE CON TEXTO */
    public CheckBox AddCheckbox(String msg, boolean flag){
        CheckBox ck_box = new CheckBox(ctx);
        ck_box.setText(msg);
        ck_box.setChecked(flag);
        ck_box.setEnabled(false);
        return ck_box;
    }

    //////----------[GRAF OBJECTS]------------------------------------------------------------------

    /**
     * AGREGAR GRAFICOS EN BASE A OBJETOS EXISTENTES EN MODELS
     */

    public LinearLayout CellDemoObjectView(DemoObject this_item){
        LinearLayout cell_layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams cell_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        cell_layout_params.setMargins(tools.toDPI(5), tools.toDPI(5), tools.toDPI(5), tools.toDPI(5));
        cell_layout.setLayoutParams(cell_layout_params);
        cell_layout.setOrientation(LinearLayout.VERTICAL);
        cell_layout.setPadding(tools.toDPI(5), tools.toDPI(5), tools.toDPI(5), tools.toDPI(5));
        cell_layout.setBackgroundColor(ctx.getResources().getColor(R.color.color_whitemid));

        cell_layout.addView(AddHeaderText(this_item.getTitle()));
        cell_layout.addView(AddSimpleText(this_item.getValue()+""));
        cell_layout.addView(AddSimpleText(this_item.getDescription()+""));

        return cell_layout;
    }

}
