package com.example.fuelisticv2client.fuelisticv2client.Callback;

import com.example.fuelisticv2client.fuelisticv2client.Model.Order;

import java.util.List;

public interface ILoadOrderCallbackListener {
    void onLoadOrderSuccess(List<Order> orderList);
    void onLoadOrderFailed(String message);
}
