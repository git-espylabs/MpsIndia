package com.espy.mps.models;

public class NewLeadModel {

    String customer_name = "";
    String customer_designation = "";
    String primary_contact = "";
    String secondary_contact = "";
    String customer_email = "";
    String customer_location = "";
    String customer_workindus = "";
    String customer_priority = "";
    String interested_prop = "";
    String interested_comment = "";
    String customer_proploc = "";
    String cusprop_budget = "";
    String custprop_comment = "";
    String custprop_project = "";
    String lead_fromid = "";
    String lead_comment = "";
    String lead_date = "";


    public NewLeadModel(String customer_name, String customer_designation, String primary_contact, String secondary_contact, String customer_email,
                        String customer_location, String customer_workindus, String customer_priority, String interested_prop,
                        String interested_comment, String customer_proploc, String cusprop_budget, String custprop_comment, String custprop_project,
                        String lead_fromid, String lead_comment, String lead_date) {
        this.customer_name = customer_name;
        this.customer_designation = customer_designation;
        this.primary_contact = primary_contact;
        this.secondary_contact = secondary_contact;
        this.customer_email = customer_email;
        this.customer_location = customer_location;
        this.customer_workindus = customer_workindus;
        this.customer_priority = customer_priority;
        this.interested_prop = interested_prop;
        this.interested_comment = interested_comment;
        this.customer_proploc = customer_proploc;
        this.cusprop_budget = cusprop_budget;
        this.custprop_comment = custprop_comment;
        this.custprop_project = custprop_project;
        this.lead_fromid = lead_fromid;
        this.lead_comment = lead_comment;
        this.lead_date = lead_date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_designation() {
        return customer_designation;
    }

    public void setCustomer_designation(String customer_designation) {
        this.customer_designation = customer_designation;
    }

    public String getPrimary_contact() {
        return primary_contact;
    }

    public void setPrimary_contact(String primary_contact) {
        this.primary_contact = primary_contact;
    }

    public String getSecondary_contact() {
        return secondary_contact;
    }

    public void setSecondary_contact(String secondary_contact) {
        this.secondary_contact = secondary_contact;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_location() {
        return customer_location;
    }

    public void setCustomer_location(String customer_location) {
        this.customer_location = customer_location;
    }

    public String getCustomer_workindus() {
        return customer_workindus;
    }

    public void setCustomer_workindus(String customer_workindus) {
        this.customer_workindus = customer_workindus;
    }

    public String getCustomer_priority() {
        return customer_priority;
    }

    public void setCustomer_priority(String customer_priority) {
        this.customer_priority = customer_priority;
    }

    public String getInterested_prop() {
        return interested_prop;
    }

    public void setInterested_prop(String interested_prop) {
        this.interested_prop = interested_prop;
    }

    public String getInterested_comment() {
        return interested_comment;
    }

    public void setInterested_comment(String interested_comment) {
        this.interested_comment = interested_comment;
    }

    public String getCustomer_proploc() {
        return customer_proploc;
    }

    public void setCustomer_proploc(String customer_proploc) {
        this.customer_proploc = customer_proploc;
    }

    public String getCusprop_budget() {
        return cusprop_budget;
    }

    public void setCusprop_budget(String cusprop_budget) {
        this.cusprop_budget = cusprop_budget;
    }

    public String getCustprop_comment() {
        return custprop_comment;
    }

    public void setCustprop_comment(String custprop_comment) {
        this.custprop_comment = custprop_comment;
    }

    public String getCustprop_project() {
        return custprop_project;
    }

    public void setCustprop_project(String custprop_project) {
        this.custprop_project = custprop_project;
    }

    public String getLead_fromid() {
        return lead_fromid;
    }

    public void setLead_fromid(String lead_fromid) {
        this.lead_fromid = lead_fromid;
    }

    public String getLead_comment() {
        return lead_comment;
    }

    public void setLead_comment(String lead_comment) {
        this.lead_comment = lead_comment;
    }

    public String getLead_date() {
        return lead_date;
    }

    public void setLead_date(String lead_date) {
        this.lead_date = lead_date;
    }
}



