package com.modern_tec.ecommerce.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;

import org.jetbrains.annotations.NotNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Product;
import com.modern_tec.ecommerce.databinding.SellerProductItemBinding;

import org.jetbrains.annotations.NotNull;

public class SellerProductAdapter extends ListAdapter<Product, SellerProductAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;

    private static final DiffUtil.ItemCallback<Product> diffCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Product oldItem, @NonNull @NotNull Product newItem) {
            return oldItem.getProductId().equals(newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Product oldItem, @NonNull @NotNull Product newItem) {
            return (oldItem.getProductName().equals(newItem.getProductName()) &&
                    oldItem.getProductDescription().equals(newItem.getProductDescription()) &&
                    oldItem.getProductDate().equals(newItem.getProductDate()) &&
                    oldItem.getProductTime().equals(newItem.getProductTime()) &&
                    oldItem.getProductPrice() == newItem.getProductPrice() &&
                    oldItem.getProductImageUrl().equals(newItem.getProductImageUrl()));
        }
    };

    public SellerProductAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public SellerProductAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(SellerProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SellerProductAdapter.ViewHolder holder, int position) {
        Product currentProduct = getItem(position);

        holder.binding.itemSellerProductName.setText(currentProduct.getProductName());
        holder.binding.itemSellerProductPrice.setText(currentProduct.getProductPrice() + " EG");
        holder.binding.itemSellerProductState.setText(currentProduct.getProductState());

        Glide
                .with(context)
                .load(currentProduct.getProductImageUrl())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(holder.binding.itemSellerProductImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(currentProduct);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SellerProductItemBinding binding;

        public ViewHolder(@NonNull @NotNull SellerProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
}


