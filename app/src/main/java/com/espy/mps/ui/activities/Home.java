package com.espy.mps.ui.activities;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.espy.mps.R;
import com.espy.mps.base.BaseActivity;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.ui.customviews.CustomTextView;
import com.espy.mps.ui.customviews.CustomTypeFace;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.Constants;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.LocationUtils;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private static boolean isInDashBoard = true;

    boolean doubleBackToExitPressedOnce = false;



    private void initializeComponents(){
        setToolbarProperties(true, getResources().getString(R.string.app_name), false);
        enableBottomBar(false);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        CircleImageView navHead = hView.findViewById(R.id.navHeaderImg);
        CustomTextView profileName = hView.findViewById(R.id.profileName);
        profileName.setText(AppPreference.getPrefAccountName(this));
        CustomTextView profileMail = hView.findViewById(R.id.profileMail);
        if (!AppPreference.getPrefAccountDesignation(this).equals("")) {
            profileMail.setText(AppPreference.getPrefAccountDesignation(this));
        }

        if (!LocationUtils.isLocationPermissionsSatisfied(this)){
            LocationUtils.requestLocationPermissions(this);
        }

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }            applyFontToMenuItem(mi);
        }

        isInDashBoard = true;
        homeTabSelected();

        hideItem();
    }

    private void hideItem()
    {
        Menu nav_Menu = navigationView.getMenu();
        String usertype = AppPreference.getPrefUType(this);
        if (usertype.equals("11") || usertype.equals("12") || usertype.equals("13") |
                usertype.equals("5") || usertype.equals("6")){
            nav_Menu.findItem(R.id.nav_folloup).setVisible(false);
            nav_Menu.findItem(R.id.nav_lead).setVisible(false);
            nav_Menu.findItem(R.id.nav_new_lead).setVisible(false);
            nav_Menu.findItem(R.id.nav_note).setVisible(false);
            nav_Menu.findItem(R.id.nav_feedback).setVisible(false);

        }else if (usertype.equals("4") || usertype.equals("3") || usertype.equals("1") |
                usertype.equals("7") || usertype.equals("8") || usertype.equals("9")){
            nav_Menu.findItem(R.id.nav_folloup).setVisible(false);
            nav_Menu.findItem(R.id.nav_note).setVisible(false);
            nav_Menu.findItem(R.id.nav_feedback).setVisible(false);
            nav_Menu.findItem(R.id.nav_veh_details).setVisible(false);
            nav_Menu.findItem(R.id.nav_mat_used).setVisible(false);
            nav_Menu.findItem(R.id.nav_mat_reqst).setVisible(false);
            nav_Menu.findItem(R.id.nav_mat_bal).setVisible(false);
            nav_Menu.findItem(R.id.nav_purchase).setVisible(false);
        }else if (usertype.equals("14") || usertype.equals("15")){
            nav_Menu.findItem(R.id.nav_folloup).setVisible(false);
            nav_Menu.findItem(R.id.nav_lead).setVisible(false);
            nav_Menu.findItem(R.id.nav_new_lead).setVisible(false);
            nav_Menu.findItem(R.id.nav_note).setVisible(false);
            nav_Menu.findItem(R.id.nav_feedback).setVisible(false);
            nav_Menu.findItem(R.id.nav_veh_details).setVisible(false);
            nav_Menu.findItem(R.id.nav_mat_used).setVisible(false);
            nav_Menu.findItem(R.id.nav_mat_reqst).setVisible(false);
            nav_Menu.findItem(R.id.nav_mat_bal).setVisible(false);
            nav_Menu.findItem(R.id.nav_purchase).setVisible(false);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/BodoniFLFBold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
//        mNewTitle.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, mNewTitle.length(), 0);
        mNewTitle.setSpan(new CustomTypeFace("" , font, this), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void executeAppDoubleTapExit(){
        this.doubleBackToExitPressedOnce = true;
        CommonUtils.showAppToast(getApplicationContext(), getResources().getString(R.string.doubleBackToExit));
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeComponents();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.nav_home:{
                isInDashBoard = true;
                homeTabSelected();
                break;
            }
            case R.id.nav_attendance:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(AttendanceActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_folloup:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(CustomerListingActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_lead:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(CustomerListingActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_expense:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(ExpenseActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_note:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(NotesActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_feedback:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(CustomerListingActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_new_lead:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(NewLeadActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_purchase:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(PurchaseBillActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_veh_details:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(VehicleDetailsActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_mat_used:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(MaterailDetailsActivity.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_mat_bal:{
                if (LocationUtils.isLocationPermissionsSatisfied(this)) {
                    moveToScreen(MaterialsDetailsActivity2.class, null, true);
                } else {
                    LocationUtils.requestLocationPermissions(this);
                }
                break;
            }
            case R.id.nav_logout:{
                AppPreference.setIsLoggedIn(this, false);
                moveToScreen(LoginActivity.class, null, true);
                break;
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!isInDashBoard) {
            homeTabSelected();
        }else if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
            } else {
                executeAppDoubleTapExit();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbarBack:{
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }
                break;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constants.PERMISSIONS_REQUEST_ACCESS_LOCATION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }else {
                    new DialogueUtils(this).showWarningDialog("Warning!", "Please allow location permissions to continue", null, 0);
                }
                break;
            }
        }
    }
}
