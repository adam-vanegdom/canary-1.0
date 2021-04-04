package com.meetkumarpatel.canary.ui.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.meetkumarpatel.canary.R;

public class SettingsItemAdapter extends RecyclerView.Adapter<SettingsItemAdapter.ViewHolder> {
    private final String[] items;
    private OnItemClickListener onItemClickListener;

    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClick(@NonNull final String item);
    }

    public SettingsItemAdapter (String[] items) {
        this.items = items;
    }

    public void setOnItemClickListener(final OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getItemName().setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    final class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = (MaterialTextView) itemView.findViewById(R.id.item_name);

            itemView.findViewById(R.id.device_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemName.toString());
                    }
                }
            });
        }

        public MaterialTextView getItemName() {
            return itemName;
        }
    }
}