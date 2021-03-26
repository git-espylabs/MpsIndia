package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IndividualFollowupSearchMaster {

    @SerializedName("nextfdate")
    @Expose
    private ArrayList<IndividualFollowupTrans> followUpList;


    public IndividualFollowupSearchMaster(ArrayList<IndividualFollowupTrans> followUpList) {
        this.followUpList = followUpList;
    }

    public ArrayList<IndividualFollowupTrans> getFollowUpList() {
        return followUpList;
    }
}
