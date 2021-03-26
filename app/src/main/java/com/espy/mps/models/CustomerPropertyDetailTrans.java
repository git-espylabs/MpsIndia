package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerPropertyDetailTrans {


    @SerializedName("cus_need_prop_id")
    @Expose
    private String cus_need_prop_id;

    @SerializedName("cus_need_prop_name")
    @Expose
    private String cus_need_prop_name;

    public CustomerPropertyDetailTrans(String cus_need_prop_id, String cus_need_prop_name) {
        this.cus_need_prop_id = cus_need_prop_id;
        this.cus_need_prop_name = cus_need_prop_name;
    }

    public String getCus_need_prop_id() {
        return cus_need_prop_id;
    }

    public String getCus_need_prop_name() {
        return cus_need_prop_name;
    }

    @NonNull
    @Override
    public String toString() {
        return getCus_need_prop_name();
    }
}
