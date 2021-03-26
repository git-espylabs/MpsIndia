package com.espy.mps.apiutils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponseParser {

    @SerializedName("result")
    @Expose
    private String result;

    public CommonResponseParser(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
