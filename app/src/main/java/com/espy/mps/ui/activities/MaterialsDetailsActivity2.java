package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.MaterialDetailsFragmennt2;
import com.espy.mps.ui.fragments.MaterialDetailsFragment;

import static com.espy.mps.utils.FragmentConstants.MATERIAL_DETAILS_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.MATERIAL_DETAILS_FRAGMENT2;
import static com.espy.mps.utils.FragmentConstants.WORK_DETAILS_FRAGMENT;

public class MaterialsDetailsActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Materials Balanced", true);
        enableBottomBar(false);

        setFragment(new MaterialDetailsFragmennt2(),MATERIAL_DETAILS_FRAGMENT2,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class, null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MaterialDetailsFragmennt2 fragment = (MaterialDetailsFragmennt2 ) getSupportFragmentManager().findFragmentByTag(MATERIAL_DETAILS_FRAGMENT2);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}

