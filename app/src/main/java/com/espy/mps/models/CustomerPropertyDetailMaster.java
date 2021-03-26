package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerPropertyDetailMaster {

    @SerializedName("customerprop_detail")
    @Expose
    private ArrayList<CustomerPropertyDetailTrans> customerPropertyDetailsList;

    public CustomerPropertyDetailMaster(ArrayList<CustomerPropertyDetailTrans> customerPropertyDetailsList) {
        this.customerPropertyDetailsList = customerPropertyDetailsList;
    }

    public ArrayList<CustomerPropertyDetailTrans> getCustomerPropertyDetailsList() {
        return customerPropertyDetailsList;
    }
}
