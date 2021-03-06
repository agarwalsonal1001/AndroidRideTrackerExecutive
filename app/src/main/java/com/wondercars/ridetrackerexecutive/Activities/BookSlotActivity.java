package com.wondercars.ridetrackerexecutive.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wondercars.ridetrackerexecutive.Adapters.GenericSpinnerAdapter;
import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.CustomClasses.ExpandableHeightGridView;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.CheckIfSlotAvailable.CheckSlotRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.CheckIfSlotAvailable.CheckSlotResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCarModelsDTOs.CarModels;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCarModelsDTOs.GetCarModelsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCarModelsDTOs.GetCarModelsResponseObject;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCarsDTOs.GetCarsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetCarsDTOs.GetCarsResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetVeriantsDTOs.GetVeriantsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetVeriantsDTOs.GetVeriantsResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.GetVeriantsDTOs.VeriantsDetails;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsResponseObj;
import com.wondercars.ridetrackerexecutive.Utils.APIConstants;
import com.wondercars.ridetrackerexecutive.Utils.AppConstants;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;
import umer.accl.utils.MyAppsLog;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ResponseObjectType.CAR_MODELS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ResponseObjectType.CAR_REGISTRATION_NUMBER;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ResponseObjectType.VERIANT_DETAILS;
import static umer.accl.utils.DateUtils.DD_MMM_YYYY_DASH_DATE_FORMAT;
import static umer.accl.utils.DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT;
import static umer.accl.utils.DateUtils.HH_MM_TIME_FORMAT;
import static umer.accl.utils.DateUtils.YYYY_MM_dd_DASH_DATE_FORMAT;

public class BookSlotActivity extends BaseActivity {

    private final int GET_VARIANTS_SERVICE_ID = 0;
    private final int GET_CARMODELS_SERVICEID = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_select_car_models)
    Spinner spinnerSelectCarModels;
    @BindView(R.id.spinner_select_variant)
    Spinner spinnerSelectVariant;
    @BindView(R.id.spinner_select_registration_number)
    Spinner spinnerSelectRegistrationNumber;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.ll_pick_date)
    LinearLayout llPickDate;
    @BindView(R.id.tv_selected_date)
    TextView tvSelectedDate;
    @BindView(R.id.gridv_timeslots)
    ExpandableHeightGridView gridvTimeslots;
    @BindView(R.id.ll_carAvailableOrNot)
    LinearLayout ll_carAvailableOrNot;
    @BindView(R.id.tv_fromTime)
    TextView tvFromTime;
    @BindView(R.id.tv_toTime)
    TextView tvToTime;

    /*@BindView(R.id.tv_fromTime)
    TextView tvFromTime;
    @BindView(R.id.tv_toTime)
    TextView tvToTime;*/

    /* @BindView(R.id.spinner_select_from_time)
     Spinner spinnerSelectFromTime;
     @BindView(R.id.spinner_select_to_time)
     Spinner spinnerSelectToTime;*/
    private StringBuffer variantId, carModel, registrationNumber;
    String carId = "", fromTime = "", toTime = "", bookingSelectedDate = "",currentDate = "";
    private GetCarsRequestObj getCarsRequestObj;
    private final int GET_CARS_SERVICE_ID = 2, GET_SLOTS_SERVICE_ID = 3, UPSERT_SLOTS_SERVICE_ID = 4, AVAILABLE_SLOTS_SERVICE_ID = 5, CHECK_SLOTISAVAILABLE_SERVICE_ID = 6;
    HashMap<Integer, BookingSlotsObj> bookingSlotsObjHashMap;
    int fromTimeInInt = 0, toTimeInInt = 0, dayOfMonthForRide = 0, month = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);
        ButterKnife.bind(this);
        init();
        setActionBar(toolbar, "Book Test Drive Slot");
    }

    private void init() {
        currentDate = DateUtils.getCurrentDate(DateUtils.DD_MMM_YYYY_DASH_DATE_FORMAT);
        bookingSlotsObjHashMap = new HashMap<>();
        variantId = new StringBuffer();
        carModel = new StringBuffer();
        registrationNumber = new StringBuffer();
        //callGetVariantsAPI();
        callGetCarModelsAPI();
    }

