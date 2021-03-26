package com.espy.mps.interfaces;

import com.espy.mps.models.ActivityTypeTrans;
import com.espy.mps.models.CustomerStatusTrans;
import com.espy.mps.models.FeedbackTrans;

import java.util.ArrayList;

public interface FeedbackCallback {
    void onFeedbackHistoryDownloaded(ArrayList<FeedbackTrans> feedbackList);

    void onFeedbackUploaded(String result);

    void onApiErrorResponse(String message, int type);
}
