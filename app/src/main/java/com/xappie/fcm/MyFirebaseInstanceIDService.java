package com.xappie.fcm;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;


/**
 * Created by Santosh on 29-11-2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Utility.setSharedPrefStringData(this, Constants.KEY_FCM_TOKEN, refreshedToken);
        Utility.showLog(TAG, "Refreshed token: " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Intent intent = new Intent(Constants.KEY_TOKEN_RECEIVER);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra(Constants.KEY_FCM_TOKEN, token);
        broadcastManager.sendBroadcast(intent);
    }
}