package com.espy.mps.app;

import android.util.Log;

import com.espy.mps.BuildConfig;

public class Applog {

    public static String TAG = "MPSLOG";

    public static final int VERBOSE = 1;
    public static final int ERROR = 2;
    public static final int INFO = 3;
    public static final int WARNING = 4;

    public static void logString(String message){
        if (BuildConfig.IS_LOG_ENABLED) {
            logString(TAG, message, INFO);
        }
    }
    public static void logString(String tag, String message){
        if (BuildConfig.DEBUG) {
            logString(tag, message, INFO);
        }
    }

    public static void logString(String tag, String message, int logType){

        if (BuildConfig.IS_LOG_ENABLED){
            switch (logType){
                case VERBOSE:{
                    Log.v(tag, message);
                    break;
                }
                case ERROR:{
                    Log.e(tag, message);
                    break;
                }
                case INFO:{
                    Log.i(tag, message);
                    break;
                }
                case WARNING:{
                    Log.w(tag, message);
                    break;
                }
            }
        }

    }
}
