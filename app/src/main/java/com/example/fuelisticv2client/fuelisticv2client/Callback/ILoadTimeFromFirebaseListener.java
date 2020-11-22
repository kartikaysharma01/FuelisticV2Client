package com.example.fuelisticv2client.fuelisticv2client.Callback;

import com.example.fuelisticv2client.fuelisticv2client.Model.OrderModel;

public interface ILoadTimeFromFirebaseListener {
    void onLoadTimeSuccess(OrderModel order, long estimateTimeInMs);
    void onLoadTimeFailed(String message);

}
