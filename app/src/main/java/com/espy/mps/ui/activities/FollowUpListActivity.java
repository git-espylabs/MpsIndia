package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.FeedbackFragment;
import com.espy.mps.ui.fragments.FollowupListFragment;

import static com.espy.mps.utils.FragmentConstants.FEEDBACK_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.FEEDBACK_HISTORY_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.FOLLOWUP_HISTORY_FRAGMENT;

public class FollowUpListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Followup", true);
        enableBottomBar(false);

        setFragment(new FollowupListFragment(),FOLLOWUP_HISTORY_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        if (AppSession.isIndividual) {
            moveToScreen(Home.class,null, true);
        } else {
            moveToScreen(FollowupActivity.class,null, true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FollowupListFragment fragment = (FollowupListFragment) getSupportFragmentManager().findFragmentByTag(FOLLOWUP_HISTORY_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
