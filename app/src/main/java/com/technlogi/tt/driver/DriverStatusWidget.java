package com.technlogi.tt.driver;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.technlogi.tt.R;

/**
 * Implementation of App Widget functionality.
 */
public class DriverStatusWidget extends AppWidgetProvider {

    private static final String STATUS_CHANGE = "statusChangeButton";
    private boolean toggle = true;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.driver_status_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        views.setOnClickPendingIntent(R.id.driverStatusWidgetButton,getPendingSelfIntent(context, STATUS_CHANGE));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        SharedPreferences sharedPreferences;
        if (STATUS_CHANGE.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.driver_status_widget);
            ComponentName watchWidget = new ComponentName(context,DriverStatusWidget.class);

            sharedPreferences = context.getSharedPreferences("driverStatus",Context.MODE_PRIVATE);
            toggle = sharedPreferences.getBoolean("status",true);

            if (toggle) {
                views.setTextViewText(R.id.driverStatusWidgetButton,"OFFLINE");
                views.setInt(R.id.driverStatusWidgetButton,"setBackgroundResource",R.color.colorSecondary);
            } else {
                views.setTextViewText(R.id.driverStatusWidgetButton,"ONLINE");
                views.setInt(R.id.driverStatusWidgetButton,"setBackgroundResource",R.color.colorPrimary);
            }


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("status",!toggle);
            editor.commit();

            appWidgetManager.updateAppWidget(watchWidget, views);
        }
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent1 = new Intent(context, DriverStatusWidget.class);
        intent1.setAction(action);
        return PendingIntent.getBroadcast(context,0,intent1,0);
    }
}