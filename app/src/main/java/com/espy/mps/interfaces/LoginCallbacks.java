package com.espy.mps.interfaces;

import com.espy.mps.models.UserMaster;

public interface LoginCallbacks {

    void onLoginSuccess(UserMaster userMaster);

    void onLoginFail(String message);
}
