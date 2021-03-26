package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.VehicleDetailsFragment;

import static com.espy.mps.utils.FragmentConstants.VEHICLE_DETAILS_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.WORK_DETAILS_FRAGMENT;

public class VehicleDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Vehicle Details", true);
        enableBottomBar(false);

        setFragment(new VehicleDetailsFragment(),VEHICLE_DETAILS_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class, null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VehicleDetailsFragment fragment = (VehicleDetailsFragment ) getSupportFragmentManager().findFragmentByTag(VEHICLE_DETAILS_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
