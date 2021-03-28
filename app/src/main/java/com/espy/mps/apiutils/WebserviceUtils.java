package com.espy.mps.apiutils;

import android.content.Context;
import android.content.res.AssetManager;

import com.espy.mps.R;
import com.espy.mps.app.AppSession;
import com.espy.mps.app.Applog;
import com.espy.mps.interfaces.AppVersionCallback;
import com.espy.mps.interfaces.AttendanceCallbacks;
import com.espy.mps.interfaces.CommonApiInteractionListener;
import com.espy.mps.interfaces.CustomerCallbacks;
import com.espy.mps.interfaces.ExpenseInterfaces;
import com.espy.mps.interfaces.FeedbackCallback;
import com.espy.mps.interfaces.FollowupCallback;
import com.espy.mps.interfaces.HomeIteneriesCallback;
import com.espy.mps.interfaces.IteneriesCallBack;
import com.espy.mps.interfaces.LeadCallbacks;
import com.espy.mps.interfaces.LeadIteneriesCallback;
import com.espy.mps.interfaces.LeaveCallbacks;
import com.espy.mps.interfaces.LoginCallbacks;
import com.espy.mps.interfaces.NotesInterface;
import com.espy.mps.interfaces.WorkDetailsCallBack;
import com.espy.mps.models.AcitivityTypeMaster;
import com.espy.mps.models.AppVersionMaster;
import com.espy.mps.models.CustomerDetailModelMaster;
import com.espy.mps.models.CustomerListingMaster;
import com.espy.mps.models.CustomerPriorityMaster;
import com.espy.mps.models.CustomerPropertyDetailMaster;
import com.espy.mps.models.CustomerStatusMaster;
import com.espy.mps.models.ExpenseTypeMaster;
import com.espy.mps.models.FeedbackMaster;
import com.espy.mps.models.FolloupMaster;
import com.espy.mps.models.FollowupCountParser;
import com.espy.mps.models.IndividualFollowupMaster;
import com.espy.mps.models.IndividualFollowupSearchMaster;
import com.espy.mps.models.IntrestPropertyMaster;
import com.espy.mps.models.LeadFromDetailsMaster;
import com.espy.mps.models.LeadHistoryMaster;
import com.espy.mps.models.NewLeadModel;
import com.espy.mps.models.NewLeaveModel;
import com.espy.mps.models.ProjectNameListMaster;
import com.espy.mps.models.UserMaster;
import com.espy.mps.models.WorkIndustryDetailMaster;
import com.espy.mps.preferences.AppPreference;
import com.espy.mps.utils.DialogueUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.espy.mps.utils.CommonUtils.stream2file;
import static com.espy.mps.utils.DialogueUtils.ACTIVITY_TYPES_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_DETAILS_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_LIST_DOWNLAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.CUSTOMER_STATUS_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.FEEDBACK_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.FOLLOWUP_DOWNLOAD_ERROR;
import static com.espy.mps.utils.DialogueUtils.LEAD_HISTORY_DOWNLOAD_SUCCESS;

public class WebserviceUtils {
    Context context;
    GetDataService service;

    public WebserviceUtils(Context context) {
        this.context = context;
        service = ResApiRetro.getRetrofitInstance().create(GetDataService.class);
    }

