package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.LeadHistoryFragment;

import static com.espy.mps.utils.FragmentConstants.LEAD_HISTORY_FRAGMENT;

public class LeadHistoryActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Leads", true);
        enableBottomBar(false);

        setFragment(new LeadHistoryFragment(),LEAD_HISTORY_FRAGMENT,null);

        AppSession.customer_id = getIntent().getStringExtra("cid");

    }

    @Override
    public void onBackPressed() {
        moveToScreen(CustomerListingActivity.class,null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LeadHistoryFragment fragment = (LeadHistoryFragment) getSupportFragmentManager().findFragmentByTag(LEAD_HISTORY_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
