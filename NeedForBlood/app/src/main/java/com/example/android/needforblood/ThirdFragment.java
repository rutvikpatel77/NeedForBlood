package com.example.android.needforblood;

// shows users on Map

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by aakanxushah on 11/3/16.
 */

public class ThirdFragment extends Fragment {

    View myView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.third_layout,container,false);

        Button clickButton = (Button) myView.findViewById(R.id.button2);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("Before Intent"," * * * * * *");
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
                Log.e("After Intent"," * * * * * *");
            }
        });

        return myView;
    }



}
