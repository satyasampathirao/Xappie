package com.xappie.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfileActivity extends AppCompatActivity {

    @BindView(R.id.tv_my_profile_back_icon)
    TextView tv_my_profile_back_icon;

    @BindView(R.id.tv_my_profile_menu_icon)
    TextView tv_my_profile_menu_icon;
    @BindView(R.id.tv_my_profile_location_icon)
    TextView tv_my_profile_location_icon;
    @BindView(R.id.tv_my_profile_notification_icon)
    TextView tv_my_profile_notification_icon;
    @BindView(R.id.tv_my_profile_font_icon)
    TextView tv_my_profile_font_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_my_profile);

        ButterKnife.bind(this);
        initUI();
    }
    private void initUI() {

        tv_my_profile_back_icon.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_my_profile_font_icon.setTypeface(Utility.getFontAwesomeWebFont(this));
        tv_my_profile_location_icon.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_my_profile_menu_icon.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_my_profile_notification_icon.setTypeface(Utility.getMaterialIconsRegular(this));


    }

    @OnClick(R.id.b_edit_profile)
    public void edit_profile(View v)
    {
        Intent i = new Intent(this, EditProfileActivity.class);
        startActivity(i);
    }
    }

