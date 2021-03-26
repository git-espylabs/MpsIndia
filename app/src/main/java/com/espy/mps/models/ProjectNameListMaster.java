package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProjectNameListMaster {

    @SerializedName("project_det")
    @Expose
    private ArrayList<ProjectNameListTrans> projectList;

    public ProjectNameListMaster(ArrayList<ProjectNameListTrans> projectList) {
        this.projectList = projectList;
    }

    public ArrayList<ProjectNameListTrans> getProjectList() {
        return projectList;
    }
}
