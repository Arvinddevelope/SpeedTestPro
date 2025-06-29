package com.arvind.speedtestpro;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpeedTestTask extends AsyncTask<Void, Double, Double[]> {

    private Context context;
    private TextView tvDownload, tvUpload;
    private SpeedGraphView graphView;
    private ProgressBar progressBar;
    private Button btnStart;

    public SpeedTestTask(Context context, TextView tvDownload, TextView tvUpload, SpeedGraphView graphView, ProgressBar progressBar, Button btnStart) {
        this.context = context;
        this.tvDownload = tvDownload;
        this.tvUpload = tvUpload;
        this.graphView = graphView;
        this.progressBar = progressBar;
        this.btnStart = btnStart;
    }

    @Override
    protected Double[] doInBackground(Void... voids) {
        double downloadSpeed = testDownloadSpeed();
        double uploadSpeed = simulateUploadSpeed(); // Fake as real upload needs server
        return new Double[]{downloadSpeed, uploadSpeed};
    }

    @Override
    protected void onProgressUpdate(Double... values) {
        graphView.updateGraph(values[0].floatValue());
    }

    @Override
    protected void onPostExecute(Double[] result) {
        tvDownload.setText(String.format("Download Speed: %.2f Mbps", result[0]));
        tvUpload.setText(String.format("Upload Speed: %.2f Mbps", result[1]));
        progressBar.setVisibility(View.GONE);
        btnStart.setEnabled(true);
    }

    private double testDownloadSpeed() {
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL("https://speed.hetzner.de/100MB.bin"); // replace with smaller URL if needed
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream input = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int read;
            long total = 0;

            while ((read = input.read(buffer)) != -1 && total < 5_000_000) {
                total += read;
                double currentSpeed = (total * 8.0) / ((System.currentTimeMillis() - startTime) / 1000.0) / 1_000_000;
                publishProgress(currentSpeed);
            }

            input.close();
            long endTime = System.currentTimeMillis();
            double timeTaken = (endTime - startTime) / 1000.0;

            return (total * 8.0) / (timeTaken * 1_000_000);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private double simulateUploadSpeed() {
        // Placeholder logic
        return Math.random() * 5 + 1;
    }
}