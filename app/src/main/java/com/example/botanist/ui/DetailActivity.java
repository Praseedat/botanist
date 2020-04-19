package com.example.botanist.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.botanist.R;
import com.example.botanist.databinding.ActivityDetailBinding;
import com.example.botanist.room.DiseaseDao;
import com.example.botanist.room.DiseaseList;
import com.example.botanist.room.RoomClient;
import com.example.botanist.tfLite.Recognition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private List<Recognition> results;
    private Bitmap capturedImage;

    public static void start(Context context, ArrayList<Recognition> results, byte[] bitmap) {
        Intent starter = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putByteArray("bitmap",bitmap);
        bundle.putParcelableArrayList("result", results);
        starter.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= 29) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        getExtras();
        setData();
        setToolBar();
    }

    private void setToolBar() {

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Disease Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // For back arrow on toolbar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Selection of items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // get the ID
        if (item.getItemId() == R.id.action_history_btn) {
            HistoryActivity.start(getApplicationContext());
            return true;
        }
        return false;
    }

    //For Back navigation Icon logic
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (!results.isEmpty()) {
            String result = "Chances for " + results.get(0).getTitle() + " is " + convertFloatIntoPercentage(results.get(0).getConfidence());
            binding.DiseaseOne.setText(result);
            setIntoLocalDb(result);
        }
        binding.capturedImage.setImageBitmap(capturedImage);
    }

    private void setIntoLocalDb(String disease) {
        RoomClient roomClient = RoomClient.getDatabase(getApplicationContext());
        new InsertAsyncTask(roomClient.diseaseDao()).execute(new DiseaseList(disease, getTime()));
    }

    private String getTime() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(c.getTime());
    }

    private void getExtras () {
        Intent intent = getIntent();
        if (intent != null) {
            results = intent.getParcelableArrayListExtra("result");

            // Decoding image
            capturedImage = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra("bitmap"), 0,
                    Objects.requireNonNull(intent.getByteArrayExtra("bitmap")).length);
        }
    }

    @SuppressLint("DefaultLocale")
    private String convertFloatIntoPercentage(Float confidence) {
        return String.format("%.2f", confidence * 100) + "%";
    }


    private static class InsertAsyncTask extends AsyncTask<DiseaseList, Void, Void> {

        private final DiseaseDao mAsyncTaskDao;

        InsertAsyncTask(DiseaseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(DiseaseList... diseaseLists) {
            mAsyncTaskDao.insert(diseaseLists[0]);
            return null;
        }
    }

}
