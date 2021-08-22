package com.modern_tec.ecommerce.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.databinding.OrderItemBinding;
import com.modern_tec.ecommerce.databinding.SellerOrderItemBinding;

import org.jetbrains.annotations.NotNull;

public class SellerOrdersAdapter extends ListAdapter<Order, SellerOrdersAdapter.ViewHolder> {
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

    public SellerOrdersAdapter() {
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
    public SellerOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(SellerOrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SellerOrdersAdapter.ViewHolder holder, int position) {
        Order order = getItem(position);

        holder.binding.orderItemId.setText(order.getOrderId());
        holder.binding.orderItemName.setText(order.getUserName());
        holder.binding.orderItemTime.setText(order.getOrderTime());
        holder.binding.orderItemDate.setText(order.getOrderDate());
        holder.binding.orderItemAddressLane.setText(order.getUserAddress().getAddressName());
        holder.binding.orderItemCity.setText(order.getUserAddress().getCity());
        holder.binding.orderItemPhone.setText(order.getUserAddress().getPhone());
        holder.binding.orderItemPrice.setText(order.getTotalPrice() + " EG");


        holder.binding.orderDeliveredBtn.setOnClickListener(new View.OnClickListener() {
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
        SellerOrderItemBinding binding;

        public ViewHolder(@NonNull @NotNull SellerOrderItemBinding binding) {
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
