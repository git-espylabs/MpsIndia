package com.espy.mps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExpenseTypeMaster {

    @SerializedName("expense_type")
    @Expose
    private ArrayList<ExpenseTypeTrans> expenseList;

    public ExpenseTypeMaster(ArrayList<ExpenseTypeTrans> expenseList) {
        this.expenseList = expenseList;
    }

    public ArrayList<ExpenseTypeTrans> getExpenseList() {
        return expenseList;
    }
}
