package com.wondercars.executiveridetracker.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wondercars.executiveridetracker.Adapters.GenericSpinnerAdapter;
import com.wondercars.executiveridetracker.Adapters.GridViewAdapter;
import com.wondercars.executiveridetracker.BaseClasses.BaseActivity;
import com.wondercars.executiveridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.executiveridetracker.CustomClasses.ExpandableHeightGridView;
import com.wondercars.executiveridetracker.Manager.PreferenceManager;
import com.wondercars.executiveridetracker.R;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarModelsDTOs.CarModels;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarModelsDTOs.GetCarModelsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarModelsDTOs.GetCarModelsResponseObject;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.GetSlotsResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetVeriantsDTOs.GetVeriantsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetVeriantsDTOs.GetVeriantsResponseObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.GetVeriantsDTOs.VeriantsDetails;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsRequestObj;
import com.wondercars.executiveridetracker.Retrofit.DTOs.UpsortSlotsDTOs.UpsertSlotsResponseObj;
import com.wondercars.executiveridetracker.Utils.APIConstants;
import com.wondercars.executiveridetracker.Utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;

import static com.wondercars.executiveridetracker.Adapters.GridViewAdapter.numberOfSelectedSlots;
import static com.wondercars.executiveridetracker.Application.ExecutiveRideTrackerApplicationClass.getTimeSlotsList;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.executiveridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.executiveridetracker.Utils.AppConstants.ResponseObjectType.CAR_MODELS;
import static com.wondercars.executiveridetracker.Utils.AppConstants.ResponseObjectType.CAR_REGISTRATION_NUMBER;
import static com.wondercars.executiveridetracker.Utils.AppConstants.ResponseObjectType.VERIANT_DETAILS;

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
    @BindView(R.id.button_book_slot)
    Button buttonBookSlot;
    @BindView(R.id.ll_pick_date)
    LinearLayout llPickDate;
    @BindView(R.id.tv_selected_date)
    TextView tvSelectedDate;
    @BindView(R.id.gridv_timeslots)
    ExpandableHeightGridView gridvTimeslots;
    private StringBuffer variantId, carModel, registrationNumber;
    String carId = "";
    private GetCarsRequestObj getCarsRequestObj;
    private final int GET_CARS_SERVICE_ID = 2, GET_SLOTS_SERVICE_ID = 3, UPSERT_SLOTS_SERVICE_ID = 4;
    HashMap<Integer, BookingSlotsObj> bookingSlotsObjHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);
        ButterKnife.bind(this);
        init();
        setActionBar(toolbar,"Book Slot");
    }

    private void init() {
        bookingSlotsObjHashMap = new HashMap<>();
        variantId = new StringBuffer();
        carModel = new StringBuffer();
        registrationNumber = new StringBuffer();
        callGetVariantsAPI();
        callGetCarModelsAPI();
    }


    private GetSlotsRequestObj getSlotsRequestObj() {
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
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
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
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private UpsertSlotsRequestObj getUpsertSlotsRequestObj() {

        UpsertSlotsRequestObj upsertSlotsRequestObj = new UpsertSlotsRequestObj();
        upsertSlotsRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        upsertSlotsRequestObj.setBookingDate(tvSelectedDate.getText().toString());
        upsertSlotsRequestObj.setCarId(carId);
        upsertSlotsRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        for (Integer key : bookingSlotsObjHashMap.keySet()) {
            upsertSlotsRequestObj.setFromTime(tvSelectedDate.getText().toString() + " " + bookingSlotsObjHashMap.get(key).getFromTime() + ":00");
            upsertSlotsRequestObj.setToTime(tvSelectedDate.getText().toString() + " " + bookingSlotsObjHashMap.get(key).getToTime() + ":00");
            break;
        }
        return upsertSlotsRequestObj;
    }

    private void callUpsertSlotAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertSlotsRequestObj(), UpsertSlotsResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_SLOTS_API, UPSERT_SLOTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private void clearGridViewAdapter() {
        gridvTimeslots.setAdapter(null);
        gridvTimeslots.setVisibility(View.GONE);
        tvSelectedDate.setText("Pick Date");
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
                                        clearGridViewAdapter();
                                        //variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getVariantName());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        // variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getVariantName());
                                        variantId.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getVariantId());
                                    }
                                });
                            }


                        } else if (responseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(responseObj.getStatus().getErrorDescription());
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
                                            callGetCarsAPI();
                                            clearGridViewAdapter();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            carModel.append(((CarModels) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getCarModelId());
                                            callGetCarsAPI();
                                        }
                                    });

                                }
                            } else if (carModelsResponseObject.getStatus().getStatusCode() == FAILURE) {
                                showLongToast(carModelsResponseObject.getStatus().getErrorDescription());
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
                                            clearGridViewAdapter();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            registrationNumber.append(((CarDetailObj) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getRegistrationNumber());
                                            carId = ((CarDetailObj) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getCarId();
                                        }
                                    });
                                } else {
                                    GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(BookSlotActivity.this, 1,
                                            new ArrayList<CarDetailObj>(), CAR_REGISTRATION_NUMBER);
                                    spinnerSelectRegistrationNumber.setAdapter(genericSpinnerAdapter);
                                    showShortToast("No car available");
                                }
                            } else if (getCarsResponseObj.getStatus().getStatusCode() == FAILURE) {
                                showLongToast(getCarsResponseObj.getStatus().getErrorDescription());
                            }
                        }
//                        //
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case GET_SLOTS_SERVICE_ID:
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


                                } /*else {
                                    gridvTimeslots.setVisibility(View.GONE);
                                    //   GridViewAdapter gridViewAdapter = new GridViewAdapter(BookSlotActivity.this, getTimeSlotsList());
                                    gridvTimeslots.setAdapter(null);
                                    showShortToast("No Slot available");
                                }*/
                                gridvTimeslots.setVisibility(View.VISIBLE);
                                GridViewAdapter gridViewAdapter = new GridViewAdapter(BookSlotActivity.this, slotsList /*getSlotsResponseObj.getBookingSlots()*/);
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
                                                showShortToast("You can select maximum two slots");
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

                    break;


                case UPSERT_SLOTS_SERVICE_ID:
                    UpsertSlotsResponseObj getSlotsResponseObj = (UpsertSlotsResponseObj) object;
                    if (getSlotsResponseObj != null && getSlotsResponseObj.getStatus() != null) {
                        if (getSlotsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showShortToast("Slot booked Successfully");
                        } else {
                            showShortToast(getSlotsResponseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;

                default:
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {
            showLongToast(AppConstants.ToastMessages.SOMETHING_WENT_WRONG);
        }
    };


    @OnClick({R.id.ll_pick_date, R.id.button_book_slot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pick_date:
                DateUtils.showDatePicker(BookSlotActivity.this, R.style.datepicker, 2018, 0, 1, onDateSetListener);
                break;
            case R.id.button_book_slot:
                if (bookingSlotsObjHashMap.size() > 0) {
                    callUpsertSlotAPI();
                } else {
                    showLongToast("Please select slot");
                }
                break;
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            tvSelectedDate.setText(DateUtils.getDateIn_YYYYMMdd_Dash_Format(year, month, dayOfMonth));
            callGetSlotAPI();
        }
    };
}
