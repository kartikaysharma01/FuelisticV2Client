package com.example.fuelisticv2client.fuelisticv2client.UI.ui.my_orders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fuelisticv2client.fuelisticv2client.Model.OrderModel;

import java.util.List;

public class MyOrderViewModel extends ViewModel {

    private MutableLiveData<List<OrderModel>> mutableLiveDataOrderList;

    public MyOrderViewModel(){
        mutableLiveDataOrderList= new MutableLiveData<>();

    }

    public MutableLiveData<List<OrderModel>> getMutableLiveDataOrderList() {
        return mutableLiveDataOrderList;
    }

    public void setMutableLiveDataOrderList(List<OrderModel> orderList) {
        mutableLiveDataOrderList.setValue(orderList);
    }
}