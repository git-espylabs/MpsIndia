package com.espy.mps.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.espy.fusedlocationapi.GPSListener;
import com.espy.fusedlocationapi.GPSManager;
import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.AttendanceCallbacks;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.ui.activities.Home;
import com.espy.mps.ui.customviews.CustomImageView;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.ATTENDANCE_UPLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.ATTENDANCE_UPLOAD_SUCCESS;

public class AttendanceFragmentOld extends BaseFragment implements View.OnClickListener, AttendanceCallbacks, DialogInteractionListener, GPSListener {

    @BindView(R.id.punch)
    CustomImageView punch;

    @BindView(R.id.punchout)
    CustomImageView punchout;

    @BindView(R.id.pinlay)
    LinearLayout pinlay;

    @BindView(R.id.poutay)
    LinearLayout poutay;

    private String status = "1";

    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;

    String imageFilePath = "";

    private void processAttendance(){
        if (NetworkUtils.isNetworkConnected(getActivity())){
            showProgress(getActivity(), "Processing..");
            GPSManager.getInstance().checkLocationSettings(getActivity(), null);
        }else {
            CommonUtils.showAppToast(getActivity(), "No Internet connectivity!");
        }
    }

    private void sendAttendance(){

        String lats = "0.0";
        String longs = "0.0";
        try {
            if (AppSession.location != null) {
                lats = String.valueOf(AppSession.location.getLatitude());
                longs = String.valueOf(AppSession.location.getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String status = this.status;
        new WebserviceUtils(getActivity()).processAttendance(status, lats, longs, this);
    }

    private void handleAttendanceResponse(String response){
        if (response.equals("1")){
            if (!AppPreference.isPunchedIn(getActivity())) {
                AppPreference.setIsPunchedIn(getActivity(), true);
                poutay.setVisibility(View.VISIBLE);
                pinlay.setVisibility(View.GONE);
            }else {
                AppPreference.setIsPunchedIn(getActivity(), false);
                poutay.setVisibility(View.GONE);
                pinlay.setVisibility(View.VISIBLE);
            }
            new DialogueUtils(getActivity()).showSuccessDialog("Success", "Attendance marked successfully.", null,ATTENDANCE_UPLOAD_SUCCESS);
        }else {
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Attendance sending Failed! Please contact Admin.", null,ATTENDANCE_UPLOAD_ERROR);
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.espy.mps.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                pictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(pictureIntent,
                        CAMERA_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir("Capture");
        if (!storageDir.exists()){
            storageDir.mkdir();
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_old, container, false);
        ButterKnife.bind(this, view);

        GPSManager.getInstance().setGpsCallback(this);

        punch.setOnClickListener(this);
        punchout.setOnClickListener(this);

        if (AppPreference.isPunchedIn(getActivity())){
            poutay.setVisibility(View.VISIBLE);
            pinlay.setVisibility(View.GONE);
        }else {
            poutay.setVisibility(View.GONE);
            pinlay.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.punch:{
                status = "1";
                processAttendance();
                break;
            }
            case R.id.punchout:{
                status = "0";
                processAttendance();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_REQUEST:{
                if (resultCode == Activity.RESULT_OK) {
                    if (imageFilePath !=null && !imageFilePath.equals("")) {
                        imageFilePath = CommonUtils.compressImage(imageFilePath, getActivity());
//                        Glide.with(this).load(imageFilePath).into(capture);
                    }
                } else {
                    imageFilePath = "";
                }
                break;
            }
            case 1029:{
                if (resultCode == Activity.RESULT_OK) {
                    onLocationSettingsSatisfied();
                }
                break;
            }
        }
    }

    @Override
    public void onAttendanceSend(String result) {
        dismissProgress();
        handleAttendanceResponse(result);
    }

    @Override
    public void onSendingAttendanceFailed(String message) {
        dismissProgress();
        handleAttendanceResponse(message);
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        if (requestCode == ATTENDANCE_UPLOAD_SUCCESS){
            moveToScreen(Home.class, null, true);
        }else if (requestCode == ATTENDANCE_UPLOAD_ERROR){

        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onLocationUpdate(Location location, int i) {
        AppSession.location = location;
        sendAttendance();
    }

    @Override
    public void onLocationTrackUpdate(Location location, int i) {

    }

    @Override
    public void onLocationSettingsSatisfied() {
        GPSManager.getInstance().getLastLocation(getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_PERMISSION_CODE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCameraIntent();
                }else {
                    CommonUtils.showAppToast(getContext(),"Permission Denied");
                }
                break;
            }
        }
    }
}
