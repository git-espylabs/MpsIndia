package com.espy.mps.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.ExpenseFragment;
import com.espy.mps.ui.fragments.NotesFragment;

import static com.espy.mps.utils.FragmentConstants.EXPENSE_FRAGMENT;
import static com.espy.mps.utils.FragmentConstants.NOTES_FRAGMENT;

public class NotesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Notes", true);
        enableBottomBar(false);

        setFragment(new NotesFragment(),NOTES_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class,null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NotesFragment fragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(NOTES_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
