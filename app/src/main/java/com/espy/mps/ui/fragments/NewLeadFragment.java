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
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.LeadCallbacks;
import com.espy.mps.interfaces.LeadIteneriesCallback;
import com.espy.mps.models.CustomerPriorityTrans;
import com.espy.mps.models.CustomerPropertyDetailTrans;
import com.espy.mps.models.InterestPropertyTrans;
import com.espy.mps.models.LeadFromDetailTrans;
import com.espy.mps.models.LeadHistoryTrans;
import com.espy.mps.models.NewLeadModel;
import com.espy.mps.models.WorkIndustryDetailsTrans;
import com.espy.mps.ui.activities.CustomerMenusActivity;
import com.espy.mps.ui.activities.Home;
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
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_PRIORITIES_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_PROPERTY_TYPES_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.INTRESTED_PROPERTY_TYPES_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.LEAD_FROM_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.LEAD_UPLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.LEAD_UPLOAD_SUCCESS;
import static com.espy.mps.utils.DialogueUtils.LEAD_UPLOAD_WARNING;
import static com.espy.mps.utils.DialogueUtils.WORKING_INDUSTRY_TYPES_DOWNLOAD_ERROR;

public class NewLeadFragment extends BaseFragment implements DialogInteractionListener, LeadCallbacks, LeadIteneriesCallback {


    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    @BindView(R.id.leadDate)
    TextView leadDate;

    @BindView(R.id.spnIndusTypes)
    Spinner spnIndusTypes;

    @BindView(R.id.spnPriority)
    Spinner spnPriority;

    @BindView(R.id.spnInterstProperty)
    Spinner spnInterstProperty;

    @BindView(R.id.spnProject)
    Spinner spnProject;

    @BindView(R.id.spnLeadFrom)
    Spinner spnLeadFrom;

    @BindView(R.id.cstName)
    EditText cstName;

    @BindView(R.id.cstDesgn)
    EditText cstDesgn;

    @BindView(R.id.cstPContct)
    EditText cstPContct;

    @BindView(R.id.secContact)
    EditText secContact;

    @BindView(R.id.etemail)
    EditText etemail;

    @BindView(R.id.etlocation)
    EditText etlocation;

    @BindView(R.id.etintrestComment)
    EditText etintrestComment;

    @BindView(R.id.etbudget)
    EditText etbudget;

    @BindView(R.id.etpropertyComment)
    EditText etpropertyComment;

    @BindView(R.id.desc)
    EditText desc;

    @BindView(R.id.etinPropertLocation)
    EditText etinPropertLocation;




    ArrayList<InterestPropertyTrans> interestPropertyList = new ArrayList<>();
    ArrayAdapter<InterestPropertyTrans> interestPropertyAdapter;

    ArrayList<LeadFromDetailTrans> leadFromDetailsList = new ArrayList<>();
    ArrayAdapter<LeadFromDetailTrans> leadFromDetailsAdapter;

    ArrayList<WorkIndustryDetailsTrans> workIndustryDetailsList = new ArrayList<>();
    ArrayAdapter<WorkIndustryDetailsTrans> workIndustryDetailsAdapter;

    ArrayList<CustomerPropertyDetailTrans> customerPropertyDetailsList = new ArrayList<>();
    ArrayAdapter<CustomerPropertyDetailTrans> customerPropertyDetailsAdapter;

    ArrayList<CustomerPriorityTrans> customerPrioritiesList = new ArrayList<>();
    ArrayAdapter<CustomerPriorityTrans> customerPrioritiesAdapter;

    String customer_name = "";
    String customer_designation = "";
    String primary_contact = "";
    String secondary_contact = "";
    String customer_email = "";
    String customer_location = "";
    String customer_workindus = "";
    String customer_priority = "";
    String interested_prop = "";
    String interested_comment = "";
    String cusprop_budget = "";
    String custprop_comment = "";
    String custprop_project = "";
    String lead_fromid = "";
    String lead_comment = "";
    String customer_proploc = "";
    private static String lead_date = "";

    WebserviceUtils webserviceUtils;

    DialogueUtils dialogueUtils;

    private void getToday(){
        lead_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        String ndate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        leadDate.setText(ndate);
    }

