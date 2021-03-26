package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.CustomerListFragment;
import com.espy.mps.ui.fragments.NotesFragment;

import static com.espy.mps.utils.FragmentConstants.CUSTOMER_LIST_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.NOTES_FRAGMENT;

public class CustomerListingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Customers", true);
        enableBottomBar(false);

        setFragment(new CustomerListFragment(),CUSTOMER_LIST_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class,null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CustomerListFragment fragment = (CustomerListFragment) getSupportFragmentManager().findFragmentByTag(CUSTOMER_LIST_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
