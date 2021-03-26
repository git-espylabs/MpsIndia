package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTrans {
    @SerializedName("id")
    @Expose
    private String userid;

    @SerializedName("uname")
    @Expose
    private String uname;


    @SerializedName("u_type")
    @Expose
    private String u_type;


    @SerializedName("account_name")
    @Expose
    private String account_name;


    @SerializedName("account_designation")
    @Expose
    private String account_designation;


    @SerializedName("account_mob")
    @Expose
    private String account_mob;



    public UserTrans(String userid, String uname, String u_type, String account_name, String account_designation, String account_mob) {
        this.userid = userid;
        this.uname = uname;
        this.u_type = u_type;
        this.account_name = account_name;
        this.account_designation = account_designation;
        this.account_mob = account_mob;
    }

    public String getUserid() {
        return userid;
    }

    public String getUname() {
        return uname;
    }

    public String getU_type() {
        return u_type;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getAccount_designation() {
        return account_designation;
    }

    public String getAccount_mob() {
        return account_mob;
    }


}
