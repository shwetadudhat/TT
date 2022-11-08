package com.technlogi.tt;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.android.libraries.places.api.Places;
import com.tt.customsocket.WebSocketClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import static com.technlogi.tt.Constant.NOTIFICATION_CHANNEL_ID;

public class ConfigApp extends Application {

    WebSocketClient webSocketClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Places.initialize(this,getResources().getString(R.string.google_map_api_key));
        if(Build.VERSION_CODES.O >= Build.VERSION.PREVIEW_SDK_INT) createNotificationChannel();
       // instantiateWebSocket();
    }

    private void instantiateWebSocket() {

        webSocketClient =  new WebSocketClient(getApplicationContext());
        webSocketClient.connect("url");

    }

    public WebSocketClient getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, getResources().getResourceName(R.string.app_name), importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
