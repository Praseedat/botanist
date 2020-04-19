package com.example.botanist.viewHolders;


import androidx.recyclerview.widget.RecyclerView;

import com.example.botanist.databinding.ItemDiseaseHistoryBinding;


public class DiseaseViewHolder extends RecyclerView.ViewHolder {
    private final ItemDiseaseHistoryBinding binding;

    public DiseaseViewHolder(ItemDiseaseHistoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemDiseaseHistoryBinding getBinding() {
        return binding;
    }
}

