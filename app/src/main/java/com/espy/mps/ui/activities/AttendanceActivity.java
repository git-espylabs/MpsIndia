package com.espy.mps.ui.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.AttendanceFragmentNew;
import static com.espy.mps.utils.FragmentConstants.ATTENDANCE_FRAGMENT_NEW;

public class AttendanceActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Attendance", true);
        enableBottomBar(false);

        setFragment(new AttendanceFragmentNew(),ATTENDANCE_FRAGMENT_NEW,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class,null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AttendanceFragmentNew fragment = (AttendanceFragmentNew) getSupportFragmentManager().findFragmentByTag(ATTENDANCE_FRAGMENT_NEW);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
