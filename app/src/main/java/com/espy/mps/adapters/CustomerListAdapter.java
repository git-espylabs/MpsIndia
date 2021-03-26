package com.espy.mps.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espy.mps.R;
import com.espy.mps.app.AppSession;
import com.espy.mps.models.CustomerListingTrans;
import com.espy.mps.ui.activities.CustomerMenusActivity;
import com.espy.mps.ui.activities.LeadHistoryActivity;
import com.espy.mps.ui.customviews.CustomTextView;
import com.espy.mps.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerListAdapter  extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder>  {

    Context context;
    ArrayList<CustomerListingTrans> customerList;

    public CustomerListAdapter(Context context, ArrayList<CustomerListingTrans> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_customer_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerListingTrans model = customerList.get(position);

        int[] colorList = {R.drawable.oval_shape, R.drawable.oval_shape2, R.drawable.oval_shape3, R.drawable.oval_shape4, R.drawable.oval_shape5};

        holder.name.setText(CommonUtils.capitalizeSentence(model.getCustomer_name()));
        holder.phone.setText(model.getCustomer_primary_contact());
        if (model.getCustomer_designation() != null && !model.getCustomer_designation().equals("")) {
            holder.seprator1.setVisibility(View.VISIBLE);
            holder.designation.setText(model.getCustomer_designation());
        } else {
            holder.seprator1.setVisibility(View.GONE);
            holder.designation.setText("");
        }
        holder.nameIcon.setText(getNameIcon(CommonUtils.capitalizeSentence(model.getCustomer_name())));

        holder.mainLay.setOnClickListener(view -> {
            AppSession.customer_name  = CommonUtils.capitalizeSentence(model.getCustomer_name());
            AppSession.customer_lead_id = model.getCustomer_leadid();
            AppSession.customer_id = model.getCustomer_loginid();
            context.startActivity(new Intent(context, CustomerMenusActivity.class));
        });
    }

    private String getNameIcon(String name){
        String retValue = "TS";

        try {
            String[] splited = name.split(" ");
            String part1 = splited[0];
            String part2;
            if (splited.length>1) {
                part2 = splited[1];
            } else {
                part2 = splited[0];
            }

            retValue = part1.substring(0,1)+part2.substring(0,1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retValue;
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameIcon)
        TextView nameIcon;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.phone)
        TextView phone;

        @BindView(R.id.designation)
        TextView designation;

        @BindView(R.id.seprator1)
        View seprator1;



        @BindView(R.id.mainLay)
        LinearLayout mainLay;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