    public void processAppLogin(String username, String password, LoginCallbacks callbacks){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("username", username);
            json.put("password", password);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<UserMaster> call = service.authenticateUser(body);
        call.enqueue(new Callback<UserMaster>() {
            @Override
            public void onResponse(Call<UserMaster> call, Response<UserMaster> response) {
                try {
                    Applog.logString("ws:login_android-", "Request: " + call.request().toString());
                    if (response != null && response.body() != null && response.body().getLogin_det() != null && !response.body().getLogin_det().isEmpty()) {
                        Applog.logString("ws:login_android-", "Response Success: " + response.body().toString());
                        callbacks.onLoginSuccess(response.body());
                    } else {
                        callbacks.onLoginFail(context.getResources().getString(R.string.common_error_message));
                        Applog.logString("ws:login_android-", "Response Success: " + "Null object received");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Applog.logString("ws:login_android-", "Response exception: " + e.getMessage());
                    callbacks.onLoginFail(context.getResources().getString(R.string.common_error_message));
                }
            }

            @Override
            public void onFailure(Call<UserMaster> call, Throwable t) {
                Applog.logString("ws:login_android-", "Request: " + call.request().toString());
                Applog.logString("ws:login_android-", "onFailure: " + t.getMessage());
                callbacks.onLoginFail(context.getResources().getString(R.string.common_exception_message));
            }
        });
    }

    public void processAttendance(String status, String lat, String longs, AttendanceCallbacks callbacks){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("attend_staff_loginid", AppPreference.getPrefUserId(context));
            json.put("attend_status", status);
            json.put("attend_latt", lat);
            json.put("attend_long", longs);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CommonResponseParser> call = service.sendAttendance(body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    Applog.logString("ws:login_android-", "Request: " + call.request().toString());
                    if (response != null && response.body() != null && response.body().getResult() != null && !response.body().getResult().equals("") && !response.body().getResult().equals("0")) {
                        Applog.logString("ws:login_android-", "Response Success: " + response.body().toString());
                        callbacks.onAttendanceSend(response.body().getResult());
                    } else {
                        callbacks.onSendingAttendanceFailed(context.getResources().getString(R.string.common_error_message));
                        Applog.logString("ws:login_android-", "Response Success: " + "Null object received");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Applog.logString("ws:login_android-", "Response exception: " + e.getMessage());
                    callbacks.onSendingAttendanceFailed(context.getResources().getString(R.string.common_error_message));
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                Applog.logString("ws:login_android-", "Request: " + call.request().toString());
                Applog.logString("ws:login_android-", "onFailure: " + t.getMessage());
                callbacks.onSendingAttendanceFailed(context.getResources().getString(R.string.common_exception_message));
            }
        });
    }

