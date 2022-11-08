package com.technlogi.tt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.technlogi.tt.common.broadcast.NotificationBroadcast;

import androidx.core.app.NotificationCompat;

import static com.technlogi.tt.Constant.NOTIFICATION_CHANNEL_ID;

public class Notification {

    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder mBuilder;
    private static android.app.Notification notification;
    private static final int NotificationID = 1005;

    private static RemoteViews contentView;

    public void runNotification(Context context) {

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

//        contentView = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
//        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
//        Intent switchIntent = new Intent(this,MainActivity.class);
//        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 1020, switchIntent, 0);

        contentView = new RemoteViews(context.getPackageName(),R.layout.notification_layout);
        contentView.setImageViewResource(R.id.notification_icon,R.mipmap.ic_launcher);
        Intent intent1 = new Intent(context, NotificationBroadcast.class);
        intent1.putExtra("id","100");
        PendingIntent buttonPendingIntent = PendingIntent.getBroadcast(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.notification_acceptButton,buttonPendingIntent);



        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setAutoCancel(false);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(android.app.Notification.PRIORITY_HIGH);
        mBuilder.setOnlyAlertOnce(true);
        mBuilder.build().flags = android.app.Notification.FLAG_NO_CLEAR | android.app.Notification.PRIORITY_HIGH;
        mBuilder.setContent(contentView);
//        mBuilder.addAction(R.mipmap.ic_launcher,"Accept",buttonPendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        notification = mBuilder.build();
        notificationManager.notify(NotificationID, notification);
    }

}
