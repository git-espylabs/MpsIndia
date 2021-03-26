package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WorkIndustryDetailMaster {

    @SerializedName("workindustry_detail")
    @Expose
    private ArrayList<WorkIndustryDetailsTrans> workIndustryDetailsList;

    public WorkIndustryDetailMaster(ArrayList<WorkIndustryDetailsTrans> workIndustryDetailsList) {
        this.workIndustryDetailsList = workIndustryDetailsList;
    }

    public ArrayList<WorkIndustryDetailsTrans> getWorkIndustryDetailsList() {
        return workIndustryDetailsList;
    }
}
