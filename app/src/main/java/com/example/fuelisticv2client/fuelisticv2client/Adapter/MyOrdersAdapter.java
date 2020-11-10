package com.example.fuelisticv2client.fuelisticv2client.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.example.fuelisticv2client.fuelisticv2client.Model.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder> {

    private Context context;
    private List<Order> orderList;
    private Calendar calender;
    private SimpleDateFormat simpleDateFormat;


    public MyOrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        calender = Calendar.getInstance();
        simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    @NonNull
    @Override
    public MyOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_order_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.MyViewHolder holder, int position) {
        calender.setTimeInMillis(orderList.get(position).getOrderDate());
        Date date = new Date(orderList.get(position).getOrderDate());
        holder.txt_order_date.setText(new StringBuilder(Common.getDayOfWeek(calender.get(Calendar.DAY_OF_WEEK)))
        .append(" ")
        .append(simpleDateFormat.format(date)));
        holder.txt_delivery_date.setText(new StringBuilder("Delivery date: ").append(orderList.get(position).getDeliveryDate()));
        holder.txt_order_amount.setText(new StringBuilder("Amount: Rs ").append(orderList.get(position).getTotalPayment()));
        holder.txt_order_status.setText(new StringBuilder("Status: ").append(Common.converStatusToText(orderList.get(position).getOrderStatus())));
    }

    @Override
    public int getItemCount() {
        return orderList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        TextView myOrderMenuIcon = findViewById(R.id.txt_order_comment);
        @BindView(R.id.txt_order_amount)
        TextView txt_order_amount;

        @BindView(R.id.txt_order_status)
        TextView txt_order_status;

        @BindView(R.id.txt_delivery_date)
        TextView txt_delivery_date;

        @BindView(R.id.txt_order_date)
        TextView txt_order_date;

        Unbinder unbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

}
