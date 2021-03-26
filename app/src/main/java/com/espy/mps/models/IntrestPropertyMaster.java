package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IntrestPropertyMaster {

    @SerializedName("intrestprop_detail")
    @Expose
    private ArrayList<InterestPropertyTrans> interestPropertyList;

    public IntrestPropertyMaster(ArrayList<InterestPropertyTrans> interestPropertyList) {
        this.interestPropertyList = interestPropertyList;
    }

    public ArrayList<InterestPropertyTrans> getInterestPropertyList() {
        return interestPropertyList;
    }
}
