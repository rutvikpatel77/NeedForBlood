package com.example.android.needforblood;

// shows users on Map
// import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by aakanxushah on 11/3/16.
 */

public class Eligibility extends Fragment {

    View myView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.eligibility,container,false);



        Button eligibility_button = (Button) myView.findViewById(R.id.el_btn);
        final EditText age_et = (EditText) myView.findViewById(R.id.el_age_et);
        final EditText wt_et = (EditText) myView.findViewById(R.id.el_wt_et);



        eligibility_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int age = Integer.parseInt(age_et.getText().toString());
                int weight = Integer.parseInt(wt_et.getText().toString());

                if (age > 20 && weight > 130){
                    Log.e("ELIGIBILITY"," you can donate blood");
                    FragmentManager fm = getFragmentManager();
                    yesDialogFragment yesDialogFragment = new yesDialogFragment ();
                    yesDialogFragment.show(fm, "yes Donate Blood");
                }
                else{
                    FragmentManager fm = getFragmentManager();
                    noDialogFragment noDialogFragment = new noDialogFragment ();
                    noDialogFragment.show(fm, "no Donate Blood");
                }

            }
        });

        return myView;
    }

}
