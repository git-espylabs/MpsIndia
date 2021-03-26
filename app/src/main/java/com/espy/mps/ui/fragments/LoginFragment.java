package com.espy.mps.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.espy.mps.BuildConfig;
import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.Applog;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.LoginCallbacks;
import com.espy.mps.models.UserMaster;
import com.espy.mps.models.UserTrans;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.ui.activities.Home;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.INVALID_LOGIN_CREDENTIAL_WARNING;
import static com.espy.mps.utils.DialogueUtils.LOGIN_ERROR;
import static com.espy.mps.utils.NetworkUtils.isNetworkConnected;

public class LoginFragment extends BaseFragment implements View.OnClickListener, LoginCallbacks, DialogInteractionListener {

    @BindView(R.id.btnLogin)
    TextView btnLogin;

    @BindView(R.id.username)
    EditText et_username;

    @BindView(R.id.password)
    EditText et_password;

    DialogueUtils dialogueUtils;




    private void initializeComponents(){
        dialogueUtils = new DialogueUtils(getActivity());
        btnLogin.setOnClickListener(this);

        if (BuildConfig.DEBUG){
            et_username.setText("superadmin");
            et_password.setText("superadmin@123");
        }
    }

    private void initiateLogin(){

        if (isNetworkConnected(getContext())) {
            showProgress(getActivity(), "Please wait..");

            String uname = et_username.getText().toString().trim();
            String pass = et_password.getText().toString().trim();

            if (uname.length()>0 && pass.length()>0) {
                new WebserviceUtils(getContext()).processAppLogin(uname, pass, this);
            } else {
                dialogueUtils.showWarningDialog(getResources().getString(R.string.app_name), "Invalid Login credentials!", null, INVALID_LOGIN_CREDENTIAL_WARNING);
                dismissProgress();
            }
        } else {
            CommonUtils.showAppToast(getActivity(), "No Internet connectivity!");
        }
    }

    private void handleUserSuccessfullLogin(UserMaster master){
        ArrayList<UserTrans> trans = master.getLogin_det();
        String userId = trans.get(0).getUserid();
        String userName = trans.get(0).getUname();
        String u_type = trans.get(0).getU_type();
        String account_name = trans.get(0).getAccount_name();
        String account_designation = trans.get(0).getAccount_designation();
        String account_mob = trans.get(0).getAccount_name();
        if (userId != null && !userId.equals("0")){
            AppPreference.setIsLoggedIn(getActivity(), true);
            AppPreference.setPrefUserId(getActivity(), userId);
            AppPreference.setPrefUserName(getActivity(), userName);
            AppPreference.setPrefUType(getActivity(), u_type);
            AppPreference.setPrefAccountName(getActivity(), account_name);
            AppPreference.setPrefAccountDesignation(getActivity(), account_designation);
            AppPreference.setPrefAccountMob(getActivity(), account_mob);

            dismissProgress();
            moveToScreen(Home.class, null, true);
        }else {
            Applog.logString(getActivity().getClass().getSimpleName(), "invalid user id");
            dialogueUtils.showErrorDialog("OOPS!", getResources().getString(R.string.login_Failed), null, LOGIN_ERROR);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        initializeComponents();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:{
                initiateLogin();
                break;
            }
        }
    }

    @Override
    public void onLoginSuccess(UserMaster userMaster) {
        dismissProgress();
        handleUserSuccessfullLogin(userMaster);
    }

    @Override
    public void onLoginFail(String message) {
        dismissProgress();
        dialogueUtils.showErrorDialog("OOPS!", getResources().getString(R.string.login_Failed), null, LOGIN_ERROR);
    }

    @Override
    public void onPositiveResponse(int requestCode) {

    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }
}
