package com.arvind.speedtestpro;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvDownloadSpeed, tvUploadSpeed, tvPing, tvNetworkType;
    ProgressBar progressBar;
    Button btnStartTest;
    SpeedGraphView speedGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Views
        tvDownloadSpeed = findViewById(R.id.tvDownloadSpeed);
        tvUploadSpeed = findViewById(R.id.tvUploadSpeed);
        tvPing = findViewById(R.id.tvPing);
        tvNetworkType = findViewById(R.id.tvNetworkType);
        progressBar = findViewById(R.id.progressBar);
        btnStartTest = findViewById(R.id.btnStartTest);
        speedGraphView = findViewById(R.id.graphView);

        // Show Network Type
        String type = NetworkUtil.getNetworkType(this);
        tvNetworkType.setText("Connected via: " + type);

        // Button Click
        btnStartTest.setOnClickListener(v -> {
            btnStartTest.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            speedGraphView.resetGraph();

            // Start Ping Test
            new PingTestTask(tvPing).execute();

            // Start Download/Upload Test
            new SpeedTestTask(this, tvDownloadSpeed, tvUploadSpeed, speedGraphView, progressBar, btnStartTest).execute();
        });
    }
}