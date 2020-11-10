package com.example.fuelisticv2client.fuelisticv2client.Common;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.example.fuelisticv2client.fuelisticv2client.Model.UserModel;

import java.util.Random;

public class Common {

    public static final String USER_REFERENCES = "Users";
    public static final String ORDER_REF = "Orders";

    public static UserModel currentUser;

    public static final double diesel_price = 62.09;

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
}
