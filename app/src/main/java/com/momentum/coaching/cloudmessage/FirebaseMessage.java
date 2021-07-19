package com.momentum.coaching.cloudmessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.momentum.coaching.activity.MainActivity;
import com.momentum.coaching.R;

public class FirebaseMessage extends FirebaseMessagingService {
    public static int NOTIFICATION_ID = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        generateNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());

    }

    private void generateNotification(String body, String title) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),"Momentum")
                .setSmallIcon(R.drawable.momentumlogo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (NOTIFICATION_ID > 1073741824)
        {
            NOTIFICATION_ID = 0;
        }
        notificationManager.notify(NOTIFICATION_ID++,notificationBuilder.build());
    }
}
