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
import com.espy.mps.adapters.FeedbackListAdapater;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.FeedbackCallback;
import com.espy.mps.models.FeedbackTrans;
import com.espy.mps.ui.activities.FeedbackActivity;
import com.espy.mps.utils.CommonUtils;
import com.espy.mps.utils.DialogueUtils;
import com.espy.mps.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.espy.mps.base.BaseActivity.dismissProgress;
import static com.espy.mps.base.BaseActivity.showProgress;
import static com.espy.mps.utils.DialogueUtils.FEEDBACK_DOWNLOAD_ERROR;

public class FeedbackListFragment extends BaseFragment implements FeedbackCallback, DialogInteractionListener {


    @BindView(R.id.list)
    RecyclerView list;

    String customer_id = "";


    private RecyclerView.LayoutManager layoutManager;

    private void getHistoryList(){
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            showProgress(getActivity(),"Getting Feedback list..");
            customer_id = AppSession.customer_id;
            new WebserviceUtils(getActivity()).getFeedbackList(this);
        } else {
            CommonUtils.showAppToast(getActivity(), "No Internet connection!");
        }
    }

    private void populateViews(ArrayList<FeedbackTrans>  arrayList){
        FeedbackListAdapater adapterNew = new FeedbackListAdapater(getActivity(),arrayList);
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

        getHistoryList();

        return view;
    }

    @Override
    public void onPositiveResponse(int requestCode) {
        if (requestCode == FEEDBACK_DOWNLOAD_ERROR){
            moveToScreen(FeedbackActivity.class, null, true);
        }
    }

    @Override
    public void onNegetiveResponse(int requestCode) {

    }

    @Override
    public void onFeedbackHistoryDownloaded(ArrayList<FeedbackTrans> feedbackList) {
        dismissProgress();
        populateViews(feedbackList);
    }

    @Override
    public void onFeedbackUploaded(String result) {

    }

    @Override
    public void onApiErrorResponse(String message, int type) {
        dismissProgress();
        new DialogueUtils(getActivity()).showErrorDialog("OOPS",
                "Could not fetch records, Please contact admin.",this, FEEDBACK_DOWNLOAD_ERROR);
    }
}
