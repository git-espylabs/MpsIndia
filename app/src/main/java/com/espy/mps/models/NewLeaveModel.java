package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewLeaveModel {

    private String leave_from_date;

    private String leave_todate;

    private String leave_count;

    private String leave_title;

    private String leave_desc;

    public NewLeaveModel(String leave_from_date, String leave_todate, String leave_count, String leave_title, String leave_desc) {
        this.leave_from_date = leave_from_date;
        this.leave_todate = leave_todate;
        this.leave_count = leave_count;
        this.leave_title = leave_title;
        this.leave_desc = leave_desc;
    }

    public String getLeave_from_date() {
        return leave_from_date;
    }

    public String getLeave_todate() {
        return leave_todate;
    }

    public String getLeave_count() {
        return leave_count;
    }

    public String getLeave_title() {
        return leave_title;
    }

    public String getLeave_desc() {
        return leave_desc;
    }
}
