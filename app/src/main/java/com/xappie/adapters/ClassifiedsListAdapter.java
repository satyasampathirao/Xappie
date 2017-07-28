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
import com.xappie.fragments.ClassifiedsDetailFragment;
import com.xappie.models.EntertainmentModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 28/07/2017
 */

public class ClassifiedsListAdapter extends BaseAdapter {

    private DashBoardActivity mDashBoardActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<EntertainmentModel> entertainmentModels;
    private Typeface mOpenSansBoldTypeface;
    private Typeface mOpenSansRegularTypeface;

    public ClassifiedsListAdapter(DashBoardActivity mDashBoardActivity, ArrayList<EntertainmentModel> entertainmentModels) {
        this.mDashBoardActivity = mDashBoardActivity;
        mLayoutInflater = LayoutInflater.from(mDashBoardActivity);
        this.entertainmentModels = entertainmentModels;
        mOpenSansBoldTypeface = Utility.getOpenSansBold(mDashBoardActivity);
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(mDashBoardActivity);
    }

    @Override
    public int getCount() {
        return entertainmentModels.size();
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
        ClassifiedsListHolder mClassifiedsListHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.entertainment_list_item,
                    null);
            mClassifiedsListHolder = new ClassifiedsListHolder();
            mClassifiedsListHolder.img_logo = (ImageView) convertView.findViewById(R.id.img_logo);
            mClassifiedsListHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mClassifiedsListHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mClassifiedsListHolder.tv_posted_by = (TextView) convertView.findViewById(R.id.tv_posted_by);
            mClassifiedsListHolder.tv_posted_by.setVisibility(View.GONE);
            mClassifiedsListHolder.tv_time.setTextColor(Utility.getColor(mDashBoardActivity, R.color.hello_color));

            mClassifiedsListHolder.tv_title.setTypeface(mOpenSansBoldTypeface);
            mClassifiedsListHolder.tv_time.setTypeface(mOpenSansRegularTypeface);
            mClassifiedsListHolder.tv_posted_by.setTypeface(mOpenSansRegularTypeface);

            convertView.setTag(mClassifiedsListHolder);
        } else {
            mClassifiedsListHolder = (ClassifiedsListHolder) convertView.getTag();
        }

        EntertainmentModel entertainmentModel = entertainmentModels.get(position);
        // mClassifiedsListHolder.tv_title.setText(entertainmentModel.getTitle());
        mClassifiedsListHolder.tv_title.setText("Lorem ipsum is simply dummy text of the printing and typesetting industry");
        mClassifiedsListHolder.tv_time.setText(Utility.getResourcesString(mDashBoardActivity, R.string.ravi_kiran).toUpperCase());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new ClassifiedsDetailFragment(), ClassifiedsDetailFragment.TAG, null, mDashBoardActivity);
            }
        });


        return convertView;
    }

    private class ClassifiedsListHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_posted_by;
        ImageView img_logo;
    }
}
