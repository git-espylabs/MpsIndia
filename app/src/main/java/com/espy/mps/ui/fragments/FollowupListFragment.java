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
import com.espy.mps.adapters.FollowupListAdapter;
import com.espy.mps.adapters.IndividualFolloupAdapter;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.FollowupCallback;
import com.espy.mps.models.FollowupTrans;
import com.espy.mps.models.IndividualFollowupTrans;
import com.espy.mps.ui.activities.FollowupActivity;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.FOLLOWUP_DOWNLOAD_ERROR;

public class FollowupListFragment extends BaseFragment implements FollowupCallback, DialogInteractionListener {


    @BindView(R.id.list)
    RecyclerView list;

    String customer_id = "";


    private RecyclerView.LayoutManager layoutManager;

    private void getHistoryList(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Feedback list..");
            customer_id = AppSession.customer_id;
            new WebserviceUtils(getActivity()).getFollowupList(this);
        } else {
            CommonUtils.showAppToast(getActivity(), "No Internet connection!");
        }
    }

    private void getIndividualHistoryList(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Followup list..");
            String day = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            new WebserviceUtils(getActivity()).getIndividualFollowupList(this, day);
        } else {
            CommonUtils.showAppToast(getActivity(), "No Internet connection!");
        }
    }

    private void populateViews(ArrayList<FollowupTrans>  arrayList){
        FollowupListAdapter adapterNew = new FollowupListAdapter(getActivity(),arrayList);
        list.setAdapter(adapterNew);
        adapterNew.notifyDataSetChanged();
    }

    private void populateIndividualViews(ArrayList<IndividualFollowupTrans>  arrayList){
        IndividualFolloupAdapter adapterNew = new IndividualFolloupAdapter(getActivity(),arrayList);
        list.setAdapter(adapterNew);
        adapterNew.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        if (AppSession.isIndividual) {
            getIndividualHistoryList();
        }else {
            getHistoryList();
        }

        return view;
    }

    @Override
    public void onPositiveResponse(int requestCode) {
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onFollowupHistoryDownloaded(ArrayList<FollowupTrans> followupList) {
        dismissProgress();
        populateViews(followupList);
    }

    @Override
    public void onFollowupUploaded(String result) {

    }

    @Override
    public void onIndividualFollowupsDownloaded(ArrayList<IndividualFollowupTrans> followupList) {
        dismissProgress();
        populateIndividualViews(followupList);
    }

    @Override
    public void onApiErrorResponse(String message, int type) {
        dismissProgress();
        new DialogueUtils(getActivity()).showErrorDialog("OOPS",
                "Could not fetch records, Please contact admin.",this, FOLLOWUP_DOWNLOAD_ERROR);
    }
}
