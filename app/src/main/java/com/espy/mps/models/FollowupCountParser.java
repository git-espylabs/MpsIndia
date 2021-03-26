package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowupCountParser {

    @SerializedName("next_followup_count")
    @Expose
    private String count;

    public FollowupCountParser(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }
}