    private void populatetCustomerPropertyTypes(){
        customerPropertyDetailsAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, customerPropertyDetailsList);
        customerPropertyDetailsAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnProject.setAdapter(customerPropertyDetailsAdapter);
    }

    private void populatetLeadFromTypes(){
        leadFromDetailsAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, leadFromDetailsList);
        leadFromDetailsAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnLeadFrom.setAdapter(leadFromDetailsAdapter);
    }

    private void populatetWorkIndustryTypes(){
        workIndustryDetailsAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, workIndustryDetailsList);
        workIndustryDetailsAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnIndusTypes.setAdapter(workIndustryDetailsAdapter);
    }

    private void populatetInterestPropertyTypes(){
        interestPropertyAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, interestPropertyList);
        interestPropertyAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnInterstProperty.setAdapter(interestPropertyAdapter);
    }

    private void populatetCustomerPriorityTypes(){
        customerPrioritiesAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, customerPrioritiesList);
        customerPrioritiesAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnPriority.setAdapter(customerPrioritiesAdapter);
    }


    private void getInterestPropertyTypes(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Properties data..");
            interestPropertyList.clear();
            interestPropertyList.add(new InterestPropertyTrans("0", "--Select Interested property--"));
            new WebserviceUtils(getActivity()).getIntrestPropertyDetails(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void getCustomerPropertyTypes(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Customer Properties data..");
            customerPropertyDetailsList.clear();
            customerPropertyDetailsList.add(new CustomerPropertyDetailTrans("0", "--Select Project--"));
            new WebserviceUtils(getActivity()).getCustomerPropertyDetailsList(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void getLeadFromTypes(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Lead from types..");
            leadFromDetailsList.clear();
            leadFromDetailsList.add(new LeadFromDetailTrans("0", "--Select Lead from--"));
            new WebserviceUtils(getActivity()).getLeadFromDetailsList(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void getWorkIndustryTypes(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting industry types..");
            workIndustryDetailsList.clear();
            workIndustryDetailsList.add(new WorkIndustryDetailsTrans("0", "--Select Working Industry--"));
            new WebserviceUtils(getActivity()).getWorkIndustryDetailsList(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }

    private void getCustomerPriorityTypes(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Customer priorities data..");
            customerPrioritiesList.clear();
            customerPrioritiesList.add(new CustomerPriorityTrans("0", "--Select Priority--"));
            new WebserviceUtils(getActivity()).getCustomerPriorityList(this);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }



    private void submitData(){
        customer_name = cstName.getText().toString();
        customer_designation = cstDesgn.getText().toString();
        primary_contact = cstPContct.getText().toString();
        secondary_contact = secContact.getText().toString();
        customer_email = etemail.getText().toString();
        customer_location = etlocation.getText().toString();
        interested_comment = etintrestComment.getText().toString();
        cusprop_budget = etbudget.getText().toString();
        custprop_comment = etpropertyComment.getText().toString();
        lead_comment = desc.getText().toString();
        customer_proploc = etinPropertLocation.getText().toString();
        customer_workindus = workIndustryDetailsList.get(spnIndusTypes.getSelectedItemPosition()).getWrin_id();
        customer_priority = customerPrioritiesList.get(spnPriority.getSelectedItemPosition()).getCustomer_priority_id();
        custprop_project = interestPropertyList.get(spnInterstProperty.getSelectedItemPosition()).getProject_id();
        interested_prop = customerPropertyDetailsList.get(spnProject.getSelectedItemPosition()).getCus_need_prop_id();
        lead_fromid = leadFromDetailsList.get(spnLeadFrom.getSelectedItemPosition()).getLdty_id();

        NewLeadModel model = new NewLeadModel(customer_name, customer_designation, primary_contact, secondary_contact, customer_email, customer_location,
                customer_workindus, customer_priority, interested_prop, interested_comment, customer_proploc, cusprop_budget, custprop_comment,
                custprop_project, lead_fromid, lead_comment, lead_date);

        if (NetworkUtils.isNetworkConnected(getActivity())){
            showProgress(getActivity(),"Processing data..");
            webserviceUtils.insertLead(this, model);
        } else {
            CommonUtils.showAppToast(getActivity(),"No Internet connection!");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_lead, container, false);
        ButterKnife.bind(this, view);

        webserviceUtils = new WebserviceUtils(getActivity());

        dialogueUtils = new DialogueUtils(getActivity());

        getToday();

        getCustomerPropertyTypes();

        btnSubmit.setOnClickListener(view1 -> {
            dialogueUtils.showWarningDialog("Confirm?", "Upload deatails?", this, LEAD_UPLOAD_WARNING);
        });

        leadDate.setOnClickListener(view12 -> {
            DialogFragment newFragment = new FollowupFragment.DateTimePickerFragment(leadDate, 1);
            newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        });

        return view;
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        switch (requestCode){
            case LEAD_UPLOAD_WARNING:{
                submitData();
                break;
            }
            case LEAD_UPLOAD_SUCCESS:{
                moveToScreen(Home.class, null, true);
                break;
            }
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onLeadUploaded(String result) {
        dismissProgress();
        dialogueUtils.showSuccessDialog("SUCCESS", "New Lead Added!",
                this, LEAD_UPLOAD_SUCCESS);
    }

    @Override
    public void onLeadHistoryDwnloaded(ArrayList<LeadHistoryTrans> leadList) {

    }

    @Override
    public void onIntrestPropertyDetailsDownloaded(ArrayList<InterestPropertyTrans> interestPropertyList) {
        dismissProgress();
        this.interestPropertyList.addAll(interestPropertyList);
        populatetInterestPropertyTypes();
        getLeadFromTypes();
    }

    @Override
    public void onLeadFromDetailsDownloaded(ArrayList<LeadFromDetailTrans> leadFromDetailsList) {
        dismissProgress();
        this.leadFromDetailsList.addAll(leadFromDetailsList);
        populatetLeadFromTypes();
        getWorkIndustryTypes();
    }

    @Override
    public void onWorkIndustryDetailsDownloaded(ArrayList<WorkIndustryDetailsTrans> workIndustryDetailsList) {
        dismissProgress();
        this.workIndustryDetailsList.addAll(workIndustryDetailsList);
        populatetWorkIndustryTypes();
        getCustomerPriorityTypes();
    }

    @Override
    public void onCustomerPropertyDetailsDownloaded(ArrayList<CustomerPropertyDetailTrans> customerPropertyDetailsList) {
        dismissProgress();
        this.customerPropertyDetailsList.addAll(customerPropertyDetailsList);
        populatetCustomerPropertyTypes();
        getInterestPropertyTypes();
    }

    @Override
    public void onustomerPriorityDownloaded(ArrayList<CustomerPriorityTrans> customerPrioritiesList) {
        dismissProgress();
        this.customerPrioritiesList.addAll(customerPrioritiesList);
        populatetCustomerPriorityTypes();
    }

    @Override
    public void onApiErrorResponse(String message, int type) {
        dismissProgress();
        if (type == CUSTOMER_PROPERTY_TYPES_DOWNLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Project types not available",
                    this, CUSTOMER_PROPERTY_TYPES_DOWNLOAD_ERROR);
        }else if (type == INTRESTED_PROPERTY_TYPES_DOWNLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Customer interested properties not available",
                    this, INTRESTED_PROPERTY_TYPES_DOWNLOAD_ERROR);
        }else if (type == WORKING_INDUSTRY_TYPES_DOWNLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Working industry types not available",
                    this, WORKING_INDUSTRY_TYPES_DOWNLOAD_ERROR);
        }else if (type == LEAD_FROM_DOWNLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Lead from types not available",
                    this, LEAD_FROM_DOWNLOAD_ERROR);
        }else if (type == CUSTOMER_PRIORITIES_DOWNLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "Customer Priority types not available",
                    this, CUSTOMER_PRIORITIES_DOWNLOAD_ERROR);
        }else if (type == LEAD_UPLOAD_ERROR){
            dialogueUtils.showErrorDialog("OOPS!", "New Lead upload failed, contact admin",
                    this, LEAD_UPLOAD_ERROR);
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
                lead_date = new SimpleDateFormat("yyyy-MM-dd").format(nDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateTimeText.setText(reqDate);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            reqTime = hourOfDay + ":" + minute;
//            lead_date = reqTime;
            dateTimeText.setText(reqTime);
        }
    }
}
