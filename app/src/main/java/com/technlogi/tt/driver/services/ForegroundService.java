package com.technlogi.tt.driver.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.technlogi.tt.R;
import com.technlogi.tt.user.MainActivity;

import static com.technlogi.tt.Constant.NOTIFICATION_CHANNEL_ID;
import static com.technlogi.tt.user.MainActivity.CHANNEL_ID;

public class ForegroundService extends Service {

    private static RemoteViews contentView;
    private static Notification notification;
    private static NotificationManager notificationManager;
    private static final int NotificationID = 1005;
    private static NotificationCompat.Builder mBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

       /* Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_baseline_close_24)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);*/
        //do heavy work on a background thread
        //stopSelf();

        com.technlogi.tt.Notification notification = new com.technlogi.tt.Notification();
        notification.runNotification(getApplicationContext());

//        RunNotification();
        return START_NOT_STICKY;
    }

//    private void RunNotification() {
//
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
//
//        contentView = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
//        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
//        Intent switchIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 1020, switchIntent, 0);
//       // contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent);
//
////        contentView = new RemoteViews(getPackageName(),R.layout.notification_layout);
////        contentView.setImageViewResource(R.id.notification_icon,R.mipmap.ic_launcher);
////        int notificationId = 100;
////        Intent intent1 = new Intent("button_clicked");
////        intent1.putExtra("id",notificationId);
////        PendingIntent buttonPendingIntent = PendingIntent.getBroadcast(this,123,intent1,0);
////        contentView.setOnClickPendingIntent(R.id.acceptBtn,buttonPendingIntent);
//
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setAutoCancel(false);
//        mBuilder.setOngoing(true);
//        mBuilder.setPriority(Notification.PRIORITY_HIGH);
//        mBuilder.setOnlyAlertOnce(true);
//        mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
//        mBuilder.setContent(contentView);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "channel_id";
//            NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            notificationManager.createNotificationChannel(channel);
//            mBuilder.setChannelId(channelId);
//        }
//
//        notification = mBuilder.build();
//        startForeground(1, notification);
//        //notificationManager.notify(NotificationID, notification);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}