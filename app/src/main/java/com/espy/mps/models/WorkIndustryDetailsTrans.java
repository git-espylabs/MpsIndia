package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkIndustryDetailsTrans {


    @SerializedName("wrin_id")
    @Expose
    private String wrin_id;

    @SerializedName("wrin_name")
    @Expose
    private String wrin_name;

    public WorkIndustryDetailsTrans(String wrin_id, String wrin_name) {
        this.wrin_id = wrin_id;
        this.wrin_name = wrin_name;
    }

    public String getWrin_id() {
        return wrin_id;
    }

    public String getWrin_name() {
        return wrin_name;
    }

    @NonNull
    @Override
    public String toString() {
        return getWrin_name();
    }
}
