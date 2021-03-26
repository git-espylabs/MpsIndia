package com.espy.mps.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espy.mps.R;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.CustomerCallbacks;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.models.CustomerDetailModelTrans;
import com.espy.mps.models.CustomerListingTrans;
import com.espy.mps.ui.activities.CustomerDetailsActivity;
import com.espy.mps.ui.activities.FeedbackActivity;
import com.espy.mps.ui.activities.FollowupActivity;
import com.espy.mps.ui.activities.NewLeadActivity;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_DETAILS_DOWNLOAD_ERROR;

public class CustomerMenusFragment extends BaseFragment implements View.OnClickListener, DialogInteractionListener, CustomerCallbacks {

    @BindView(R.id.customerName)
    TextView customerName;

    /* Customer details views */

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.desgination)
    TextView desgination;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.priority)
    TextView priority;
    /* Customer details views */

    /* Property details views */
    @BindView(R.id.interestLocation)
    TextView interestLocation;

    @BindView(R.id.propertyType)
    TextView propertyType;

    @BindView(R.id.budget)
    TextView budget;

    @BindView(R.id.propertyComment)
    TextView propertyComment;

    @BindView(R.id.projectName)
    TextView projectName;

    @BindView(R.id.ourPropertyComment)
    TextView ourPropertyComment;
    /* Property details views */

    /* Lead details views */
    @BindView(R.id.leadDate)
    TextView leadDate;

    @BindView(R.id.leadComment)
    TextView leadComment;
    /* Lead details views */

    @BindView(R.id.btnFollowUpDetail)
    Button btnFollowUpDetail;

    @BindView(R.id.btnFeedbackDetail)
    Button btnFeedbackDetail;



    WebserviceUtils webserviceUtils;

    DialogueUtils dialogueUtils;

    private void getCustomerDetails(){

        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(), "Getting Customer Details..");
            webserviceUtils.getCustomerDetails(this);
        } else {
            CommonUtils.showAppToast(getActivity(), "No Internet connection!");
        }
    }
    private void populateViews(CustomerDetailModelTrans model){
        try {
            customerName.setText(model.getCustomer_name());
            phone.setText(model.getCustomer_primary_contact());
            email.setText(model.getCustomer_email());
            location.setText(model.getCustomer_location());
            desgination.setText(model.getCustomer_designation());
            priority.setText(model.getCustomer_priority());
            interestLocation.setText(model.getCstmr_property_requirements_intrest_loc());
            propertyType.setText(model.getCstmr_property_requirements_type_of_propid());
            budget.setText(model.getCstmr_property_requirements_budget());
            propertyComment.setText(model.getCstmr_property_requirements_comments());
            projectName.setText(model.getProject_name());
            ourPropertyComment.setText(model.getOur_intrest_property_comment());
            leadDate.setText(CommonUtils.formatDate_yyyyMMdd(model.getLead_details_date()));
            leadComment.setText(model.getLead_details_comments());
        } catch (Exception e) {
            e.printStackTrace();
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Some details cannot be fetched, Please contact Admin",
                    this, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_menus, container, false);
        ButterKnife.bind(this, view);

        btnFollowUpDetail.setOnClickListener(this);
        btnFeedbackDetail.setOnClickListener(this);

        webserviceUtils = new WebserviceUtils(getActivity());
        dialogueUtils = new DialogueUtils(getActivity());

        customerName.setText(AppSession.customer_name);

        getCustomerDetails();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFollowUpDetail:{
                moveToScreen(FollowupActivity.class, null, true);
                break;
            }
            case R.id.btnFeedbackDetail:{
                moveToScreen(FeedbackActivity.class, null, true);
                break;
            }
        }
    }

    @Override
    public void onCustomerListDownloaded(ArrayList<CustomerListingTrans> customerList) {

    }

    @Override
    public void onCustomerDetailsDownloaded(ArrayList<CustomerDetailModelTrans> customerDetails) {
        dismissProgress();
        if (customerDetails != null && !customerDetails.isEmpty()) {
            AppSession.setCustomerDetailsObject(customerDetails.get(0));
            populateViews(customerDetails.get(0));
        }
    }

    @Override
    public void onApiResponseError(String message, int type) {
        dismissProgress();
        switch (type){
            case CUSTOMER_DETAILS_DOWNLOAD_ERROR:{
                dialogueUtils.showErrorDialog("OOPS!", "Customer details cannot be downloaded. Please contact admin",
                        this, CUSTOMER_DETAILS_DOWNLOAD_ERROR);
                break;
            }
        }
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        switch (requestCode){
            case CUSTOMER_DETAILS_DOWNLOAD_ERROR:{
                break;
            }
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }
}
