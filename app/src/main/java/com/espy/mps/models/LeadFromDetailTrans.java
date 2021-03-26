package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadFromDetailTrans {


    @SerializedName("ldty_id")
    @Expose
    private String ldty_id;

    @SerializedName("ldty_name")
    @Expose
    private String ldty_name;

    public LeadFromDetailTrans(String ldty_id, String ldty_name) {
        this.ldty_id = ldty_id;
        this.ldty_name = ldty_name;
    }

    public String getLdty_id() {
        return ldty_id;
    }

    public String getLdty_name() {
        return ldty_name;
    }

    @NonNull
    @Override
    public String toString() {
        return getLdty_name();
    }
}
