package com.espy.mps.interfaces;

public interface HomeIteneriesCallback {

    void onFollowupCountGot(String count);

    void onApiErrorResponse(String mesage, int type);
}
