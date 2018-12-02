package com.wondercars.ridetrackerexecutive.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wondercars.ridetrackerexecutive.BaseClasses.BaseActivity;
import com.wondercars.ridetrackerexecutive.CustomClasses.AppProgressDialog;
import com.wondercars.ridetrackerexecutive.Manager.PreferenceManager;
import com.wondercars.ridetrackerexecutive.Modules.DirectionFinder;
import com.wondercars.ridetrackerexecutive.Modules.DirectionFinderListener;
import com.wondercars.ridetrackerexecutive.Modules.Duration;
import com.wondercars.ridetrackerexecutive.Modules.Route;
import com.wondercars.ridetrackerexecutive.R;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.OtpDTOs.SendOtpRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.OtpDTOs.SendOtpResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertRideDTOs.UpsertRideRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertRideDTOs.UpsertRideResponseObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertTestDriveDTOs.UpsertTestDriveRequestObj;
import com.wondercars.ridetrackerexecutive.Retrofit.DTOs.UpsertTestDriveDTOs.UpsertTestDriveResponseObj;
import com.wondercars.ridetrackerexecutive.Services.MyService;
import com.wondercars.ridetrackerexecutive.Utils.APIConstants;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.MyAppsLog;

import static com.wondercars.ridetrackerexecutive.Manager.PreferenceManager.PREF_RIDE_BEFORE_TESTDRIVE_IS_GOING_ON;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetrackerexecutive.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetrackerexecutive.Utils.AppConstants.ToastMessages.SOMETHING_WENT_WRONG;

