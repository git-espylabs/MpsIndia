package com.espy.mps.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.espy.mps.R;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.CustomerDetailFragment;
import com.espy.mps.ui.fragments.CustomerListFragment;

import static com.espy.mps.utils.FragmentConstants.CUSTOMER_DETAILS_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.CUSTOMER_LIST_FRAGMENT;

public class CustomerDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, getResources().getString(R.string.app_name), true);
        enableBottomBar(false);

        setFragment(new CustomerDetailFragment(),CUSTOMER_DETAILS_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
//        AppSession.clearCustomerData();
        moveToScreen(CustomerMenusActivity.class, null, true);
    }
}
