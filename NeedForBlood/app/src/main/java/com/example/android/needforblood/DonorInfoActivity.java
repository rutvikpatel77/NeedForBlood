package com.example.android.needforblood;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class DonorInfoActivity extends AppCompatActivity {

    String user;
    private TextView name,bg,age,no;
    private User donor;
    Button call,sms;

    Firebase fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_info);
        Log.e("** IN DONOR PAGE **","% % % % %");
        final Context context = this;

        donor = (User)getIntent().getSerializableExtra("user");
        name=(TextView)findViewById(R.id.name_v);
        bg=(TextView)findViewById(R.id.bg_v);
        age=(TextView)findViewById(R.id.age_v);
        no=(TextView)findViewById(R.id.phone_v);

        call=(Button)findViewById(R.id.call);
        sms=(Button)findViewById(R.id.sms);


       // printInfo(user);

        if(donor!=null){
            name.setText(donor.getName());
            bg.setText(donor.getBg());
            age.setText(donor.getAge());
            no.setText(donor.getPhone());
        }

        //call.setOnClickListener(new View.OnClickListener());

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    // set the data, number has to be string
                    String phNum=donor.getPhone();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phNum));

                    //noinspection MissingPermission
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(),"Calling...", Toast.LENGTH_LONG).show();
                        startActivity(callIntent);
                        return;
                    }

                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),"Your call has failed...",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    // set the data, number has to be string

                    String phNum=donor.getPhone();
                    //noinspection MissingPermission
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(),"Sending...",Toast.LENGTH_LONG).show();
                        //startActivity(smsIntent);

                        Uri uri = Uri.parse("smsto:"+phNum);
                        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                        it.putExtra("sms_body", "The SMS text");
                        startActivity(it);
                        return;
                    }

                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),"Your msg is not sent...",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });




    }

    private void printInfo(final String user) {

        fb=new Firebase("https://needforblood-362e3.firebaseio.com/users");

        fb.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String user_key=data.getKey();
                    //Log.e("* * * * * * * * * *",user_key);
                    checkUser(user_key,user);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void checkUser(String user_key, final String user) {

        Firebase fb1=new Firebase("https://needforblood-362e3.firebaseio.com/users/"+user_key);

        fb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user12=dataSnapshot.getValue(User.class);
                if(user12.getName().equalsIgnoreCase(user) ){
                    user12.setKey(dataSnapshot.getKey());
                    donor=user12;

                }else{
                    //Log.e("_ _ _ _ _ _ _ _ _ _ _"," In the else");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
