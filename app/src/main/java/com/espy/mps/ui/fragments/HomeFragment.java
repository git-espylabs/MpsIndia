package com.espy.mps.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.espy.mps.BuildConfig;
import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.AppVersionCallback;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.HomeIteneriesCallback;
import com.espy.mps.models.AppVersionTrans;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.ui.activities.AttendanceActivity;
import com.espy.mps.ui.activities.CustomerListingActivity;
import com.espy.mps.ui.activities.ExpenseActivity;
import com.espy.mps.ui.activities.FollowUpListActivity;
import com.espy.mps.ui.activities.FollowupSearchListActivity;
import com.espy.mps.ui.activities.MaterailDetailsActivity;
import com.espy.mps.ui.activities.MaterialRequestActivity;
import com.espy.mps.ui.activities.MaterialsDetailsActivity2;
import com.espy.mps.ui.activities.PurchaseBillActivity;
import com.espy.mps.ui.activities.VehicleDetailsActivity;
import com.espy.mps.ui.activities.WorkDetailsActivity;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;

public class HomeFragment extends BaseFragment implements HomeIteneriesCallback, AppVersionCallback, DialogInteractionListener {


    @BindView(R.id.badge1)
    TextView badge1;

    @BindView(R.id.badge2)
    TextView badge2;

    @BindView(R.id.badge3)
    TextView badge3;

    @BindView(R.id.m1)
    CardView m1;

    @BindView(R.id.m2)
    CardView m2;

    @BindView(R.id.m3)
    CardView m3;

    @BindView(R.id.m4)
    CardView m4;

    @BindView(R.id.m5)
    CardView m5;

    @BindView(R.id.m6)
    CardView m6;

    @BindView(R.id.m7)
    CardView m7;

    @BindView(R.id.m8)
    CardView m8;

    @BindView(R.id.m9)
    CardView m9;

    @BindView(R.id.m10)
    CardView m10;

    @BindView(R.id.m11)
    CardView m11;

    @BindView(R.id.m12)
    CardView m12;


    private void getIndividualFollowupCount(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Loading..");
            String day = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            new WebserviceUtils(getActivity()).getIndividualFollowupCount(this, day);
        } else {
            CommonUtils.showAppToast(getActivity(), "No Internet connection!");
        }
    }

    private void checkAppVersion(){
        if (NetworkUtils.isNetworkConnected(getActivity())){
            new WebserviceUtils(getActivity()).getAppVersion(this);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        checkAppVersion();

        m1.setOnClickListener(view1 -> {
            AppSession.isIndividual = true;
            moveToScreen(FollowUpListActivity.class, null, true);
        });

        m2.setOnClickListener(view1 -> {
            moveToScreen(CustomerListingActivity.class, null, true);
        });

        m3.setOnClickListener(view1 -> {
            AppSession.isIndividual = true;
            moveToScreen(FollowupSearchListActivity.class, null, true);
        });

        m4.setOnClickListener(view1 -> {
            moveToScreen(AttendanceActivity.class, null, true);
        });

        m5.setOnClickListener(view1 -> {
            moveToScreen(CustomerListingActivity.class, null, true);
        });

        m6.setOnClickListener(view1 -> {
            moveToScreen(ExpenseActivity.class, null, true);
        });

        m7.setOnClickListener(view1 -> {
            moveToScreen(WorkDetailsActivity.class, null, true);
        });

        m8.setOnClickListener(view1 -> {
            moveToScreen(VehicleDetailsActivity.class, null, true);
        });

        m9.setOnClickListener(view1 -> {
            moveToScreen(MaterailDetailsActivity.class, null, true);
        });

        m10.setOnClickListener(view1 -> {
            moveToScreen(MaterialsDetailsActivity2.class, null, true);
        });

        m11.setOnClickListener(view1 -> {
            moveToScreen(PurchaseBillActivity.class, null, true);
        });

        m12.setOnClickListener(view1 -> {
            moveToScreen(MaterialRequestActivity.class, null, true);
        });

        String usertype = AppPreference.getPrefUType(getActivity());
        if (usertype.equals("11") | usertype.equals("12") | usertype.equals("13") |
                usertype.equals("5") | usertype.equals("6")){
            m5.setVisibility(View.GONE);
            m8.setVisibility(View.VISIBLE);
            m9.setVisibility(View.VISIBLE);
            m10.setVisibility(View.VISIBLE);
            m11.setVisibility(View.VISIBLE);
            m12.setVisibility(View.VISIBLE);

        } else if (usertype.equals("4") | usertype.equals("3") | usertype.equals("1") |
                usertype.equals("7") | usertype.equals("8") | usertype.equals("9")) {
            m5.setVisibility(View.VISIBLE);
            m8.setVisibility(View.GONE);
            m9.setVisibility(View.GONE);
            m10.setVisibility(View.GONE);
            m11.setVisibility(View.GONE);
            m12.setVisibility(View.GONE);
        }else {
            m5.setVisibility(View.VISIBLE);
            m8.setVisibility(View.VISIBLE);
            m9.setVisibility(View.VISIBLE);
            m10.setVisibility(View.VISIBLE);
            m11.setVisibility(View.VISIBLE);
            m12.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onFollowupCountGot(String count) {
        dismissProgress();
        int cnt = 0;
        try {
            cnt = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (cnt>0){
            badge1.setVisibility(View.VISIBLE);
            badge1.setText(count);
        }else {
            badge1.setVisibility(View.GONE);
        }
    }

    @Override
    public void onApiErrorResponse(String mesage, int type) {
        dismissProgress();
    }

    @Override
    public void onAppVersionReceived(AppVersionTrans version) {
        if (Integer.parseInt(version.getVersion_code()) > BuildConfig.VERSION_CODE && !version.getVersion_name().equals(BuildConfig.VERSION_NAME)){
            String message = "You are using an out dated version(v" + BuildConfig.VERSION_NAME + ") of MPS! An updated version(v" + version.getVersion_name() + ")available in Google Play Store.";
            new DialogueUtils(getActivity()).showUpdateNotice("Update Required!", message, this, 100256);
        }else {
            getIndividualFollowupCount();
        }
    }

    @Override
    public void onApiErrorResponse(String message) {
        getIndividualFollowupCount();
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        if (requestCode == 100256){
            try {
                startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getActivity().getPackageName())), 10001);
            } catch (Exception e) {
                e.printStackTrace();
                startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getActivity().getPackageName())), 10001);
            }
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001){
            checkAppVersion();
        }

    }
}