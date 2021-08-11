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
import com.modern_tec.ecommerce.core.models.SellerUserOrders;

import org.jetbrains.annotations.NotNull;

public class AdminUserOrdersAdapter extends ListAdapter<SellerUserOrders, AdminUserOrdersAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private static final DiffUtil.ItemCallback<SellerUserOrders> diffCallback = new DiffUtil.ItemCallback<SellerUserOrders>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull SellerUserOrders oldItem, @NonNull @NotNull SellerUserOrders newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull SellerUserOrders oldItem, @NonNull @NotNull SellerUserOrders newItem) {
            return oldItem.getOrderList().equals(newItem.getOrderList());
        }
    };

    public AdminUserOrdersAdapter() {
        super(diffCallback);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_orders_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminUserOrdersAdapter.ViewHolder holder, int position) {
        SellerUserOrders sellerUserOrders = getItem(position);
        holder.userName.setText("Name: " + sellerUserOrders.getOrderList().get(0).getUserName());
        holder.ordersQuantity.setText("Orders Quantity: " + sellerUserOrders.getOrderList().size());

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView ordersQuantity;
        Button showOrdersBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.item_user_orders_user_name);
            ordersQuantity = itemView.findViewById(R.id.item_user_orders_quantity);
            showOrdersBtn = itemView.findViewById(R.id.item_user_orders_show);

            showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(SellerUserOrders sellerUserOrders);
    }
}
