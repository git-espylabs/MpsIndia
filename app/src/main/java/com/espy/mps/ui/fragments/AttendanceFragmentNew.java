package com.espy.mps.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
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
import com.espy.mps.ui.customviews.CustomTextView;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;

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
import static com.espy.mps.utils.NetworkUtils.isNetworkConnected;

public class AttendanceFragmentNew extends BaseFragment implements View.OnClickListener, AttendanceCallbacks, DialogInteractionListener, GPSListener {

    @BindView(R.id.punch)
    CustomTextView punch;

    @BindView(R.id.punchout)
    CustomTextView punchout;

    @BindView(R.id.capture)
    CustomImageView capture;

    @BindView(R.id.userImg)
    CustomImageView userImg;

    @BindView(R.id.punchinlay)
    LinearLayout punchinlay;

    @BindView(R.id.punchoutlay)
    LinearLayout punchoutlay;

    @BindView(R.id.specLay)
    RelativeLayout specLay;

    private String status = "1";

    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;

    private static final int GALLERY_REQUEST = 1889;
    private static final int GALLERY_PERMISSION_CODE = 101;

    String imageFilePath = "";
    String userType = "";

    private void processAttendance(){
        if (userType.equals("14") || userType.equals("15")){
            processAttendanceWithImage();
        }else {
            processAttendanceWithOutImage();
        }
    }

    private void processAttendanceWithImage(){
        if (imageFilePath !=null && !imageFilePath.equals("")) {
            if (isNetworkConnected(getActivity())){
                showProgress(getActivity(), "Processing..");
                GPSManager.getInstance().checkLocationSettings(getActivity(), null);
            }else {
                CommonUtils.showAppToast(getActivity(), "No Internet connectivity!");
            }
        } else {
            CommonUtils.showAppToast(getActivity(), "Please capture your image.");
        }
    }

    private void processAttendanceWithOutImage(){
        if (isNetworkConnected(getActivity())){
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
        String t = AppPreference.getPrefUserId(getActivity());
        if (userType.equals("14") || userType.equals("15")) {
            new WebserviceUtils(getActivity()).insertAttendanceMultiPart(status, lats, longs, imageFilePath, this);
        } else {
            new WebserviceUtils(getActivity()).insertAttendanceMultiPart(status, lats, longs, "", this);
        }
    }

    private void handleAttendanceResponse(String response){
        if (response.equals("1")){
            if (!AppPreference.isPunchedIn(getActivity())) {
                AppPreference.setIsPunchedIn(getActivity(), true);
            }else {
                AppPreference.setIsPunchedIn(getActivity(), false);
            }
            new DialogueUtils(getActivity()).showSuccessDialog("Success", "Attendance marked successfully.", null,ATTENDANCE_UPLOAD_SUCCESS);
        }else if (response.equals("2")){
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "You are already punched in for the day", null,0);
        }else if (response.equals("3")){
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "You are already punched out for the day", null,0);
        }
        else {
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

    private void openGalleryIntent() {
        Intent pictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.espy.pebblesgate.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, GALLERY_REQUEST);
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

    private void toggleViews(boolean puncedin){
        if (puncedin){
            punchinlay.setBackground(getResources().getDrawable(R.drawable.rect_curved_corner_green_border_white_filled));
            punch.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_checked_in_active, 0, 0);

            punchoutlay.setBackground(getResources().getDrawable(R.drawable.rect_curved_corner_grey_border_white_filled));
            punchout.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_check_out_inactive, 0, 0);
        }else {
            punchinlay.setBackground(getResources().getDrawable(R.drawable.rect_curved_corner_grey_border_white_filled));
            punch.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_checked_in_inactive, 0, 0);

            punchoutlay.setBackground(getResources().getDrawable(R.drawable.rect_curved_corner_green_border_white_filled));
            punchout.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_ckeck_out_active, 0, 0);
        }
    }

    private void startImageChooserDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Select");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION_CODE);
                        }else {
                            openGalleryIntent();
                        }
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        myAlertDialog.show();
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        ButterKnife.bind(this, view);

        GPSManager.getInstance().setGpsCallback(this);

        punch.setOnClickListener(this);
        punchout.setOnClickListener(this);
        capture.setOnClickListener(this);
        userImg.setOnClickListener(this);
        userType = AppPreference.getPrefUType(requireActivity());

        if (userType.equals("14") || userType.equals("15")){
            specLay.setVisibility(View.VISIBLE);
        }else {
            specLay.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.punch:{
                status = "1";
                toggleViews(true);
                processAttendance();
                break;
            }
            case R.id.punchout:{
                status = "0";
                toggleViews(false);
                processAttendance();
                break;
            }
            case R.id.capture:
            case R.id.userImg: {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }else {
                    openCameraIntent();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_REQUEST: {
                if (resultCode == Activity.RESULT_OK) {
                    if (imageFilePath !=null && !imageFilePath.equals("")) {
                        imageFilePath = CommonUtils.compressImage(imageFilePath, getActivity());
//                        Glide.with(this).load(imageFilePath).into(capture);
                        Glide.with(this).load(imageFilePath).into(userImg);
                    }
                } else {
                    imageFilePath = "";
                }
                break;
            }
            case GALLERY_REQUEST: {
                if (resultCode == Activity.RESULT_OK) {
                    imageFilePath = getRealPathFromURI(getActivity(), data.getData());
                    if (imageFilePath !=null && !imageFilePath.equals("")) {
                        imageFilePath = CommonUtils.compressImage(imageFilePath, getActivity());
//                        Glide.with(this).load(imageFilePath).into(capture);
                        Glide.with(this).load(imageFilePath).into(userImg);
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
            case GALLERY_PERMISSION_CODE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGalleryIntent();
                }else {
                    CommonUtils.showAppToast(getContext(),"Permission Denied");
                }
                break;
            }
        }
    }
}
