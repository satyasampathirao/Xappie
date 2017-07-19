package com.xappie.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.fragments.HomeFragment;
import com.xappie.utils.Utility;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private TextView tv_location_icon;
    private TextView tv_notifications_icon;
    private TextView tv_language_icon;

    private LinearLayout layout_topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        initUI();
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tv_location_icon = (TextView) toolbar.findViewById(R.id.tv_location_icon);
        tv_location_icon.setTypeface(Utility.getMaterialIconsRegular(this));

        tv_notifications_icon = (TextView) toolbar.findViewById(R.id.tv_notifications_icon);
        tv_notifications_icon.setTypeface(Utility.getMaterialIconsRegular(this));

        tv_language_icon = (TextView) toolbar.findViewById(R.id.tv_language_icon);
        tv_language_icon.setTypeface(Utility.getMaterialIconsRegular(this));

        layout_topics = (LinearLayout) findViewById(R.id.layout_topics);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        initNavigationDrawer();
        setDataToHomeTabs();
    }

    /**
     * This method is used to sets the Navigation Drawer operation
     */
    public void initNavigationDrawer() {
        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, DashBoardActivity.this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(DashBoardActivity.this,
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        navMenuView.addItemDecoration(horizontalDecoration);


       /* navMenuView.addItemDecoration(new DividerItemDecoration(DashBoardActivity.this, DividerItemDecoration.VERTICAL));
        Drawable horizontalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.discussions:

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.entertainment:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.top_stories:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.gallery:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.videos:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.events:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.classifieds:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.jobs:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.account_settings:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        logout();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        ImageView img_user_image = (ImageView) header.findViewById(R.id.img_user_image);
        TextView txt_name = (TextView) header.findViewById(R.id.txt_name);
        TextView txt_user_designation = (TextView) header.findViewById(R.id.txt_user_designation);

        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        final View.OnClickListener originalToolbarListener = actionBarDrawerToggle.getToolbarNavigationClickListener();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(originalToolbarListener);
                }
            }
        });

    }

    /**
     * This method is used to logout from the application
     */
    private void logout() {
        navigateToDashBoard();
    }

    private void navigateToDashBoard() {

    }

    /**
     * This method is used to navigation of location selection
     */
    @OnClick(R.id.tv_location_icon)
    void navigateLocation() {

    }

    /**
     * This method is used to navigation of notifications
     */
    @OnClick(R.id.tv_notifications_icon)
    void navigateNotifications() {

    }

    /**
     * This method is used to navigation of language
     */
    @OnClick(R.id.tv_language_icon)
    void navigateLanguage() {

    }

    private int selected_position = 0;

    private void setDataToHomeTabs() {
        layout_topics.removeAllViews();
        for (int i = 0; i < getTabNames().size(); i++) {
            @SuppressLint("InflateParams")
            LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.textview_layout, null);
            TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
            tv_title.setText(getTabNames().get(i));
            if (i == selected_position) {
                tv_title.setTypeface(Utility.getOpenSansBold(DashBoardActivity.this));
            } else {
                tv_title.setTypeface(Utility.getOpenSansRegular(DashBoardActivity.this));
            }
            tv_title.setId(i);
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = view.getId();
                    selected_position = pos;
                    setDataToHomeTabs();
                }
            });
            layout_topics.addView(ll);
        }
    }

    private ArrayList<String> getTabNames() {
        ArrayList<String> mTabNames = new ArrayList<>();
        mTabNames.add(Utility.getResourcesString(this, R.string.home).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.discussions).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.entertainment).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.top_stories).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.gallery).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.videos).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.events).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.classifieds).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.jobs).toUpperCase());
        return mTabNames;
    }

}