public class StartRideFromTestDriveOptionActivity extends BaseActivity implements OnMapReadyCallback, DirectionFinderListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int START_RIDE_SERVICE_ID = 0, END_RIDE_SERVICE_ID = 1;
    private static final int UPSERT_TEST_DRIVE_SERVICE_ID = 3;
    private static final int SEND_OTP_SERVICE_ID = 4;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    @BindView(R.id.search_button)
    TextView searchTextView;
    @BindView(R.id.button_startRide)
    Button buttonStartRide;

    @BindView(R.id.tv_booking_id)
    TextView tv_booking_id;

    private GoogleMap mMap;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 12345;

    LocationManager manager;
    ArrayList<String> startAndEndLocation = new ArrayList<>();

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation, startLocation;
    private Marker mCurrLocationMarker;
    UpsertRideRequestObj upsertRideRequestObj;
    UpsertTestDriveRequestObj upsertTestDriveRequestObj;
    private Duration mDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_ride);
        ButterKnife.bind(this);
        upsertTestDriveRequestObj = new UpsertTestDriveRequestObj();
       if(PreferenceManager.readInteger(PreferenceManager.PREF_ON_GOING_RIDE_TYPE)==PREF_RIDE_BEFORE_TESTDRIVE_IS_GOING_ON){
           init();
       }
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void init(){
        buttonStartRide.setTag("stopride");
        buttonStartRide.setText("Stop Ride");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            displayPromptForEnablingGPS(this);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(StartRideFromTestDriveOptionActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            return;
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(StartRideFromTestDriveOptionActivity.this, "Please enable permissions to use map", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static void displayPromptForEnablingGPS(
            final Activity activity) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable either GPS or any other location"
                + " service to find current location.  Click OK to go to"
                + " location services settings to let you do so.";
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.startActivity(new Intent(action));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private UpsertTestDriveRequestObj getUpsertTestDriveRequestObj() {
        //UpsertTestDriveRequestObj upsertTestDriveRequestObj = new UpsertTestDriveRequestObj();
        upsertTestDriveRequestObj.setCarId(upsertRideRequestObj.getCarId());
        upsertTestDriveRequestObj.setId(upsertRideRequestObj.getId());
        upsertTestDriveRequestObj.setCustomer_id(upsertRideRequestObj.getCustomer_id());
        upsertTestDriveRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        upsertTestDriveRequestObj.setRide_type("Test Drive");
        upsertTestDriveRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        upsertTestDriveRequestObj.setCustomer_enquiry_no(upsertRideRequestObj.getEnquiryNumber());
        upsertTestDriveRequestObj.setCustomer_name(upsertRideRequestObj.getName());
        upsertTestDriveRequestObj.setCustomer_mobile(upsertRideRequestObj.getMobileNumber());
        upsertTestDriveRequestObj.setOngoing_test_drive_flg("Y");


        return upsertTestDriveRequestObj;

    }

    private void callUpsertTestDriveAPI(int serviceID) {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertTestDriveRequestObj(), UpsertTestDriveResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_TEST_DRIVE, serviceID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    private SendOtpRequestObj getSendOtpRequestObj() {
        SendOtpRequestObj sendOtpRequestObj = new SendOtpRequestObj();
        sendOtpRequestObj.setCustomerMonileNumber("91" + upsertRideRequestObj.getMobileNumber());
        sendOtpRequestObj.setCustomer_email(upsertRideRequestObj.getEmailID());
        sendOtpRequestObj.setIsTestDrive("Y");
        sendOtpRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID));
        return sendOtpRequestObj;
    }

    private void callSendOTP_API() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getSendOtpRequestObj(), SendOtpResponseObj.class,
                APIConstants.RetrofitMethodConstants.SEND_OTP_TO_CUSTOMER, SEND_OTP_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    @OnClick({R.id.search_button, R.id.button_startRide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    MyAppsLog.e("StartRideActivity", e.getMessage());
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                    MyAppsLog.e("StartRideActivity", e.getMessage());
                }
                break;

            case R.id.button_startRide:
                try {
                    if (view.getTag().toString().equalsIgnoreCase("startride")) {
                        if (validateRequestObject()) {
                            PreferenceManager.writeInteger(PreferenceManager.PREF_ON_GOING_RIDE_TYPE, PREF_RIDE_BEFORE_TESTDRIVE_IS_GOING_ON);
                            upsertRideRequestObj = (UpsertRideRequestObj) PreferenceManager.getStringToObject(PreferenceManager.readString(PreferenceManager.PREF_RIDECUSTOMER_IFO), UpsertRideRequestObj.class);
                            getUpsertTestDriveRequestObj();
                            upsertTestDriveRequestObj.setId(upsertRideRequestObj.getId());
                          //  upsertRideRequestObj.setRideType("Customer Visit");
                            String startLatLng[] = startAndEndLocation.get(0).split(",");
                            if (startLatLng.length > 1) {
                                upsertTestDriveRequestObj.setStart_lat(startLatLng[0]);
                                upsertTestDriveRequestObj.setStart_long(startLatLng[1]);
                            }
                            if (mDuration != null) {
                                upsertTestDriveRequestObj.setExpected_duration_of_travel(String.valueOf(mDuration.value));
                            }
                            callUpsertTestDriveAPI(START_RIDE_SERVICE_ID);
                           // callUpsertRideAPI(upsertRideRequestObj, START_RIDE_SERVICE_ID);
                        }
                    } else {
                        String endLatLng[] = startAndEndLocation.get(1).split(",");
                        if (endLatLng != null && endLatLng.length > 1) {
                            upsertTestDriveRequestObj.setEnd_lat(endLatLng[0]);
                            upsertTestDriveRequestObj.setEnd_long(endLatLng[1]);
                        }
                        //upsertRideRequestObj.setRide_completed_flg("Y");
                        callUpsertTestDriveAPI(END_RIDE_SERVICE_ID);
                      //  callUpsertRideAPI(upsertRideRequestObj, END_RIDE_SERVICE_ID);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
        }
    }

    private void callUpsertRideAPI(UpsertRideRequestObj upsertRideRequestObj, int serviceid) {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, upsertRideRequestObj, UpsertRideResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPSERT_RIDE_API, serviceid, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .setRetrofitHeaderses(getRetrofitHeaderses())
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {
            switch (serviceId) {

                case START_RIDE_SERVICE_ID:
                    UpsertTestDriveResponseObj upsertRideResponseObj = (UpsertTestDriveResponseObj) object;
                    if (upsertRideResponseObj != null && upsertRideResponseObj.getStatus() != null) {
                        if (upsertRideResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            String bookingID = PreferenceManager.readString(PreferenceManager.PREF_TESTDRIVE_ID);
                            tv_booking_id.setText("Booking ID  "+bookingID.substring(bookingID.length()-7,bookingID.length()-1));
                            buttonStartRide.setTag("stopride");
                            buttonStartRide.setText("Stop Ride");
                           // upsertRideRequestObj.setId(PreferenceManager.readString(PreferenceManager.PREF_RIDE_ID));
                            startService(new Intent(getApplicationContext(), MyService.class));
                        } else if (upsertRideResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongSnackBar(upsertRideResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;
                case END_RIDE_SERVICE_ID:
                    UpsertTestDriveResponseObj upsertRideResponse = (UpsertTestDriveResponseObj) object;
                    stopService(new Intent(getApplicationContext(), MyService.class));
                    if (upsertRideResponse != null && upsertRideResponse.getStatus() != null) {
                        if (upsertRideResponse.getStatus().getStatusCode() == SUCCESS) {
                            showLongSnackBar("Ride stoped successfully");
                            PreferenceManager.writeInteger(PreferenceManager.PREF_ON_GOING_RIDE_TYPE,-1);
                            PreferenceManager.writeString(PreferenceManager.PREF_RIDE_ID, "");
                            callUpsertTestDriveAPI(UPSERT_TEST_DRIVE_SERVICE_ID);
                            /*callActivity(NavigationActivity.class);
                            finish();*/
                        } else if (upsertRideResponse.getStatus().getStatusCode() == FAILURE) {
                            showLongSnackBar(upsertRideResponse.getStatus().getErrorDescription());
                        }
                    }
                    break;

                case UPSERT_TEST_DRIVE_SERVICE_ID:
                    UpsertTestDriveResponseObj upsertTestDriveResponseObj = (UpsertTestDriveResponseObj) object;
                    if (upsertTestDriveResponseObj != null && upsertTestDriveResponseObj.getStatus() != null) {
                        if (upsertTestDriveResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showLongSnackBar("Test-Drive Details saved successfully");
                            /*UpsertRideRequestObj upsertRideRequestObj = getUpsertRideRequestObj();
                            upsertRideRequestObj.setId(upsertRideResponseObj.getId());
                            String upsertRideCustomerInfo = PreferenceManager.getObjectToString(upsertRideRequestObj);
                            PreferenceManager.writeString(PREF_RIDECUSTOMER_IFO, upsertRideCustomerInfo);*/
                           // PreferenceManager.writeString(PREF_TESTDRIVE_ID, upsertTestDriveResponseObj.getId());
                            callSendOTP_API();
                        } else if (upsertTestDriveResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongSnackBar(upsertTestDriveResponseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;

                case SEND_OTP_SERVICE_ID:

                    SendOtpResponseObj sendOtpResponseObj = (SendOtpResponseObj) object;
                    if (sendOtpResponseObj != null) {
                        if (sendOtpResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            callActivity(EnterOtpActivity.class);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {
            showLongSnackBar(SOMETHING_WENT_WRONG);
        }
    };

    private boolean validateRequestObject() {
        if (startAndEndLocation != null && startAndEndLocation.size() == 2) {
            return true;
        } else {
            showLongSnackBar("Please select destination to Start a ride");
        }

        return false;
    }

    private void sendRequest() {
        try {
            new DirectionFinder(this, startAndEndLocation.get(0), startAndEndLocation.get(1)).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mMap.clear();
                Place place = PlaceAutocomplete.getPlace(this, data);

                if (startAndEndLocation.size() >= 2) {
                    startAndEndLocation.remove(1);
                }
                startAndEndLocation.add(place.getLatLng().latitude + "," + place.getLatLng().longitude);
                searchTextView.setText(place.getName());
                Toast.makeText(getApplicationContext(), place.getAddress(), Toast.LENGTH_LONG).show();
                if (startAndEndLocation.size() == 2) {
                    sendRequest();
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        Duration duration = null;
        for (Route route : routes) {
            if(duration==null) {
                duration = route.duration;
                mDuration =null;
                mDuration = duration;
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
//            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
//            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);
            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            if (startAndEndLocation.size() == 0) {
                startAndEndLocation.add("" + location.getLatitude() + "," + location.getLongitude());
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mMap.addMarker(markerOptions);

            //move map camera
           /* mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
*/
            //stop location updates
           /* if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }*/
        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private double calculateDistance(Location startPoint, Location endPoint) {
       /* Location startPoint = new Location("locationA");
        startPoint.setLatitude(17.372102);
        startPoint.setLongitude(78.484196);*/

       /* Location endPoint = new Location("locationA");
        endPoint.setLatitude(17.375775);
        endPoint.setLongitude(78.469218);*/

        showLongSnackBar(startPoint.distanceTo(endPoint) / 1000 + "");
        return startPoint.distanceTo(endPoint) / 1000;
    }

}
