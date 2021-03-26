package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityTypeTrans {

    @SerializedName("activity_type_id")
    @Expose
    private String activity_type_id;

    @SerializedName("activity_type_name")
    @Expose
    private String activity_type_name;

    @NonNull
    @Override
    public String toString() {
        return getActivity_type_name();
    }

    public ActivityTypeTrans(String activity_type_id, String activity_type_name) {
        this.activity_type_id = activity_type_id;
        this.activity_type_name = activity_type_name;
    }

    public String getActivity_type_id() {
        return activity_type_id;
    }

    public String getActivity_type_name() {
        return activity_type_name;
    }
}
