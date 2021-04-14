package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.ExpenseFragment;

import static com.espy.mps.utils.FragmentConstants.EXPENSE_FRAGMENT;

public class ExpenseActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Expense", true);
        enableBottomBar(false);

        setFragment(new ExpenseFragment(),EXPENSE_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class,null, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ExpenseFragment fragment = (ExpenseFragment ) getSupportFragmentManager().findFragmentByTag(EXPENSE_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
