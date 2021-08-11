package com.modern_tec.ecommerce.presentation.adapters;

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
            return (oldItem.getUserName().equals(newItem.getUserName()) &&
                    oldItem.getUserEmail().equals(newItem.getUserEmail()) &&
                    oldItem.getUserAddress().equals(newItem.getUserAddress()) &&
                    oldItem.getUserCity().equals(newItem.getUserCity()) &&
                    oldItem.getUserPhone().equals(newItem.getUserPhone()) &&
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderAdapter.ViewHolder holder, int position) {
        Order order = getItem(position);
        holder.userName.setText("Name: " + order.getUserName());
        holder.userEmail.setText("Email: " + order.getUserEmail());
        holder.userPhone.setText("Phone: " + order.getUserPhone());
        holder.userAddress.setText("Shipping Address: " + order.getUserAddress());
        holder.userCity.setText("City: " + order.getUserCity());
        holder.orderPrice.setText("Total Price: " + order.getTotalPrice() + " EG");
        holder.orderDate.setText("Date of Order: " + order.getOrderDate() + "-" + order.getOrderTime());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView userEmail;
        TextView userPhone;
        TextView userAddress;
        TextView userCity;
        TextView orderPrice;
        TextView orderDate;
        Button orderShowProductsBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.item_order_user_name);
            userEmail = itemView.findViewById(R.id.item_order_user_email);
            userPhone = itemView.findViewById(R.id.item_order_user_phone);
            userAddress = itemView.findViewById(R.id.item_order_user_address);
            userCity = itemView.findViewById(R.id.item_order_user_city);
            orderPrice = itemView.findViewById(R.id.item_order_total_price);
            orderDate = itemView.findViewById(R.id.item_order_date_time);
            orderShowProductsBtn = itemView.findViewById(R.id.item_order_show_details_btn);


            orderShowProductsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOrderClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onOrderClickListener.onOrderClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }

    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }
}
