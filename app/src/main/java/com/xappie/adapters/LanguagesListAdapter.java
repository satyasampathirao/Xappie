package com.xappie.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.models.LanguageModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;


public class LanguagesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<LanguageModel> languagesListModels;
    private Typeface mOpenSansRegularTypeface;

    public LanguagesListAdapter(Context context, ArrayList<LanguageModel> languagesListModels) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.languagesListModels = languagesListModels;
        mOpenSansRegularTypeface = Utility.getOpenSansRegular(context);
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

        LanguagesListAdapter.LanguagesListHolder mLanguagesListHolder;
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

        LanguageModel languagesListModel = languagesListModels.get(position);
        mLanguagesListHolder.tv_title.setText(languagesListModel.getName_native());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashBoardIntent = new Intent(context, DashBoardActivity.class);
                context.startActivity(dashBoardIntent);
            }
        });

        return convertView;
    }

    private class LanguagesListHolder {
        TextView tv_title;

    }
}
