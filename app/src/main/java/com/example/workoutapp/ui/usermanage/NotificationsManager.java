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
import com.example.workoutapp.Chat;
import com.example.workoutapp.R;
import com.example.workoutapp.UserActivityController;

import java.util.ArrayList;


public class NotificationsManager{
    private static final String CHANNEL_ID = "notificación";
    private static Context ctx;

    public NotificationsManager(){
        ctx = ActivityController.getContext();
    }

    public static NotificationCompat.Builder getBuilder(String title, String text) {

        return new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_definitiu1)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notification(Integer NOTIFICATION_ID, String title, String text) {
        int IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
        String CHANNEL_NAME = "notificación";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE);

        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(channel);
        mNotificationManager.notify(NOTIFICATION_ID, getBuilder(title, text).build());
    }

    public void checkJoinedActivities(){
        SharedPreferencesController spc = new SharedPreferencesController(ctx);
        if(spc.getAlertJoins()) {
            UserActivityController uaController = new UserActivityController(ctx);

            uaController.getJoinedActivities(new UserActivityController.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String message) {
                }

                @Override
                public void onResponseFavorites(ArrayList<Activitat> ret) {
                }

                @Override
                public void onResponseFav() {
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponseJoinedActivites(ArrayList<Activitat> ret) {
                    for (int i = 0; i < ret.size(); i++) {
                        Long date = ret.get(i).getTimestamp();
                        boolean notified = spc.isNotified(ret.get(i).getId(), "joins");
                        if (!notified && date * 1000L <= System.currentTimeMillis() + 172800000 && !ret.get(i).isOld()) {
                            Integer notification_id = ret.get(i).getId() + 200000;
                            spc.setNotified(ret.get(i).getId(),"joins");
                            String organization = ret.get(i).getOrganizerName();
                            String name = ret.get(i).getName();
                            String text = "Quedan menos de 2 días para la actividad";
                            notification(notification_id, name + " - " + organization, text);

                        }
                    }
                }

                @Override
                public void onResponseChat(ArrayList<Chat> ret) {
                    
                }

            });
        }

    }

    public void checkFavsActivities() {
        SharedPreferencesController spc = new SharedPreferencesController(ctx);
        if(spc.getAlertFavs()) {
            UserActivityController uaController = new UserActivityController(ctx);

            uaController.getFavorites(new UserActivityController.VolleyResponseListener() {

                @Override
                public void onError(String message) {
                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String message) {
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponseFavorites(ArrayList<Activitat> ret) {
                    for (int i = 0; i < ret.size(); i++) {
                        int joineds = ret.get(i).getClientJoined();
                        boolean notified = spc.isNotified(ret.get(i).getId(), "favs");
                        if (!notified && joineds <= 2 && !ret.get(i).isOld()) {
                            Integer notification_id = ret.get(i).getId() + 200001;
                            spc.setNotified(ret.get(i).getId(),"favs");
                            String organization = ret.get(i).getOrganizerName();
                            String name = ret.get(i).getName();
                            String text = "Quedan menos de 2 plazas para la actividad que tienes en favoritos";
                            notification(notification_id, name + " - " + organization, text);

                        }
                    }
                }

                @Override
                public void onResponseFav() {
                }

                @Override
                public void onResponseJoinedActivites(ArrayList<Activitat> ret) {
                }

                @Override
                public void onResponseChat(ArrayList<Chat> ret) {

                }
            });
        }
    }
}
