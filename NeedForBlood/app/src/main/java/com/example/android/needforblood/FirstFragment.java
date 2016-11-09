package com.example.android.needforblood;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by aakanxushah on 11/3/16.
 */

public class FirstFragment extends Fragment {

    View myView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.first_layout,container,false);
        return myView;
    }


}
