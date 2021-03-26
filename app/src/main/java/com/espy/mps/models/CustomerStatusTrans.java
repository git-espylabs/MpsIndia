package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerStatusTrans {

    @SerializedName("cstmr_statusid")
    @Expose
    private String cstmr_statusid;

    @SerializedName("cstmr_status_name")
    @Expose
    private String cstmr_status_name;

    @NonNull
    @Override
    public String toString() {
        return getCstmr_status_name();
    }

    public CustomerStatusTrans(String cstmr_statusid, String cstmr_status_name) {
        this.cstmr_statusid = cstmr_statusid;
        this.cstmr_status_name = cstmr_status_name;
    }

    public String getCstmr_statusid() {
        return cstmr_statusid;
    }

    public String getCstmr_status_name() {
        return cstmr_status_name;
    }
}
