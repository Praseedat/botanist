package com.example.botanist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.example.botanist.R;
import com.example.botanist.databinding.ActivityMainBinding;
import com.example.botanist.tfLite.Classifier;
import com.example.botanist.tfLite.Recognition;
import com.example.botanist.tfLite.TensorFlowImageClassifier;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String MODEL_PATH = "optimized_graph.tflite";
    private static final boolean QUANT = false;
    private static final String LABEL_PATH = "retrained_labels.txt";
    private static final int INPUT_SIZE = 224;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private Classifier classifier;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        captureImage();
        getCameraImage();
        initTensorFlowAndLoadModel();
    }

    private void captureImage() {
        binding.captureImage.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.cameraView.captureImage();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.cameraView.start();
    }

    private void getCameraImage() {
        binding.cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                processCameraImage(cameraKitImage);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void processCameraImage(CameraKitImage cameraKitImage) {
        Bitmap bitmap = cameraKitImage.getBitmap();

        bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

        final List<Recognition> results = classifier.recognizeImage(bitmap);

        ArrayList<Recognition> arrayList = new ArrayList<>(results);

        binding.progressBar.setVisibility(View.GONE);

        // Navigating into Detail Activity
        DetailActivity.start(getApplicationContext(), arrayList, compressedImage(bitmap));
    }

    private byte[] compressedImage(Bitmap bitmap) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, bs);
        return bs.toByteArray();
    }

    @Override
    protected void onPause() {
        binding.cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(() -> classifier.close());
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(() -> {
            try {
                classifier = TensorFlowImageClassifier.create(
                        getAssets(),
                        MODEL_PATH,
                        LABEL_PATH,
                        INPUT_SIZE,
                        QUANT);
                makeButtonVisible();
            } catch (final Exception e) {
                throw new RuntimeException("Error initializing TensorFlow!", e);
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(() -> binding.captureImage.show());
    }
}
