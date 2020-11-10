package com.example.fuelisticv2client.fuelisticv2client.Callback;

import com.example.fuelisticv2client.fuelisticv2client.Model.Order;

public interface ILoadTimeFromFirebaseListener {
    void onLoadTimeSuccess(Order order, long estimateTimeInMs);
    void onLoadTimeFailed(String message);

}
