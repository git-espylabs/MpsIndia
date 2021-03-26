package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailModelTrans {


    @SerializedName("customer_designation")
    @Expose
    private String customer_designation;

    @SerializedName("customer_primary_contact")
    @Expose
    private String customer_primary_contact;

    @SerializedName("customer_secondary_contact")
    @Expose
    private String customer_secondary_contact;

    @SerializedName("customer_email")
    @Expose
    private String customer_email;

    @SerializedName("customer_location")
    @Expose
    private String customer_location;

    @SerializedName("wrin_name")
    @Expose
    private String wrin_name;

    @SerializedName("customer_priority")
    @Expose
    private String customer_priority;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("lead_details_date")
    @Expose
    private String lead_details_date;

    @SerializedName("lead_details_comments")
    @Expose
    private String lead_details_comments;

    @SerializedName("cstmr_property_requirements_intrest_loc")
    @Expose
    private String cstmr_property_requirements_intrest_loc;

    @SerializedName("cstmr_property_requirements_budget")
    @Expose
    private String cstmr_property_requirements_budget;

    @SerializedName("cstmr_property_requirements_comments")
    @Expose
    private String cstmr_property_requirements_comments;

    @SerializedName("cstmr_property_requirements_type_of_propid")
    @Expose
    private String cstmr_property_requirements_type_of_propid;

    @SerializedName("project_name")
    @Expose
    private String project_name;

    @SerializedName("our_intrest_property_comment")
    @Expose
    private String our_intrest_property_comment;


    public CustomerDetailModelTrans(String customer_designation, String customer_primary_contact, String customer_secondary_contact,
                                    String customer_email, String customer_location, String wrin_name, String customer_priority,
                                    String customer_name, String lead_details_date, String lead_details_comments,
                                    String cstmr_property_requirements_intrest_loc, String cstmr_property_requirements_budget,
                                    String cstmr_property_requirements_comments, String cstmr_property_requirements_type_of_propid,
                                    String project_name, String our_intrest_property_comment) {
        this.customer_designation = customer_designation;
        this.customer_primary_contact = customer_primary_contact;
        this.customer_secondary_contact = customer_secondary_contact;
        this.customer_email = customer_email;
        this.customer_location = customer_location;
        this.wrin_name = wrin_name;
        this.customer_priority = customer_priority;
        this.customer_name = customer_name;
        this.lead_details_date = lead_details_date;
        this.lead_details_comments = lead_details_comments;
        this.cstmr_property_requirements_intrest_loc = cstmr_property_requirements_intrest_loc;
        this.cstmr_property_requirements_budget = cstmr_property_requirements_budget;
        this.cstmr_property_requirements_comments = cstmr_property_requirements_comments;
        this.cstmr_property_requirements_type_of_propid = cstmr_property_requirements_type_of_propid;
        this.project_name = project_name;
        this.our_intrest_property_comment = our_intrest_property_comment;
    }

    public String getCustomer_designation() {
        return (customer_designation != null) ? customer_designation : "";
    }

    public String getCustomer_primary_contact() {
        return (customer_primary_contact != null) ? customer_primary_contact : "";
    }

    public String getCustomer_secondary_contact() {
        return (customer_secondary_contact != null) ? customer_secondary_contact : "";
    }

    public String getCustomer_email() {
        return (customer_email != null) ? customer_email : "";
    }

    public String getCustomer_location() {
        return (customer_location != null) ? customer_location : "";
    }

    public String getWrin_name() {
        return (wrin_name != null) ? wrin_name : "";
    }

    public String getCustomer_priority() {
        return (customer_priority != null) ? customer_priority : "";
    }

    public String getCustomer_name() {
        return (customer_name != null) ? customer_name : "";
    }

    public String getLead_details_date() {
        return (lead_details_date != null) ? lead_details_date : "";
    }

    public String getLead_details_comments() {
        return (lead_details_comments != null) ? lead_details_comments : "";
    }

    public String getCstmr_property_requirements_intrest_loc() {
        return (cstmr_property_requirements_intrest_loc != null) ? cstmr_property_requirements_intrest_loc : "";
    }

    public String getCstmr_property_requirements_budget() {
        return (cstmr_property_requirements_budget != null) ? cstmr_property_requirements_budget : "";
    }

    public String getCstmr_property_requirements_comments() {
        return (cstmr_property_requirements_comments != null) ? cstmr_property_requirements_comments : "";
    }

    public String getCstmr_property_requirements_type_of_propid() {
        return (cstmr_property_requirements_type_of_propid != null) ? cstmr_property_requirements_type_of_propid : "";
    }

    public String getProject_name() {
        return (project_name != null) ? project_name : "";
    }

    public String getOur_intrest_property_comment() {
        return (our_intrest_property_comment != null) ? our_intrest_property_comment : "";
    }
}
