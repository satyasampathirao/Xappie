package com.xappie.aynctaskold;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.xappie.R;
import com.xappie.models.Model;
import com.xappie.parser.Parser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Utility;

import java.util.HashMap;

/**
 * Created by ShankarRao on 3/28/2016.
 */
public class ServerIntractorAsync extends BaseAsynkTask {
    private String mResponse = null;
    private Model model;

    public ServerIntractorAsync(Context context, String dialogMessage,
                                boolean showDialog, String url, HashMap<String, String> mParamMap,
                                APIConstants.REQUEST_TYPE requestType, IAsyncCaller caller, Parser parser) {
        super(context, dialogMessage, showDialog, url, mParamMap, requestType,
                caller, parser);

    }

    @Override
    public Integer doInBackground(Void... params) {
        if (!Utility.isNetworkAvailable(mContext)) {
            return 0;
        }

        switch (mRequestType) {
            case GET:
                Utility.showLog("Request URL ", mUrl);
                Utility.showLog("Request mParams ", "" + mParams);
                mResponse = Utility.httpGetRequestToServer(Utility.getURL(mUrl, mParams));
                if (mResponse != null) {
                    Utility.showLog("mResponse  ", mResponse);
                }
                return parseResposnse(mResponse);
            case POST:
                Utility.showLog("Request URL ", mUrl);
                Utility.showLog("Request mParams != null getParams  ", ""
                        + Utility.getParams(mParams));
                if (mUrl.contains(APIConstants.LOGIN) || mUrl.contains(APIConstants.SIGN_UP)) {
                    mResponse = Utility.httpLoginCookiesPostRequest(mUrl,
                            Utility.getParams(mParams), mContext);
                } else if (mUrl.contains(APIConstants.FORGOT_PASSWORD)
                        || mUrl.contains(APIConstants.RESET_PASSWORD)
                        || mUrl.contains(APIConstants.UPDATE_PASSWORD)
                        || mUrl.contains(APIConstants.UPDATE_PROFILE)
                        || mUrl.contains(APIConstants.ADD_EVENT)
                        ) {
                    mResponse = Utility.httpPostRequestToServerWithHeaderCookies(mUrl,
                            Utility.getParams(mParams), mContext);
                }
                if (mResponse != null) {
                    Utility.showLog("mResponse  ", mResponse);
                }
                return parseResposnse(mResponse);
            default:
                return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        try {

            if (!isCancelled() && result == 1) {
                if (model != null) {
                    caller.onComplete(model);
                } else {
                    Utility.showToastMessage(mContext, "Server response error!");
                }
            } else if (result == 0) {
                Utility.showSettingDialog(
                        mContext,
                        mContext.getResources().getString(
                                R.string.no_internet_msg),
                        mContext.getResources().getString(
                                R.string.no_internet_title),
                        Utility.NO_INTERNET_CONNECTION).show();
                model = null;
                caller.onComplete(model);
            } else {
                model = null;
                caller.onComplete(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int parseResposnse(String response) {
        if (response != null) {

            return getResponse(response);

        }

        return -1;
    }

    private int getResponse(String response) {
        try {
            model = parser.parse(response, mContext);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
     *
	 * @Sparity
	 *
	 * These methods are to make asynctasks concurrent, and run on paralel on
	 * android 3+
	 */

    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
        execute(task, (P[]) null);
    }

    @SuppressLint("NewApi")
    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task,
                                                                 P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}

