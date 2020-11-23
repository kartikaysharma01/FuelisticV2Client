package com.example.fuelisticv2client.fuelisticv2client.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Model.ShippingOrderModel;
import com.example.fuelisticv2client.fuelisticv2client.Model.TokenModel;
import com.example.fuelisticv2client.fuelisticv2client.Model.UserModel;

import com.example.fuelisticv2client.fuelisticv2client.Services.MyFCMServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Common {

    public static final String USER_REFERENCES = "Users";
    public static final String ORDER_REF = "Orders";
    public static final String NOTI_TITLE = "title";
    public static final String NOTI_CONTENT = "content";
    public static final String SHIPPING_ORDER_REF = "ShippingOrder";
    private static final String TOKEN_REF = "Tokens";

    public static UserModel currentUser;

    public static final double diesel_price = 62.09;
    public static ShippingOrderModel currentShippingOrder;

    public static String createOrderNumber() {
        return new StringBuilder()
                .append(System.currentTimeMillis())
                .append(Math.abs(new Random().nextInt()))
                .toString();
    }

    public static String converStatusToText(int orderStatus) {
        switch (orderStatus){
            case 0:
                return "Placed";
            case 1:
                return "Confirmed";
            case 2:
                return "Completed";
            case -1:
                return "Cancelled";
            default:
                return "Unknown";

        }
    }

    public static String getDayOfWeek(int i) {
        i = i - 1;
        switch (i){
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "Unknown";
        }
    }

    public static void setSpanString(String welcome, String fullName, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(welcome);
        SpannableString spannableString = new SpannableString(fullName);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 0,fullName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        textView.setText(builder, TextView.BufferType.SPANNABLE);
    }

    public static String createTopicOrder() {
        return new StringBuilder("/topics/new_order").toString();
    }

    public static void showNotification(Context context, int id, String title, String content, Intent intent) {
        PendingIntent pendingIntent = null;
        if(intent != null)
            pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String NOTIFICATION_CHANNEL_ID = "fuelistic_v2";
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Fuelistic V2", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Fuelistic V2");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.fuel_tank_logo));

        if(pendingIntent != null)
            builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);



    }

    public static void updateToken(Context context, String newToken) {

        if(Common.currentUser!=null){
            FirebaseDatabase.getInstance()
                    .getReference(Common.TOKEN_REF)
                    .child(Common.currentUser.getPhoneNo())
                    .setValue(new TokenModel(Common.currentUser.getPhoneNo(), newToken))
                    .addOnFailureListener(e -> Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
        }

    }

    public static List<LatLng> decodePoly(String encoded) {
        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat =0 , lng=0;
        while (index<len)
        {
            int b, shift= 0, result =0;
            do {
                b = encoded.charAt(index++)- 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b> 0x20);
            int dlat = ((result & 1) != 0 ? ~(result>>1):(result>>1) );
            lat += dlat;

            shift =0;
            result = 0;

            do {
                b = encoded.charAt(index++)- 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b> 0x20);
            int dlng = ((result & 1) != 0 ? ~(result>>1):(result>>1) );
            lng += dlng;

            LatLng p = new LatLng(((double)lat / 1E5), ((double)lng / 1E5));
            poly.add(p);
        }
        return poly;
    }
}
