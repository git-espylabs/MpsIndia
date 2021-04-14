package com.espy.mps.ui.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espy.fusedlocationapi.GPSListener;
import com.espy.fusedlocationapi.GPSManager;
import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.ExpenseInterfaces;
import com.espy.mps.models.ExpenseTypeTrans;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.ui.activities.Home;
import com.espy.mps.ui.customviews.CustomEditText;
import com.espy.mps.ui.customviews.CustomTextView;
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
import static com.espy.mps.utils.DialogueUtils.EXPENSE_TYPE_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.EXPENSE_UPLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.EXPENSE_UPLOAD_SUCCESS;
import static com.espy.mps.utils.DialogueUtils.EXPENSE_UPLOAD_WARNING;

public class ExpenseFragment extends BaseFragment implements ExpenseInterfaces, DialogInteractionListener, GPSListener {


    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    @BindView(R.id.edate)
    CustomTextView edate;

    @BindView(R.id.desc)
    CustomEditText desc;

    @BindView(R.id.eamount)
    CustomEditText eamount;

    @BindView(R.id.spnExpenseType)
    Spinner spnExpenseType;

    DialogueUtils dialogueUtils;
    WebserviceUtils webserviceUtils;

    ArrayList<ExpenseTypeTrans> expenseTypeList = new ArrayList<>();
    ArrayAdapter<ExpenseTypeTrans> expenseTypeAdapter;

    String expType = "";
    String expDescription = "";

    static String requestDate = "2019-01-01";

    static String requestTime = "00:00";

    private void getToday(){
        requestDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        requestTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        edate.setText(requestDate);
    }


    private void initialize(){

        dialogueUtils = new DialogueUtils(getActivity());
        webserviceUtils = new WebserviceUtils(getActivity());

        getToday();

        GPSManager.getInstance().setGpsCallback(this);

        getExpenseTypes();
    }

    private void populateExpenseType(ArrayList<ExpenseTypeTrans> expenseTypeList){
        expenseTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, expenseTypeList);
        expenseTypeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnExpenseType.setAdapter(expenseTypeAdapter);
    }

    private void uploadExpense(){
        if (NetworkUtils.isNetworkConnected(getActivity())){
            showProgress(getActivity(),"Uploading expense..");
            GPSManager.getInstance().checkLocationSettings(getActivity(), null);
        }else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void getExpenseTypes(){
        expenseTypeList.clear();
        expenseTypeList.add(new ExpenseTypeTrans("0","Select"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Expense types..");
            webserviceUtils.getExpenseTypesList(this);
        } else {
            CommonUtils.showAppToast(getActivity(), "No internet connection!");
        }
    }

    private void processDetails(){
        expType = expenseTypeList.get(spnExpenseType.getSelectedItemPosition()).getExpense_type_id();
        expDescription = desc.getText().toString();
        requestDate = CommonUtils.formatDate_ddMMyyyy(requestDate);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        ButterKnife.bind(this, view);

        initialize();

        btnSubmit.setOnClickListener(view1 -> {
            processDetails();
            if (spnExpenseType.getSelectedItemPosition()>0 && requestDate.length()>0 && expDescription.length()>0) {
                dialogueUtils.showWarningDialog("Confirm?", "Confirm uploading expense?",
                        this, EXPENSE_UPLOAD_WARNING);
            } else {
                CommonUtils.showAppToast(getActivity(), "Please fill all details");
            }
        });

        edate.setOnClickListener(view12 -> {
            DialogFragment newFragment = new DateTimePickerFragment(edate, 1);
            newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        });

        return view;
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        switch (requestCode){
            case EXPENSE_UPLOAD_WARNING:{
                uploadExpense();
                break;
            }
            case EXPENSE_UPLOAD_SUCCESS:{
                moveToScreen(Home.class, null, true);
                break;
            }
            case EXPENSE_UPLOAD_ERROR:{
                break;
            }
            case EXPENSE_TYPE_DOWNLOAD_ERROR:{
                break;
            }
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onExpenseTypesDownloaded(ArrayList<ExpenseTypeTrans> expenseList) {
        dismissProgress();
        expenseTypeList.addAll(expenseList);
        populateExpenseType(expenseTypeList);
    }

    @Override
    public void onExpenseUploaded(String response) {
        dismissProgress();
        dialogueUtils.showSuccessDialog("Success", "Expense uploaded successfully", this, EXPENSE_UPLOAD_SUCCESS);
    }

    @Override
    public void onApiResponseError(String message, int type) {
        dismissProgress();
        if (type == EXPENSE_UPLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Uploading failed!", this, EXPENSE_UPLOAD_ERROR);
        }else if (type == EXPENSE_TYPE_DOWNLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Failed to get expense list!", this, EXPENSE_TYPE_DOWNLOAD_ERROR);
        }
    }

    @Override
    public void onLocationUpdate(Location location, int i) {
        if (location != null) {
            AppSession.location = location;
            AppPreference.setAppLatitude(getActivity(), String.valueOf(location.getLatitude()));
            AppPreference.setAppLongitude(getActivity(), String.valueOf(location.getLongitude()));
        }
        webserviceUtils.insertExpense(this,expType, expDescription, requestDate);
    }

    @Override
    public void onLocationTrackUpdate(Location location, int i) {

    }

    @Override
    public void onLocationSettingsSatisfied() {
        GPSManager.getInstance().getLastLocation(getActivity());
    }


    @SuppressLint("ValidFragment")
    public static class DateTimePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener,
            TimePickerDialog.OnTimeSetListener {

        TextView dateTimeText;
        int type;

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
            requestDate = year + "-" + m + "-" + d;
            Date nDate;
            try {
                nDate = new SimpleDateFormat("yyyy-M-d").parse(requestDate);
                requestDate = new SimpleDateFormat("dd-MM-yyyy").format(nDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateTimeText.setText(d + "-" + m + "-" + year);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            requestTime = hourOfDay + ":" + minute;
            dateTimeText.setText(requestTime);
        }
    }
}
