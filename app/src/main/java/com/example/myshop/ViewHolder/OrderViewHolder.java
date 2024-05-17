package com.example.myshop.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView orderName, orderTime;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderName = itemView.findViewById(R.id.order_name);
        orderTime = itemView.findViewById(R.id.order_time);
    }
}
