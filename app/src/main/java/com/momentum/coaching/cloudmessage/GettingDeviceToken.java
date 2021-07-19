package com.momentum.coaching.cloudmessage;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class GettingDeviceToken extends FirebaseMessagingService {

    /*@Override
    public void onTokenRefresh() {
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("DEVICE TOKEN: " ,deviceToken);
    }*/
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
