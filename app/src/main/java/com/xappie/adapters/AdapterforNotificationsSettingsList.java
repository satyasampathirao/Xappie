package com.xappie.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;

/**
 * Created by Ravi on 21-Jul-17.
 */

public class AdapterforNotificationsSettingsList extends BaseAdapter {

    private int[] setting_text;
    private FragmentActivity context;
    private boolean[] check_state;
    private static LayoutInflater inflater = null;

    public AdapterforNotificationsSettingsList(DashBoardActivity activity,int[] topic,boolean[] value)
    {
        setting_text = topic;
        context = activity;
        check_state = value;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return setting_text.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder {
        TextView tv_text;
        SwitchCompat sw_value;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdapterforNotificationsSettingsList.Holder holder = new AdapterforNotificationsSettingsList.Holder();
        View rowView;


        rowView = inflater.inflate(R.layout.custom_notifications_settings_item, null);
        holder.tv_text = (TextView) rowView.findViewById(R.id.tv_settings_text);
        holder.sw_value = (SwitchCompat) rowView.findViewById(R.id.switch_button);

        holder.tv_text.setText(setting_text[i]);
        holder.sw_value.setChecked(check_state[i]);

        return rowView;

    }
}
