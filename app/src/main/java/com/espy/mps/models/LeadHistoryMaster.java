package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeadHistoryMaster {

    @SerializedName("lead_detail")
    @Expose
    private ArrayList<LeadHistoryTrans> leadList;

    public LeadHistoryMaster(ArrayList<LeadHistoryTrans> leadList) {
        this.leadList = leadList;
    }

    public ArrayList<LeadHistoryTrans> getLeadList() {
        return leadList;
    }
}
