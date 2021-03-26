package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserMaster {

    @SerializedName("login_det")
    @Expose
    private ArrayList<UserTrans> login_det;

    public UserMaster(ArrayList<UserTrans> login_det) {
        this.login_det = login_det;
    }

    public ArrayList<UserTrans> getLogin_det() {
        return login_det;
    }
}
