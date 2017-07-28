package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifiedsPostFragment extends Fragment {

    public static final String TAG = ClassifiedsPostFragment.class.getSimpleName();
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


    @BindView(R.id.tv_post)
    TextView tv_post;
    @BindView(R.id.edt_topic_title)
    EditText edt_topic_title;
    @BindView(R.id.edt_description)
    EditText edt_description;


    @BindView(R.id.rl_img_item)
    RelativeLayout rl_img_item;
    @BindView(R.id.img_selected)
    ImageView img_selected;
    @BindView(R.id.tv_delete_icon)
    TextView tv_delete_icon;

    @BindView(R.id.ll_browse)
    LinearLayout ll_browse;
    @BindView(R.id.tv_camera_icon)
    TextView tv_camera_icon;
    @BindView(R.id.tv_gallery_icon)
    TextView tv_gallery_icon;

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
        View rootView = inflater.inflate(R.layout.fragment_classifieds_post, container, false);
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

        tv_post.setTypeface(Utility.getOpenSansRegular(mParent));
        edt_topic_title.setTypeface(Utility.getOpenSansRegular(mParent));
        edt_description.setTypeface(Utility.getOpenSansRegular(mParent));

        tv_delete_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_camera_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_gallery_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

    }

}
