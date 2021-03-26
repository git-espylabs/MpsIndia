package com.espy.mps.app;

import android.location.Location;

import com.espy.mps.models.CustomerDetailModelTrans;

public class AppSession {

    public static Location location = null;
    public static String customer_id = "0";
    public static String customer_name = "";
    public static String customer_lead_id = "";
    public static boolean isIndividual = false;

    private static CustomerDetailModelTrans customerDetailsObject;

    public static CustomerDetailModelTrans getCustomerDetailsObject() {
        return customerDetailsObject;
    }

    public static void setCustomerDetailsObject(CustomerDetailModelTrans customerDetailsObject) {
        AppSession.customerDetailsObject = customerDetailsObject;
    }

    public static void clearCustomerData(){
        AppSession.customerDetailsObject = null;
        customer_id = "0";
        customer_name = "";
        customer_lead_id = "";
    }
}
