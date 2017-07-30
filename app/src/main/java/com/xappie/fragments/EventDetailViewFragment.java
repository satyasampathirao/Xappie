package com.xappie.fragments;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shankar on 7/28/2017.
 */
public class EventDetailViewFragment extends Fragment {

    public static final String TAG = EventDetailViewFragment.class.getSimpleName();
    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    /**
     * Event Detail
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

    @BindView(R.id.tv_event_name)
    TextView tv_event_name;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.tv_dress_code)
    TextView tv_dress_code;
    @BindView(R.id.tv_dress_code_value)
    TextView tv_dress_code_value;
    @BindView(R.id.tv_restrictions)
    TextView tv_restrictions;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_event_tag_line_text_comes_here)
    TextView tv_event_tag_line_text_comes_here;
    @BindView(R.id.tv_total_cost)
    TextView tv_total_cost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_event_detail_view, container, false);
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
        tv_title.setText(Utility.getResourcesString(mParent, R.string.events));
        tv_title.setTypeface(Utility.getOpenSansRegular(mParent));

        tv_location_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_notifications_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_language_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

        tv_event_name.setTypeface(Utility.getOpenSansBold(mParent));
        tv_date_time.setTypeface(Utility.getOpenSansBold(mParent));
        tv_dress_code.setPaintFlags(tv_dress_code.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_dress_code.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_dress_code_value.setTypeface(Utility.getOpenSansBold(mParent));
        tv_restrictions.setTypeface(Utility.getOpenSansBold(mParent));
        tv_address.setTypeface(Utility.getOpenSansBold(mParent));

        tv_event_tag_line_text_comes_here.setTypeface(Utility.getOpenSansBold(mParent));
        tv_total_cost.setTypeface(Utility.getOpenSansRegular(mParent));
    }
}
