package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerListingTrans {


    @SerializedName("customer_id")
    @Expose
    private String customer_id;

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

    @SerializedName("customer_work_idustryid")
    @Expose
    private String customer_work_idustryid;

    @SerializedName("customer_priority")
    @Expose
    private String customer_priority;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("customer_loginid")
    @Expose
    private String customer_loginid;

    @SerializedName("customer_leadid")
    @Expose
    private String customer_leadid;

    @SerializedName("customer_addedloginid")
    @Expose
    private String customer_addedloginid;

    public CustomerListingTrans(String customer_id, String customer_designation, String customer_primary_contact, String customer_secondary_contact,
                                String customer_email, String customer_location, String customer_work_idustryid, String customer_priority,
                                String customer_name, String customer_loginid, String customer_leadid, String customer_addedloginid) {
        this.customer_id = customer_id;
        this.customer_designation = customer_designation;
        this.customer_primary_contact = customer_primary_contact;
        this.customer_secondary_contact = customer_secondary_contact;
        this.customer_email = customer_email;
        this.customer_location = customer_location;
        this.customer_work_idustryid = customer_work_idustryid;
        this.customer_priority = customer_priority;
        this.customer_name = customer_name;
        this.customer_loginid = customer_loginid;
        this.customer_leadid = customer_leadid;
        this.customer_addedloginid = customer_addedloginid;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_designation() {
        return customer_designation;
    }

    public String getCustomer_primary_contact() {
        return customer_primary_contact;
    }

    public String getCustomer_secondary_contact() {
        return customer_secondary_contact;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getCustomer_location() {
        return customer_location;
    }

    public String getCustomer_work_idustryid() {
        return customer_work_idustryid;
    }

    public String getCustomer_priority() {
        return customer_priority;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_loginid() {
        return customer_loginid;
    }

    public String getCustomer_leadid() {
        return customer_leadid;
    }

    public String getCustomer_addedloginid() {
        return customer_addedloginid;
    }
}
