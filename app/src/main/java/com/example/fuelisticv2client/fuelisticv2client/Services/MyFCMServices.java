package com.example.fuelisticv2client.fuelisticv2client.Services;

import androidx.annotation.NonNull;

import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFCMServices extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Map<String,String> dataRecv = remoteMessage.getData();
        Common.showNotification(this, new Random().nextInt(),
                dataRecv.get(Common.NOTI_TITLE),
                dataRecv.get(Common.NOTI_CONTENT),
                null);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Common.updateToken(this, s);
    }
}
