package com.xappie.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.fragments.DiscussionsListFragment;
import com.xappie.models.DiscussionsModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/28/2017.
 */

public class DiscussionsAdapter extends BaseAdapter {
    private DashBoardActivity mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<DiscussionsModel> discussionsModels;
    private Typeface typefaceOpenSansRegular;

    public DiscussionsAdapter(DashBoardActivity context, ArrayList<DiscussionsModel> discussionsModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.discussionsModels = discussionsModels;
        typefaceOpenSansRegular = Utility.getOpenSansRegular(mContext);
    }

    @Override
    public int getCount() {
        return discussionsModels.size();
    }

    @Override
    public Object getItem(int position) {
        return discussionsModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        DiscussionsAdapter.DiscussionsAdapterHolder mDiscussionsAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.discussions_grid_item,
                    null);
            mDiscussionsAdapterHolder = new DiscussionsAdapter.DiscussionsAdapterHolder();
            mDiscussionsAdapterHolder.txt_popular_name = (TextView) convertView.findViewById(R.id.tv_discussions_name);
            mDiscussionsAdapterHolder.img_popular_our_forum = (ImageView) convertView.findViewById(R.id.img_discussions_forum);
            mDiscussionsAdapterHolder.txt_popular_name.setTypeface(typefaceOpenSansRegular);
            convertView.setTag(mDiscussionsAdapterHolder);
        } else {
            mDiscussionsAdapterHolder = (DiscussionsAdapter.DiscussionsAdapterHolder) convertView.getTag();
        }

        final DiscussionsModel discussionsModel = discussionsModels.get(position);
        mDiscussionsAdapterHolder.txt_popular_name.setText(discussionsModel.getName());
        /*if (!Utility.isValueNullOrEmpty(discussionsModel.getImage())) {
            Utility.universalImageLoaderPicLoading(mDiscussionsAdapterHolder.img_popular_our_forum,
                    discussionsModel.getImage(), null, R.drawable.xappie_place_);
        } else {
            Utility.universalImageLoaderPicLoading(mDiscussionsAdapterHolder.img_popular_our_forum,
                    "", null, R.drawable.xappie_place_);
        }*/

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.navigateDashBoardFragment(new DiscussionsListFragment(), DiscussionsListFragment.TAG, null, mContext);
            }
        });

        return convertView;
    }


    private class DiscussionsAdapterHolder {
        private TextView txt_popular_name;
        private ImageView img_popular_our_forum;
    }
}
