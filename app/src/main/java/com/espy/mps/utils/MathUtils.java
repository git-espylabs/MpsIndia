package com.espy.mps.utils;

public class MathUtils {

    public static double parseDoubleValue(String stringValue){
        double retValue = 0.00;
        try {
            retValue = (stringValue != null) ? Double.parseDouble(stringValue) : 0.00;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }

    public static int parseIntValue(String stringValue){
        int retValue = 0;
        try {
            retValue = (stringValue != null && stringValue.length()>0) ? Integer.parseInt(stringValue) : 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }

    public static String doubleToString(double doubleValue){
        String retVal = "0.0";

        try {
            retVal = String.valueOf(doubleValue);
        } catch (Exception e) {
            e.printStackTrace();
            retVal = "0.0";
        }

        return retVal;
    }
}
