package com.espy.mps.apiutils;

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
import com.espy.mps.models.ProjectNameListMaster;
import com.espy.mps.models.UserMaster;
import com.espy.mps.models.WorkIndustryDetailMaster;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService {

    @POST("login_android")
    Call<UserMaster> authenticateUser(@Body RequestBody json);

    @POST("add_attendance")
    Call<CommonResponseParser> sendAttendance(@Body RequestBody json);

    @Multipart
    @POST("display_pic")
    Call<CommonResponseParser> insertDisplayPic(@Part("displaypic_supmrktid") RequestBody displaypic_supmrktid,
                                                @Part("displaypic_latt") RequestBody displaypic_latt,
                                                @Part("displaypic_long") RequestBody displaypic_long,
                                                @Part("displaypic_added_accountid") RequestBody displaypic_added_accountid,
                                                @Part("displaypic_name") RequestBody displaypic_name,
                                                @Part("displaypic_divisionid") RequestBody displaypic_divisionid,
                                                @Part MultipartBody.Part image);

    @POST("expense_type")
    Call<ExpenseTypeMaster> getExpenseTypesList(@Body RequestBody json);

    @POST("add_expense")
    Call<CommonResponseParser> insertExpense(@Body RequestBody json);

    @POST("add_notes")
    Call<CommonResponseParser> insertNotes(@Body RequestBody json);

    @POST("customer_det")
    Call<CustomerListingMaster> getCustomersList(@Body RequestBody json);

    @POST("lead_det")
    Call<LeadHistoryMaster> getLeadHistoryList(@Body RequestBody json);

    @POST("customer_and_prop_det")
    Call<CustomerDetailModelMaster> getCustomersDetails(@Body RequestBody json);

    @POST("customer_feedback_det")
    Call<FeedbackMaster> getFeedbackList(@Body RequestBody json);

    @POST("customer_followup_det")
    Call<FolloupMaster> getFollowupList(@Body RequestBody json);

    @POST("add_leave")
    Call<CommonResponseParser> insertLeave(@Body RequestBody json);

    @POST("add_newlead")
    Call<CommonResponseParser> insertLead(@Body RequestBody json);

    @POST("get_lead_activity_type")
    Call<AcitivityTypeMaster> getActivityTypes(@Body RequestBody json);

    @POST("get_lead_customer_status")
    Call<CustomerStatusMaster> getCustomerStatus(@Body RequestBody json);

    @POST("add_feedback")
    Call<CommonResponseParser> insertFeedback(@Body RequestBody json);

    @POST("add_followup")
    Call<CommonResponseParser> insertFollowup(@Body RequestBody json);

    @POST("intrestprop_det")
    Call<IntrestPropertyMaster> getIntrestPropertyDetails(@Body RequestBody json);

    @POST("leadfrom_det")
    Call<LeadFromDetailsMaster> getLeadFromDetailsList(@Body RequestBody json);

    @POST("workindustry_det")
    Call<WorkIndustryDetailMaster> getWorkIndustryDetailsList(@Body RequestBody json);

    @POST("customerprop_det")
    Call<CustomerPropertyDetailMaster> getCustomerPropertyDetailsList(@Body RequestBody json);

    @POST("customer_priority_det")
    Call<CustomerPriorityMaster> getCustomerPriorityList(@Body RequestBody json);

    @POST("nextfollowupdatecount_det")
    Call<IndividualFollowupMaster> getIndividualFollowupList(@Body RequestBody json);

    @POST("nextfollowupdatecount")
    Call<FollowupCountParser> getIndividualFollowupCount(@Body RequestBody json);

    @POST("nextfolloupfromtodatesearch")
    Call<IndividualFollowupSearchMaster> getIndividualFollowupSearchList(@Body RequestBody json);

    @Multipart
    @POST("daily_works")
    Call<CommonResponseParser> insertWorkDetails(@Part("worktitle") RequestBody displaypic_supmrktid,
                                                @Part("workdesc") RequestBody displaypic_latt,
                                                 @Part("addedby") RequestBody addedby,
                                                 @Part("workstatus") RequestBody workstatus,
                                                 @Part("work_lati") RequestBody work_lati,
                                                 @Part("work_long") RequestBody work_long,
                                                 @Part("project_id") RequestBody project_id,
                                                 @Part("usertype") RequestBody usertype,
                                                @Part MultipartBody.Part image);

    @Multipart
    @POST("add_vehicle_load_details")
    Call<CommonResponseParser> insertVehicleDetails(@Part("vehicle_title") RequestBody displaypic_supmrktid,
                                                 @Part("vehicle_desc") RequestBody displaypic_latt,
                                                 @Part("user_id") RequestBody addedby,
                                                 @Part("vehicle_num") RequestBody workstatus,
                                                 @Part("vehicle_latt") RequestBody work_lati,
                                                 @Part("vehicle_long") RequestBody work_long,
                                                 @Part("project_id") RequestBody project_id,
                                                 @Part("usertype_id") RequestBody usertype,
                                                 @Part MultipartBody.Part image);

    @Multipart
    @POST("add_material_used_details")
    Call<CommonResponseParser> insertMateralDetails(@Part("mat_use_title") RequestBody displaypic_supmrktid,
                                                    @Part("mat_use_desc") RequestBody displaypic_latt,
                                                    @Part("mat_use_userid") RequestBody addedby,
                                                    @Part("mat_use_latt") RequestBody work_lati,
                                                    @Part("mat_use_long") RequestBody work_long,
                                                    @Part("mat_use_project_id") RequestBody project_id,
                                                    @Part("mat_use_usertype_id") RequestBody usertype,
                                                    @Part MultipartBody.Part image);

    @Multipart
    @POST("add_material_balance_details")
    Call<CommonResponseParser> insertMateralDetails2(@Part("mat_baldet_title") RequestBody displaypic_supmrktid,
                                                    @Part("mat_baldet_desc") RequestBody displaypic_latt,
                                                    @Part("mat_baldet_userid") RequestBody addedby,
                                                    @Part("mat_baldet_latt") RequestBody work_lati,
                                                    @Part("mat_baldet_long") RequestBody work_long,
                                                    @Part("mat_baldet_project_id") RequestBody project_id,
                                                    @Part("mat_baldet_usertypeid") RequestBody usertype,
                                                    @Part MultipartBody.Part image);

    @Multipart
    @POST("add_purchase_details")
    Call<CommonResponseParser> insertPurchaseBill(@Part("purch_title") RequestBody displaypic_supmrktid,
                                                     @Part("purch_desc") RequestBody displaypic_latt,
                                                     @Part("purch_userid") RequestBody addedby,
                                                     @Part("purch_latt") RequestBody work_lati,
                                                     @Part("purch_long") RequestBody work_long,
                                                     @Part("purch_project_id") RequestBody project_id,
                                                     @Part("purch_usertypeid") RequestBody usertype,
                                                     @Part MultipartBody.Part image);

    @Multipart
    @POST("add_material_request_details")
    Call<CommonResponseParser> insertMateralRequest(@Part("mat_req_title") RequestBody displaypic_supmrktid,
                                                     @Part("mat_req_desc") RequestBody displaypic_latt,
                                                     @Part("mat_req_userid") RequestBody addedby,
                                                     @Part("mat_req_latt") RequestBody work_lati,
                                                     @Part("mat_req_long") RequestBody work_long,
                                                     @Part("mat_req_project_id") RequestBody project_id,
                                                     @Part("mat_req_usertype_id") RequestBody usertype,
                                                     @Part MultipartBody.Part image);

    @POST("project_name")
    Call<ProjectNameListMaster> getProjectIds(@Body RequestBody json);

    @POST("version_check")
    Call<AppVersionMaster> getAppVersionFromServer(@Body RequestBody json);
}
