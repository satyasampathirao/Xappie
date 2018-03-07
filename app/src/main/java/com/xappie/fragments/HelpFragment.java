package com.xappie.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    public static final String TAG = HelpFragment.class.getSimpleName();

    private DashBoardActivity mParent;
    private AppBarLayout appBarLayout;
    private FrameLayout mFrameLayout;
    private CoordinatorLayout.LayoutParams mParams;

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_account_settings)
    TextView tv_account_settings;
    @BindView(R.id.tv_promotions)
    TextView tv_promotions;
    @BindView(R.id.tv_terms)
    TextView tv_terms;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.tv_report)
    TextView tv_report;
    @BindView(R.id.tv_general)
    TextView tv_general;
    @BindView(R.id.tv_general_mail)
    TextView tv_general_mail;
    @BindView(R.id.tv_something)
    TextView tv_something;
    @BindView(R.id.tv_something_mail)
    TextView tv_something_mail;
    @BindView(R.id.tv_report_abuse)
    TextView tv_report_abuse;
    @BindView(R.id.tv_report_abuse_mail)
    TextView tv_report_abuse_mail;
    @BindView(R.id.tv_advertise)
    TextView tv_advertise;
    @BindView(R.id.tv_policies)
    TextView tv_policies;
    @BindView(R.id.tv_if_you)
    TextView tv_if_you;

    private Typeface mTypefaceOpenSansRegular;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        mFrameLayout = (FrameLayout) getActivity().findViewById(R.id.content_frame);
        mParams = (CoordinatorLayout.LayoutParams) mFrameLayout.getLayoutParams();
        Utility.sendGoogleAnalytics(mParent, TAG);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (appBarLayout != null) {
            mParams.setBehavior(null);
            mFrameLayout.requestLayout();
            appBarLayout.setVisibility(View.GONE);
        }
        View rootView = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    /**
     * This method is used to initialization
     */
    private void initUI() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);

        tv_back.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_account_settings.setTypeface(mTypefaceOpenSansRegular);
        tv_privacy.setTypeface(mTypefaceOpenSansRegular);
        tv_promotions.setTypeface(mTypefaceOpenSansRegular);
        tv_terms.setTypeface(mTypefaceOpenSansRegular);
        tv_if_you.setTypeface(mTypefaceOpenSansRegular);
        tv_general.setTypeface(mTypefaceOpenSansRegular);
        tv_general_mail.setTypeface(mTypefaceOpenSansRegular);
        tv_something.setTypeface(mTypefaceOpenSansRegular);
        tv_something_mail.setTypeface(mTypefaceOpenSansRegular);
        tv_report.setTypeface(mTypefaceOpenSansRegular);
        tv_report_abuse.setTypeface(mTypefaceOpenSansRegular);
        tv_report_abuse_mail.setTypeface(mTypefaceOpenSansRegular);
        tv_policies.setTypeface(mTypefaceOpenSansRegular);
        tv_advertise.setTypeface(mTypefaceOpenSansRegular);
    }

    @OnClick(R.id.tv_privacy)
    public void openPrivacy() {
        String url = "http://xappie.com/pages/privacy_policy";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.tv_promotions)
    public void openPromotions() {
        String url = "http://xappie.com/pages/advertise";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.tv_terms)
    public void openTerms() {
        String url = "http://xappie.com/pages/terms_conditions";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    @OnClick(R.id.tv_back)
    public void navigateBack() {
        mParent.onBackPressed();
    }

    @OnClick(R.id.tv_general_mail)
    public void giveFeedback()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: feedback@xappie.com"));
        startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }

    @OnClick(R.id.tv_something_mail)
    public void giveSupport()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: support@xappie.com"));
        startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }

    @OnClick(R.id.tv_report_abuse_mail)
    public void giveReport()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: report@xappie.com"));
        startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }
}
