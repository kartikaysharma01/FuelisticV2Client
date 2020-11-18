package com.example.fuelisticv2client.fuelisticv2client.Callback;

import com.example.fuelisticv2client.fuelisticv2client.Model.OrderModel;

import java.util.List;

public interface ILoadOrderCallbackListener {
    void onLoadOrderSuccess(List<OrderModel> orderList);
    void onLoadOrderFailed(String message);
}
