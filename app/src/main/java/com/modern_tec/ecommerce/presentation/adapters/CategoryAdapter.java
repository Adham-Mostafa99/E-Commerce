package com.modern_tec.ecommerce.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Category;

import org.jetbrains.annotations.NotNull;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.ViewHolder> {
    OnCLickItem onCLickItem;

    private static final DiffUtil.ItemCallback<Category> diffCallback = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Category oldItem, @NonNull @NotNull Category newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Category oldItem, @NonNull @NotNull Category newItem) {
            return false;
        }
    };

    public CategoryAdapter() {
        super(diffCallback);
    }

    public void setOnCLickItem(OnCLickItem onCLickItem) {
        this.onCLickItem = onCLickItem;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.ViewHolder holder, int position) {

        holder.categoryName.setText(getItem(position).getText());
        holder.categoryImage.setImageDrawable(getItem(position).getImage());
        holder.categoryColor.setImageDrawable(getItem(position).getColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickItem.onclick(getItem(position).getText());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        ImageView categoryImage;
        ImageView categoryColor;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.category_item_name);
            categoryImage = itemView.findViewById(R.id.category_item_image);
            categoryColor = itemView.findViewById(R.id.category_item_color);

        }
    }

    public interface OnCLickItem {
        void onclick(String name);
    }
}
