package com.example.fuelisticv2client.fuelisticv2client.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Callback.IRecyclerClickListener;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.example.fuelisticv2client.fuelisticv2client.Model.OrderModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder> {

    private Context context;
    private List<OrderModel> orderList;
    private Calendar calender;
    private SimpleDateFormat simpleDateFormat;


    public MyOrdersAdapter(Context context, List<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
        calender = Calendar.getInstance();
        simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public OrderModel getItemAtPosition(int pos){
        return orderList.get(pos);
    }

    public void setItemAtPosition(int pos, OrderModel item){
        orderList.set(pos, item);
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
        holder.txt_order_status.setText(new StringBuilder("Status: ").append(Common.convertStatusToText(orderList.get(position).getOrderStatus())));

        holder.setRecyclerClickListener((view, position1) -> showDialog(position1));

    }

    private void showDialog(int position) {
        View layout_dialog = LayoutInflater.from(context).inflate(R.layout.layout_dialog_order_detail, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout_dialog);

        Button btn_ok = (Button) layout_dialog.findViewById(R.id.btn_ok);
        TextInputEditText order_details_order_num= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_order_num);
        TextInputEditText order_details_order_status= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_order_status);
        TextInputEditText order_details_transaction_id= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_transaction_id);
        TextInputEditText order_details_delivery_address= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_delivery_address);
        TextInputEditText order_details_delivery_charge= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_delivery_charge);
        TextInputEditText order_details_total_order_amt= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_total_order_amt);
        TextInputEditText order_details_order_date= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_order_date);
        TextInputEditText order_details_order_quantity= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_order_quantity);
        TextInputEditText order_details_delivery_mode= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_delivery_mode);
        TextInputEditText order_details_delivery_cmt= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_delivery_cmt);
        TextInputEditText order_details_delivery_date= (TextInputEditText) layout_dialog.findViewById(R.id.order_details_delivery_date);

        order_details_order_num.setText(orderList.get(position).getOrderNumber());
        order_details_order_status.setText(Common.convertStatusToText(orderList.get(position).getOrderStatus()) );
        order_details_transaction_id.setText(orderList.get(position).getTransactionId());
        order_details_delivery_address.setText(orderList.get(position).getShippingAddress());
        order_details_delivery_charge.setText(Integer.toString(orderList.get(position).getDeliveryCharge()));

        calender.setTimeInMillis(orderList.get(position).getOrderDate());
        Date date = new Date(orderList.get(position).getOrderDate());
        order_details_order_date.setText(new StringBuilder(Common.getDayOfWeek(calender.get(Calendar.DAY_OF_WEEK)))
                .append(", ")
                .append(simpleDateFormat.format(date)));
        order_details_total_order_amt.setText(String.valueOf(orderList.get(position).getTotalPayment()));
        order_details_order_quantity.setText(orderList.get(position).getQuantity());
        order_details_delivery_mode.setText(orderList.get(position).getDeliveryMode());
        order_details_delivery_cmt.setText(orderList.get(position).getComment());
        order_details_delivery_date.setText(orderList.get(position).getDeliveryDate());


        //show dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // custom dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);

        btn_ok.setOnClickListener(view -> dialog.dismiss());
    }

    @Override
    public int getItemCount() {
        return orderList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        IRecyclerClickListener recyclerClickListener;

        public void setRecyclerClickListener(IRecyclerClickListener recyclerClickListener) {
            this.recyclerClickListener = recyclerClickListener;
        }


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            recyclerClickListener.onItemClickListener(view, getAdapterPosition());

        }
    }

}
