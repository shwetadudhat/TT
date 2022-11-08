package com.technlogi.tt.common.broadcast;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.technlogi.tt.common.utils.NetworkUtil;
import com.technlogi.tt.user.MainActivity;

public class InternetConnectivityChange extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);

        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {

            } else {

            }
        }

    }

}
