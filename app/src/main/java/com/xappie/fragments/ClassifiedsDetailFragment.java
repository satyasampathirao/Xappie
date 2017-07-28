package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shankar on 7/28/2017.
 */

public class ClassifiedsDetailFragment extends Fragment {

    public static final String TAG = ClassifiedsDetailFragment.class.getSimpleName();
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

    @BindView(R.id.img_person)
    ImageView img_person;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_post_title)
    TextView tv_post_title;
    @BindView(R.id.tv_topic_details)
    TextView tv_topic_details;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.img_uploaded)
    ImageView img_uploaded;

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
        View rootView = inflater.inflate(R.layout.fragment_classifieds_detail, container, false);
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
        tv_title.setText(Utility.getResourcesString(mParent, R.string.auto_mobiles));
        tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

        tv_location_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_notifications_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_language_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

        tv_name.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_date.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_post_title.setTypeface(Utility.getOpenSansBold(mParent));
        tv_topic_details.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_price.setTypeface(Utility.getOpenSansBold(mParent));
        tv_post_title.setText("Lorem ipsum is simply dummy text of the printing and typesetting");
        tv_topic_details.setText("Lorem ipsum is simply dummy text of the printing and typesetting" +
                "Lorem ipsum is simply dummy text of the printing and typesetting" +
                "Lorem ipsum is simply dummy text of the printing and typesetting" +
                "Lorem ipsum is simply dummy text of the printing and typesetting" +
                "Lorem ipsum is simply dummy text of the printing and typesetting" +
                "Lorem ipsum is simply dummy text of the printing and typesetting");

        tv_price.setText("Price : $20,200");
    }

}
