package com.xappie.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;

/**
 * Created by Ravi on 20-Jul-17.
 */

public class AdapterforNotificationList extends BaseAdapter
{
    private int[] heading;
    private FragmentActivity context;
    private int[] time;
    private String[] name;
    private String[] dots;

    private static LayoutInflater inflater = null;

    public AdapterforNotificationList(DashBoardActivity activity, int[] matter, int[] count,String[] person, String[] dot) {

        heading = matter;
        context = activity;
        time = count;
        name = person;
        dots = dot;


        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return heading.length;
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
        TextView tv_head;
        TextView tv_time;
        TextView tv_dot;
        TextView  tv_name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        Holder holder = new Holder();
        View rowView;


        rowView = inflater.inflate(R.layout.custom_notification_item, null);
        holder.tv_head = (TextView) rowView.findViewById(R.id.tv_list_heading);
        holder.tv_time = (TextView) rowView.findViewById(R.id.tv_list_time);
        holder.tv_dot=(TextView) rowView.findViewById(R.id.tv_list_dot);
        holder.tv_name= (TextView) rowView.findViewById(R.id.tv_list_person);

        holder.tv_head.setText(heading[i]);
        holder.tv_time.setText(time[i]);
        holder.tv_name.setText(name[i]);
        holder.tv_dot.setText(dots[i]);


        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });

        return rowView;
    }
}
