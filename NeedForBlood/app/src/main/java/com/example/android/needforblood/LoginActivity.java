package com.example.android.needforblood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.authentication.Constants;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.path;

public class LoginActivity extends AppCompatActivity {

    private Button signin,signup;
    private EditText name,pw;
    private  Firebase fb;
    private StringBuilder sb;
    private Map<String,User> hm;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        name = (EditText) findViewById(R.id.username);
        pw = (EditText) findViewById(R.id.password);

        /*fb=new Firebase("https://needforblood-ae0b1.firebaseio.com/users");

        fb.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String user_key=data.getKey();
                    Log.e("* * * * * * * * * *",user_key);
                    addUser(user_key);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String uname = name.getText().toString();
                final String pword=pw.getText().toString();

                fb=new Firebase("https://needforblood-362e3.firebaseio.com/users");

                fb.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            String user_key=data.getKey();
                            //Log.e("* * * * * * * * * *",user_key);
                            checkUser(user_key,uname,pword);

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg=new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(reg);
            }
        });
    }

    private void checkUser(String user_key, final String username, final String password) {

        Firebase fb1=new Firebase("https://needforblood-362e3.firebaseio.com/users/"+user_key);

        fb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user12=dataSnapshot.getValue(User.class);
             //   Log.e("@@@@@@@@@@@@",dataSnapshot.getKey());

                //hm.put(user12.getUsename(),user12);
                //Log.e("_ _ _ _ _ _ _ _ _ _ _"," Here Here Here");
                if(user12.getUsename().equalsIgnoreCase(username) && user12.getPassword().equalsIgnoreCase(password)){
                   // Log.e("_ _ _ _ _ _ _ _ _ _ _"," * * * * User Found * * * * * *");
                    /*currentUser=user12;
                    Intent user=new Intent(LoginActivity.this,UserActivity.class);
                    LoginActivity.this.startActivity(user);*/

                    user12.setKey(dataSnapshot.getKey());
                    Intent i = new Intent();
                    Bundle b = new Bundle();
                    b.putSerializable("user",user12);
                    i.putExtras(b);
                    i.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(i);

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
