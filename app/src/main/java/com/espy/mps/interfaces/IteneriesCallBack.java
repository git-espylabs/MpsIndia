package com.espy.mps.interfaces;

import com.espy.mps.models.ActivityTypeTrans;
import com.espy.mps.models.CustomerStatusTrans;

import java.util.ArrayList;

public interface IteneriesCallBack {


    void onActivityTypesDownladed(ArrayList<ActivityTypeTrans> activityTypesList);

    void onCustomerStatusDownladed(ArrayList<CustomerStatusTrans> customerStatusList);

    void onApiErrorResponse(String message, int type);
}
