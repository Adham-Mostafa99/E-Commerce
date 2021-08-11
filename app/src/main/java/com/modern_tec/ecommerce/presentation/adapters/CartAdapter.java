package com.modern_tec.ecommerce.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.CartProduct;

import org.jetbrains.annotations.NotNull;

public class CartAdapter extends ListAdapter<CartProduct, CartAdapter.ViewHolder> {
    OnItemClickListener onItemClickListener;

    private final static DiffUtil.ItemCallback<CartProduct> diffCallback = new DiffUtil.ItemCallback<CartProduct>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull CartProduct oldItem, @NonNull @NotNull CartProduct newItem) {
            return oldItem.getProductId().equals(newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull CartProduct oldItem, @NonNull @NotNull CartProduct newItem) {
            return (oldItem.getProductName().equals(newItem.getProductName())&&
                    oldItem.getProductDate().equals(newItem.getProductDate())&&
                    oldItem.getProductTime().equals(newItem.getProductTime()) &&
                    oldItem.getProductDiscount() == newItem.getProductDiscount() &&
                    oldItem.getProductPrice() == newItem.getProductPrice() &&
                    oldItem.getProductQuantity() == newItem.getProductQuantity()&&
                    oldItem.getProductTotalPrice() == newItem.getProductTotalPrice());
        }
    };

    public CartAdapter() {
        super(diffCallback);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapter.ViewHolder holder, int position) {
        CartProduct cartProduct = getItem(position);
        holder.productName.setText(cartProduct.getProductName());
        holder.productQuantity.setText("Quantity=" + cartProduct.getProductQuantity());
        holder.productPrice.setText("Price  " + cartProduct.getProductPrice() + " EG");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productQuantity;
        TextView productPrice;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.cart_item_product_name);
            productQuantity = itemView.findViewById(R.id.cart_item_product_quantity);
            productPrice = itemView.findViewById(R.id.cart_item_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
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
        void onItemClick(CartProduct cartProduct);
    }
}
