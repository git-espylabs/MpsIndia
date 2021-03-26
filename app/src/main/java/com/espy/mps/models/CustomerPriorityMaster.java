package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerPriorityMaster {

    @SerializedName("customer_priority_detail")
    @Expose
    private ArrayList<CustomerPriorityTrans> customerPrioritiesList;

    public CustomerPriorityMaster(ArrayList<CustomerPriorityTrans> customerPrioritiesList) {
        this.customerPrioritiesList = customerPrioritiesList;
    }

    public ArrayList<CustomerPriorityTrans> getCustomerPrioritiesList() {
        return customerPrioritiesList;
    }
}
