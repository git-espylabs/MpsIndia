package com.espy.mps.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseTypeTrans {


    @SerializedName("expense_type_id")
    @Expose
    private String expense_type_id;

    @SerializedName("expense_type_name")
    @Expose
    private String expense_type_name;

    public ExpenseTypeTrans(String expense_type_id, String expense_type_name) {
        this.expense_type_id = expense_type_id;
        this.expense_type_name = expense_type_name;
    }

    public String getExpense_type_id() {
        return expense_type_id;
    }

    public String getExpense_type_name() {
        return expense_type_name;
    }

    @NonNull
    @Override
    public String toString() {
        return getExpense_type_name();
    }
}
