package com.espy.mps.interfaces;

import com.espy.mps.models.CustomerDetailModelTrans;
import com.espy.mps.models.CustomerListingTrans;

import java.util.ArrayList;

public interface CustomerCallbacks {

    void onCustomerListDownloaded(ArrayList<CustomerListingTrans> customerList);

    void onCustomerDetailsDownloaded(ArrayList<CustomerDetailModelTrans> customerDetails);

    void onApiResponseError(String message, int type);
}
