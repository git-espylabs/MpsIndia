package com.espy.mps.ui.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.AttendanceFragment;

import static com.espy.mps.utils.FragmentConstants.ATTENDANCE_FRAGMENT;

public class AttendanceActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarProperties(true, "Attendance", true);
        enableBottomBar(false);

        setFragment(new AttendanceFragment(),ATTENDANCE_FRAGMENT,null);
    }

    @Override
    public void onBackPressed() {
        moveToScreen(Home.class,null, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AttendanceFragment fragment = (AttendanceFragment ) getSupportFragmentManager().findFragmentByTag(ATTENDANCE_FRAGMENT);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
