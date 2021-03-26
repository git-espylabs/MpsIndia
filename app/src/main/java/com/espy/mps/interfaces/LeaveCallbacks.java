package com.espy.mps.interfaces;

public interface LeaveCallbacks {

    void onLeaveUploaded(String result);

    void onApiErrorResponse(String message);
}
