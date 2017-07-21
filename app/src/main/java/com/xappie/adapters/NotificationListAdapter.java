package com.xappie.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.models.NotificationsListModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Ravi on 20-Jul-17.
 * Edited by Shankar on 21/07/2017
 */

public class NotificationListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<NotificationsListModel> notificationsListModels;
    private Typeface mOpenSansRegularTypeface;

    public NotificationListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<NotificationsListModel> notificationsListModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.notificationsListModels = notificationsListModels;
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return notificationsListModels.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        NotificationListAdapter.NotificationListHolder mNotificationListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_notification_item,
                    null);
            mNotificationListHolder = new NotificationListAdapter.NotificationListHolder();
            mNotificationListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_list_heading);
            mNotificationListHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_list_time);
            mNotificationListHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_list_person);

            mNotificationListHolder.tv_title.setTypeface(mOpenSansRegularTypeface);
            mNotificationListHolder.tv_time.setTypeface(mOpenSansRegularTypeface);
            mNotificationListHolder.tv_name.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mNotificationListHolder);
        } else {
            mNotificationListHolder = (NotificationListAdapter.NotificationListHolder) convertView.getTag();
        }

        NotificationsListModel notificationsListModel = notificationsListModels.get(position);
        mNotificationListHolder.tv_title.setText(notificationsListModel.getTitle());
        mNotificationListHolder.tv_time.setText(notificationsListModel.getTime());
        mNotificationListHolder.tv_name.setText(notificationsListModel.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class NotificationListHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_name;
    }
}
