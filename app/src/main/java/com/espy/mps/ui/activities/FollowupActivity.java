package com.espy.mps.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.espy.mps.R;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.FeedbackFragment;
import com.espy.mps.ui.fragments.FollowupFragment;

import static com.espy.mps.utils.FragmentConstants.FEEDBACK_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.FOLLOWUP_FRAGMENT;

public class FollowupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Follow up", true, true);
        enableBottomBar(false);

        setFragment(new FollowupFragment(),FOLLOWUP_FRAGMENT,null);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.toolbarSearch){
            moveToScreen(FollowUpListActivity.class, null, true);
        }
    }

    @Override
    public void onBackPressed() {
        moveToScreen(CustomerMenusActivity.class, null, true);
    }
}
