package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedbackMaster {

    @SerializedName("customer_feedbak_det")
    @Expose
    private ArrayList<FeedbackTrans> feedbackList;

    public FeedbackMaster(ArrayList<FeedbackTrans> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public ArrayList<FeedbackTrans> getFeedbackList() {
        return feedbackList;
    }
}
