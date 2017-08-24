package appwork.almayce.easyboil;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by almayce on 26.10.16.
 */
public class Alarm extends WakefulBroadcastReceiver {

    private AlarmManager am;

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");

        wl.acquire();

        Intent resultIntent = new Intent(context, MainActivity.class);

        if (MyApplication.isActivityVisible())
            resultIntent = intent;

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 333,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Easyboil");
        builder.setContentText("Boiled!");
        builder.setColor(context.getResources().getColor(R.color.colorAccent));
        builder.setSmallIcon(R.drawable.alarm_check);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(333, notification);

        Intent intentBoiled = new Intent(context, BoiledActivity.class);
        context.startActivity(intentBoiled);
        wl.release();
    }

    public void startAlarm(Context context) {
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }

    public void setAlarm(Context context, int min) {
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int alarmTime = 1000 * 60 * min;
        // Millisec * Second * Minute

        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + alarmTime, pi);
    }

    public void cancelAlarm(Context context) {
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }
}
