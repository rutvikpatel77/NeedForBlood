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
    private Firebase fb;
    boolean exist;
    String search_user;

    @Nullable
    @Override
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.second_layout,container,false);

        search = (TextView) myView.findViewById(R.id.search);
        donor = (TextView) myView.findViewById(R.id.donor);
        search_btn = (Button) myView.findViewById(R.id.search_btn);

        search_user = search.getText().toString();

        Log.e(" @ @  @ @ @ @ @ @",search_user);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fb=new Firebase("https://needforblood-362e3.firebaseio.com/users");

                fb.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            Log.e("# # # #BEFORE # # # #","JATE UPAR");
                            String user_key=data.getKey();
                            Log.e("# # # # AFTER# # # # #","JATE");
                            checkUser(user_key,search_user);
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                if(exist){

                    //donor.setText(search_user);
                }else{
                  //  donor.setText("No user Found");
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

                if(user12.getUsename().equalsIgnoreCase(search_user1)){
                    Log.e("_ _ _ _ _ _ _ _ _ _ _"," * * * * User Found * * * * * *");
                    /*currentUser=user12;
                    Intent user=new Intent(LoginActivity.this,UserActivity.class);
                    LoginActivity.this.startActivity(user);*/
                    exist=true;
                }else{
                   Log.e("_ _ _ CHECK USER _ _ _",user12.getUsename()+":"+search_user1);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
