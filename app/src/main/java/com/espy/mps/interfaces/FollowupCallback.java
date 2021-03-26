package com.espy.mps.interfaces;

import com.espy.mps.models.ActivityTypeTrans;
import com.espy.mps.models.CustomerStatusTrans;
import com.espy.mps.models.FollowupTrans;
import com.espy.mps.models.IndividualFollowupTrans;

import java.util.ArrayList;

public interface FollowupCallback {
    void onFollowupHistoryDownloaded(ArrayList<FollowupTrans> followupList);

    void onFollowupUploaded(String result);

    void onIndividualFollowupsDownloaded(ArrayList<IndividualFollowupTrans> followupList);

    void onApiErrorResponse(String message, int type);

}
