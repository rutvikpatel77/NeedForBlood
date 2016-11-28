package com.example.android.needforblood;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by Ishrat on 11/1/2016.
 */

public class locationService extends Service {
    private LocationListener listener;
    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        listener = new LocationListener() {
            //when there is location update, onLocationChanged will be called
            @Override
            public void onLocationChanged(Location location) {
                //pass the location to the main activity using broadcast class
                Intent i = new Intent("locationUpdate");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                i.putExtra("longitude", location.getLongitude());
                i.putExtra("latitude", location.getLatitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            //if location service is disabled on the phone, we will point user to
            //the settings page to enable them, we do that in onProviderDisabled method
            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        //initialize location manager with system service method
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //to start the process we call the manager with requestLocationUpdates method, it will update every 1 seconds
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000000,0,listener);

    }

    //when the service is destroyed we must ensure the listener is not active anymore to avoid memory leaks
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //unregister location manager

            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }
}
