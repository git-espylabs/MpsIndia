package com.espy.mps.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.espy.mps.R;
import com.espy.mps.ui.activities.FeedbackListActivity;
import com.espy.mps.ui.activities.FollowUpListActivity;
import com.espy.mps.ui.fragments.HomeFragment;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.utils.FragmentConstants.HOME_FRAGMENT;

public class BaseActivity extends AppCompatActivity  implements View.OnClickListener {

    @BindView(R.id.tbCommon)
    Toolbar toolbar;

    @BindView(R.id.toolbarBack)
    ImageView toolbarBack;

    @BindView(R.id.toolbarSearch)
    ImageView toolbarSearch;

    @BindView(R.id.customToolbarSearch)
    LinearLayout customToolbarSearch;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.home)
    ImageView home;

    @BindView(R.id.profile)
    ImageView profile;

    @BindView(R.id.notifications)
    ImageView notifications;

    @BindView(R.id.bottom_bar)
    LinearLayout bottom_bar;

    public static KProgressHUD hud;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbarBack:{
                onBackPressed();
                break;
            }
        }
    }

    protected void setToolbarProperties(boolean isVisible, String title, boolean isBackButtonRequired){

        if (!isVisible){
            getSupportActionBar().hide();
        }else {
            ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            toolbar_title.setText(title);
            if (isBackButtonRequired){
                toolbarBack.setBackgroundResource(R.drawable.back);
            }else {
                toolbarBack.setBackgroundResource(R.drawable.ic_menu_toolbar);
            }
        }

    }

    protected void setToolbarProperties(boolean isVisible, String title, boolean isBackButtonRequired, boolean isHistoryRequired){

        if (!isVisible){
            getSupportActionBar().hide();
        }else {
            ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            toolbar_title.setText(title);
            if (isBackButtonRequired){
                toolbarBack.setBackgroundResource(R.drawable.back);
            }else {
                toolbarBack.setBackgroundResource(R.drawable.ic_menu_toolbar);
            }
            if (isHistoryRequired){
                customToolbarSearch.setVisibility(View.VISIBLE);
                toolbarSearch.setBackgroundResource(R.drawable.history);
            }else {
                customToolbarSearch.setVisibility(View.INVISIBLE);
            }
        }

    }

    protected void enableBottomBar(boolean isEnabled){
        bottom_bar.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
    }

    private void setListeners(){
        toolbarBack.setOnClickListener(this);
        toolbarSearch.setOnClickListener(this);
    }

    protected void moveToScreen(Class targetActivity, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

        if (isFinish) {
            this.finish();
        }
    }

    public  void setFragment(Fragment targetFragment, String fragmentTag, Bundle bundle){
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (bundle != null){
            targetFragment.setArguments(bundle);
        }
        transaction.replace(R.id.container, targetFragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showProgress(Context context, String message){
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(message)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public static void dismissProgress(){
        try {
            if (hud != null) {
                hud.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void homeTabSelected(){
        setFragment(new HomeFragment(), HOME_FRAGMENT, null);
        home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_dark));
        profile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_light));
        notifications.setImageDrawable(getResources().getDrawable(R.drawable.ic_notification_light));
    }
}
