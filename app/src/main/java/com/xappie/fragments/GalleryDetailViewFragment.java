package com.xappie.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryDetailViewFragment extends Fragment {


    public static final String TAG = GalleryDetailViewFragment.class.getSimpleName();
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

    @BindView(R.id.tv_header_title)
    TextView tv_header_title;
    @BindView(R.id.tv_written_by)
    TextView tv_written_by;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_description)
    TextView tv_description;


    @BindView(R.id.tv_next_news)
    TextView tv_next_news;
    @BindView(R.id.tv_next_news_header_title)
    TextView tv_next_news_header_title;
    @BindView(R.id.tv_next_icon)
    TextView tv_next_icon;

    @BindView(R.id.tv_more_topics)
    TextView tv_more_topics;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.ll_related_topics)
    LinearLayout ll_related_topics;


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
        View rootView = inflater.inflate(R.layout.fragment_gallery_detail_view, container, false);
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

    /**
     * This method is used for sets the typeface screen
     */
    private void setTypeFace() {
        tv_notification_arrow_back_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_notification_menu_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

        tv_title.setVisibility(View.INVISIBLE);

        tv_location_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_notifications_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_language_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

        tv_header_title.setText("Allu arjun, Pooja Hedga are the saving grace of this tacky song");
        tv_header_title.setTypeface(Utility.getOpenSansBold(mParent));

        tv_written_by.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_time.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_description.setText("Allu arjun, Pooja Hedga are the saving grace of this tacky song " +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song " +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song " +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song" +
                "Allu arjun, Pooja Hedga are the saving grace of this tacky song");
        tv_description.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_next_news.setTypeface(Utility.getOpenSansRegular(mParent));
        tv_next_news_header_title.setText("Arjun is back with Kurukshetra movie. For more gossips, film reviews, mania, life things, series");
        tv_next_news_header_title.setTypeface(Utility.getOpenSansBold(mParent));
        tv_next_icon.setTypeface(Utility.getMaterialIconsRegular(mParent));

        tv_more_topics.setTypeface(Utility.getMaterialIconsRegular(mParent));
        tv_more.setTypeface(Utility.getMaterialIconsRegular(mParent));

        setRelatedTopics(getRelatedTopicsData());
    }

    private ArrayList<RelatedTopicsModel> getRelatedTopicsData() {
        ArrayList<RelatedTopicsModel> relatedTopicsModels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            RelatedTopicsModel relatedTopicsModel = new RelatedTopicsModel();
            relatedTopicsModel.setId(R.drawable.video_hint);
            relatedTopicsModel.setTitle("Prabhas bahubali 2 second week posters");
            relatedTopicsModels.add(relatedTopicsModel);
        }
        return relatedTopicsModels;
    }


    /*Set Related Data*/
    private void setRelatedTopics(ArrayList<RelatedTopicsModel> relatedTopicsModels) {
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 8f);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4f);
        lp2.setMargins(15, 15, 15, 15);

        ll_related_topics.removeAllViews();

        int rowCount = 0;
        LinearLayout linearLayout = null;

        if (relatedTopicsModels != null && relatedTopicsModels.size() > 0) {
            for (int j = 0; j < relatedTopicsModels.size(); j++) {

                if (rowCount == 0) {
                    linearLayout = new LinearLayout(mParent);
                    linearLayout.setLayoutParams(lp1);
                }

                @SuppressLint("InflateParams")
                LinearLayout ll = (LinearLayout) mParent.getLayoutInflater().inflate(R.layout.releated_topic_item, null);
                TextView tv_related_title = (TextView) ll.findViewById(R.id.tv_related_title);
                ImageView img_topic = (ImageView) ll.findViewById(R.id.img_topic);

                tv_related_title.setText(relatedTopicsModels.get(j).getTitle());
                tv_related_title.setTypeface(Utility.getOpenSansRegular(mParent));

                ll.setLayoutParams(lp2);
                linearLayout.addView(ll);

                rowCount += 1;
                if (rowCount == 2 || j == relatedTopicsModels.size() - 1) {
                    ll_related_topics.addView(linearLayout);
                    rowCount = 0;
                }
            }
        }
    }
}
