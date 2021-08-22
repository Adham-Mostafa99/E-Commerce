package com.modern_tec.ecommerce.presentation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.databinding.OrderItemBinding;

import org.jetbrains.annotations.NotNull;

public class OrderAdapter extends ListAdapter<Order, OrderAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private OnOrderClickListener onOrderClickListener;

    private static final DiffUtil.ItemCallback<Order> diffCallback = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Order oldItem, @NonNull @NotNull Order newItem) {
            return oldItem.getOrderId().equals(newItem.getOrderId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Order oldItem, @NonNull @NotNull Order newItem) {
            return (oldItem.getOrderId().equals(newItem.getOrderId()) &&
                    oldItem.getUserName().equals(newItem.getUserName()) &&
                    oldItem.getUserAddress().getAddressId().equals(newItem.getUserAddress().getAddressId()) &&
                    oldItem.getOrderDate().equals(newItem.getOrderDate()) &&
                    oldItem.getOrderTime().equals(newItem.getOrderTime()) &&
                    oldItem.getState().equals(newItem.getState()) &&
                    oldItem.getTotalPrice() == newItem.getTotalPrice() &&
                    oldItem.getCartProductList().size() == newItem.getCartProductList().size()
            );
        }
    };

    public OrderAdapter() {
        super(diffCallback);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnOrderClickListener(OnOrderClickListener onOrderClickListener) {
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(OrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderAdapter.ViewHolder holder, int position) {
        Order order = getItem(position);

        holder.binding.orderItemId.setText(order.getOrderId());
        holder.binding.orderItemName.setText(order.getUserName());
        holder.binding.orderItemTime.setText(order.getOrderTime());
        holder.binding.orderItemDate.setText(order.getOrderDate());
        holder.binding.orderItemAddressLane.setText(order.getUserAddress().getAddressName());
        holder.binding.orderItemCity.setText(order.getUserAddress().getCity());
        holder.binding.orderItemPhone.setText(order.getUserAddress().getPhone());
        holder.binding.orderItemPrice.setText(order.getTotalPrice() + " EG");


        holder.binding.orderAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(order);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderClickListener != null && position != RecyclerView.NO_POSITION) {
                    onOrderClickListener.onOrderClick(order);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OrderItemBinding binding;

        public ViewHolder(@NonNull @NotNull OrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }
}
