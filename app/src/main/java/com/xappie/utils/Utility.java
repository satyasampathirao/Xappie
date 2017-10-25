package com.xappie.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xappie.R;
import com.xappie.fragments.AllEventsListFragment;
import com.xappie.fragments.FindJobsListFragment;
import com.xappie.models.EntertainmentListModel;
import com.xappie.models.EntertainmentModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Shankar on 6/29/2017.
 **/

public class Utility {


    public static final int NO_INTERNET_CONNECTION = 1;
    private static final int NO_GPS_ACCESS = 2;

    private static final int CONNECTION_TIMEOUT = 30000;

    /**
     * TO CHECK IS IT BELOW MARSHMALLOW OR NOT
     */
    public static boolean isMarshmallowOS() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    /**
     * ASSIGN THE COLOR
     **/
    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23)
            return ContextCompat.getColor(context, id);
        else
            return context.getResources().getColor(id);
    }


    /**
     * FONT AWESOME WEB FONT TYPEFACE
     * This method is used to set the icons in font awesome
     **/
    public static Typeface getFontAwesomeWebFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
    }


    /**
     * OPEN SANS REGULAR TYPEFACE
     * This method is used to set the icons in Material Icons Regular
     **/
    public static Typeface getOpenSansRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
    }

    /**
     * OPEN SANS BOLD TYPEFACE
     * This method is used to set the icons in Material Icons Regular
     **/
    public static Typeface getOpenSansBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf");
    }

    /**
     * MATERIAL ICONS REGULAR TYPEFACE
     * This method is used to set the icons in Material Icons Regular
     **/
    public static Typeface getMaterialIconsRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/matireal_icons_regular.ttf");
    }

    /**
     * FOUNDATION ICONS TYPEFACE
     * This method is used to set the icons in Foundation Icons
     **/
    public static Typeface getFoundationIcons(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/foundation-icons.ttf");
    }

    /**
     * MOON ICONS TYPEFACE
     * This method is used to set the icons in Foundation Icons
     **/
    public static Typeface getMoonIcons(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/moon.ttf");
    }


    /**
     * HIDE THE KEYBOARD FOR FRAGMENT
     **/
    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * HIDE THE KEYBOARD FOR ACTIVITY
     **/
    public static void hideSoftKeyPad(Context context) {
        Activity activity = (Activity) context;
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GET REAL PATH FROM URI
     *
     * @param contentUri Uri of the real path
     * @return String
     */
    public static String _getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * GET Resources String
     *
     * @param context Context of the class
     * @param id      Id of the resource
     * @return String
     */
    public static String getResourcesString(Context context, int id) {
        String value = null;
        if (context != null && id != -1) {
            value = context.getResources().getString(id);
        }
        return value;
    }

    /**
     * Check the value is null or empty
     *
     * @param value Value of that string
     * @return Boolean returns the value true or false
     */
    public static boolean isValueNullOrEmpty(String value) {
        boolean isValue = false;
        if (value == null || value.equals("") || value.equals("0.0")
                || value.equals("null") || value.trim().length() == 0) {
            isValue = true;
        }
        return isValue;
    }

    /**
     * Shows toast message
     *
     * @param context Context of the class
     * @param message What message you have to show
     */
    public static void showToastMessage(Context context, String message) {
        try {
            if (!isValueNullOrEmpty(message) && context != null) {
                final Toast toast = Toast.makeText(
                        context.getApplicationContext(), message,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To Print logcat this method is used.
     *
     * @param logMsg Purpose of the log
     * @param logVal What you want to print
     */
    public static void showLog(String logMsg, String logVal) {
        try {
            if (Constants.logMessageOnOrOff) {
                if (!isValueNullOrEmpty(logMsg) && !isValueNullOrEmpty(logVal)) {
                    Log.e(logMsg, logVal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ASSIGN THE DRAWBLE
     **/
    public static Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * Shared preference method to set and get boolean variable
     */
    public static void setSharedPrefBooleanData(Context context, String key, boolean value) {
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
        appInstallInfoEditor.putBoolean(key, value);
        appInstallInfoEditor.apply();
    }

    public static boolean getSharedPrefBooleanData(Context context, String key) {
        SharedPreferences userAccountPreference = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE);
        return userAccountPreference.getBoolean(key, false);
    }

    /**
     * Sharedpreference method to set and get string variable
     */
    public static void setSharedPrefStringData(Context context, String key, String value) {
        try {
            if (context != null) {
                SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
                appInstallInfoEditor.putString(key, value);
                appInstallInfoEditor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GET SHARED PREFERENCES STRING DATA
     */
    public static String getSharedPrefStringData(Context context, String key) {

        try {
            SharedPreferences userAcountPreference = context
                    .getSharedPreferences(Constants.APP_PREF,
                            Context.MODE_PRIVATE);
            return userAcountPreference.getString(key, "");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";

    }

    /**
     * This method is used to navigate or replace fragment
     */
    public static void navigateDashBoardFragment(Fragment fragment,
                                                 String tag, Bundle bundle, FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.content_frame, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * This method is used to navigate or replace fragment
     */
    public static void navigateAllEventsFragment(Fragment fragment,
                                                 String tag, Bundle bundle, FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.content_frame_events, fragment, tag);
        if (!tag.equalsIgnoreCase(AllEventsListFragment.TAG))
            fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }


    public static void navigateAllJobsFragment(Fragment fragment,
                                               String tag, Bundle bundle, FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.content_frame_jobs, fragment, tag);
        if (!tag.equalsIgnoreCase(FindJobsListFragment.TAG))
            fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * UNIVERSAL IMAGE LOADER
     * <p>
     * to load image uri to image
     */
    public static void universalImageLoaderPicLoading(ImageView ivImageView, String ImageUrl, final ProgressBar progressBar, int placeholder) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(placeholder)
                .showImageForEmptyUri(placeholder)
                .showImageOnFail(placeholder)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        if (progressBar != null) {
            ImageLoader.getInstance().displayImage(ImageUrl, ivImageView, options, new SimpleImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }


            });
        } else {
            ImageLoader.getInstance().displayImage(ImageUrl, ivImageView, options);
        }

    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTING) {
                return true;
            } else return connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTING;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static android.app.AlertDialog showSettingDialog(final Context context,
                                                            String msg, String title, final int id) {
        return new android.app.AlertDialog.Builder(context)
                // .setMobile_icon_code(android.R.attr.alertDialogIcon)
                .setMessage(msg)
                .setTitle(title)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        })
                .setNegativeButton(R.string.alert_dialog_setting,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                switch (id) {
                                    case Utility.NO_INTERNET_CONNECTION:
                                        context.startActivity(new Intent(
                                                android.provider.Settings.ACTION_SETTINGS));
                                        break;
                                    case Utility.NO_GPS_ACCESS:
                                        context.startActivity(new Intent(
                                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).create();
    }

     /*
     *
	 *
	 * These methods are to make async tasks concurrent, and run on parallel on
	 * android 3+
	 */

    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
        execute(task, (P[]) null);
    }

    @SafeVarargs
    @SuppressLint("NewApi")
    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task,
                                                                 P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    public static String httpGetRequestToServer(String url) {
        InputStream inputStream = null;
        String result = "";
        String hashKeyStr = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpclient.getParams(),
                    CONNECTION_TIMEOUT);
            URI uri = new URI(url.replace(" ", "%20"));
            HttpGet request = new HttpGet(uri);
            HttpResponse httpResponse = httpclient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                result = null;
            }

            if (statusCode == 200) {
                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();
                // convert inputstream to string
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                } else {
                    result = "Did not work!";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static String getURL(String url,
                                HashMap<String, String> paramMap) {
        StringBuilder sb = new StringBuilder().append(url + "?");
        boolean first = true;
        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                if (first) {
                    sb.append(entry.getKey()).append("=")
                            .append(entry.getValue());
                    first = false;
                } else {
                    sb.append("&").append(entry.getKey()).append("=")
                            .append(entry.getValue());
                }
            }
        }
        return sb.toString();
    }

    public static String GETHeader(String url, Context mContext) {

        InputStream inputStream = null;
        String result = "";
        try {
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams,
                    CONNECTION_TIMEOUT);

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(httpGet);
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String capitalizeFirstLetter(String s) {
        if (s.length() == 0) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * This method is used to get data from the more topics with some conditions
     */
    public static ArrayList<EntertainmentModel> getMoreTopicsList(int position, ArrayList<EntertainmentModel> entertainmentModelsList) {
        ArrayList<EntertainmentModel> entertainmentModels = new ArrayList<>();
        if (position == 0) {
            for (int i = 1; i < entertainmentModelsList.size(); i++) {
                entertainmentModels.add(entertainmentModelsList.get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 1) {
            for (int i = 0; i < entertainmentModelsList.size(); i++) {
                if (i != 1)
                    entertainmentModels.add(entertainmentModelsList.get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 2) {
            for (int i = 0; i < entertainmentModelsList.size(); i++) {
                if (i != 2)
                    entertainmentModels.add(entertainmentModelsList.get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else if (position == 3) {
            for (int i = 0; i < entertainmentModelsList.size(); i++) {
                if (i != 3)
                    entertainmentModels.add(entertainmentModelsList.get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        } else {
            for (int i = 0; i < entertainmentModelsList.size(); i++) {
                entertainmentModels.add(entertainmentModelsList.get(i));
                if (entertainmentModels.size() == 4)
                    break;
            }
        }
        return entertainmentModels;
    }

    public static void setSnackBar(AppCompatActivity parent, View mView, String message) {
        SnackBar snackBar = new SnackBar();
        snackBar.view(mView)
                .customActionFont(getFontAwesomeWebFont(parent))
                .customTitleFont(getFontAwesomeWebFont(parent))
                .text(message, "OK", 2)
                .textColors(Color.WHITE, getColor(parent, R.color.black))
                .backgroundColor(getColor(parent, R.color.error_alert))
                .duration(SnackBar.SnackBarDuration.LONG)
                .show();
    }


    public static List<NameValuePair> getParams(HashMap<String, String> paramMap) {
        if (paramMap == null) {
            return null;
        }
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            paramsList.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }
        return paramsList;
    }


    public static String httpLoginCookiesPostRequest(String URL, Object paramsList, Context mContext) {
        String userAgent = "(Android; Mobile) Chrome";
        int TIME_OUT = 30000;
        String data = null;
        HttpPost httppost = new HttpPost(URL);
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParams, TIME_OUT);

        DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
        httppost.setHeader("User-Agent", userAgent);

        httppost.setParams(httpParams);

        InputStream is = null;
        try {
            if (paramsList != null)
                httppost.setEntity(new UrlEncodedFormEntity(
                        (List<? extends NameValuePair>) paramsList));
            // httppost.setEntity(gettingResponse(paramsList));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();

            List<Cookie> cookies = httpclient.getCookieStore().getCookies();
            if (cookies.isEmpty()) {

            } else {
                for (int i = 0; i < cookies.size(); i++) {
                    Utility.setSharedPrefStringData(mContext, Constants.LOGIN_SESSION_ID, cookies.get(i).getValue());
                }
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                data = null;
            }

            if (statusCode == 200) {
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 0)
                        sb.append(line + "\n");
                }
                data = sb.toString();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String httpPostRequestToServerWithHeaderCookies(String URL, Object paramsList, Context mContext) {
        String userAgent = "(Android; Mobile) Chrome";
        int TIME_OUT = 30000;
        String data = null;
        HttpPost httppost = new HttpPost(URL);
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParams, TIME_OUT);

        DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
        httppost.setHeader("User-Agent", userAgent);
        Utility.showLog("ci_session", "ci_session: " + Utility.getSharedPrefStringData(mContext, Constants.LOGIN_SESSION_ID));
        httppost.setHeader("Cookie", "ci_session=" + Utility.getSharedPrefStringData(mContext, Constants.LOGIN_SESSION_ID) + ";");
        httppost.setParams(httpParams);

        InputStream is = null;
        try {
            if (paramsList != null)
                httppost.setEntity(new UrlEncodedFormEntity(
                        (List<? extends NameValuePair>) paramsList));
            // httppost.setEntity(gettingResponse(paramsList));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            Utility.showLog("Response code", "" + statusCode);
            if (statusCode == 204) {
                data = null;
            }

            if (statusCode == 200) {
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 0)
                        sb.append(line + "\n");
                }
                data = sb.toString();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * This method is used to get the current date
     */
    public static String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * This method is used to get the current date
     */
    public static String getJoiningDate(String sDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(sDate);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }
}
