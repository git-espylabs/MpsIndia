package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.MaterialDetailsFragmennt2;
import com.espy.mps.ui.fragments.PurchaseBillFragment;

import static com.espy.mps.utils.FragmentConstants.MATERIAL_DETAILS_FRAGMENT2;
import static com.espy.mps.utils.FragmentConstants.PURCHASE_DETAILS_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.WORK_DETAILS_FRAGMENT;

public class PurchaseBillActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Purchase Bill", true);
        enableBottomBar(false);

        setFragment(new PurchaseBillFragment(),PURCHASE_DETAILS_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class, null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PurchaseBillFragment fragment = (PurchaseBillFragment ) getSupportFragmentManager().findFragmentByTag(PURCHASE_DETAILS_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
