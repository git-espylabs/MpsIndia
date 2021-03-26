package com.espy.mps.ui.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espy.fusedlocationapi.GPSListener;
import com.espy.fusedlocationapi.GPSManager;
import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.NotesInterface;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.ui.activities.Home;
import com.espy.mps.ui.customviews.CustomEditText;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.NOTES_UPLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.NOTES_UPLOAD_SUCCESS;
import static com.espy.mps.utils.DialogueUtils.NOTES_UPLOAD_WARNING;

public class NotesFragment extends BaseFragment implements NotesInterface, DialogInteractionListener, GPSListener {


    @BindView(R.id.desc)
    CustomEditText desc;

    @BindView(R.id.btnSubmit)
    TextView btnSubmit;


    DialogueUtils dialogueUtils;
    WebserviceUtils webserviceUtils;

    String noteDesc = "";

    private void initialize(){

        dialogueUtils = new DialogueUtils(getActivity());
        webserviceUtils = new WebserviceUtils(getActivity());

        GPSManager.getInstance().setGpsCallback(this);
    }

    private void uploadNote(){
        if (NetworkUtils.isNetworkConnected(getActivity())){
            showProgress(getActivity(),"Uploading note..");
            GPSManager.getInstance().checkLocationSettings(getActivity(), null);
        }else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, view);

        initialize();

        btnSubmit.setOnClickListener(view1 -> {
            noteDesc = desc.getText().toString();
            if (noteDesc.length()>0) {
                dialogueUtils.showWarningDialog("Confirm?", "Confirm uploading note?",
                        this, NOTES_UPLOAD_WARNING);
            } else {
                CommonUtils.showAppToast(getActivity(), "Please fill all details");
            }
        });

        return view;
    }

    @Override
    public void onLocationUpdate(Location location, int i) {
        if (location != null) {
            AppSession.location = location;
            AppPreference.setAppLatitude(getActivity(), String.valueOf(location.getLatitude()));
            AppPreference.setAppLongitude(getActivity(), String.valueOf(location.getLongitude()));
        }
        webserviceUtils.insertNotes(this,noteDesc);
    }

    @Override
    public void onLocationSettingsSatisfied() {
        GPSManager.getInstance().getLastLocation(getActivity());
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        switch (requestCode){
            case NOTES_UPLOAD_WARNING:{
                uploadNote();
                break;
            }
            case NOTES_UPLOAD_SUCCESS:{
                moveToScreen(Home.class, null, true);
                break;
            }
            case NOTES_UPLOAD_ERROR:{
                break;
            }
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onNotesUploadee() {
        dismissProgress();
        dialogueUtils.showSuccessDialog("Success", "Notes uploaded successfully", this, NOTES_UPLOAD_SUCCESS);
    }

    @Override
    public void onApiResponseError(String message, int type) {
        dismissProgress();
        if (type == NOTES_UPLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Uploading failed!", this, NOTES_UPLOAD_ERROR);
        }
    }
}
