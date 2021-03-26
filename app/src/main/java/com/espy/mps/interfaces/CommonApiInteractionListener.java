package com.espy.mps.interfaces;

public interface CommonApiInteractionListener {

    void onApiResponseSuccess(String response);

    void onApiResponseFail(String message);
}
