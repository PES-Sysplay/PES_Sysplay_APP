package com.example.workoutapp.ui.usermanage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.R;
import com.example.workoutapp.UserActivityController;

import java.util.ArrayList;


public class NotificationsManager{
    private static String CHANNEL_ID = "notificación";
    private static String CHANNEL_NAME = "notificación";
    private static Integer NOTIFICATION_ID;
    private Integer IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
    private static Context ctx;

    public NotificationsManager(){
        ctx = ActivityController.getContext();
    }

    public static NotificationCompat.Builder getBuilder(String title, String text) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_definitiu1)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notification(Integer NOTIFICATION_ID, String title, String text) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE);

        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(channel);
        this.NOTIFICATION_ID = NOTIFICATION_ID;
        mNotificationManager.notify(NOTIFICATION_ID, getBuilder(title, text).build());
    }

    public void checkActivities(){
        UserActivityController uaController = new UserActivityController(ctx);

        uaController.getJoinedActivities(new UserActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {}

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {}

            @Override
            public void onResponseFav() {}

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {
                for (int i = 0; i < ret.size(); i++) {
                   Long date = ret.get(i).getTimestamp();
                   Boolean notified = ret.get(i).isNotified();
                   if(!notified && date*1000L<=System.currentTimeMillis()+172800000 && !ret.get(i).isOld()) {
                       Integer notification_id = ret.get(i).getId();
                       ret.get(i).setNotified();
                       String organization = ret.get(i).getOrganizerName();
                       String name = ret.get(i).getName();
                       String text = "Quedan menos de 2 días para la actividad";
                       notification(notification_id, name +" - "+organization, text);

                   }
                }
            }

        });

    }
}
