package com.espy.mps.interfaces;

import com.espy.mps.models.CustomerPriorityTrans;
import com.espy.mps.models.CustomerPropertyDetailTrans;
import com.espy.mps.models.InterestPropertyTrans;
import com.espy.mps.models.LeadFromDetailTrans;
import com.espy.mps.models.WorkIndustryDetailsTrans;

import java.util.ArrayList;

public interface LeadIteneriesCallback {


    void onIntrestPropertyDetailsDownloaded(ArrayList<InterestPropertyTrans> interestPropertyList);

    void onLeadFromDetailsDownloaded(ArrayList<LeadFromDetailTrans> leadFromDetailsList);

    void onWorkIndustryDetailsDownloaded(ArrayList<WorkIndustryDetailsTrans> workIndustryDetailsList);

    void onCustomerPropertyDetailsDownloaded(ArrayList<CustomerPropertyDetailTrans> customerPropertyDetailsList);

    void onustomerPriorityDownloaded(ArrayList<CustomerPriorityTrans> customerPrioritiesList);

    void onApiErrorResponse(String message, int type);
}
