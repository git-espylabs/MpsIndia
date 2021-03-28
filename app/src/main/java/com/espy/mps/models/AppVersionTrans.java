package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersionTrans {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("vcode")
    @Expose
    private String version_code;

    @SerializedName("vname")
    @Expose
    private String version_name;


    public AppVersionTrans(String version_code, String version_name) {
        this.version_code = version_code;
        this.version_name = version_name;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }
}
