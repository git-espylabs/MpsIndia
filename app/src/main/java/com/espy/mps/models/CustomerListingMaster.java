package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerListingMaster {

    @SerializedName("customer_detail")
    @Expose
    private ArrayList<CustomerListingTrans> customerList;

    public CustomerListingMaster(ArrayList<CustomerListingTrans> customerList) {
        this.customerList = customerList;
    }

    public ArrayList<CustomerListingTrans> getCustomerList() {
        return customerList;
    }
}
