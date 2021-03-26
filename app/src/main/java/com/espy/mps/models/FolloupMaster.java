package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FolloupMaster {

    @SerializedName("customer_followup_det")
    @Expose
    private ArrayList<FollowupTrans> folowUpList;

    public FolloupMaster(ArrayList<FollowupTrans> folowUpList) {
        this.folowUpList = folowUpList;
    }

    public ArrayList<FollowupTrans> getFolowUpList() {
        return folowUpList;
    }
}
