package com.example.botanist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.botanist.R;
import com.example.botanist.adapter.DiseaseAdapter;
import com.example.botanist.databinding.ActivityHistoryBinding;
import com.example.botanist.room.RoomClient;
import com.example.botanist.utility.Utility;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private DiseaseAdapter diseaseAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, HistoryActivity.class);
        if (Build.VERSION.SDK_INT >= 29) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        initRecyclerView();
        fetchData();
    }

    private void initRecyclerView() {
        diseaseAdapter = new DiseaseAdapter(getApplicationContext());
        Utility.setVerticalRecyclerView(binding.recyclerView, diseaseAdapter,
                getApplicationContext(), false, true);
    }

    private void fetchData() {
        RoomClient.getDatabase(getApplicationContext()).diseaseDao()
                .getAllData().observe(this, diseaseLists -> diseaseAdapter.addItemRange(diseaseLists));
    }
}
