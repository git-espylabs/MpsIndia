package com.espy.mps.preferences;

import android.content.Context;

public class AppPreference extends BasePreference {

    private final static String PREF_NAME = "app.espy.fiftyninere";

    private final static String PREF_IS_LOGGED_IN = "isLoggedIn";
    private static final String PREF_USER_ID = "pref_user_id";
    private static final String PREF_USER_NAME = "pref_user_name";
    private static final String PREF_U_TYPE = "pref_u_type";
    private static final String PREF_ACCOUNT_NAME = "pref_acccount_name";
    private static final String PREF_ACCOUNT_DESIGNATION = "pref_account_designtaion";
    private static final String PREF_ACCOUNT_MOB = "pref_account_mob";
    private static final String PREF_APP_LATITUDE = "pref_app_latitude";
    private static final String PREF_APP_LONGITUDE = "pref_app_longitude";
    private final static String PREF_IS_PUNCHED_IN = "isPunchedIn";




    public static boolean isLoggedIn(Context context) {
        return getSetting(context,PREF_NAME, PREF_IS_LOGGED_IN, false);
    }
    public static void setIsLoggedIn(Context context, boolean value){
        writeSetting(context,PREF_NAME, PREF_IS_LOGGED_IN, value);
    }

    /*************/

    public static String getAppLatitude(Context context) {
        return getSetting(context,PREF_NAME, PREF_APP_LATITUDE,"0.0");
    }
    public static void setAppLatitude(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_APP_LATITUDE,value);
    }

    /*************/

    public static String getAppLongitude(Context context) {
        return getSetting(context,PREF_NAME, PREF_APP_LONGITUDE,"0.0");
    }
    public static void setAppLongitude(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_APP_LONGITUDE,value);
    }

    /*************/

    public static String getPrefUserId(Context context) {
        return getSetting(context,PREF_NAME, PREF_USER_ID,"0.0");
    }
    public static void setPrefUserId(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_USER_ID,value);
    }

    /*************/

    public static String getPrefUserName(Context context) {
        return getSetting(context,PREF_NAME, PREF_USER_NAME,"");
    }
    public static void setPrefUserName(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_USER_NAME,value);
    }

    /*************/

    public static String getPrefUType(Context context) {
        return getSetting(context,PREF_NAME, PREF_U_TYPE,"");
    }
    public static void setPrefUType(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_U_TYPE,value);
    }

    /*************/

    public static String getPrefAccountName(Context context) {
        return getSetting(context,PREF_NAME, PREF_ACCOUNT_NAME,"");
    }
    public static void setPrefAccountName(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_ACCOUNT_NAME,value);
    }

    /*************/

    public static String getPrefAccountDesignation(Context context) {
        return getSetting(context,PREF_NAME, PREF_ACCOUNT_DESIGNATION,"");
    }
    public static void setPrefAccountDesignation(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_ACCOUNT_DESIGNATION,value);
    }

    /*************/

    public static String getPrefAccountMob(Context context) {
        return getSetting(context,PREF_NAME, PREF_ACCOUNT_MOB,"");
    }
    public static void setPrefAccountMob(Context context, String value){
        writeSetting(context,PREF_NAME, PREF_ACCOUNT_MOB,value);
    }

    /*************/

    public static boolean isPunchedIn(Context context) {
        return getSetting(context,PREF_NAME, PREF_IS_PUNCHED_IN, false);
    }
    public static void setIsPunchedIn(Context context, boolean value){
        writeSetting(context,PREF_NAME, PREF_IS_PUNCHED_IN, value);
    }

    /*************/

}
