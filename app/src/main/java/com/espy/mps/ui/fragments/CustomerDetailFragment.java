package com.espy.mps.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espy.mps.R;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.models.CustomerDetailModelTrans;
import com.espy.mps.utils.DialogueUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerDetailFragment extends BaseFragment implements DialogInteractionListener {

    /* Customer details views */
    @BindView(R.id.customerName)
    TextView customerName;

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
            leadDate.setText(model.getLead_details_date());
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
        View view = inflater.inflate(R.layout.fragment_customer_details_display, container, false);
        ButterKnife.bind(this, view);

        CustomerDetailModelTrans customerDetailObject = AppSession.getCustomerDetailsObject();

        if (customerDetailObject != null){
            populateViews(customerDetailObject);
        }else {
            new DialogueUtils(getActivity()).showErrorDialog("OOPS!", "Some details cannot be fetched, Please contact Admin",
                    this, 0);
        }

        return view;
    }

    @Override
    public void onPositiveResponse(int requestCode) {

    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }
}
