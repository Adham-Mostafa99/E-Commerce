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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.CartProduct;

import org.jetbrains.annotations.NotNull;

public class CartAdapter extends ListAdapter<CartProduct, CartAdapter.ViewHolder> {
    OnChangeQuantity onChangeQuantity;
    OnDelete onDelete;
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

    public CartAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }


    public void setOnChangeQuantity(OnChangeQuantity onChangeQuantity) {
        this.onChangeQuantity = onChangeQuantity;
    }

    public void setOnDelete(OnDelete onDelete) {
        this.onDelete = onDelete;
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
        holder.sellerName.setText(cartProduct.getProductSeller());
        holder.productQuantity.setNumber(cartProduct.getProductQuantity() + "");
        holder.productPrice.setText(cartProduct.getProductPrice() + " EG");

        Glide
                .with(context)
                .load(cartProduct.getProductImage())
                .placeholder(R.drawable.product_placeholder)
                .into(holder.productImage);

        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete.onDelete(cartProduct.getProductId());
            }
        });

        holder.productQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                onChangeQuantity.onChange(cartProduct, newValue);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView sellerName;
        ImageView removeProduct;
        ElegantNumberButton productQuantity;
        TextView productPrice;
        ImageView productImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.cart_item_product_name);
            sellerName = itemView.findViewById(R.id.cart_item_seller_name);
            productQuantity = itemView.findViewById(R.id.cart_item_product_quantity);
            productPrice = itemView.findViewById(R.id.cart_item_product_price);
            removeProduct = itemView.findViewById(R.id.cart_item_product_delete);
            productImage = itemView.findViewById(R.id.cart_item_product_image);


        }
    }


    public interface OnDelete {
        void onDelete(String id);
    }

    public interface OnChangeQuantity {
        void onChange(CartProduct cartProduct, int quantity);
    }
}
