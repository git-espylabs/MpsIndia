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
import com.espy.mps.models.LeadHistoryTrans;
import com.espy.mps.ui.customviews.CustomTextView;
import com.espy.mps.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackListAdapater extends RecyclerView.Adapter<FeedbackListAdapater.ViewHolder>  {

    Context context;
    ArrayList<FeedbackTrans> list;

    public FeedbackListAdapater(Context context, ArrayList<FeedbackTrans> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_feedback_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedbackTrans model = list.get(position);

        holder.fid.setText("Feedback id: "+model.getLead_feedback_id());

        holder.status.setText("Current Status: "+model.getLead_feedback_current_status());

        holder.lid.setText("Lead Id: "+model.getLead_feedback_leadid());

        holder.comment.setText("Remarkks: "+model.getLead_feedback_remarks());

        holder.cName.setText("Customer Name: "+model.getLead_feedback_customer_id());

        holder.acttivity_type.setText("Activity Type: "+model.getLead_feedback_activity_typeid());

        holder.dateLead.setText("Feedback Added date: "+ CommonUtils.formatDate_yyyyMMddHHmmss(model.getLead_feedback_addeddate()));

        holder.mainLay.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment)
        TextView comment;

        @BindView(R.id.fid)
        TextView fid;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.lid)
        TextView lid;

        @BindView(R.id.acttivity_type)
        TextView acttivity_type;

        @BindView(R.id.cName)
        TextView cName;

        @BindView(R.id.dateLead)
        TextView dateLead;

        @BindView(R.id.mainLay)
        LinearLayout mainLay;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
