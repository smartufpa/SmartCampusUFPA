package com.example.kaeuc.smartufpa.utils;


import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kaeuc on 26/01/2017.
 */

public class SystemServicesManager {


    public static boolean isWifiEnabled(Context parentContext){
        boolean isConnected = false;
        ConnectivityManager cm =
                (ConnectivityManager)parentContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!= null){
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if(!isWiFi){
                isConnected = activeNetwork.isConnectedOrConnecting();
            }
        }

        return isConnected;
    }

    public static boolean isMobileDataEnabled(Context parentContext){
        boolean isConnected = false;
        ConnectivityManager cm =
                (ConnectivityManager)parentContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!= null){
            boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            if(!isMobile){
                isConnected = activeNetwork.isConnectedOrConnecting();
            }
        }

        return isConnected;
    }

    public static boolean isNetworkEnabled(Context parentContext){
        return isMobileDataEnabled(parentContext) || isWifiEnabled(parentContext);
    }

    public static boolean isGPSEnabled(Context parentContext){
        LocationManager locationManager = (LocationManager) parentContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}