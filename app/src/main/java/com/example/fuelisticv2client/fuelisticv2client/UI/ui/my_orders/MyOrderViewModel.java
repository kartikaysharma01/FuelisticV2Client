package com.example.fuelisticv2client.fuelisticv2client.UI.ui.my_orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fuelisticv2client.fuelisticv2client.Model.Order;

import java.util.List;

public class MyOrderViewModel extends ViewModel {

    private MutableLiveData<List<Order>> mutableLiveDataOrderList;

    public MyOrderViewModel(){
        mutableLiveDataOrderList= new MutableLiveData<>();

    }

    public MutableLiveData<List<Order>> getMutableLiveDataOrderList() {
        return mutableLiveDataOrderList;
    }

    public void setMutableLiveDataOrderList(List<Order> orderList) {
        mutableLiveDataOrderList.setValue(orderList);
    }
}