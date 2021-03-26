package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowupTrans {


    @SerializedName("lead_followup_id")
    @Expose
    private String lead_followup_id;


    @SerializedName("customername")
    @Expose
    private String lead_followup_cstmr_id;


    @SerializedName("activity_type")
    @Expose
    private String lead_followup_activity_typeid;


    @SerializedName("lead_followup_date")
    @Expose
    private String lead_followup_date;


    @SerializedName("lead_followup_time")
    @Expose
    private String lead_followup_time;


    @SerializedName("lead_followup_expected_closing_date")
    @Expose
    private String lead_followup_expected_closing_date;


    @SerializedName("lead_followup_leadid")
    @Expose
    private String lead_followup_leadid;


    @SerializedName("lead_followup_remarks")
    @Expose
    private String lead_followup_remarks;


    public FollowupTrans(String lead_followup_id, String lead_followup_cstmr_id, String lead_followup_activity_typeid, String lead_followup_date, String lead_followup_time, String lead_followup_expected_closing_date, String lead_followup_leadid, String lead_followup_remarks) {
        this.lead_followup_id = lead_followup_id;
        this.lead_followup_cstmr_id = lead_followup_cstmr_id;
        this.lead_followup_activity_typeid = lead_followup_activity_typeid;
        this.lead_followup_date = lead_followup_date;
        this.lead_followup_time = lead_followup_time;
        this.lead_followup_expected_closing_date = lead_followup_expected_closing_date;
        this.lead_followup_leadid = lead_followup_leadid;
        this.lead_followup_remarks = lead_followup_remarks;
    }

    public String getLead_followup_id() {
        return lead_followup_id;
    }

    public String getLead_followup_cstmr_id() {
        return lead_followup_cstmr_id;
    }

    public String getLead_followup_activity_typeid() {
        return lead_followup_activity_typeid;
    }

    public String getLead_followup_date() {
        return lead_followup_date;
    }

    public String getLead_followup_time() {
        return lead_followup_time;
    }

    public String getLead_followup_expected_closing_date() {
        return lead_followup_expected_closing_date;
    }

    public String getLead_followup_leadid() {
        return lead_followup_leadid;
    }

    public String getLead_followup_remarks() {
        return lead_followup_remarks;
    }
}
