package com.espy.mps.interfaces;

import com.espy.mps.models.ExpenseTypeTrans;

import java.util.ArrayList;

public interface ExpenseInterfaces {

    void onExpenseTypesDownloaded(ArrayList<ExpenseTypeTrans> expenseList);

    void onExpenseUploaded(String response);

    void onApiResponseError(String message, int type);
}
