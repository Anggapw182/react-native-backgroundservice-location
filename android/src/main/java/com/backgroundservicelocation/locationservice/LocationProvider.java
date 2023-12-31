package com.backgroundservicelocation.locationservice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Promise;

import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;

//Get Imei
import android.provider.Settings;
import static android.provider.Settings.Secure.getString;

// @ReactModule(name = LocationProvider.NAME)
public class LocationProvider extends ReactContextBaseJavaModule {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private final ReactApplicationContext reactContext;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String urlPost = null;
    public static final String NAME = "LocationProvider";
    private String uniqueID = getUniqueIdSync();

    public LocationProvider(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.locationManager = (LocationManager) reactContext.getSystemService(Context.LOCATION_SERVICE);
        this.locationListener = createLocationListener();
    }

    @NonNull
    @Override
    public String getName() {
        return "LocationProvider";
    }

    public String getUniqueIdSync() { return getString(getReactApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID); }
    
    @ReactMethod
    public void getUniqueId(Promise p) {
        Log.d("LogImei", getUniqueIdSync());
        p.resolve(getUniqueIdSync());
    }

    @ReactMethod
    public void startLocationUpdates(int delay,@Nullable String urlPost, @Nullable String userid) {
        Log.d("LF", "START FRESH");
        if (ContextCompat.checkSelfPermission(reactContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        }

        if(ContextCompat.checkSelfPermission(reactContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d("LF", "isLocationPermissionGranted TRUE");

            SharedPreferences sharedPreferences = reactContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("urlPost", urlPost);
            editor.putString("userid", userid);
            editor.apply();

            this.urlPost = urlPost;
            startForegroundService();
            startLocationUpdatesInternal(delay);
        }else{
            Log.d("LF", "isLocationPermissionGranted FALSE");
            ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    private void startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "location_service_channel";
            NotificationChannel channel = new NotificationChannel(channelId, "Location Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = reactContext.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            Notification notification = new NotificationCompat.Builder(reactContext, channelId)
                    .setContentTitle("Location Service")
                    .setContentText("Running")
                    .build();

            Intent serviceIntent = new Intent(reactContext, LocationForegroundService.class);
            ContextCompat.startForegroundService(reactContext, serviceIntent);
        } else {
            Intent serviceIntent = new Intent(reactContext, LocationForegroundService.class);
            reactContext.startService(serviceIntent);
        }
    }

    private void startLocationUpdatesInternal(int delay) {
        Log.d("SC", "CHANGE FROM FRESH");
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(reactContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(reactContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("SC", "CHANGE FROM  err");
                throw new RuntimeException("Location permission not granted");
            }
            Log.d("SC", "CHANGE FROM  ads");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, delay, 0, locationListener, Looper.getMainLooper());
        }
        Log.d("SC", "CHANGE FROM  a123ds");
    }

    @ReactMethod
    public void stopLocationUpdates() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private LocationListener createLocationListener() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("LC", "CHANGE FROM as");
                if (location != null) {
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date date = new Date();
                    String AndroidSystemDate = dateFormat.format(date);

                    WritableMap locationMap = Arguments.createMap();
                    locationMap.putDouble("latitude", location.getLatitude());
                    locationMap.putDouble("longitude", location.getLongitude());
                    locationMap.putDouble("accuracy", location.getAccuracy());
                    locationMap.putString("androiddate", AndroidSystemDate);
                    locationMap.putString("uniqueID", uniqueID);

                    SharedPreferences sharedpreferences = reactContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    String getUrlPost = sharedpreferences.getString("urlPost", null);
                    String getuserid = sharedpreferences.getString("userid", null);
                    String createdby = "system";
                    locationMap.putString("urlPost", getUrlPost);
                    if(getuserid == null || getuserid.isEmpty())
                    {
                        createdby = "system";
                        locationMap.putString("userid", "system");
                        Log.d("getuserid null", String.valueOf(createdby));
                    }else{
                        createdby = getuserid;
                        locationMap.putString("userid", getuserid);
                        Log.d("getuserid", createdby);
                    }

                    sendLocationUpdate(locationMap);
                    if(getUrlPost != null){
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.accumulate("lat",  location.getLatitude());
                            jsonObject.accumulate("lon",  location.getLongitude());
                            jsonObject.accumulate("accuracy", location.getAccuracy());
                            jsonObject.accumulate("androiddate", AndroidSystemDate);
                            jsonObject.accumulate("uniqueID", String.valueOf(uniqueID));
                            jsonObject.accumulate("userid", String.valueOf(getuserid));
                            jsonObject.accumulate("createdby", String.valueOf(createdby));
                            new PostLocation(new PostLocation.OnDataCollectedCallback() {
                                @Override
                                public void onDataCollected(String data) {
                                }
                            }).execute(getUrlPost, String.valueOf(jsonObject));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Handle location status changes if needed
            }

            @Override
            public void onProviderEnabled(String provider) {
                // Handle provider enabled if needed
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Handle provider disabled if needed
            }
        };
    }

    private void sendLocationUpdate(WritableMap locationMap) {
        // Emit the location update event
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onLocationUpdate", locationMap);
    }
}
