package com.espy.mps.ui.activities;

import android.os.Bundle;

import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseActivity;
import com.espy.mps.interfaces.CustomerCallbacks;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.models.CustomerDetailModelTrans;
import com.espy.mps.models.CustomerListingTrans;
import com.espy.mps.ui.fragments.CustomerMenusFragment;
import com.espy.mps.utils.DialogueUtils;

import java.util.ArrayList;

import static com.espy.mps.utils.FragmentConstants.CUSTOMER_MENU_FRAGMENT;

public class CustomerMenusActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, getResources().getString(R.string.app_name), true);
        enableBottomBar(false);

        setFragment(new CustomerMenusFragment(),CUSTOMER_MENU_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        if (AppSession.isIndividual) {
            moveToScreen(FollowUpListActivity.class, null, true);
        } else {
            moveToScreen(CustomerListingActivity.class, null, true);
        }
    }
}
