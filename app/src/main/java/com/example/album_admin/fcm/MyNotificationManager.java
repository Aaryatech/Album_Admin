package com.example.album_admin.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.album_admin.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;


public class MyNotificationManager {

    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;

    public Context mCtx;

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public void showBigNotification(String title, String message, Intent intent) {
        PendingIntent resultPendingIntent = PendingIntent.getActivity(mCtx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("ANDROID","----------------------------------O");
            NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);
            String id = "id_product";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, title, importance);
            mChannel.setDescription(message);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(mChannel);

            NotificationCompat.BigTextStyle bigPictureStyle = new NotificationCompat.BigTextStyle();
            bigPictureStyle.setBigContentTitle(title);
            bigPictureStyle.bigText(message);
            // bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());

            PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mCtx,"id_product")
                    .setSmallIcon(R.mipmap.ic_launcher_round) //your app icon
                    .setBadgeIconType(R.mipmap.ic_launcher) //your app icon
                    .setChannelId(id)
                    .setContentTitle(title)
                    .setAutoCancel(true).setContentIntent(pendingIntent)
                    .setNumber(1)
                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher_round))
                    .setStyle(bigPictureStyle)
                    .setColor(255)
                    .setContentText(message)
                    .setWhen(System.currentTimeMillis());
            notificationManager.notify(1, notificationBuilder.build());

        } else {
            Log.e("ANDROID","---------------------------------- < O");

            NotificationCompat.BigTextStyle bigPictureStyle1 = new NotificationCompat.BigTextStyle();
            bigPictureStyle1.setBigContentTitle(title);
            bigPictureStyle1.bigText(message);
            //bigPictureStyle1.setSummaryText(Html.fromHtml(message).toString());

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle(title)
                    .setStyle(bigPictureStyle1)
                    .setContentText(message)
                    .setContentIntent(resultPendingIntent);
            NotificationManager manager = (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        }

    }

    public void showSmallNotification(String title, String message, Intent intent) {

        //Uri uri = Uri.parse("mattersofgrey.com/audio/DEX-Gen-MainThemeDing.mp3");
       Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("ANDROID","----------------------------------O");
            NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);
            String id = "id_product";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel mChannel = new NotificationChannel(id,
                    mCtx.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            mChannel.setDescription("");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setSound(alarmSound, attributes); // This is IMPORTANT

            notificationManager.createNotificationChannel(mChannel);

            PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mCtx,"id_product")
                    .setSmallIcon(R.mipmap.ic_launcher) //your app icon
                    .setBadgeIconType(R.mipmap.ic_launcher) //your app icon
                    .setChannelId(id)
                    .setContentTitle(title)
                    .setAutoCancel(true).setContentIntent(pendingIntent)
                    .setNumber(1)
                    .setColor(255)
                    .setContentText(message)
                    .setSound(alarmSound)
                    .setWhen(System.currentTimeMillis());
            notificationManager.notify(1, notificationBuilder.build());


        } else {
            Log.e("ANDROID","---------------------------------- < O");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(resultPendingIntent);
            NotificationManager manager = (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        }

    /*    NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_square_logo).setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
*/
      //  playNotificationSound();

        //Log.e("showSmallNotification", "-------------------------------------");
    }

    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public void playNotificationSound() {
        try {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(mCtx, notification);
            r.play();
            Vibrator v = (Vibrator) mCtx.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
            Log.e("playNotificationSound","----------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
