package com.espy.mps.ui.fragments;

import android.Manifest;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.espy.fusedlocationapi.GPSListener;
import com.espy.fusedlocationapi.GPSManager;
import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.WorkDetailsCallBack;
import com.espy.mps.models.ProjectNameListTrans;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.ui.activities.Home;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.COMN_DEATAILS_UPLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.COMN_DEATAILS_UPLOAD_SUCCESS;
import static com.espy.mps.utils.DialogueUtils.WORK_DEATAILS_PROJECT_NAMES_DOWNLOAD_ERROR;

public class VehicleDetailsFragment extends BaseFragment implements DialogInteractionListener, WorkDetailsCallBack, GPSListener {



    @BindView(R.id.submit)
    Button btnSubmit;

    @BindView(R.id.captureImg)
    TextView captureImg;

    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.vehNo)
    EditText vehNo;

    @BindView(R.id.spnProject)
    Spinner spnProject;

    @BindView(R.id.descrption)
    EditText descrption;

    @BindView(R.id.capImg)
    ImageView capImg;


    String remarks = "";
    String titleTxt = "";

    ArrayList<ProjectNameListTrans> projectcList = new ArrayList<>();
    ArrayAdapter<ProjectNameListTrans> projectcListAdapter;

    String projectid = "";
    String vehicleNoTxt = "";

    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;

    String imageFilePath = "";


    private void getProjectNames(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Loading data..");
            projectcList.clear();
            projectcList.add(new ProjectNameListTrans("0", "Select"));
            new WebserviceUtils(getActivity()).getProjectIds(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void populatetProjectList(){
        projectcListAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, projectcList);
        projectcListAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnProject.setAdapter(projectcListAdapter);
    }

    private void submitData(){
        projectid = projectcList.get(spnProject.getSelectedItemPosition()).getProject_id();
        vehicleNoTxt = vehNo.getText().toString();
        remarks = descrption.getText().toString();
        titleTxt = title.getText().toString();

        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Loading data..");
            GPSManager.getInstance().checkLocationSettings(getActivity(), null);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
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
//                pictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_REQUEST:{
                if (resultCode == Activity.RESULT_OK) {
                    if (imageFilePath !=null && !imageFilePath.equals("")) {
                        imageFilePath = CommonUtils.compressImage(imageFilePath, getActivity());
                        captureImg.setVisibility(View.GONE);
                        capImg.setVisibility(View.VISIBLE);
                        Glide.with(this).load(imageFilePath).into(capImg);
                    }
                } else {
                    imageFilePath = "";
                }
                break;
            }
            case 1029:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        onLocationSettingsSatisfied();
                        break;
                    case Activity.RESULT_CANCELED:
                        dismissProgress();
                        CommonUtils.showDeveloperToast(getActivity(), "Location Settings not Satisfied", Toast.LENGTH_LONG);
                        break;
                    default:
                        dismissProgress();
                        CommonUtils.showDeveloperToast(getActivity(), "Location Settings not Satisfied", Toast.LENGTH_LONG);
                        break;
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_details, container, false);
        ButterKnife.bind(this, view);


        GPSManager.getInstance().setGpsCallback(this);

        btnSubmit.setOnClickListener(view1 -> {
            submitData();
        });

        captureImg.setOnClickListener(view1 -> {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }else {
                openCameraIntent();
            }
        });

        getProjectNames();

        return view;
    }


    @Override
    public void onLocationUpdate(Location location, int i) {
        if (location != null) {
            AppSession.location = location;
            AppPreference.setAppLatitude(getActivity(), String.valueOf(location.getLatitude()));
            AppPreference.setAppLongitude(getActivity(), String.valueOf(location.getLongitude()));
            new WebserviceUtils(getActivity()).insertVehicleDetails(this, titleTxt, remarks, imageFilePath, vehicleNoTxt, projectid);
        }else {
            dismissProgress();
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Location not available, check your device location settings", null, 0);
        }
    }

    @Override
    public void onLocationSettingsSatisfied() {
        GPSManager.getInstance().getLastLocation(getActivity());
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        if (requestCode == COMN_DEATAILS_UPLOAD_SUCCESS){
            moveToScreen(Home.class, null, true);
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onDetailsUploadSuccess(String response) {
        dismissProgress();
        new DialogueUtils(getActivity()).showSuccessDialog("SUCCESS", "Vehicle details uploaded",
                this, COMN_DEATAILS_UPLOAD_SUCCESS);
    }

    @Override
    public void onProjectListDownladed(ArrayList<ProjectNameListTrans> projectList) {
        dismissProgress();
        projectcList.addAll(projectList);
        populatetProjectList();
    }

    @Override
    public void onApiErrorResponse(String message, int type) {
        dismissProgress();
        if (type == WORK_DEATAILS_PROJECT_NAMES_DOWNLOAD_ERROR){
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Project list not available",
                    this, WORK_DEATAILS_PROJECT_NAMES_DOWNLOAD_ERROR);
        }else if (type == COMN_DEATAILS_UPLOAD_ERROR){
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Vehicle details upload failed",
                    this, COMN_DEATAILS_UPLOAD_ERROR);
        }
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
