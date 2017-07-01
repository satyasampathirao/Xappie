package com.xappie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity {


    @BindView(R.id.tv_edit_profile_back_icon)
    TextView tv_edit_profile_back_icon;

    @BindView(R.id.tv_edit_profile_menu_icon)
    TextView tv_edit_profile_menu_icon;
    @BindView(R.id.tv_edit_profile_location_icon)
    TextView tv_edit_profile_location_icon;
    @BindView(R.id.tv_edit_profile_notification_icon)
    TextView tv_edit_profile_notification_icon;
    @BindView(R.id.tv_edit_profile_font_icon)
    TextView tv_edit_profile_font_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        initUI();
    }
    private void initUI() {
        tv_edit_profile_back_icon.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_edit_profile_font_icon.setTypeface(Utility.getFontAwesomeWebFont(this));
        tv_edit_profile_location_icon.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_edit_profile_menu_icon.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_edit_profile_notification_icon.setTypeface(Utility.getMaterialIconsRegular(this));


    }
}