/*

    private GetAvailableSlotsRequestObj getAvailableSlotsRequestObj() {
        GetAvailableSlotsRequestObj getAvailableSlotsRequestObj = new GetAvailableSlotsRequestObj();
        getAvailableSlotsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        getAvailableSlotsRequestObj.setCarId(carId);
        getAvailableSlotsRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        try {
            getAvailableSlotsRequestObj.setBookingDate(tvSelectedDate.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getAvailableSlotsRequestObj;
    }

    private void callAvailableGetSlotAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getAvailableSlotsRequestObj(), GetAvailableSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_AVAILABLE_TIMESLOTS, AVAILABLE_SLOTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }
*/

   /* private GetSlotsRequestObj getSlotsRequestObj() {
        GetSlotsRequestObj getSlotsRequestObj = new GetSlotsRequestObj();
        getSlotsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        getSlotsRequestObj.setBookingDate(tvSelectedDate.getText().toString());
        getSlotsRequestObj.setCarId(carId);
        return getSlotsRequestObj;
    }

    private void callGetSlotAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getSlotsRequestObj(), GetSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_SLOTS_API, GET_SLOTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }*/


    private CheckSlotRequestObj getCheckSlotRequestObj() {
        CheckSlotRequestObj checkSlotRequestObj = new CheckSlotRequestObj();
        try {
            checkSlotRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
            checkSlotRequestObj.setBookingDate(DateUtils.formatStringDateFromOneToAnother(tvSelectedDate.getText().toString(), DD_MMM_YYYY_DASH_DATE_FORMAT, DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT));
            checkSlotRequestObj.setFromTime(fromTime);
            checkSlotRequestObj.setToTime(toTime);
            checkSlotRequestObj.setCarId(carId);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return checkSlotRequestObj;
    }

    private void callCheckIfSlotIsAvailableAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCheckSlotRequestObj(), CheckSlotResponseObj.class,
                APIConstants.RetrofitMethodConstants.CHECK_IS_SLOT_AVAILABLE, CHECK_SLOTISAVAILABLE_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }


    private GetVeriantsRequestObj getVeriantsRequestObj() {

        return new GetVeriantsRequestObj(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
    }

    private void callGetVariantsAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getVeriantsRequestObj(), GetVeriantsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_VARIANTS, GET_VARIANTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }

    private GetCarModelsRequestObj getCarModelsRequestObj() {
        return new GetCarModelsRequestObj(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
    }

    private void callGetCarModelsAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCarModelsRequestObj(), GetCarModelsResponseObject.class,
                APIConstants.RetrofitMethodConstants.GET_CAR_MODELS_API, GET_CARMODELS_SERVICEID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private GetCarsRequestObj getCarsRequestObj() {
        getCarsRequestObj = new GetCarsRequestObj();
        getCarsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        getCarsRequestObj.setCarModelName(carModel.toString());
        getCarsRequestObj.setVariantName(variantId.toString());
        return getCarsRequestObj;
    }


    private void callGetCarsAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCarsRequestObj(), GetCarsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_CARS_API, GET_CARS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private UpsertSlotsRequestObj getUpsertSlotsRequestObj() {

        UpsertSlotsRequestObj upsertSlotsRequestObj = new UpsertSlotsRequestObj();
        try {
            upsertSlotsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
            upsertSlotsRequestObj.setBookingDate(DateUtils.formatStringDateFromOneToAnother(tvSelectedDate.getText().toString(), DD_MMM_YYYY_DASH_DATE_FORMAT, DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT));
            upsertSlotsRequestObj.setCarId(carId);
            upsertSlotsRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
            upsertSlotsRequestObj.setFromTime(fromTime);
            upsertSlotsRequestObj.setToTime(toTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return upsertSlotsRequestObj;
    }

   /*  private void callUpsertSlotAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertSlotsRequestObj(), UpsertSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_SLOTS_API, UPSERT_SLOTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }*/

    private void clearGridViewAdapter() {
        gridvTimeslots.setAdapter(null);
        gridvTimeslots.setVisibility(View.GONE);
        tvSelectedDate.setText("Pick Date");
    }

    private void clearSpinnerAdapters() {
        /*spinnerSelectFromTime.setAdapter(null);
        spinnerSelectToTime.setAdapter(null);*/
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case GET_VARIANTS_SERVICE_ID:
                    GetVeriantsResponseObj responseObj = (GetVeriantsResponseObj) object;
                    if (responseObj != null && responseObj.getStatus() != null) {
                        if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                            if (responseObj.getCarVariants() != null && responseObj.getCarVariants().size() > 0) {

                                GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(BookSlotActivity.this, 1,
                                        responseObj.getCarVariants(), VERIANT_DETAILS);
                                spinnerSelectVariant.setAdapter(genericSpinnerAdapter);
                                spinnerSelectVariant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (variantId.length() > 0) {
                                            variantId.setLength(0);
                                            variantId.trimToSize();
                                        }
                                        variantId.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getVariantName());
                                        callGetCarsAPI();
                                        clearSpinnerAdapters();
                                        //clearGridViewAdapter();
                                        //variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getVariantName());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        // variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getVariantName());
                                        variantId.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getVariantId());
                                        callGetCarsAPI();
                                    }
                                });
                            }


                        } else if (responseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongSnackBar(responseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;

                case GET_CARMODELS_SERVICEID:
                    try {
                        GetCarModelsResponseObject carModelsResponseObject = (GetCarModelsResponseObject) object;
                        if (carModelsResponseObject != null && carModelsResponseObject.getStatus() != null) {
                            if (carModelsResponseObject.getStatus().getStatusCode() == SUCCESS) {
                                if (carModelsResponseObject.getCarModels() != null && carModelsResponseObject.getCarModels().size() > 0) {
                                    GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(BookSlotActivity.this, 1,
                                            carModelsResponseObject.getCarModels(), CAR_MODELS);
                                    spinnerSelectCarModels.setAdapter(genericSpinnerAdapter);
                                    spinnerSelectCarModels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (carModel.length() > 0) {
                                                carModel.setLength(0);
                                                carModel.trimToSize();
                                            }
                                            carModel.append(((CarModels) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getCarModelName());
                                            callGetVariantsAPI();
                                            //callGetCarsAPI();
                                            clearSpinnerAdapters();
                                            //clearGridViewAdapter();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            carModel.append(((CarModels) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getCarModelId());
                                            callGetVariantsAPI();
                                            // callGetCarsAPI();
                                        }
                                    });

                                }
                            } else if (carModelsResponseObject.getStatus().getStatusCode() == FAILURE) {
                                showLongSnackBar(carModelsResponseObject.getStatus().getErrorDescription());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case GET_CARS_SERVICE_ID:
                    try {
                        GetCarsResponseObj getCarsResponseObj = (GetCarsResponseObj) object;
                        if (getCarsResponseObj != null && getCarsResponseObj.getStatus() != null) {
                            if (getCarsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                if (getCarsResponseObj.getCars() != null && getCarsResponseObj.getCars().size() > 0) {
                                    ll_carAvailableOrNot.setVisibility(View.VISIBLE);
                                    GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(BookSlotActivity.this, 1,
                                            getCarsResponseObj.getCars(), CAR_REGISTRATION_NUMBER);
                                    spinnerSelectRegistrationNumber.setAdapter(genericSpinnerAdapter);
                                    spinnerSelectRegistrationNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (registrationNumber.length() > 0) {
                                                registrationNumber.setLength(0);
                                                registrationNumber.trimToSize();
                                            }
                                            registrationNumber.append(((CarDetailObj) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getRegistrationNumber());
                                            carId = ((CarDetailObj) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getCarId();
                                            clearSpinnerAdapters();
                                            // clearGridViewAdapter();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            registrationNumber.append(((CarDetailObj) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getRegistrationNumber());
                                            carId = ((CarDetailObj) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getCarId();
                                        }
                                    });
                                } else {
                                    ll_carAvailableOrNot.setVisibility(View.GONE);
                                   /* GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(BookSlotActivity.this, 1,
                                            new ArrayList<CarDetailObj>(), CAR_REGISTRATION_NUMBER);*/
                                    spinnerSelectRegistrationNumber.setAdapter(null);
                                    showLongSnackBar("No car available against selected model and variant.'/n' Please select different model or variant");
                                }
                            } else if (getCarsResponseObj.getStatus().getStatusCode() == FAILURE) {
                                ll_carAvailableOrNot.setVisibility(View.GONE);
                                showLongSnackBar("No car available against selected model and variant.");
                                MyAppsLog.e("Error in GET_CARS_SERVICE_ID service", getCarsResponseObj.getStatus().getErrorDescription());
                            }
                        }
//                        //
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

               /* case GET_SLOTS_SERVICE_ID:
                    try {

                        GetSlotsResponseObj getSlotsResponseObj = (GetSlotsResponseObj) object;
                        if (getSlotsResponseObj != null && getSlotsResponseObj.getStatus() != null) {
                            if (getSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                ArrayList<BookingSlotsObj> slotsList = getTimeSlotsList();
                                if (getSlotsResponseObj.getBookingSlots() != null && getSlotsResponseObj.getBookingSlots().size() > 0) {
                                    for (int i = 0; i < getSlotsResponseObj.getBookingSlots().size(); i++) {
                                        String timeFrom = DateUtils.formatStringDateFromOneToAnother(getSlotsResponseObj.getBookingSlots().get(i).getFromTime(), DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT, DateUtils.HH_MM_TIME_FORMAT);
                                        String timeTo = DateUtils.formatStringDateFromOneToAnother(getSlotsResponseObj.getBookingSlots().get(i).getToTime(), DateUtils.MMM_DD_YYYY_hh_mm_ss_a_DATE_FORMAT, DateUtils.HH_MM_TIME_FORMAT);
                                        for (int k = 0; k < slotsList.size(); k++) {
                                            (slotsList.get(k)).setUid(getSlotsResponseObj.getBookingSlots().get(i).getUid());
                                            if (timeFrom.equalsIgnoreCase(slotsList.get(k).getFromTime())
                                                    && timeTo.equalsIgnoreCase(slotsList.get(k).getToTime()
                                            )) {
                                                (slotsList.get(k)).setAvailable(false);
                                            }
                                        }
                                    }


                                } *//*else {
                                    gridvTimeslots.setVisibility(View.GONE);
                                    //   GridViewAdapter gridViewAdapter = new GridViewAdapter(BookSlotActivity.this, getTimeSlotsList());
                                    gridvTimeslots.setAdapter(null);
                                    showSnackBar("No Slot available");
                                }*//*
                                gridvTimeslots.setVisibility(View.VISIBLE);
                                GridViewAdapter gridViewAdapter = new GridViewAdapter(BookSlotActivity.this, slotsList *//*getSlotsResponseObj.getBookingSlots()*//*);
                                gridvTimeslots.setAdapter(gridViewAdapter);
                                gridvTimeslots.setExpanded(true);
                                gridvTimeslots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View v,
                                                            int position, long id) {
                                        if (((BookingSlotsObj) ((GridViewAdapter) parent.getAdapter()).getListOfData().get(position)).isSelected()) {
                                            ((BookingSlotsObj) ((GridViewAdapter) parent.getAdapter()).getListOfData().get(position)).setSelected(false);
                                            numberOfSelectedSlots = numberOfSelectedSlots - 1;
                                            bookingSlotsObjHashMap.remove(position);
                                        } else {
                                            if (numberOfSelectedSlots < 2) {
                                                ((BookingSlotsObj) ((GridViewAdapter) parent.getAdapter()).getListOfData().get(position)).setSelected(true);
                                                numberOfSelectedSlots = numberOfSelectedSlots + 1;
                                                bookingSlotsObjHashMap.put(position, (BookingSlotsObj) ((GridViewAdapter) parent.getAdapter()).getListOfData().get(position));
                                            } else {
                                                showLongSnackBar("You can select maximum two slots");
                                            }
                                        }
                                        ((GridViewAdapter) parent.getAdapter()).notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;*/


                case UPSERT_SLOTS_SERVICE_ID:
                    UpsertSlotsResponseObj getSlotsResponseObj = (UpsertSlotsResponseObj) object;
                    if (getSlotsResponseObj != null && getSlotsResponseObj.getStatus() != null) {
                        if (getSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showSnackBar("Slot booked Successfully", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    callActivity(NavigationActivity.class);
                                    finish();
                                }
                            });

                        } else {
                            showLongSnackBar(getSlotsResponseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;

               /* case AVAILABLE_SLOTS_SERVICE_ID:

                    GetAvailableSlotsResponseObj getAvailableSlotsResponseObj = (GetAvailableSlotsResponseObj) object;

                    if (getAvailableSlotsResponseObj != null && getAvailableSlotsResponseObj.getStatus() != null) {
                        if (getAvailableSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            if (getAvailableSlotsResponseObj.getFromTimesAvailable() != null && getAvailableSlotsResponseObj.getFromTimesAvailable().size() > 0) {
                                GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(BookSlotActivity.this, 0, getAvailableSlotsResponseObj.getFromTimesAvailable(), AVAILABLE_SLOTS_FROM_TIME);
                                spinnerSelectFromTime.setAdapter(genericSpinnerAdapter);
                                spinnerSelectFromTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        fromTime = bookingSelectedDate + " " + String.valueOf(((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(position)) + ":00:00";
                                        fromTimeInInt = (int) ((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        fromTime = bookingSelectedDate + " " + String.valueOf(((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(0)) + ":00:00";
                                        fromTimeInInt = (int) ((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(0);
                                    }
                                });

                            } else {
                                fromTime = "";
                                fromTimeInInt = -1;
                            }
                            if (getAvailableSlotsResponseObj.getToTimesAvailable() != null && getAvailableSlotsResponseObj.getToTimesAvailable().size() > 0) {
                                GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(BookSlotActivity.this, 0, getAvailableSlotsResponseObj.getToTimesAvailable(), AVAILABLE_SLOTS_TO_TIME);
                                spinnerSelectToTime.setAdapter(genericSpinnerAdapter);
                                spinnerSelectToTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        toTime = bookingSelectedDate + " " + String.valueOf(((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(position)) + ":00:00";
                                        toTimeInInt = (int) ((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        toTime = bookingSelectedDate + " " + String.valueOf(((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(0)) + "00:00";
                                        toTimeInInt = (int) ((GenericSpinnerAdapter) parent.getAdapter()).getItemList().get(0);
                                    }
                                });
                            } else {
                                toTime = "";
                                toTimeInInt = -1;
                            }
                        }
                    }
                    break;*/


                case CHECK_SLOTISAVAILABLE_SERVICE_ID:

                    CheckSlotResponseObj checkSlotResponseObj = (CheckSlotResponseObj) object;
                    if (checkSlotResponseObj != null && checkSlotResponseObj.getStatus() != null) {
                        if (checkSlotResponseObj.getStatus().getStatusCode() == SUCCESS && checkSlotResponseObj.isSlotAvailable()) {
                            Intent intent = new Intent(BookSlotActivity.this, CustomerListActivity.class);
                            intent.putExtra("slotInfo", getUpsertSlotsRequestObj());
                            startActivity(intent);
                        } else if (checkSlotResponseObj.getStatus().getStatusCode() == FAILURE || checkSlotResponseObj.isSlotAvailable() == false) {
                            showLongToast(checkSlotResponseObj.getStatus().getErrorDescription() + "");
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {
            showLongSnackBar(AppConstants.ToastMessages.SOMETHING_WENT_WRONG);
        }
    };


    @OnClick({R.id.ll_pick_date, R.id.button_next, R.id.tv_fromTime, R.id.tv_toTime})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.ll_pick_date:
                DateUtils.showDatePicker(BookSlotActivity.this, R.style.datepicker, 2018, 0, 1, onDateSetListener);
                break;
            case R.id.button_next:
                if (validateField()) {
                   /* if ((toTimeInInt - fromTimeInInt) > 2) {
                        showSnackBar("The time difference between 2 slots can not exceed 2 hours");
                    } else*/

                    if (fromTimeInInt == -1) {
                        showLongSnackBar("Please select from time.");
                    } else if (toTimeInInt == -1) {
                        showLongSnackBar("Please select to time");
                    } else {
                        if (!TextUtils.isEmpty(tvSelectedDate.getText().toString())/* && DateUtils.getCurrentDate(DD_MMM_YYYY_DASH_DATE_FORMAT).equalsIgnoreCase(tvSelectedDate.getText().toString())*/) {

                            Date fromTimeDa = DateUtils.getStringToDate(fromTime, DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT);
                            Date toTimeDT = DateUtils.getStringToDate(toTime, DateUtils.DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT);
                            int currentHour = getCurrentYearCalender().get(Calendar.HOUR_OF_DAY);

                            if(currentDate.equalsIgnoreCase(bookingSelectedDate)) {
                                if (currentHour > fromTimeInInt && currentHour != fromTimeInInt) {
                                    showLongSnackBar("From-Time should be greater than current time");
                                }
                            }else {
                                if (fromTimeDa.after(toTimeDT)) {
                                    showLongSnackBar("From-Time should be less than To-Time");
                                } else if (fromTimeDa.equals(toTimeDT)) {
                                    showLongSnackBar("To-Time should be greater than From-Time");
                                } else {
                                    callCheckIfSlotIsAvailableAPI();
                                }
                            }


                           /* if (currentHour > fromTimeInInt && currentHour != fromTimeInInt) {
                                showLongSnackBar("From-Time should be greater than current time");
                            } else {
                                if (fromTimeInInt > toTimeInInt) {
                                    showLongSnackBar("From-Time should be less than To-Time");
                                } else if (fromTimeInInt == toTimeInInt) {
                                    showLongSnackBar("To-Time should be greater than From-Time");

                                } else {

                                    callCheckIfSlotIsAvailableAPI();
                                    Intent intent = new Intent(BookSlotActivity.this, CustomerListActivity.class);
                                    intent.putExtra("slotInfo", getUpsertSlotsRequestObj());
                                    startActivity(intent);
                                    //callUpsertSlotAPI();
                                }
                            }*/
                        }
                    }
                }
                // if (bookingSlotsObjHashMap.size() > 0) {

                /*} else {
                    showSnackBar("Please select slot");
                }*/
                break;

            case R.id.tv_fromTime:
            case R.id.tv_toTime:
                if (!isEmpty(bookingSelectedDate)) {

                    final Calendar c = Calendar.getInstance();
                    int hr = c.get(Calendar.HOUR_OF_DAY);
                    int min = c.get(Calendar.MINUTE);
                /*new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        updateTime(hourOfDay, minute, view.getId());
                    }
                }, hr, min, false).show(getFragmentManager(),"FromTime");*/

                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(RadialPickerLayout mview, int hourOfDay, int minute, int second) {
                                    updateTime(hourOfDay, minute, view.getId());
                                }
                            },
                            hr,
                            min,
                            false
                    );

                    tpd.vibrate(true);
                    tpd.dismissOnPause(true);
                    tpd.enableSeconds(false);
                    tpd.setAccentColor(Color.parseColor(AppConstants.ColorStrings.colorAccent));
                    tpd.setTitle("Pick Time");
                    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                    int monthOfYear = c.get(Calendar.MONTH);
                    if (c.get(Calendar.DAY_OF_MONTH) == dayOfMonthForRide && hr < 20) {
                        tpd.setMinTime(hr, min,
                                c.get(Calendar.SECOND));
                        tpd.setMaxTime(20, 0, 0);
                        tpd.show(getFragmentManager(), "Timepickerdialog");
                    } else if (c.get(Calendar.DAY_OF_MONTH) < dayOfMonthForRide && monthOfYear == month || c.get(Calendar.DAY_OF_MONTH) > dayOfMonthForRide && monthOfYear < month) {
                        tpd.setMinTime(8, 0,
                                c.get(Calendar.SECOND));
                        tpd.setMaxTime(20, 0, 0);
                        tpd.show(getFragmentManager(), "Timepickerdialog");
                    } else {
                        showLongSnackBar("Showroom is closed for Today");
                    }
                    break;
                } else {
                    showSnackBar("Please select date first");
                }
        }
    }

    private void updateTime(int hours, int mins, int id) {
        int hoursInNotInAMPM = hours;
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String apiFormatTime = new StringBuilder().append(hoursInNotInAMPM).append(':').append(minutes).toString();
        String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();

        switch (id) {

            case R.id.tv_fromTime:

                tvFromTime.setText(aTime);
                fromTime = bookingSelectedDate + " " + apiFormatTime + ":00";
                fromTimeInInt = hoursInNotInAMPM;

                break;

            case R.id.tv_toTime:

                tvToTime.setText(aTime);
                toTime = bookingSelectedDate + " " + apiFormatTime + ":00";
                toTimeInInt = hoursInNotInAMPM;

                break;
        }

    }


    private void recreateDateTimeAfterDateChange(){
        try {
            if(!isEmpty(fromTime))
            fromTime = bookingSelectedDate + " " + DateUtils.formatStringDateFromOneToAnother(fromTime,DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT,HH_MM_TIME_FORMAT) + ":00";
            if(!isEmpty(toTime))
            toTime = bookingSelectedDate + " " + DateUtils.formatStringDateFromOneToAnother(toTime,DD_MMM_YYYY_HH_MM_SS_DASH_DATE_FORMAT,HH_MM_TIME_FORMAT) + ":00";
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private boolean validateField() {

        if (tvSelectedDate.getText().toString().equalsIgnoreCase("Pick Date") || isEmpty(tvSelectedDate.getText().toString())) {
            showLongSnackBar("Date can not be blank.");
            return false;
        }
        if (isEmpty(fromTime)) {
            showLongSnackBar("Please select From Time.");
            return false;
        }
        if (isEmpty(toTime)) {
            showLongSnackBar("Please select To Time.");
            return false;
        }


        return true;
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            try {
                BookSlotActivity.this.month = month;
                dayOfMonthForRide = dayOfMonth;
                bookingSelectedDate = DateUtils.formatStringDateFromOneToAnother(DateUtils.getDateIn_YYYYMMdd_Dash_Format(year, month, dayOfMonth), YYYY_MM_dd_DASH_DATE_FORMAT, DD_MMM_YYYY_DASH_DATE_FORMAT);
                tvSelectedDate.setText(bookingSelectedDate);
                recreateDateTimeAfterDateChange();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // callAvailableGetSlotAPI();
        }
    };

    public Calendar getCurrentYearCalender() {
        Calendar cal = Calendar.getInstance();
        return cal;
    }
}
