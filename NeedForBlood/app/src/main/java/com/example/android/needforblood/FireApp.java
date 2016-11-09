package com.example.android.needforblood;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by rbpatel7 on 11/3/2016.
 */

public class FireApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);

    }

}
