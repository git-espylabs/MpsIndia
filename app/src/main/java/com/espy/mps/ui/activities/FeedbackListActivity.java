package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.FeedbackFragment;
import com.espy.mps.ui.fragments.FeedbackListFragment;
import com.espy.mps.ui.fragments.LeadHistoryFragment;

import static com.espy.mps.utils.FragmentConstants.FEEDBACK_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.FEEDBACK_HISTORY_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.LEAD_HISTORY_FRAGMENT;

public class FeedbackListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Feedback", true);
        enableBottomBar(false);

        setFragment(new FeedbackListFragment(),FEEDBACK_HISTORY_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(FeedbackActivity.class,null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FeedbackListFragment fragment = (FeedbackListFragment) getSupportFragmentManager().findFragmentByTag(FEEDBACK_HISTORY_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
