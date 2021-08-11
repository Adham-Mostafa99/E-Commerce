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

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ViewHolder> {

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

    public ProductAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ViewHolder holder, int position) {
        Product currentProduct = getItem(position);

        holder.productName.setText(currentProduct.getProductName());
        holder.productPrice.setText(currentProduct.getProductPrice() + " EG");
        holder.productDescription.setText(currentProduct.getProductDescription());

        Glide
                .with(context)
                .load(currentProduct.getProductImageUrl())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(holder.productImage);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        ImageView productImage;
        TextView productPrice;
        TextView productDescription;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.item_product_name);
            productImage = itemView.findViewById(R.id.item_product_image);
            productDescription = itemView.findViewById(R.id.item_product_desc);
            productPrice = itemView.findViewById(R.id.item_product_price);

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
        void onItemClick(Product product);
    }
}
