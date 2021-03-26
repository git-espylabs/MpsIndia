package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerStatusMaster {

    @SerializedName("lead_customer_status")
    @Expose
    private ArrayList<CustomerStatusTrans> customerStatusList;

    public CustomerStatusMaster(ArrayList<CustomerStatusTrans> customerStatusList) {
        this.customerStatusList = customerStatusList;
    }

    public ArrayList<CustomerStatusTrans> getCustomerStatusList() {
        return customerStatusList;
    }
}
