package com.arvind.speedtestpro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;

public class NetworkUtil {

    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return "Unknown";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (nc == null) return "No Internet";

            if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return "WiFi";
            if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return "Mobile Data";
        }

        return "Unknown";
    }
}