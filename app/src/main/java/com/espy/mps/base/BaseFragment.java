package com.espy.mps.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {


    protected void moveToScreen(Class targetActivity, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

        if (isFinish) {
            getActivity().finish();
        }
    }
}
