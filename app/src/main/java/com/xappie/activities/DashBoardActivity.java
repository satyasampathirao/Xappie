package com.xappie.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.xappie.R;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.customviews.CircleTransform;
import com.xappie.customviews.CustomProgressDialog;
import com.xappie.customviews.FilePath;
import com.xappie.designes.MaterialDialog;
import com.xappie.fragments.AccountSettingFragment;
import com.xappie.fragments.AddNewEventFragment;
import com.xappie.fragments.ApplyJobsFragment;
import com.xappie.fragments.ClassifiedsAddFragment;
import com.xappie.fragments.ClassifiedsFragment;
import com.xappie.fragments.CountriesFragment;
import com.xappie.fragments.DiscussionsFragment;
import com.xappie.fragments.EntertainmentFragment;
import com.xappie.fragments.EventsFragment;
import com.xappie.fragments.GalleryFragment;
import com.xappie.fragments.HelpFragment;
import com.xappie.fragments.HomeFragment;
import com.xappie.fragments.JobsFragment;
import com.xappie.fragments.LanguageFragment;
import com.xappie.fragments.MyProfileFragment;
import com.xappie.fragments.NotificationsFragment;
import com.xappie.fragments.PostJobFragment;
import com.xappie.fragments.PreferenceFragment;
import com.xappie.fragments.TopStoriesFragment;
import com.xappie.fragments.VideosFragment;
import com.xappie.models.ImageUploadModel;
import com.xappie.models.Model;
import com.xappie.parser.ImageUpdateParser;
import com.xappie.permisions.Permissions;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DashBoardActivity extends BaseActivity implements IAsyncCaller {

    private static final String TAG ="DashBoardActivity" ;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;

    private TextView tv_location_icon;
    private TextView tv_notifications_icon;
    private TextView tv_language_icon;

    private LinearLayout layout_topics;
    private File mSelectedFile;
    public static File mYourFile;
    private CustomProgressDialog customProgressDialog;

    private ImageUploadModel mImageUploadModel;

    private ImageView img_user_image;
    private TextView tv_first_last;
    public InterstitialAd mInterstitialAd;

    public static Uri imageUri;
    private static final String SAVED_INSTANCE_URI = "uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        if (savedInstanceState != null) {
            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
        }
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        initUI();
        customProgressDialog = new CustomProgressDialog(this);
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        tv_location_icon = (TextView) toolbar.findViewById(R.id.tv_location_icon);
        tv_location_icon.setTypeface(Utility.getFontAwesomeWebFont(this));

        tv_notifications_icon = (TextView) toolbar.findViewById(R.id.tv_notifications_icon);
        tv_notifications_icon.setTypeface(Utility.getFontAwesomeWebFont(this));

        tv_language_icon = (TextView) toolbar.findViewById(R.id.tv_language_icon);
        tv_language_icon.setTypeface(Utility.getFontAwesomeWebFont(this));

        layout_topics = (LinearLayout) findViewById(R.id.layout_topics);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(Utility.getResourcesString(this, R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        initNavigationDrawer();
        setDataToHomeTabs();
        checkForRunTimePermissions();
    }

    private void checkForRunTimePermissions() {
        if (Utility.isMarshmallowOS()) {
            Permissions.getInstance().setActivity(this);
            CheckForPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void CheckForPermissions(final Context mContext, final String... mPermissions) {
        // A request for two permissions
        Permissions.getInstance().requestPermissions(new Permissions.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permissions.ResultSet resultSet) {

                if (!resultSet.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    final MaterialDialog denyDialog = new MaterialDialog(mContext, Permissions.TITLE,
                            Permissions.MESSAGE);
                    //Positive
                    denyDialog.setAcceptButton("RE-TRY");
                    denyDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckForPermissions(mContext, mPermissions);
                        }
                    });
                    denyDialog.show();
                }
            }

            @Override
            public void onRationaleRequested(Permissions.IOnRationaleProvided callback, String... permissions) {
                Permissions.getInstance().showRationaleInDialog(Permissions.TITLE,
                        Permissions.MESSAGE, "RE-TRY", callback);
            }
        }, mPermissions);
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

        if (Utility.getSharedPrefBooleanData(this, Constants.IS_LOGIN_COMPLETED)) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.logout).setVisible(true);
            nav_Menu.findItem(R.id.account_settings).setVisible(true);
        } else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.account_settings).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(false);
        }

        if (Utility.getSharedPrefStringData(this, Constants.IS_FB_LOGIN).equalsIgnoreCase(Constants.XAPPIE)) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.account_settings).setVisible(true);
        } else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.account_settings).setVisible(false);
        }
       /* navMenuView.addItemDecoration(new DividerItemDecoration(DashBoardActivity.this, DividerItemDecoration.VERTICAL));
        Drawable horizontalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, DashBoardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.discussions:
                        Utility.navigateDashBoardFragment(new DiscussionsFragment(), DiscussionsFragment.TAG, null, DashBoardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.entertainment:
                        Utility.navigateDashBoardFragment(new EntertainmentFragment(), EntertainmentFragment.TAG, null, DashBoardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.top_stories:
                        Utility.navigateDashBoardFragment(new TopStoriesFragment(), TopStoriesFragment.TAG, null, DashBoardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.gallery:
                        drawerLayout.closeDrawers();
                        Utility.navigateDashBoardFragment(new GalleryFragment(), GalleryFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.videos:
                        drawerLayout.closeDrawers();
                        Utility.navigateDashBoardFragment(new VideosFragment(), VideosFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.events:
                        drawerLayout.closeDrawers();
                        Utility.navigateDashBoardFragment(new EventsFragment(), EventsFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.classifieds:
                        drawerLayout.closeDrawers();
                        Utility.navigateDashBoardFragment(new ClassifiedsFragment(), ClassifiedsFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.jobs:
                        drawerLayout.closeDrawers();
                        Utility.navigateDashBoardFragment(new JobsFragment(), JobsFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.preferences:
                        drawerLayout.closeDrawers();
                       /* showDialogForPreferences(); */
                        Utility.navigateDashBoardFragment(new PreferenceFragment(), PreferenceFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.account_settings:
                        drawerLayout.closeDrawers();
                        Utility.navigateDashBoardFragment(new AccountSettingFragment(), AccountSettingFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.help:
                        drawerLayout.closeDrawers();
                        Utility.navigateDashBoardFragment(new HelpFragment(), HelpFragment.TAG, null, DashBoardActivity.this);
                        break;
                    case R.id.logout:
                        logout();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        img_user_image = (ImageView) header.findViewById(R.id.img_user_image);
        tv_first_last = (TextView) header.findViewById(R.id.tv_first_last);
        TextView tv_edit = (TextView) header.findViewById(R.id.tv_edit);
        TextView tv_sign_in_to_xappie = (TextView) header.findViewById(R.id.tv_sign_in_to_xappie);
        tv_sign_in_to_xappie.setTypeface(Utility.getOpenSansBold(this));
        TextView txt_view_profile = (TextView) header.findViewById(R.id.txt_view_profile);
        txt_view_profile.setTypeface(Utility.getOpenSansRegular(this));
        TextView tv_joined = (TextView) header.findViewById(R.id.tv_joined);
        tv_joined.setTypeface(Utility.getOpenSansRegular(this));
        TextView txt_hello = (TextView) header.findViewById(R.id.txt_hello);
        txt_hello.setTypeface(Utility.getOpenSansBold(this));

        tv_edit.setTypeface(Utility.getFontAwesomeWebFont(this));
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                showPickAlert();
            }
        });

        if (Utility.getSharedPrefBooleanData(this, Constants.IS_LOGIN_COMPLETED)) {
            tv_sign_in_to_xappie.setVisibility(View.GONE);
            tv_edit.setVisibility(View.VISIBLE);
            tv_joined.setVisibility(View.VISIBLE);
            txt_view_profile.setVisibility(View.VISIBLE);
            tv_joined.setTextColor(Utility.getColor(DashBoardActivity.this, R.color.dark_gray));
            tv_joined.setText(Utility.getResourcesString(DashBoardActivity.this, R.string.joined) + " " +
                    Utility.getSharedPrefStringData(DashBoardActivity.this, Constants.JOIN_DATE).toUpperCase());
            if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(DashBoardActivity.this, Constants.SIGN_UP_DISPLAY_NAME))) {
                txt_hello.setText(Utility.getSharedPrefStringData(DashBoardActivity.this, Constants.SIGN_UP_DISPLAY_NAME));
            } else
                txt_hello.setText(Utility.getSharedPrefStringData(DashBoardActivity.this, Constants.SIGN_UP_FIRST_NAME)
                        + " " + Utility.getSharedPrefStringData(DashBoardActivity.this, Constants.SIGN_UP_LAST_NAME));
            Utility.setThemeColorToBackground(img_user_image, this);

            if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_PHOTO))) {
                Picasso.with(this).load(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_PHOTO))
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .placeholder(Utility.getDrawable(this, R.drawable.avatar_image))
                        .transform(new CircleTransform()).into(img_user_image);
                tv_first_last.setVisibility(View.GONE);
            } else {
                if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_FIRST_NAME))) {
                    tv_first_last.setText(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_FIRST_NAME).substring(0, 1));
                } else {
                    tv_first_last.setText("XP");
                }
                if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_LAST_NAME))) {
                    tv_first_last.setText(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_FIRST_NAME).substring(0, 1)
                            + Utility.getSharedPrefStringData(this, Constants.SIGN_UP_LAST_NAME).substring(0, 1));
                }
                tv_first_last.setVisibility(View.VISIBLE);
                Picasso.with(this).load("asdasda")
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .transform(new CircleTransform()).into(img_user_image);
            }
        } else {
            img_user_image.setImageDrawable(Utility.getDrawable(this, R.drawable.avatar_image));
            tv_sign_in_to_xappie.setVisibility(View.VISIBLE);
            tv_joined.setVisibility(View.GONE);
            tv_edit.setVisibility(View.GONE);
            txt_view_profile.setVisibility(View.GONE);
            txt_hello.setText(Utility.getResourcesString(this, R.string.hello));
        }
        tv_sign_in_to_xappie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        txt_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                Utility.navigateDashBoardFragment(new MyProfileFragment(), MyProfileFragment.TAG, null, DashBoardActivity.this);
            }
        });

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
     * This method is used to show the edit dialog
     */
    private void showEditDialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Post Image"), Constants.FROM_POST_FORUM_GALLERY_ID);
    }


    private void showPickAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashBoardActivity.this);
        alertDialogBuilder.setMessage("Take Photo");
        alertDialogBuilder.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        showEditDialog();
                    }
                });
        alertDialogBuilder.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        captureFile();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void captureFile() {
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent, Constants.FROM_POST_FORUM_HOME_CAMERA_ID);*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        this.imageUri = FileProvider.getUriForFile(this,
                this.getApplicationContext().getPackageName() + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri);
        this.startActivityForResult(intent, Constants.FROM_POST_FORUM_HOME_CAMERA_ID);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.FROM_POST_FORUM_GALLERY_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                mSelectedFile = new File(path);
                updateImageToServer();
            }
        } else if (requestCode == Constants.FROM_POST_FORUM_ADD_EVENT_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                mSelectedFile = new File(path);
                AddNewEventFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_POST_FORUM_ADD_CLASSIFIEDS_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                mSelectedFile = new File(path);
                ClassifiedsAddFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_POSTING_LOGO_FILE_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                PostJobFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_FILE_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                ApplyJobsFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_POST_FORUM_ADD_EVENT_CAMERA_ID) {
            if (resultCode == Activity.RESULT_OK) {
                //Bitmap bmp = (Bitmap) data.getExtras().get(Utility.getResourcesString(this, R.string.data));
                try {
                    Bitmap bmp = decodeBitmapUri(this, imageUri);
                    String selectedImgPath = Utility.saveBitmap(bmp);
                    AddNewEventFragment.getInstance().updateFile(selectedImgPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == Constants.FROM_POST_FORUM_ADD_CLASSIFIEDS_CAMERA_ID) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Bitmap bmp = decodeBitmapUri(this, imageUri);
                    String selectedImgPath = Utility.saveBitmap(bmp);
                    ClassifiedsAddFragment.getInstance().updateFile(selectedImgPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*Bitmap bmp = (Bitmap) data.getExtras().get(Utility.getResourcesString(this, R.string.data));
                String selectedImgPath = Utility.saveBitmap(bmp);
                ClassifiedsAddFragment.getInstance().updateFile(selectedImgPath);*/
            }
        } else if (requestCode == Constants.FROM_POST_FORUM_HOME_CAMERA_ID) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Bitmap bmp = decodeBitmapUri(this, imageUri);
                    String selectedImgPath = Utility.saveBitmap(bmp);
                    mSelectedFile = new File(selectedImgPath);
                    updateImageToServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 300;
        int targetH = 680;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


        int scaleFactor = Math.min(photoW / photoW, photoH / photoH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

    /**
     * This method is used to update the profile pic
     */
    private void updateImageToServer() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (mSelectedFile != null) {
            File file = new File(mSelectedFile.getPath());
            if (file.exists()) {
                final MediaType MEDIA_TYPE = MediaType.parse(Utility.getMimeType(mSelectedFile.getPath()));
                builder.addFormDataPart("photo", file.getName(), RequestBody.create(MEDIA_TYPE, file));
            } else {
                Log.d(TAG, "file not exist ");
            }
        }

        builder.addFormDataPart("api_key", Constants.API_KEY_VALUE);
        builder.addFormDataPart("photo_name", mSelectedFile.getName());
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(APIConstants.UPDATE_PROFILE_PHOTO)
                .addHeader("Cookie", "ci_session=" + Utility.getSharedPrefStringData(this, Constants.LOGIN_SESSION_ID) + ";")
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient.Builder().build();

        Call call = client.newCall(request);


        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                customProgressDialog.dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                customProgressDialog.dismissProgress();
                String jsonData = response.body().string();
                Utility.showLog("jsondata", "" + jsonData);
                Utility.showToastMessage(DashBoardActivity.this, "Your Profile is updated successfully");
                DashBoardActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.showToastMessage(DashBoardActivity.this, "Your Profile is updated successfully");
                    }
                });
            }
        });
    }

    /**
     * This method is used to show the Preferences dialog
     */
 /*   private void showDialogForPreferences() {
        final Dialog mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.home_custmization_dialog);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);

        TextView tv_location_icon = (TextView) mDialog.findViewById(R.id.tv_location_icon);
        tv_location_icon.setTypeface(Utility.getFontAwesomeWebFont(this));
        TextView tv_location = (TextView) mDialog.findViewById(R.id.tv_location);
        tv_location.setTypeface(Utility.getOpenSansRegular(this));

        TextView tv_notification_menu_icon = (TextView) mDialog.findViewById(R.id.tv_notification_menu_icon);
        tv_notification_menu_icon.setTypeface(Utility.getFontAwesomeWebFont(this));
        TextView tv_notification = (TextView) mDialog.findViewById(R.id.tv_notification);
        tv_notification.setTypeface(Utility.getOpenSansRegular(this));

        TextView tv_language_icon = (TextView) mDialog.findViewById(R.id.tv_language_icon);
        tv_language_icon.setTypeface(Utility.getFontAwesomeWebFont(this));
        TextView tv_language = (TextView) mDialog.findViewById(R.id.tv_language);
        tv_language.setTypeface(Utility.getOpenSansRegular(this));

        TextView tv_customization_icon = (TextView) mDialog.findViewById(R.id.tv_customization_icon);
        tv_customization_icon.setTypeface(Utility.getFontAwesomeWebFont(this));
        TextView tv_customization = (TextView) mDialog.findViewById(R.id.tv_customization);
        tv_customization.setTypeface(Utility.getOpenSansRegular(this));

        tv_customization_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new HomePageCustomizationFragment(), HomePageCustomizationFragment.TAG, null, DashBoardActivity.this);
            }
        });
        tv_customization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new HomePageCustomizationFragment(), HomePageCustomizationFragment.TAG, null, DashBoardActivity.this);
            }
        });

        tv_location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, DashBoardActivity.this);
            }
        });

        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, DashBoardActivity.this);
            }
        });

        tv_notification_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, DashBoardActivity.this);
            }
        });

        tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, DashBoardActivity.this);
            }
        });

        tv_language_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, DashBoardActivity.this);
            }
        });

        tv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, DashBoardActivity.this);
            }
        });

        mDialog.show();
    }  */

    /**
     * This method is used to logout from the application
     */
    private void logout() {
        Utility.setSharedPrefBooleanData(this, Constants.IS_LOGIN_COMPLETED, false);
        Utility.setSharedPrefStringData(this, Constants.IS_FB_LOGIN, "");
        Utility.setSharedPrefStringData(this, Constants.SELECTED_COUNTRY_NAME, "");
        finish();
        Intent intent = new Intent(DashBoardActivity.this, DashBoardActivity.class);
        startActivity(intent);

    }

    private void navigateToDashBoard() {

    }

    /**
     * This method is used to navigation of location selection
     */
    @OnClick(R.id.tv_location_icon)
    void navigateLocation() {

        Utility.navigateDashBoardFragment(new CountriesFragment(), CountriesFragment.TAG, null, DashBoardActivity.this);
    }

    /**
     * This method is used to navigation of notifications
     */
    @OnClick(R.id.tv_notifications_icon)
    void navigateNotifications() {
        Utility.navigateDashBoardFragment(new NotificationsFragment(), NotificationsFragment.TAG, null, DashBoardActivity.this);
    }

    /**
     * This method is used to navigation of language
     */
    @OnClick(R.id.tv_language_icon)
    void navigateLanguage() {
        Utility.navigateDashBoardFragment(new LanguageFragment(), LanguageFragment.TAG, null, DashBoardActivity.this);

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
                    switch (selected_position) {
                        case 0:

                            break;
                        case 1:
                            Utility.navigateDashBoardFragment(new JobsFragment(), JobsFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 2:
                            Utility.navigateDashBoardFragment(new ClassifiedsFragment(), ClassifiedsFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 3:
                            Utility.navigateDashBoardFragment(new EventsFragment(), EventsFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 4:
                            Utility.navigateDashBoardFragment(new VideosFragment(), VideosFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 5:
                            Utility.navigateDashBoardFragment(new GalleryFragment(), GalleryFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 6:
                            Utility.navigateDashBoardFragment(new TopStoriesFragment(), TopStoriesFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 7:
                            Utility.navigateDashBoardFragment(new EntertainmentFragment(), EntertainmentFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 8:
                            Utility.navigateDashBoardFragment(new DiscussionsFragment(), DiscussionsFragment.TAG, null, DashBoardActivity.this);
                            break;
                        case 9:
                            break;

                    }
                }
            });
            layout_topics.addView(ll);
        }
    }

    private ArrayList<String> getTabNames() {
        ArrayList<String> mTabNames = new ArrayList<>();
        mTabNames.add(Utility.getResourcesString(this, R.string.home).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.jobs).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.classifieds).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.events).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.videos).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.gallery).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.top_stories).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.entertainment).toUpperCase());
        mTabNames.add(Utility.getResourcesString(this, R.string.discussions).toUpperCase());

        return mTabNames;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof ImageUploadModel) {
                mImageUploadModel = (ImageUploadModel) model;
                if (mImageUploadModel.isStatus()) {
                    Utility.showToastMessage(DashBoardActivity.this, mImageUploadModel.getMessage());
                    Utility.setSharedPrefStringData(DashBoardActivity.this, Constants.SIGN_UP_PHOTO, mImageUploadModel.getPhoto());
                    if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_PHOTO)))
                        Picasso.with(this).load(Utility.getSharedPrefStringData(this, Constants.SIGN_UP_PHOTO))
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .placeholder(Utility.getDrawable(this, R.drawable.avatar_image))
                                .transform(new CircleTransform()).into(img_user_image);
                } else {
                    Utility.showToastMessage(DashBoardActivity.this, mImageUploadModel.getMessage());
                }
            }
        }
    }
}
