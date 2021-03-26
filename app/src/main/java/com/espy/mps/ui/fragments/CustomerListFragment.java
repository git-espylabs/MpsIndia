package com.espy.mps.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espy.mps.R;
import com.espy.mps.adapters.CustomerListAdapter;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.CustomerCallbacks;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.models.CustomerDetailModelTrans;
import com.espy.mps.models.CustomerListingTrans;
import com.espy.mps.ui.activities.Home;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_LIST_DOWNLAD_ERROR;

public class CustomerListFragment extends BaseFragment implements CustomerCallbacks, DialogInteractionListener {


    @BindView(R.id.custList)
    RecyclerView custList;


    private RecyclerView.LayoutManager layoutManager;

    private void getCustomerList(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Customer list..");
            new WebserviceUtils(getActivity()).getCustomersList(this);
        } else {
            CommonUtils.showAppToast(getActivity(), "No Internet connection!");
        }
    }

    private void populateViews(ArrayList<CustomerListingTrans>  list){
        CustomerListAdapter adapterNew = new CustomerListAdapter(getActivity(),list);
        custList.setAdapter(adapterNew);
        adapterNew.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custoomer_list, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        custList.setLayoutManager(layoutManager);

        getCustomerList();

        return view;
    }

    @Override
    public void onCustomerListDownloaded(ArrayList<CustomerListingTrans> customerList) {
        dismissProgress();
        populateViews(customerList);
    }

    @Override
    public void onCustomerDetailsDownloaded(ArrayList<CustomerDetailModelTrans> customerDetails) {

    }

    @Override
    public void onApiResponseError(String message, int type) {
        dismissProgress();
        new DialogueUtils(getActivity()).showErrorDialog("OOPS",
                "Could not get customer list, Please contact admin.",this, CUSTOMER_LIST_DOWNLAD_ERROR);
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        if (requestCode == CUSTOMER_LIST_DOWNLAD_ERROR){
            moveToScreen(Home.class, null, true);
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }
}
