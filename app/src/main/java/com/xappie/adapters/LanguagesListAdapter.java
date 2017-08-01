package com.xappie.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.models.LanguagesListModel;
import com.xappie.models.NotificationsListModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Ravi on 31-Jul-17.
 */

public class LanguagesListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<LanguagesListModel> languagesListModels;
    private Typeface mOpenSansRegularTypeface;

    public LanguagesListAdapter(DashBoardActivity mDashBoardActivity,ArrayList<LanguagesListModel> languagesListModels)
    {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.languagesListModels = languagesListModels;
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return languagesListModels.size();
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

        LanguagesListAdapter.LanguagesListHolder mLanguagesListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_language_item,
                    null);
            mLanguagesListHolder = new LanguagesListAdapter.LanguagesListHolder();
            mLanguagesListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_languages_list);

           mLanguagesListHolder.tv_title.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mLanguagesListHolder);
        } else {
            mLanguagesListHolder = (LanguagesListAdapter.LanguagesListHolder) convertView.getTag();
        }

        LanguagesListModel languagesListModel = languagesListModels.get(position);
        mLanguagesListHolder.tv_title.setText(languagesListModel.getTitle());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class LanguagesListHolder {
        TextView tv_title;

    }
}
