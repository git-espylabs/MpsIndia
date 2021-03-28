package com.espy.mps.interfaces;

import com.espy.mps.models.AppVersionMaster;
import com.espy.mps.models.AppVersionTrans;

import java.util.ArrayList;

public interface AppVersionCallback {

    void onAppVersionReceived(AppVersionTrans version);

    void onApiErrorResponse(String message);
}
