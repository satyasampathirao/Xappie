package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.EntertainmentAdapter;
import com.xappie.models.EntertainmentModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntertainmentFragment extends Fragment {

    public static final String TAG = EntertainmentFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;

    /**
     * Gallery Toolbar
     */
    @BindView(R.id.tv_notification_arrow_back_icon)
    TextView tv_notification_arrow_back_icon;
    @BindView(R.id.tv_notification_menu_icon)
    TextView tv_notification_menu_icon;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_icon)
    TextView tv_location_icon;
    @BindView(R.id.tv_notifications_icon)
    TextView tv_notifications_icon;
    @BindView(R.id.tv_language_icon)
    TextView tv_language_icon;

    /**
     * Gallery Actress setup
     */
    @BindView(R.id.ll_languages)
    LinearLayout ll_languages;
    @BindView(R.id.list_view)
    ListView list_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null)
            appBarLayout.setVisibility(View.GONE);
        View rootView = inflater.inflate(R.layout.fragment_entertainment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        setTypeFace();
    }

    private void setTypeFace() {
        tv_notification_arrow_back_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_notification_menu_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(Utility.getResourcesString(mParent, R.string.entertainment).toUpperCase());
        tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

        tv_location_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_notifications_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_language_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

        setLanguages();
        setGridViewData();
    }

    /*This method is used to set the lsit view data*/
    private void setGridViewData() {
        EntertainmentAdapter entertainmentAdapter = new EntertainmentAdapter(mParent, getEntertainData());
        list_view.setAdapter(entertainmentAdapter);
    }

    private ArrayList<EntertainmentModel> getEntertainData() {
        ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            EntertainmentModel entertainmentModel = new EntertainmentModel();
            entertainmentModel.setId(R.drawable.video_hint);
            entertainmentModel.setTitle("Rarandoi");
            entertainmentModels.add(entertainmentModel);
        }
        return entertainmentModels;
    }

    /*This method is used to set the languages*/
    private void setLanguages() {
        ll_languages.removeAllViews();
        for (int i = 0; i < getLanguagesData().size(); i++) {
            LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.language_item, null);
            TextView tv_language_name = (TextView) ll.findViewById(R.id.tv_language_name);
            View view = (View) ll.findViewById(R.id.view);
            tv_language_name.setText(getLanguagesData().get(i));
            tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
            tv_language_name.setTypeface(Utility.getOpenSansBold(mParent));
            if (i == 0) {
                view.setVisibility(View.VISIBLE);
                tv_language_name.setTextColor(Utility.getColor(mParent, R.color.white));
                view.setBackgroundColor(Utility.getColor(mParent, R.color.white));
            } else {
                view.setVisibility(View.GONE);
            }
            ll_languages.addView(ll);
        }
    }

    private ArrayList<String> getLanguagesData() {
        ArrayList<String> mLanguagesData = new ArrayList<>();
        mLanguagesData.add("HINDI");
        mLanguagesData.add("ENGLISH");
        mLanguagesData.add("TELUGU");
        mLanguagesData.add("TAMIL");
        return mLanguagesData;
    }

}
