package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.MaterialDetailsFragmennt2;
import com.espy.mps.ui.fragments.MaterialRequestFragment;

import static com.espy.mps.utils.FragmentConstants.MATERIAL_DETAILS_FRAGMENT2;
import static com.espy.mps.utils.FragmentConstants.MATERIAL_REQUEST_FRAGMENT;

public class MaterialRequestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Material Request", true);
        enableBottomBar(false);

        setFragment(new MaterialRequestFragment(),MATERIAL_REQUEST_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class, null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MaterialRequestFragment fragment = (MaterialRequestFragment ) getSupportFragmentManager().findFragmentByTag(MATERIAL_REQUEST_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
