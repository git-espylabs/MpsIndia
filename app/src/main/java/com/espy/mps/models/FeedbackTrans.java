package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackTrans {

    @SerializedName("lead_feedback_id")
    @Expose
    private String lead_feedback_id;

    @SerializedName("lead_feedback_addeddate")
    @Expose
    private String lead_feedback_addeddate;

    @SerializedName("lead_feedback_current_status")
    @Expose
    private String lead_feedback_current_status;

    @SerializedName("lead_feedback_leadid")
    @Expose
    private String lead_feedback_leadid;

    @SerializedName("lead_feedback_remarks")
    @Expose
    private String lead_feedback_remarks;

    @SerializedName("customername")
    @Expose
    private String lead_feedback_customer_id;

    @SerializedName("activity_type")
    @Expose
    private String lead_feedback_activity_typeid;

    public FeedbackTrans(String lead_feedback_id, String lead_feedback_addeddate, String lead_feedback_current_status,
                         String lead_feedback_leadid, String lead_feedback_remarks, String lead_feedback_customer_id,
                         String lead_feedback_activity_typeid) {
        this.lead_feedback_id = lead_feedback_id;
        this.lead_feedback_addeddate = lead_feedback_addeddate;
        this.lead_feedback_current_status = lead_feedback_current_status;
        this.lead_feedback_leadid = lead_feedback_leadid;
        this.lead_feedback_remarks = lead_feedback_remarks;
        this.lead_feedback_customer_id = lead_feedback_customer_id;
        this.lead_feedback_activity_typeid = lead_feedback_activity_typeid;
    }

    public String getLead_feedback_id() {
        return lead_feedback_id;
    }

    public String getLead_feedback_addeddate() {
        return lead_feedback_addeddate;
    }

    public String getLead_feedback_current_status() {
        return lead_feedback_current_status;
    }

    public String getLead_feedback_leadid() {
        return lead_feedback_leadid;
    }

    public String getLead_feedback_remarks() {
        return lead_feedback_remarks;
    }

    public String getLead_feedback_customer_id() {
        return lead_feedback_customer_id;
    }

    public String getLead_feedback_activity_typeid() {
        return lead_feedback_activity_typeid;
    }
}
