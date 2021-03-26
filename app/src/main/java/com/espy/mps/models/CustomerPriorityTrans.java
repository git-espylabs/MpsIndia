package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerPriorityTrans {


    @SerializedName("customer_priority_id")
    @Expose
    private String customer_priority_id;

    @SerializedName("customer_priority_name")
    @Expose
    private String customer_priority_name;

    public CustomerPriorityTrans(String customer_priority_id, String customer_priority_name) {
        this.customer_priority_id = customer_priority_id;
        this.customer_priority_name = customer_priority_name;
    }

    public String getCustomer_priority_id() {
        return customer_priority_id;
    }

    public String getCustomer_priority_name() {
        return customer_priority_name;
    }

    @NonNull
    @Override
    public String toString() {
        return getCustomer_priority_name();
    }
}
