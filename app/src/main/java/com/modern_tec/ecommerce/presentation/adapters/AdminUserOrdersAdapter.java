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
import com.modern_tec.ecommerce.databinding.FragmentSellerAllUsersOrdersBinding;
import com.modern_tec.ecommerce.databinding.UserOrdersItemBinding;

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
        return new ViewHolder(UserOrdersItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminUserOrdersAdapter.ViewHolder holder, int position) {
        SellerUserOrders sellerUserOrders = getItem(position);

        holder.binding.userName.setText(sellerUserOrders.getOrderList().get(0).getUserName());
        holder.binding.userNumberOrders.setText(sellerUserOrders.getOrderList().size() + " Orders");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(sellerUserOrders);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UserOrdersItemBinding binding;

        public ViewHolder(@NonNull @NotNull UserOrdersItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public interface OnItemClickListener {
        void onItemClick(SellerUserOrders sellerUserOrders);
    }
}