    public void getExpenseTypesList(ExpenseInterfaces callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<ExpenseTypeMaster> call = service.getExpenseTypesList(body);
        call.enqueue(new Callback<ExpenseTypeMaster>() {
            @Override
            public void onResponse(Call<ExpenseTypeMaster> call, Response<ExpenseTypeMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getExpenseList() != null && !response.body().getExpenseList().isEmpty()) {
                        callback.onExpenseTypesDownloaded(response.body().getExpenseList());
                    } else {
                        callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), DialogueUtils.EXPENSE_TYPE_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), DialogueUtils.EXPENSE_TYPE_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ExpenseTypeMaster> call, Throwable t) {
                callback.onApiResponseError(context.getResources().getString(R.string.common_exception_message), DialogueUtils.EXPENSE_TYPE_DOWNLOAD_ERROR);
            }
        });
    }

    public void insertExpense(ExpenseInterfaces callback, String type, String description, String date){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("expense_stafflogin_id", AppPreference.getPrefUserId(context));
            json.put("expense_date", date);
            json.put("expense_exptype_id", type);
            json.put("expense_desc", description);
            json.put("expense_latt", AppPreference.getAppLatitude(context));
            json.put("expense_long", AppPreference.getAppLongitude(context));
            jsonData = json.toString();
            jsonData = jsonData.replaceAll("\\\\", "");
            jsonData.trim();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CommonResponseParser> call = service.insertExpense(body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() !=null) {
                        callback.onExpenseUploaded(response.body().getResult());
                    } else {
                        callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), DialogueUtils.EXPENSE_UPLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), DialogueUtils.EXPENSE_UPLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callback.onApiResponseError(context.getResources().getString(R.string.common_exception_message), DialogueUtils.EXPENSE_UPLOAD_ERROR);
            }
        });
    }

    public void insertNotes(NotesInterface callback, String description){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("notes_added_loginid", AppPreference.getPrefUserId(context));
            json.put("notes_desc", description);
            json.put("notes_latt", AppPreference.getAppLatitude(context));
            json.put("notes_long", AppPreference.getAppLongitude(context));
            jsonData = json.toString();
            jsonData = jsonData.replaceAll("\\\\", "");
            jsonData.trim();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CommonResponseParser> call = service.insertNotes(body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() !=null) {
                        callback.onNotesUploadee();
                    } else {
                        callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), DialogueUtils.EXPENSE_UPLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), DialogueUtils.EXPENSE_UPLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callback.onApiResponseError(context.getResources().getString(R.string.common_exception_message), DialogueUtils.EXPENSE_UPLOAD_ERROR);
            }
        });
    }

    public void getCustomersList(CustomerCallbacks callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("staff_loginid", AppPreference.getPrefUserId(context));
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CustomerListingMaster> call = service.getCustomersList(body);
        call.enqueue(new Callback<CustomerListingMaster>() {
            @Override
            public void onResponse(Call<CustomerListingMaster> call, Response<CustomerListingMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getCustomerList() != null && !response.body().getCustomerList().isEmpty()) {
                        callback.onCustomerListDownloaded(response.body().getCustomerList());
                    } else {
                        callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), CUSTOMER_LIST_DOWNLAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), CUSTOMER_LIST_DOWNLAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CustomerListingMaster> call, Throwable t) {
                callback.onApiResponseError(context.getResources().getString(R.string.common_exception_message), CUSTOMER_LIST_DOWNLAD_ERROR);
            }
        });
    }

    public void getLeadHistoryList(LeadCallbacks callback,String customer_id){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("customer_id", customer_id);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<LeadHistoryMaster> call = service.getLeadHistoryList(body);
        call.enqueue(new Callback<LeadHistoryMaster>() {
            @Override
            public void onResponse(Call<LeadHistoryMaster> call, Response<LeadHistoryMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getLeadList() != null && !response.body().getLeadList().isEmpty()) {
                        callback.onLeadHistoryDwnloaded(response.body().getLeadList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), LEAD_HISTORY_DOWNLOAD_SUCCESS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), LEAD_HISTORY_DOWNLOAD_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<LeadHistoryMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), LEAD_HISTORY_DOWNLOAD_SUCCESS);
            }
        });
    }

    public void getCustomerDetails(CustomerCallbacks callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("lead_id", AppSession.customer_lead_id);
            json.put("customer_id", AppSession.customer_id);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CustomerDetailModelMaster> call = service.getCustomersDetails(body);
        call.enqueue(new Callback<CustomerDetailModelMaster>() {
            @Override
            public void onResponse(Call<CustomerDetailModelMaster> call, Response<CustomerDetailModelMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getCustomerPropDetailList() != null && !response.body().getCustomerPropDetailList().isEmpty()) {
                        callback.onCustomerDetailsDownloaded(response.body().getCustomerPropDetailList());
                    } else {
                        callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), CUSTOMER_DETAILS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiResponseError(context.getResources().getString(R.string.common_error_message), CUSTOMER_DETAILS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CustomerDetailModelMaster> call, Throwable t) {
                callback.onApiResponseError(context.getResources().getString(R.string.common_exception_message), CUSTOMER_DETAILS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getFeedbackList(FeedbackCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("lead_id", AppSession.customer_lead_id);
            json.put("customer_id", AppSession.customer_id);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<FeedbackMaster> call = service.getFeedbackList(body);
        call.enqueue(new Callback<FeedbackMaster>() {
            @Override
            public void onResponse(Call<FeedbackMaster> call, Response<FeedbackMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getFeedbackList() != null && !response.body().getFeedbackList().isEmpty()) {
                        callback.onFeedbackHistoryDownloaded(response.body().getFeedbackList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), FEEDBACK_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), FEEDBACK_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<FeedbackMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), FEEDBACK_DOWNLOAD_ERROR);
            }
        });
    }

    public void getFollowupList(FollowupCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("lead_id", AppSession.customer_lead_id);
            json.put("customer_id", AppSession.customer_id);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<FolloupMaster> call = service.getFollowupList(body);
        call.enqueue(new Callback<FolloupMaster>() {
            @Override
            public void onResponse(Call<FolloupMaster> call, Response<FolloupMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getFolowUpList() != null && !response.body().getFolowUpList().isEmpty()) {
                        callback.onFollowupHistoryDownloaded(response.body().getFolowUpList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), FOLLOWUP_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), FOLLOWUP_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<FolloupMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), FOLLOWUP_DOWNLOAD_ERROR);
            }
        });
    }

    public void insertLead(LeadCallbacks callbacks, NewLeadModel model){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("customer_name", model.getCustomer_name());
            json.put("customer_designation", model.getCustomer_designation());
            json.put("primary_contact", model.getPrimary_contact());
            json.put("secondary_contact",model.getSecondary_contact());
            json.put("customer_email", model.getCustomer_email());
            json.put("customer_location", model.getCustomer_location());
            json.put("customer_workindus", model.getCustomer_workindus());
            json.put("customer_priority",model.getCustomer_priority());
            json.put("customer_name", model.getCustomer_name());
            json.put("interested_prop", model.getInterested_prop());
            json.put("interested_comment", model.getInterested_comment());
            json.put("customer_proploc",model.getCustomer_proploc());
            json.put("cusprop_budget", model.getCusprop_budget());
            json.put("custprop_comment", model.getCustprop_comment());
            json.put("custprop_project", model.getCustprop_project());
            json.put("lead_fromid",model.getLead_fromid());
            json.put("lead_comment",model.getLead_comment());
            json.put("lead_date",model.getLead_date());

            jsonData = json.toString();
            jsonData = jsonData.replaceAll("\\\\", "");
            jsonData.trim();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CommonResponseParser> call = service.insertLead(body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() !=null) {
                        callbacks.onLeadUploaded(response.body().getResult());
                    } else {
                        callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), DialogueUtils.LEAD_UPLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), DialogueUtils.LEAD_UPLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), DialogueUtils.LEAD_UPLOAD_ERROR);
            }
        });
    }

    public void insertFeedback(FeedbackCallback callbacks, String activity_type, String current_status, String lead_feedback_remarks){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("activity_type", activity_type);
            json.put("current_status", current_status);
            json.put("lead_id", AppSession.customer_lead_id);
            json.put("customer_id", AppSession.customer_id);
            json.put("lead_feedback_remarks", lead_feedback_remarks);

            jsonData = json.toString();
            jsonData = jsonData.replaceAll("\\\\", "");
            jsonData.trim();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CommonResponseParser> call = service.insertFeedback(body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() !=null) {
                        callbacks.onFeedbackUploaded(response.body().getResult());
                    } else {
                        callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), DialogueUtils.FEEDBACK_UPLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), DialogueUtils.FEEDBACK_UPLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), DialogueUtils.FEEDBACK_UPLOAD_ERROR);
            }
        });
    }

    public void insertFollowup(FollowupCallback callbacks, String activity_type, String current_status, String lead_followup_date,
                               String lead_followup_time, String expected_closing_date, String lead_followup_remarks){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("activitytype_id", activity_type);
            json.put("current_status", current_status);
            json.put("lead_id", AppSession.customer_lead_id);
            json.put("customer_id", AppSession.customer_id);
            json.put("lead_followup_date", lead_followup_date);
            json.put("lead_followup_time", lead_followup_time);
            json.put("expected_closing_date", expected_closing_date);
            json.put("lead_followup_remarks", AppSession.customer_lead_id);

            jsonData = json.toString();
            jsonData = jsonData.replaceAll("\\\\", "");
            jsonData.trim();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CommonResponseParser> call = service.insertFollowup(body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() !=null) {
                        callbacks.onFollowupUploaded(response.body().getResult());
                    } else {
                        callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), DialogueUtils.FOLLOWUP_UPLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), DialogueUtils.FOLLOWUP_UPLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), DialogueUtils.FOLLOWUP_UPLOAD_ERROR);
            }
        });
    }

    public void insertLeve(LeaveCallbacks callbacks, NewLeaveModel model){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("leave_from_date", model.getLeave_from_date());
            json.put("leave_todate", model.getLeave_todate());
            json.put("leave_count", model.getLeave_count());
            json.put("leave_desc",model.getLeave_desc());
            json.put("leave_addedstaff_loginid", AppPreference.getPrefUserId(context));

            jsonData = json.toString();
            jsonData = jsonData.replaceAll("\\\\", "");
            jsonData.trim();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CommonResponseParser> call = service.insertLeave(body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() !=null) {
                        callbacks.onLeaveUploaded(response.body().getResult());
                    } else {
                        callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_error_message));
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callbacks.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message));
            }
        });
    }

    public void getActivityTypes(IteneriesCallBack callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<AcitivityTypeMaster> call = service.getActivityTypes(body);
        call.enqueue(new Callback<AcitivityTypeMaster>() {
            @Override
            public void onResponse(Call<AcitivityTypeMaster> call, Response<AcitivityTypeMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getActivityTypesList() != null && !response.body().getActivityTypesList().isEmpty()) {
                        callback.onActivityTypesDownladed(response.body().getActivityTypesList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), ACTIVITY_TYPES_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), ACTIVITY_TYPES_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<AcitivityTypeMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), ACTIVITY_TYPES_DOWNLOAD_ERROR);
            }
        });
    }

    public void getCustomerStatus(IteneriesCallBack callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CustomerStatusMaster> call = service.getCustomerStatus(body);
        call.enqueue(new Callback<CustomerStatusMaster>() {
            @Override
            public void onResponse(Call<CustomerStatusMaster> call, Response<CustomerStatusMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getCustomerStatusList() != null && !response.body().getCustomerStatusList().isEmpty()) {
                        callback.onCustomerStatusDownladed(response.body().getCustomerStatusList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CustomerStatusMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getIntrestPropertyDetails(LeadIteneriesCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<IntrestPropertyMaster> call = service.getIntrestPropertyDetails(body);
        call.enqueue(new Callback<IntrestPropertyMaster>() {
            @Override
            public void onResponse(Call<IntrestPropertyMaster> call, Response<IntrestPropertyMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getInterestPropertyList() != null && !response.body().getInterestPropertyList().isEmpty()) {
                        callback.onIntrestPropertyDetailsDownloaded(response.body().getInterestPropertyList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<IntrestPropertyMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getLeadFromDetailsList(LeadIteneriesCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<LeadFromDetailsMaster> call = service.getLeadFromDetailsList(body);
        call.enqueue(new Callback<LeadFromDetailsMaster>() {
            @Override
            public void onResponse(Call<LeadFromDetailsMaster> call, Response<LeadFromDetailsMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getLeadFromDetailsList() != null && !response.body().getLeadFromDetailsList().isEmpty()) {
                        callback.onLeadFromDetailsDownloaded(response.body().getLeadFromDetailsList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<LeadFromDetailsMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getWorkIndustryDetailsList(LeadIteneriesCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<WorkIndustryDetailMaster> call = service.getWorkIndustryDetailsList(body);
        call.enqueue(new Callback<WorkIndustryDetailMaster>() {
            @Override
            public void onResponse(Call<WorkIndustryDetailMaster> call, Response<WorkIndustryDetailMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getWorkIndustryDetailsList() != null && !response.body().getWorkIndustryDetailsList().isEmpty()) {
                        callback.onWorkIndustryDetailsDownloaded(response.body().getWorkIndustryDetailsList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<WorkIndustryDetailMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getCustomerPropertyDetailsList(LeadIteneriesCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CustomerPropertyDetailMaster> call = service.getCustomerPropertyDetailsList(body);
        call.enqueue(new Callback<CustomerPropertyDetailMaster>() {
            @Override
            public void onResponse(Call<CustomerPropertyDetailMaster> call, Response<CustomerPropertyDetailMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getCustomerPropertyDetailsList() != null && !response.body().getCustomerPropertyDetailsList().isEmpty()) {
                        callback.onCustomerPropertyDetailsDownloaded(response.body().getCustomerPropertyDetailsList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CustomerPropertyDetailMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getCustomerPriorityList(LeadIteneriesCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<CustomerPriorityMaster> call = service.getCustomerPriorityList(body);
        call.enqueue(new Callback<CustomerPriorityMaster>() {
            @Override
            public void onResponse(Call<CustomerPriorityMaster> call, Response<CustomerPriorityMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getCustomerPrioritiesList() != null && !response.body().getCustomerPrioritiesList().isEmpty()) {
                        callback.onustomerPriorityDownloaded(response.body().getCustomerPrioritiesList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CustomerPriorityMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getIndividualFollowupList(FollowupCallback callback, String day){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("followupdate", day);
            json.put("staff_id", AppPreference.getPrefUserId(context));
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<IndividualFollowupMaster> call = service.getIndividualFollowupList(body);
        call.enqueue(new Callback<IndividualFollowupMaster>() {
            @Override
            public void onResponse(Call<IndividualFollowupMaster> call, Response<IndividualFollowupMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getFollowUpList() != null && !response.body().getFollowUpList().isEmpty()) {
                        callback.onIndividualFollowupsDownloaded(response.body().getFollowUpList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<IndividualFollowupMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getIndividualFollowupCount(HomeIteneriesCallback callback, String day){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("followupdate", day);
            json.put("staff_id", AppPreference.getPrefUserId(context));
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<FollowupCountParser> call = service.getIndividualFollowupCount(body);
        call.enqueue(new Callback<FollowupCountParser>() {
            @Override
            public void onResponse(Call<FollowupCountParser> call, Response<FollowupCountParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getCount() != null && !response.body().getCount().isEmpty()) {
                        callback.onFollowupCountGot(response.body().getCount());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<FollowupCountParser> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void getIndividualFollowupSearchList(FollowupCallback callback, String from_date, String to_date){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            json.put("to_date", to_date);
            json.put("from_date", from_date);
            json.put("staff_id", AppPreference.getPrefUserId(context));
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<IndividualFollowupSearchMaster> call = service.getIndividualFollowupSearchList(body);
        call.enqueue(new Callback<IndividualFollowupSearchMaster>() {
            @Override
            public void onResponse(Call<IndividualFollowupSearchMaster> call, Response<IndividualFollowupSearchMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getFollowUpList() != null && !response.body().getFollowUpList().isEmpty()) {
                        callback.onIndividualFollowupsDownloaded(response.body().getFollowUpList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<IndividualFollowupSearchMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), CUSTOMER_STATUS_DOWNLOAD_ERROR);
            }
        });
    }

    public void insertWorkDetails(WorkDetailsCallBack callBack, String title, String desc, String imagePath, String status, String projectid){

        File file = null;
        if (imagePath != null && imagePath.length()>0) {
            file = new File(imagePath);
        } else {
            AssetManager am = context.getAssets();
            try {
                InputStream inputStream = am.open("defaults/noimg.jpg");
                file = stream2file(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        RequestBody title_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody desc_d    =
                RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        RequestBody status_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody projectid_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), projectid);
        RequestBody lati    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLatitude(context));
        RequestBody longi    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLongitude(context));
        RequestBody addedby    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUserId(context));
        RequestBody utype    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUType(context));


        Call<CommonResponseParser> call = service.insertWorkDetails(title_t,desc_d, addedby, status_t, lati, longi, projectid_t, utype ,body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() != null) {
                        callBack.onDetailsUploadSuccess(response.body().getResult());
                    } else {
                        callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1115);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1115);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callBack.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), 1115);
            }
        });
    }

    public void getProjectIds(WorkDetailsCallBack callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<ProjectNameListMaster> call = service.getProjectIds(body);
        call.enqueue(new Callback<ProjectNameListMaster>() {
            @Override
            public void onResponse(Call<ProjectNameListMaster> call, Response<ProjectNameListMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getProjectList() != null && !response.body().getProjectList().isEmpty()) {
                        callback.onProjectListDownladed(response.body().getProjectList());
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1113);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1113);
                }
            }

            @Override
            public void onFailure(Call<ProjectNameListMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), 1113);
            }
        });
    }

    public void insertVehicleDetails(WorkDetailsCallBack callBack, String title, String desc, String imagePath, String status, String projectid){

        File file = null;
        if (imagePath != null && imagePath.length()>0) {
            file = new File(imagePath);
        } else {
            AssetManager am = context.getAssets();
            try {
                InputStream inputStream = am.open("defaults/noimg.jpg");
                file = stream2file(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        RequestBody title_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody desc_d    =
                RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        RequestBody status_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody projectid_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), projectid);
        RequestBody lati    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLatitude(context));
        RequestBody longi    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLongitude(context));
        RequestBody addedby    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUserId(context));
        RequestBody utype    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUType(context));


        Call<CommonResponseParser> call = service.insertVehicleDetails(title_t,desc_d, addedby, status_t, lati, longi, projectid_t, utype ,body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() != null) {
                        callBack.onDetailsUploadSuccess(response.body().getResult());
                    } else {
                        callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callBack.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), 1117);
            }
        });
    }

    public void insertMaterialDetails(WorkDetailsCallBack callBack, String title, String desc, String imagePath, String status, String projectid){

        File file = null;
        if (imagePath != null && imagePath.length()>0) {
            file = new File(imagePath);
        } else {
            AssetManager am = context.getAssets();
            try {
                InputStream inputStream = am.open("defaults/noimg.jpg");
                file = stream2file(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        RequestBody title_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody desc_d    =
                RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        RequestBody status_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody projectid_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), projectid);
        RequestBody lati    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLatitude(context));
        RequestBody longi    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLongitude(context));
        RequestBody addedby    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUserId(context));
        RequestBody utype    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUType(context));


        Call<CommonResponseParser> call = service.insertMateralDetails(title_t,desc_d, addedby, lati, longi, projectid_t, utype ,body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() != null) {
                        callBack.onDetailsUploadSuccess(response.body().getResult());
                    } else {
                        callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callBack.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), 1117);
            }
        });
    }

    public void insertMaterialDetails2(WorkDetailsCallBack callBack, String title, String desc, String imagePath, String status, String projectid){

        File file = null;
        if (imagePath != null && imagePath.length()>0) {
            file = new File(imagePath);
        } else {
            AssetManager am = context.getAssets();
            try {
                InputStream inputStream = am.open("defaults/noimg.jpg");
                file = stream2file(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        RequestBody title_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody desc_d    =
                RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        RequestBody status_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody projectid_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), projectid);
        RequestBody lati    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLatitude(context));
        RequestBody longi    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLongitude(context));
        RequestBody addedby    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUserId(context));
        RequestBody utype    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUType(context));


        Call<CommonResponseParser> call = service.insertMateralDetails2(title_t,desc_d, addedby, lati, longi, projectid_t, utype ,body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() != null) {
                        callBack.onDetailsUploadSuccess(response.body().getResult());
                    } else {
                        callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callBack.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), 1117);
            }
        });
    }

    public void insertPurchaseBill(WorkDetailsCallBack callBack, String title, String desc, String imagePath, String status, String projectid){

        File file = null;
        if (imagePath != null && imagePath.length()>0) {
            file = new File(imagePath);
        } else {
            AssetManager am = context.getAssets();
            try {
                InputStream inputStream = am.open("defaults/noimg.jpg");
                file = stream2file(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        RequestBody title_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody desc_d    =
                RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        RequestBody status_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody projectid_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), projectid);
        RequestBody lati    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLatitude(context));
        RequestBody longi    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLongitude(context));
        RequestBody addedby    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUserId(context));
        RequestBody utype    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUType(context));


        Call<CommonResponseParser> call = service.insertPurchaseBill(title_t,desc_d, addedby, lati, longi, projectid_t, utype ,body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() != null) {
                        callBack.onDetailsUploadSuccess(response.body().getResult());
                    } else {
                        callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callBack.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), 1117);
            }
        });
    }

    public void insertMateralRequest(WorkDetailsCallBack callBack, String title, String desc, String imagePath, String projectid){

        File file = null;
        if (imagePath != null && imagePath.length()>0) {
            file = new File(imagePath);
        } else {
            AssetManager am = context.getAssets();
            try {
                InputStream inputStream = am.open("defaults/noimg.jpg");
                file = stream2file(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        RequestBody title_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody desc_d    =
                RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        RequestBody projectid_t    =
                RequestBody.create(MediaType.parse("multipart/form-data"), projectid);
        RequestBody lati    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLatitude(context));
        RequestBody longi    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getAppLongitude(context));
        RequestBody addedby    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUserId(context));
        RequestBody utype    =
                RequestBody.create(MediaType.parse("multipart/form-data"), AppPreference.getPrefUType(context));


        Call<CommonResponseParser> call = service.insertMateralRequest(title_t,desc_d, addedby, lati, longi, projectid_t, utype ,body);
        call.enqueue(new Callback<CommonResponseParser>() {
            @Override
            public void onResponse(Call<CommonResponseParser> call, Response<CommonResponseParser> response) {
                try {
                    if (response != null && response.body() != null && response.body().getResult() != null) {
                        callBack.onDetailsUploadSuccess(response.body().getResult());
                    } else {
                        callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onApiErrorResponse(context.getResources().getString(R.string.common_error_message), 1117);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseParser> call, Throwable t) {
                callBack.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message), 1117);
            }
        });
    }


    public void getAppVersion(AppVersionCallback callback){
        String jsonData = "";
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("secure_key", ApiConstants.APP_SECURE_KEY);
            jsonData = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonData);
        Call<AppVersionMaster> call = service.getAppVersionFromServer(body);
        call.enqueue(new Callback<AppVersionMaster>() {
            @Override
            public void onResponse(Call<AppVersionMaster> call, Response<AppVersionMaster> response) {
                try {
                    if (response != null && response.body() != null && response.body().getAppVersionTrans() != null && !response.body().getAppVersionTrans().isEmpty()) {
                        callback.onAppVersionReceived(response.body().getAppVersionTrans().get(0));
                    } else {
                        callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onApiErrorResponse(context.getResources().getString(R.string.common_error_message));
                }
            }

            @Override
            public void onFailure(Call<AppVersionMaster> call, Throwable t) {
                callback.onApiErrorResponse(context.getResources().getString(R.string.common_exception_message));
            }
        });
    }


}
