package com.espy.mps.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.espy.mps.R;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.ExpenseFragment;
import com.espy.mps.ui.fragments.FeedbackFragment;

import static com.espy.mps.utils.FragmentConstants.EXPENSE_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.FEEDBACK_FRAGMENT;

public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Feedback", true, true);
        enableBottomBar(false);

        setFragment(new FeedbackFragment(),FEEDBACK_FRAGMENT,null);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.toolbarSearch){
            moveToScreen(FeedbackListActivity.class, null, true);
        }
    }

    @Override
    public void onBackPressed() {
        moveToScreen(CustomerMenusActivity.class, null, true);
    }
}
