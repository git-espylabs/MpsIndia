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
import com.espy.mps.models.LeadHistoryTrans;
import com.espy.mps.ui.customviews.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeadListAdapter  extends RecyclerView.Adapter<LeadListAdapter.ViewHolder>   {

    Context context;
    ArrayList<LeadHistoryTrans> list;

    public LeadListAdapter(Context context, ArrayList<LeadHistoryTrans> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_lead_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeadHistoryTrans model = list.get(position);

        holder.comment.setText(model.getLead_details_comments());

        holder.dateLead.setText(model.getLead_details_date());

        holder.mainLay.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment)
        CustomTextView comment;

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
