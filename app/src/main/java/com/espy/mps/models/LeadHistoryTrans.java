package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadHistoryTrans {


    @SerializedName("lead_details_id")
    @Expose
    private String lead_details_id;


    @SerializedName("lead_details_from_id")
    @Expose
    private String lead_details_from_id;


    @SerializedName("lead_details_date")
    @Expose
    private String lead_details_date;


    @SerializedName("lead_details_comments")
    @Expose
    private String lead_details_comments;


    @SerializedName("lead_details_customer_id")
    @Expose
    private String lead_details_customer_id;


    public LeadHistoryTrans(String lead_details_id, String lead_details_from_id, String lead_details_date, String lead_details_comments, String lead_details_customer_id) {
        this.lead_details_id = lead_details_id;
        this.lead_details_from_id = lead_details_from_id;
        this.lead_details_date = lead_details_date;
        this.lead_details_comments = lead_details_comments;
        this.lead_details_customer_id = lead_details_customer_id;
    }

    public String getLead_details_id() {
        return lead_details_id;
    }

    public String getLead_details_from_id() {
        return lead_details_from_id;
    }

    public String getLead_details_date() {
        return lead_details_date;
    }

    public String getLead_details_comments() {
        return lead_details_comments;
    }

    public String getLead_details_customer_id() {
        return lead_details_customer_id;
    }
}
