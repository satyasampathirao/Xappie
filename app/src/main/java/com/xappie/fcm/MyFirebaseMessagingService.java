package com.xappie.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.db.NotificationDataSource;
import com.xappie.models.NotificationModel;
import com.xappie.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private String category;
    private String title;
    private String source;
    private String time;
    private String image_url;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Map<String, String> params = remoteMessage.getData();
            for (Map.Entry<String, String> values : params.entrySet()) {

                 if (values.getKey().equalsIgnoreCase("category")) {
                    category = values.getValue();
                }
                else if (values.getKey().equalsIgnoreCase("title")) {
                    title = values.getValue();
                }
                else if (values.getKey().equalsIgnoreCase("time")) {
                    time = values.getValue();
                }
                else if (values.getKey().equalsIgnoreCase("source"))
                 {
                     source = values.getValue();
                 }
                 else if (values.getKey().equalsIgnoreCase("image_url"))
                 {
                     image_url = values.getValue();
                 }
            }

            sendNotification();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent i = new Intent(this, DashBoardActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        i.putExtra("from", "notification");
        i.putExtra("category", category);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), i,
                PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(pendingIntent);
        builder.setTicker(getResources().getString(R.string.custom_notification));
        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.notification_intro);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.custom_notification_item);
      //  final String time = DateFormat.getTimeInstance().format(new Date());


        /*INSERTING NOTIFICATION INTO DATABASE*/
       NotificationModel mNotificationModel= new NotificationModel();
        NotificationDataSource db= new NotificationDataSource(this);

        mNotificationModel.setCategory(category);
        mNotificationModel.setIsopened("false");
        mNotificationModel.setTitle(title);
        mNotificationModel.setTime(time);
        mNotificationModel.setImage_url(image_url);
        mNotificationModel.setSource(source);

        db.insertData(mNotificationModel);


       final String time = getCurrentTime();
        contentView.setTextViewText(R.id.tv_list_heading, title);
        contentView.setTextViewText(R.id.tv_list_time, time);
        contentView.setTextViewText(R.id.tv_list_person, source);
        notification.contentView = contentView;


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);


    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    private Bitmap getBitmapForInstructor(String attachment_url) {
        if (!Utility.isValueNullOrEmpty(attachment_url)) {
            try {
                URL url = new URL(attachment_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    //to convert round shape
                    bitmap = getRoundShapedBitmap(bitmap);
                }
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private Bitmap getBitmap(String attachment_url) {
        if (!Utility.isValueNullOrEmpty(attachment_url)) {
            try {
                URL url = new URL(attachment_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);

                }
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private Bitmap getRoundShapedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
        final RectF rectF = new RectF(rect);
        final float roundPx = 180;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }
}