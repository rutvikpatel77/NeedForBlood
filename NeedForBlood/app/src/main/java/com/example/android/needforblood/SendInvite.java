package com.example.android.needforblood;


import android.*;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by aakanxushah on 11/3/16.
 */

public class SendInvite extends Fragment {

    View myView;

    @Nullable
    @Override
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
       // myView = inflater.inflate(R.layout.send_invite,container,false);

        myView =getActivity().getLayoutInflater().inflate(R.layout.send_invite,null);
        Button clickButton = (Button) myView.findViewById(R.id.invite);
       // EditText phone=(EditText) myView.findViewById(R.id.phone);

        //final String ph1=phone.getText().toString();


        // use ph as phone number
       /*// clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Enter Message code here
            }
        });*/

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    // set the data, number has to be string

                   // String phNum=donor.getPhone();
                    //noinspection MissingPermission
                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                       Toast.makeText(getActivity(),"Sending...",Toast.LENGTH_LONG).show();
                        //startActivity(smsIntent);

                        Uri uri = Uri.parse("smsto:");
                        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                        it.putExtra("sms_body", "Find the nearest Blood Donors using this new App called 'Need For Blood'");
                        startActivity(it);
                        return;
                    }

                }catch(Exception e) {
                    Toast.makeText(getActivity(),"Your msg is not sent...",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });



        return myView;
    }


}
