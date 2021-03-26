package com.espy.mps.ui.activities;

import android.os.Bundle;

import com.espy.mps.base.BaseActivity;
import com.espy.mps.ui.fragments.LoginFragment;

import static com.espy.mps.utils.FragmentConstants.LOGIN_FRAGMENT;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_home);

        setToolbarProperties(true, "Login", false);
        enableBottomBar(false);

        setFragment(new LoginFragment(),LOGIN_FRAGMENT, null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
