package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.FeedbackFragment;
import com.espy.mps.ui.fragments.WorkDetailsFragment;

import static com.espy.mps.utils.FragmentConstants.FEEDBACK_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.WORK_DETAILS_FRAGMENT;

public class WorkDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Work Details", true);
        enableBottomBar(false);

        setFragment(new WorkDetailsFragment(),WORK_DETAILS_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class, null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WorkDetailsFragment fragment = (WorkDetailsFragment ) getSupportFragmentManager().findFragmentByTag(WORK_DETAILS_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
