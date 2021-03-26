package com.espy.mps.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressWarnings("ALL")
public class NetworkUtils {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_WIFI = 1;
    public static final int NETWORK_STATUS_MOBILE = 2;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI && activeNetwork.isConnectedOrConnecting())
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE&& activeNetwork.isConnectedOrConnecting())
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        int status = 0;
        if (conn == TYPE_WIFI) {
            status = NETWORK_STATUS_WIFI;
        } else if (conn == TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }

    public static boolean isNetworkConnected(Context context) {
        int conn = getConnectivityStatus(context);
        int status = 0;
        if (conn == TYPE_WIFI | conn == TYPE_MOBILE) {
            return true;
        }else {
            return false;
        }
    }
}

