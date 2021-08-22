package com.modern_tec.ecommerce.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.databinding.OrderProductsItemBinding;

import org.jetbrains.annotations.NotNull;

public class OrderProductsAdapter extends ListAdapter<CartProduct, OrderProductsAdapter.ViewHolder> {
    Context context;

    private final static DiffUtil.ItemCallback<CartProduct> diffCallback = new DiffUtil.ItemCallback<CartProduct>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull CartProduct oldItem, @NonNull @NotNull CartProduct newItem) {
            return oldItem.getProductId().equals(newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull CartProduct oldItem, @NonNull @NotNull CartProduct newItem) {
            return (oldItem.getProductName().equals(newItem.getProductName()) &&
                    oldItem.getProductDate().equals(newItem.getProductDate()) &&
                    oldItem.getProductTime().equals(newItem.getProductTime()) &&
                    oldItem.getProductDiscount() == newItem.getProductDiscount() &&
                    oldItem.getProductPrice() == newItem.getProductPrice() &&
                    oldItem.getProductTotalPrice() == newItem.getProductTotalPrice());
        }
    };

    public OrderProductsAdapter() {
        super(diffCallback);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ViewHolder(OrderProductsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderProductsAdapter.ViewHolder holder, int position) {
        CartProduct cartProduct = getItem(position);

        holder.binding.orderItemProductName.setText(cartProduct.getProductName());
        holder.binding.orderItemSellerName.setText(cartProduct.getProductSeller());
        holder.binding.orderItemProductQuantity.setText(String.valueOf(cartProduct.getProductQuantity()));
        holder.binding.orderItemProductPrice.setText(cartProduct.getProductPrice() + " EG");

        Glide
                .with(context)
                .load(cartProduct.getProductImage())
                .placeholder(R.drawable.product_placeholder)
                .into(holder.binding.orderItemProductImage);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OrderProductsItemBinding binding;

        public ViewHolder(@NonNull @NotNull OrderProductsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
