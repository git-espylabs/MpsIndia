package com.espy.mps.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.espy.mps.R;
import com.espy.mps.interfaces.DialogInteractionListener;

public class DialogueUtils {


    public static final int INVALID_LOGIN_CREDENTIAL_WARNING = 1080;
    public static final int LOGIN_ERROR = 1081;
    public static final int ATTENDANCE_UPLOAD_SUCCESS = 1085;
    public static final int ATTENDANCE_UPLOAD_ERROR = 1086;
    public static final int EXPENSE_UPLOAD_SUCCESS = 1082;
    public static final int EXPENSE_UPLOAD_ERROR = 1083;
    public static final int EXPENSE_TYPE_DOWNLOAD_ERROR = 1087;
    public static final int EXPENSE_UPLOAD_WARNING = 1084;
    public static final int NOTES_UPLOAD_SUCCESS = 1088;
    public static final int NOTES_UPLOAD_ERROR = 1089;
    public static final int NOTES_UPLOAD_WARNING = 1090;
    public static final int CUSTOMER_LIST_DOWNLAD_ERROR = 1091;
    public static final int LEAD_UPLOAD_SUCCESS = 1092;
    public static final int LEAD_UPLOAD_ERROR = 1093;
    public static final int LEAD_UPLOAD_WARNING = 1094;
    public static final int LEAD_HISTORY_DOWNLOAD_SUCCESS = 1095;
    public static final int LEAD_HISTORY_DOWNLOAD_ERROR = 1096;
    public static final int CUSTOMER_LIST_DOWNLAD_SUCCESS = 1097;
    public static final int CUSTOMER_DETAILS_DOWNLOAD_SUCCESS = 1098;
    public static final int CUSTOMER_DETAILS_DOWNLOAD_ERROR = 1099;
    public static final int FEEDBACK_DOWNLOAD_ERROR = 1100;
    public static final int FEEDBACK_UPLOAD_ERROR = 1101;
    public static final int FOLLOWUP_DOWNLOAD_ERROR = 1102;
    public static final int FOLLOWUP_UPLOAD_ERROR = 1103;
    public static final int ACTIVITY_TYPES_DOWNLOAD_ERROR = 1104;
    public static final int CUSTOMER_STATUS_DOWNLOAD_ERROR = 1105;
    public static final int FOLLOWUP_UPLOAD_SUCCESS = 1106;
    public static final int FEEDBACK_UPLOAD_SUCCESS = 1107;
    public static final int CUSTOMER_PROPERTY_TYPES_DOWNLOAD_ERROR = 1108;
    public static final int INTRESTED_PROPERTY_TYPES_DOWNLOAD_ERROR = 1109;
    public static final int WORKING_INDUSTRY_TYPES_DOWNLOAD_ERROR = 1110;
    public static final int LEAD_FROM_DOWNLOAD_ERROR = 1111;
    public static final int CUSTOMER_PRIORITIES_DOWNLOAD_ERROR = 1112;
    public static final int WORK_DEATAILS_PROJECT_NAMES_DOWNLOAD_ERROR = 1113;
    public static final int WORK_DEATAILS_PROJECT_NAMES_DOWNLOAD_SUCCESS = 1114;
    public static final int WORK_DEATAILS_UPLOAD_ERROR = 1115;
    public static final int WORK_DEATAILS_UPLOAD_SUCCESS = 1116;
    public static final int COMN_DEATAILS_UPLOAD_ERROR = 1117;
    public static final int COMN_DEATAILS_UPLOAD_SUCCESS = 1118;

    Context context;

    public DialogueUtils(Context context) {
        this.context = context;
    }

    public void showSuccessDialog(String title, String messgae, DialogInteractionListener listener, int dialogueRequestCode) {
        final Dialog dialog = new Dialog(context, R.style.custom_ui_dialogue);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.custom_dialog_success);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView text =dialog.findViewById(R.id.message);
        Button buttonCancel =  dialog.findViewById(R.id.buttonCancel);
        Button buttonOk =  dialog.findViewById(R.id.buttonOk);

        text.setText(messgae);
        buttonOk.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onPositiveResponse(dialogueRequestCode);
            }
        });

        dialog.show();
    }

    public void showWarningDialog(String title, String messgae, DialogInteractionListener listener, int dialogueRequestCode) {
        final Dialog dialog = new Dialog(context, R.style.custom_ui_dialogue);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.custom_dialog_warning);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView text =dialog.findViewById(R.id.message);
        Button buttonCancel =  dialog.findViewById(R.id.buttonCancel);
        Button buttonOk =  dialog.findViewById(R.id.buttonOk);

        text.setText(messgae);
        buttonOk.setOnClickListener(v ->
        {
            dialog.dismiss();
            if (listener != null) {
                listener.onPositiveResponse(dialogueRequestCode);
            }
        });
        buttonCancel.setOnClickListener(v ->
        {
            dialog.dismiss();
            if (listener != null) {
                listener.onNegetiveResponse(dialogueRequestCode);
            }
        });

        dialog.show();
    }

    public void showErrorDialog(String title, String messgae, DialogInteractionListener listener, int dialogueRequestCode) {
        final Dialog dialog = new Dialog(context, R.style.custom_ui_dialogue);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.custom_dialog_error);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView text =dialog.findViewById(R.id.message);
        Button buttonCancel =  dialog.findViewById(R.id.buttonCancel);
        Button buttonOk =  dialog.findViewById(R.id.buttonOk);

        text.setText(messgae);
        buttonOk.setOnClickListener(v ->
        {
            dialog.dismiss();
            if (listener != null) {
                listener.onPositiveResponse(dialogueRequestCode);
            }
        });

        dialog.show();
    }

    public void showConfirmationDialog(String title, String messgae, DialogInteractionListener listener, int dialogueRequestCode) {
        final Dialog dialog = new Dialog(context, R.style.custom_ui_dialogue);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.custom_dialog_warning);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView text =dialog.findViewById(R.id.message);
        Button buttonCancel =  dialog.findViewById(R.id.buttonCancel);
        Button buttonOk =  dialog.findViewById(R.id.buttonOk);

        text.setText(messgae);
        buttonOk.setOnClickListener(v ->
        {
            dialog.dismiss();
            if (listener != null) {
                listener.onPositiveResponse(dialogueRequestCode);
            }
        });
        buttonCancel.setOnClickListener(v ->
        {
            dialog.dismiss();
            if (listener != null) {
                listener.onNegetiveResponse(dialogueRequestCode);
            }
        });

        dialog.show();
    }


    public void showUpdateNotice(String title, String messgae, DialogInteractionListener listener, int dialogueRequestCode) {
        final Dialog dialog = new Dialog(context, R.style.custom_ui_dialogue);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_warning);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView statusText =dialog.findViewById(R.id.statusText);
        TextView text =dialog.findViewById(R.id.message);
        Button buttonCancel =  dialog.findViewById(R.id.buttonCancel);
        Button buttonOk =  dialog.findViewById(R.id.buttonOk);

        statusText.setText(title);
        text.setText(messgae);
        buttonCancel.setVisibility(View.GONE);
        buttonOk.setText("Update");

        buttonOk.setOnClickListener(v ->
        {
            dialog.dismiss();
            if (listener != null) {
                listener.onPositiveResponse(dialogueRequestCode);
            }
        });

        /*buttonCancel.setOnClickListener(v ->
        {
            dialog.dismiss();
            if (listener != null) {
                listener.onNegetiveResponse(dialogueRequestCode);
            }
        });*/

        dialog.show();
    }

}

