package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeadFromDetailsMaster {

    @SerializedName("leadfrom_detail")
    @Expose
    private ArrayList<LeadFromDetailTrans> leadFromDetailsList;

    public LeadFromDetailsMaster(ArrayList<LeadFromDetailTrans> leadFromDetailsList) {
        this.leadFromDetailsList = leadFromDetailsList;
    }

    public ArrayList<LeadFromDetailTrans> getLeadFromDetailsList() {
        return leadFromDetailsList;
    }
}
