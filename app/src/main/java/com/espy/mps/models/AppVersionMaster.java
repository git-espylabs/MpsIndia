package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AppVersionMaster {

    @SerializedName("version_det")
    @Expose
    private ArrayList<AppVersionTrans> appVersionTrans;

    public ArrayList<AppVersionTrans> getAppVersionTrans() {
        return appVersionTrans;
    }
}
