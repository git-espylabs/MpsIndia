package com.espy.mps.interfaces;

import com.espy.mps.models.ProjectNameListTrans;

import java.util.ArrayList;

public interface WorkDetailsCallBack {

    void onDetailsUploadSuccess(String response);

    void onProjectListDownladed(ArrayList<ProjectNameListTrans> projectList);

    void onApiErrorResponse(String message, int type);
}
