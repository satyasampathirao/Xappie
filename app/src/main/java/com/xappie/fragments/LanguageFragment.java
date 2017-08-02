package com.xappie.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.LanguagesListAdapter;
import com.xappie.models.LanguagesListModel;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LanguageFragment extends Fragment {

    public static final String TAG = LanguageFragment.class.getSimpleName();

    @BindView(R.id.tv_languages_arrow_back_icon)
    TextView tv_languages_arrow_back_icon;
    @BindView(R.id.tv_languages_menu_icon)
    TextView tv_languages_menu_icon;
    @BindView(R.id.tv_languages)
    TextView tv_languages;
    @BindView(R.id.language_list_item)
    ListView language_list_item;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;
    private Typeface mTypefaceFontAwesomeWebFont;

    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
    }

    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_language, container, false);
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
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);
        mTypefaceFontAwesomeWebFont = Utility.getFontAwesomeWebFont(mParent);
         mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_languages.setTypeface(mTypefaceOpenSansBold);
        tv_languages_arrow_back_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_languages_menu_icon.setTypeface(mTypefaceFontAwesomeWebFont);
        tv_languages.setTypeface(mTypefaceFontAwesomeWebFont);

        language_list_item.setAdapter(new LanguagesListAdapter(mParent,getSampleData()));
    }

    private ArrayList<LanguagesListModel> getSampleData() {
        ArrayList<LanguagesListModel> languagesListModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LanguagesListModel languagesListModel = new LanguagesListModel();
            languagesListModel.setTitle("HINDI");
            languagesListModels.add(languagesListModel);
        }
        return languagesListModels;
    }


    @OnClick({R.id.tv_languages_arrow_back_icon,R.id.tv_languages_menu_icon})
    public void navigateBack()
    {
        mParent.onBackPressed();
    }
}
