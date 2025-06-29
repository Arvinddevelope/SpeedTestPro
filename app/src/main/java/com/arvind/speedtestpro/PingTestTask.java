package com.arvind.speedtestpro;

import android.os.AsyncTask;
import android.widget.TextView;

public class PingTestTask extends AsyncTask<Void, Void, Double> {

    private TextView tvPing;

    public PingTestTask(TextView tvPing) {
        this.tvPing = tvPing;
    }

    @Override
    protected Double doInBackground(Void... voids) {
        try {
            long start = System.currentTimeMillis();
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 google.com");
            process.waitFor();
            long end = System.currentTimeMillis();
            return (double) (end - start);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    protected void onPostExecute(Double result) {
        tvPing.setText(String.format("Ping: %.0f ms", result));
    }
}
