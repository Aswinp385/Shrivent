package com.develop.eventmanagement;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;*/
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> params = remoteMessage.getData();
        String jsonStr = params.get("data");
        Log.i("MainActivity.TAG", "FireBase -> JSON: " + jsonStr);

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            final int id = 0;
            final String title = jsonObject.getString("title");
            final String description = jsonObject.getString("message");
            final String image = jsonObject.getString("image");


            showNotification(title, description, null, id);
           /* Glide.with(this)
                    .asBitmap()
                    .load(image)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            showNotification(title, description, null, id);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            showNotification(title, description, resource, id);
                            return false;
                        }
                    })
                    .submit();*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNotification(String title, String description, Bitmap bitmap, int id) {
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, SplashActivity.class);
      //  resultIntent.putExtra("id", id);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_event)
                .setContentTitle(title)
                .setContentText(description)
                .setTicker(getString(R.string.app_name))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentIntent(resultPendingIntent);

        if (bitmap != null) {
            NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
            bpStyle.bigPicture(bitmap);
            bpStyle.build();
            builder.setStyle(bpStyle);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence _name = getString(R.string.channel_name);
            String _description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, _name, importance);
            channel.setDescription(_description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(id, builder.build());
            playNotificationSound();
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(id, builder.build());
            playNotificationSound();
        }
    }

    public void playNotificationSound() {
        try {
                /*Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + mContext.getPackageName() + "/raw/notification");
                Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
                r.play();*/

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(this, notification);
            r.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
