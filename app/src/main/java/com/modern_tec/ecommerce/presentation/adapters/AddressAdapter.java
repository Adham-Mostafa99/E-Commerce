package com.modern_tec.ecommerce.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.databinding.AddressItemBinding;

import org.jetbrains.annotations.NotNull;

public class AddressAdapter extends ListAdapter<Address, AddressAdapter.ViewHolder> {
    private RadioButton previousButton = null;
    private OnClickItem onClickItem;

    private static final DiffUtil.ItemCallback<Address> diffCallback = new DiffUtil.ItemCallback<Address>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Address oldItem, @NonNull @NotNull Address newItem) {
            return oldItem.getAddressId().equals(newItem.getAddressId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Address oldItem, @NonNull @NotNull Address newItem) {
            return (oldItem.getAddressId().equals(newItem.getAddressId()) &&
                    oldItem.getAddressName().equals(newItem.getAddressName()) &&
                    oldItem.getCity().equals(newItem.getCity()) &&
                    oldItem.getPhone().equals(newItem.getPhone()) &&
                    oldItem.getPostalCode().equals(newItem.getPostalCode()));
        }
    };

    public AddressAdapter() {
        super(diffCallback);
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(AddressItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AddressAdapter.ViewHolder holder, int position) {

        holder.binding.itemAddressDetails.setText(getItem(position).getAddressName());
        holder.binding.itemAddressCity.setText(getItem(position).getCity());
        holder.binding.itemAddressPostalCode.setText(getItem(position).getPostalCode());
        holder.binding.itemAddressPhone.setText(getItem(position).getPhone());

        holder.binding.itemAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previousButton != null && !previousButton.equals(holder.binding.itemAddressButton)) {
                    previousButton.setChecked(false);
                }
                previousButton = holder.binding.itemAddressButton;
                onClickItem.onClick(getItem(position));
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AddressItemBinding binding;

        public ViewHolder(AddressItemBinding addressItemBinding) {
            super(addressItemBinding.getRoot());
            this.binding = addressItemBinding;
        }
    }

    public interface OnClickItem {
        void onClick(Address address);
    }
}
