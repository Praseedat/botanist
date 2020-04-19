package com.example.botanist.utility;

import android.content.Context;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Utility {
    public static void setVerticalRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, Context context, boolean nestedScroll, boolean decor) {
        if (recyclerView != null && context != null) {
            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setNestedScrollingEnabled(nestedScroll);
            if (decor) {
                DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(itemDecor);
            }
            recyclerView.setAdapter(adapter);
        }
    }
}

