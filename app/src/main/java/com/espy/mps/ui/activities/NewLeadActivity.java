package com.espy.mps.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.FeedbackFragment;
import com.espy.mps.ui.fragments.NewLeadFragment;

import static com.espy.mps.utils.FragmentConstants.FEEDBACK_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.NEW_LEAD_FRAGMENT;

public class NewLeadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Add New Lead", true);
        enableBottomBar(false);

        setFragment(new NewLeadFragment(),NEW_LEAD_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class, null, true);
    }
}
