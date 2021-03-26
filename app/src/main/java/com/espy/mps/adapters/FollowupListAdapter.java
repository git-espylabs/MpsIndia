package com.espy.mps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espy.mps.R;
import com.espy.mps.models.FeedbackTrans;
import com.espy.mps.models.FollowupTrans;
import com.espy.mps.ui.customviews.CustomTextView;
import com.espy.mps.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowupListAdapter  extends RecyclerView.Adapter<FollowupListAdapter.ViewHolder> {

    Context context;
    ArrayList<FollowupTrans> list;

    public FollowupListAdapter(Context context, ArrayList<FollowupTrans> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_followup_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FollowupTrans model = list.get(position);

        holder.fid.setText("Followup id: "+model.getLead_followup_id());

        holder.dateLead.setText("Followup date: "+ CommonUtils.formatDate_yyyyMMdd(model.getLead_followup_date()));

        holder.timeLead.setText("Followup time: "+model.getLead_followup_time());

        holder.closingDateLead.setText("Expected closing date: "+ CommonUtils.formatDate_yyyyMMdd(model.getLead_followup_expected_closing_date()));

        holder.lid.setText("Lead Id: "+model.getLead_followup_leadid());

        holder.comment.setText("Remarks: "+model.getLead_followup_remarks());

        holder.cName.setText("Customer Name: "+model.getLead_followup_cstmr_id());

        holder.acttivity_type.setText("Activity Type: "+model.getLead_followup_activity_typeid());

        holder.mainLay.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fid)
        TextView fid;

        @BindView(R.id.dateLead)
        TextView dateLead;

        @BindView(R.id.timeLead)
        TextView timeLead;

        @BindView(R.id.closingDateLead)
        TextView closingDateLead;

        @BindView(R.id.lid)
        TextView lid;

        @BindView(R.id.comment)
        TextView comment;

        @BindView(R.id.acttivity_type)
        TextView acttivity_type;

        @BindView(R.id.cName)
        TextView cName;

        @BindView(R.id.mainLay)
        LinearLayout mainLay;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
