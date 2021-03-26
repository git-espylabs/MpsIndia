package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerDetailModelMaster {

    @SerializedName("customer_prop_det")
    @Expose
    private ArrayList<CustomerDetailModelTrans> customerPropDetailList;

    public CustomerDetailModelMaster(ArrayList<CustomerDetailModelTrans> customerPropDetailList) {
        this.customerPropDetailList = customerPropDetailList;
    }

    public ArrayList<CustomerDetailModelTrans> getCustomerPropDetailList() {
        return customerPropDetailList;
    }
}
