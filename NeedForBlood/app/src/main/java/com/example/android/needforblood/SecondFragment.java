package com.example.android.needforblood;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


/**
 * Created by aakanxushah on 11/3/16.
 */

public class SecondFragment extends Fragment {

    private TextView search,donor;
    private Button search_btn;
    View myView;
    private Firebase fb,fb3;
    boolean exist;
    private User donor1;
    String search_user,pass;

    @Nullable
    @Override
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        //myView = inflater.inflate(R.layout.second_layout,container,false);

        myView =getActivity().getLayoutInflater().inflate(R.layout.second_layout,null);

        //name=(EditText)view.findViewById(R.id.nameTV);

        search = (TextView) myView.findViewById(R.id.search);
        donor = (TextView) myView.findViewById(R.id.donor);
        search_btn = (Button) myView.findViewById(R.id.search_btn);



      //  Log.e(" @ @  @ @ @ @ @ @",search_user);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_user = search.getText().toString();
                fb=new Firebase("https://needforblood-362e3.firebaseio.com/users");
               // Log.e(" @ @  @ @ @ @ @ @",search_user);
                fb.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot data:dataSnapshot.getChildren()){
                           // Log.e("# # # #BEFORE # # # #","JATE UPAR");
                            String user_key=data.getKey();
                            //Log.e("# # # # AFTER# # # # #","JATE");
                            checkUser(user_key,search_user);
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                if(exist){
                  //  Log.e("_ _ _ CHECK USER _ _ _","* * * *");
                    donor.setText(search_user);
                    exist=false;
                }else{
                    donor.setText("No user Found");
                }
            }
        });


        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // final String uname = name.getText().toString();
                //final String pword=pw.getText().toString();

                pass = search.getText().toString();
                printInfo(pass);

                if(donor1!=null){
                    Log.e("# # # #BEFORE # # # #","UPAR");
                    Intent i = new Intent();
                    Bundle b = new Bundle();
                    b.putSerializable("user",donor1);
                    i.putExtras(b);
                    i.setClass(getActivity(), DonorInfoActivity.class);
                    startActivity(i);
                    Log.e("# # # #AFTER # # # #","NICHE");
                }


            }
        });

        return myView;
    }

    private void checkUser(String user_key, final String search_user1) {

        Firebase fb1=new Firebase("https://needforblood-362e3.firebaseio.com/users/"+user_key);

        fb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user12=dataSnapshot.getValue(User.class);

                if(user12.getName().equalsIgnoreCase(search_user1)){
                    //donor.setText(search_user1);
                    /*currentUser=user12;
                    Intent user=new Intent(LoginActivity.this,UserActivity.class);
                    LoginActivity.this.startActivity(user);*/
                    exist=true;
                }else{
                   //Log.e("_ _ _ CHECK USER _ _ _",user12.getUsename()+":"+search_user1);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
                    checkUser1(user_key,user);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void checkUser1(String user_key, final String user) {

        Firebase fb1=new Firebase("https://needforblood-362e3.firebaseio.com/users/"+user_key);

        fb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user12=dataSnapshot.getValue(User.class);
                if(user12.getName().equalsIgnoreCase(user) ){
                    user12.setKey(dataSnapshot.getKey());
                    donor1=user12;

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
