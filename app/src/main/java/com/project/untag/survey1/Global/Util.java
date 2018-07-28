package com.project.untag.survey1.Global;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ikko on 11/05/2018.
 */

public class Util {

    public static boolean isConnectingToInternet(Context context){
        boolean isConnectedToWifi = false;
        boolean isConnectedToMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null){
            if(netInfo.isAvailable()){
                if(netInfo.isConnected()){
                    if(netInfo.getType() == ConnectivityManager.TYPE_WIFI){
                        isConnectedToWifi = true;
                    }else if(netInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                        isConnectedToMobile = true;
                    }
                }
            }
        }

        return isConnectedToMobile || isConnectedToWifi;
    }
}
