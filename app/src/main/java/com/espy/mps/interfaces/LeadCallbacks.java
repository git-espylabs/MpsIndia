package com.espy.mps.interfaces;

import com.espy.mps.models.LeadHistoryTrans;

import java.util.ArrayList;

public interface LeadCallbacks {
    void onLeadUploaded(String result);

    void onLeadHistoryDwnloaded(ArrayList<LeadHistoryTrans> leadList);

    void onApiErrorResponse(String message, int type);
}
