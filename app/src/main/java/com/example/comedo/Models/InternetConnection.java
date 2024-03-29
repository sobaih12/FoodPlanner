package com.example.comedo.Models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class InternetConnection {
    private static boolean isWifiConn;
    private static boolean isMobileConn;



    public InternetConnection(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        isWifiConn = false;
        isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
    }

    public static boolean isConnectedWifi(){
        return  isWifiConn;
    }

    public static boolean isConnectedMobile(){
        return isMobileConn;
    }
}
