package com.espy.mps.ui.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.FeedbackCallback;
import com.espy.mps.interfaces.IteneriesCallBack;
import com.espy.mps.models.ActivityTypeTrans;
import com.espy.mps.models.CustomerStatusTrans;
import com.espy.mps.models.FeedbackTrans;
import com.espy.mps.ui.activities.CustomerMenusActivity;
import com.espy.mps.ui.activities.FeedbackListActivity;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.ACTIVITY_TYPES_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_STATUS_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.FEEDBACK_UPLOAD_SUCCESS;
import static com.espy.mps.utils.DialogueUtils.FOLLOWUP_UPLOAD_ERROR;

public class FeedbackFragment extends BaseFragment implements FeedbackCallback, DialogInteractionListener,
        View.OnClickListener, IteneriesCallBack {


    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    @BindView(R.id.history)
    TextView history;

    @BindView(R.id.spnActivityTypes)
    Spinner spnActivityTypes;

    @BindView(R.id.spnCustomerStatus)
    Spinner spnCustomerStatus;

    @BindView(R.id.desc)
    EditText desc;


    String remarks = "";


    ArrayList<ActivityTypeTrans> activityTypeList = new ArrayList<>();
    ArrayAdapter<ActivityTypeTrans> activityTypeAdapter;

    ArrayList<CustomerStatusTrans> customerTypeList = new ArrayList<>();
    ArrayAdapter<CustomerStatusTrans> customerTypeAdapter;

    String activity_type = "";
    String current_status = "";

    private static String requestDate = "";
    private static String requestTime = "";

    private void getToday(){
        requestDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        requestTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    }


    private void getActivityTypes(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Loading data..");
            activityTypeList.clear();
            activityTypeList.add(new ActivityTypeTrans("0", "--Select Activity Type--"));
            new WebserviceUtils(getActivity()).getActivityTypes(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void getCustomerStatus(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Loading data..");
            customerTypeList.clear();
            customerTypeList.add(new CustomerStatusTrans("0", "--Select Customer Type--"));
            new WebserviceUtils(getActivity()).getCustomerStatus(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void populatetActivityTypes(){
        activityTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, activityTypeList);
        activityTypeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnActivityTypes.setAdapter(activityTypeAdapter);
    }

    private void populatetCustomerStatus(){
        customerTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, customerTypeList);
        customerTypeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnCustomerStatus.setAdapter(customerTypeAdapter);
    }

    private void submitData(){
        activity_type = activityTypeList.get(spnActivityTypes.getSelectedItemPosition()).getActivity_type_id();
        current_status = customerTypeList.get(spnCustomerStatus.getSelectedItemPosition()).getCstmr_statusid();
        remarks = desc.getText().toString();

        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Loading data..");
            new WebserviceUtils(getActivity()).insertFeedback(this, activity_type, current_status, remarks);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);

        btnSubmit.setOnClickListener(this);
        history.setOnClickListener(this);

        getToday();
        getActivityTypes();

        return view;
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        if (requestCode == FEEDBACK_UPLOAD_SUCCESS){
            moveToScreen(CustomerMenusActivity.class, null, true);
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onFeedbackHistoryDownloaded(ArrayList<FeedbackTrans> feedbackList) {
        dismissProgress();
    }

    @Override
    public void onFeedbackUploaded(String result) {
        dismissProgress();
        new DialogueUtils(getActivity()).showSuccessDialog("SUCCESS", "Feedback uploaded",
                this, FEEDBACK_UPLOAD_SUCCESS);
    }

    @Override
    public void onActivityTypesDownladed(ArrayList<ActivityTypeTrans> activityTypesList) {
        dismissProgress();
        activityTypeList.addAll(activityTypesList);
        populatetActivityTypes();
        getCustomerStatus();
    }

    @Override
    public void onCustomerStatusDownladed(ArrayList<CustomerStatusTrans> customerStatusList) {
        dismissProgress();
        customerTypeList.addAll(customerStatusList);
        populatetCustomerStatus();
    }

    @Override
    public void onApiErrorResponse(String message, int type) {
        dismissProgress();
        if (type == ACTIVITY_TYPES_DOWNLOAD_ERROR){
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Activity types not available",
                    this, ACTIVITY_TYPES_DOWNLOAD_ERROR);
        }else if (type == CUSTOMER_STATUS_DOWNLOAD_ERROR){
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Customer status not available",
                    this, CUSTOMER_STATUS_DOWNLOAD_ERROR);
        }else if (type == FOLLOWUP_UPLOAD_ERROR){
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Follow up upload failed",
                    this, FOLLOWUP_UPLOAD_ERROR);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:{
                submitData();
                break;
            }
            case R.id.history:{
                moveToScreen(FeedbackListActivity.class, null, true);
                break;
            }
        }
    }




    @SuppressLint("ValidFragment")
    public static class DateTimePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener,
            TimePickerDialog.OnTimeSetListener {

        TextView dateTimeText;
        int type;
        String reqDate = "";
        String reqTime = "";

        public DateTimePickerFragment(TextView dateTimeText, int type) {
            this.dateTimeText = dateTimeText;
            this.type = type;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            if (type == 1) {
                return new DatePickerDialog(getActivity(), this, year, month, day);
            } else {
                return new TimePickerDialog(getActivity(), this, hour, minute, true);
            }
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String d, m;
            if ((month + 1 < 10) || (day < 10)) {
                if ((month + 1) < 10) {
                    m = "0" + (month + 1);
                } else {
                    m = String.valueOf((month + 1));
                }
                if (day < 10) {
                    d = "0" + day;
                } else {
                    d = String.valueOf(day);
                }
            } else {
                d = String.valueOf(day);
                m = String.valueOf((month + 1));
            }
            reqDate = year + "-" + m + "-" + d;
            Date nDate;
            try {
                nDate = new SimpleDateFormat("yyyy-M-d").parse(reqDate);
                reqDate = new SimpleDateFormat("dd-MM-yyyy").format(nDate);
                requestDate = reqDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateTimeText.setText(reqDate);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            reqTime = hourOfDay + ":" + minute;
            requestTime = reqTime;
            dateTimeText.setText(reqTime);
        }
    }
}
