package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectNameListTrans {

    @SerializedName("project_id")
    @Expose
    private String project_id;

    @SerializedName("project_name")
    @Expose
    private String project_name;

    public ProjectNameListTrans(String project_id, String project_name) {
        this.project_id = project_id;
        this.project_name = project_name;
    }

    public String getProject_id() {
        return project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    @NonNull
    @Override
    public String toString() {
        return getProject_name();
    }
}
