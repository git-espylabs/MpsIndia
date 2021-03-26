package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AcitivityTypeMaster {

    @SerializedName("lead_activity_type")
    @Expose
    private ArrayList<ActivityTypeTrans> activityTypesList;

    public AcitivityTypeMaster(ArrayList<ActivityTypeTrans> activityTypesList) {
        this.activityTypesList = activityTypesList;
    }

    public ArrayList<ActivityTypeTrans> getActivityTypesList() {
        return activityTypesList;
    }
}
