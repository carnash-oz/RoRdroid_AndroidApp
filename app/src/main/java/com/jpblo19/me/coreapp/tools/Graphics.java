package com.jpblo19.me.coreapp.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.models.DemoObject;

/**
 * Created by jpblo19 on 5/16/16.
 */
public class Graphics {

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
        PREF_FONTSIZE_SIMPLETEXT = pref.getInt(ctx.getString(R.string.pref_fontsize_simpletext), 13);
        PREF_FONTSIZE_HEADERTEXT = pref.getInt(ctx.getString(R.string.pref_fontsize_headertext), 15);
    }

    public TextView AddSimpleText(String msg){
        TextView txt_view = new TextView(ctx);
        LinearLayout.LayoutParams txt_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        txt_view.setLayoutParams(txt_view_params);
        txt_view.setText(msg);
        txt_view.setSingleLine();
        txt_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, PREF_FONTSIZE_SIMPLETEXT);

        return txt_view;
    }

    public TextView AddHeaderText(String msg){
        TextView txt_view = new TextView(ctx);
        LinearLayout.LayoutParams txt_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        txt_view.setLayoutParams(txt_view_params);
        txt_view.setText(msg);
        txt_view.setSingleLine();
        txt_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, PREF_FONTSIZE_HEADERTEXT);
        txt_view.setTypeface(null, Typeface.BOLD);

        return txt_view;
    }

    public LinearLayout CellInfoView(String header, String body){
        LinearLayout cell_layout = new LinearLayout(ctx);
        LinearLayout.LayoutParams cell_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        cell_layout.setLayoutParams(cell_layout_params);
        cell_layout.setOrientation(LinearLayout.VERTICAL);
        cell_layout.setBackgroundColor(ctx.getResources().getColor(R.color.color_whitemid));

        cell_layout.addView(AddHeaderText(header));
        cell_layout.addView(AddSimpleText(body));

        return cell_layout;
    }

    //////----------[GRAF OBJECTS]------------------------------------------------------------------

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
