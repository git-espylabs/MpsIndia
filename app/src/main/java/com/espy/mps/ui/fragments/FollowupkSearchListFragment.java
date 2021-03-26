package com.espy.mps.ui.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espy.mps.R;
import com.espy.mps.adapters.FollowupListAdapter;
import com.espy.mps.adapters.IndividualFolloupAdapter;
import com.espy.mps.apiutils.WebserviceUtils;
import com.espy.mps.app.AppSession;
import com.espy.mps.base.BaseFragment;
import com.espy.mps.interfaces.DialogInteractionListener;
import com.espy.mps.interfaces.FeedbackCallback;
import com.espy.mps.interfaces.FollowupCallback;
import com.espy.mps.models.FeedbackTrans;
import com.espy.mps.models.FollowupTrans;
import com.espy.mps.models.IndividualFollowupTrans;
import com.espy.mps.ui.activities.FollowupActivity;
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
import static com.espy.mps.utils.DialogueUtils.FOLLOWUP_DOWNLOAD_ERROR;

public class FollowupkSearchListFragment extends BaseFragment implements FollowupCallback, DialogInteractionListener {



    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.fdate)
    TextView fdate;

    @BindView(R.id.tdate)
    TextView tdate;

    @BindView(R.id.srch)
    TextView srch;

    String customer_id = "";

    private static String requestDate = "";
    private static String requestTime = "";

    private void getToday(){
        requestDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        requestTime = new SimpleDateFormat("HH:mm a").format(Calendar.getInstance().getTime());

        fdate.setText(requestDate);
        tdate.setText(requestDate);
    }

    private String[] getConvertDates(){
        String frmdate = fdate.getText().toString();
        String todate = tdate.getText().toString();

        try {
            Date frdate = new SimpleDateFormat("dd-MM-yyyy").parse(frmdate);
            frmdate = new SimpleDateFormat("yyyy-MM-dd").format(frdate);

            Date trdate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
            todate = new SimpleDateFormat("yyyy-MM-dd").format(trdate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{frmdate, todate};
    }


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

            new WebserviceUtils(getActivity()).getIndividualFollowupSearchList(this, getConvertDates()[0], getConvertDates()[1]);
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
        View view = inflater.inflate(R.layout.fragment_followup_search, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        getToday();

        fdate.setOnClickListener(view1 -> {

            DialogFragment newFragment = new DateTimePickerFragment(fdate, 1);
            newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        });

        tdate.setOnClickListener(view1 -> {

            DialogFragment newFragment = new DateTimePickerFragment(tdate, 1);
            newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        });

        srch.setOnClickListener(view12 -> {
            if (AppSession.isIndividual) {
                getIndividualHistoryList();
            }else {
                getHistoryList();
            }
        });

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
