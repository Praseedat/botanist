package com.example.botanist.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botanist.databinding.ItemDiseaseHistoryBinding;
import com.example.botanist.room.DiseaseList;
import com.example.botanist.viewHolders.DiseaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<DiseaseList> mListItems = new ArrayList<>();
    private final Context mContext;

    //constructor
    public DiseaseAdapter(Context context) { // constructor
        this.mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDiseaseHistoryBinding binding = ItemDiseaseHistoryBinding
                .inflate(inflater, parent, false);
        return new DiseaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DiseaseViewHolder userViewHolder;

        if (holder instanceof DiseaseViewHolder) {
            userViewHolder = (DiseaseViewHolder) holder;
            setDiseaseViewHolder(userViewHolder, position);
        }
    }

    private void setDiseaseViewHolder(DiseaseViewHolder diseaseViewHolder, int position) {
        DiseaseList disease = mListItems.get(position);
        if (disease != null) {
            diseaseViewHolder.getBinding().diseaseDetails.setText(disease.getDisease());
            diseaseViewHolder.getBinding().date.setText(disease.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    //-------------------------------------Manipulating RecyclerView--------------------------------//
    public void clearData() {
        if (!mListItems.isEmpty()) {
            mListItems.clear();
            notifyDataSetChanged();
        }
    }

    public void addItemRange(List<DiseaseList> items) {
        if (items != null) {
            int position = mListItems.size();
            mListItems.addAll(position, items);
            notifyItemRangeInserted(position, items.size());
        }
    }
}

