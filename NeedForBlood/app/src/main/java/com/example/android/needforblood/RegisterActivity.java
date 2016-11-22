package com.example.android.needforblood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText username,password,age,bg,name,phone;
    Button register;
    private Firebase fb,fb1;
    List<String> users=new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // following code will get all the users in an Arraylist
       /* fb1=new Firebase("https://needforblood-362e3.firebaseio.com/users");

        fb1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String user_key=data.getKey();
                    //Log.e("* * * * * * * * * *",user_key);
                    //boolean ans=alreadyUser(user_key,username1);

                    Firebase fb2=new Firebase("https://needforblood-362e3.firebaseio.com/users/"+user_key);

                    fb2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            users=new ArrayList<String>();
                            User user1=dataSnapshot.getValue(User.class);
                            users.add(user1.getUsename());
                            //Log.e("# # # # # ",users.get(0));
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        age = (EditText) findViewById(R.id.age);
        bg = (EditText) findViewById(R.id.bloodgroup);
        name=(EditText) findViewById(R.id.name);
        phone =(EditText) findViewById(R.id.phone);

        register =(Button) findViewById(R.id.register);
        fb=new Firebase("https://needforblood-362e3.firebaseio.com/users");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username1=username.getText().toString();
                String password1=password.getText().toString();
                String age1=age.getText().toString();
                String bg1=bg.getText().toString();
                String name1=name.getText().toString();
                String phone1=phone.getText().toString();

                User new_user =new User();
                new_user.setUsename(username1);
                new_user.setPassword(password1);
                new_user.setAge(age1);
                new_user.setBg(bg1);
                new_user.setName(name1);
                new_user.setLat("33.424564");
                new_user.setLon("-111.928001");
                new_user.setPhone(phone1);
                //Log.e("# # # # # ",users.get(0));

                uniqueUser();

               /* Log.e("- - - - - - - - - - ",users.size()+"");
*/
                for(int i=0;i<users.size();i++){
                    Log.e("- - - - - - - - - - ",users.get(i));
                }

                if(!users.contains(username1)){
                    fb.push().setValue(new_user);

                    Intent login=new Intent(RegisterActivity.this,LoginActivity.class);
                    RegisterActivity.this.startActivity(login);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), username1+" Already Exist!", Toast.LENGTH_LONG);
                    toast.show();
                }



            }
        });


    }



    private void uniqueUser() {

        fb1=new Firebase("https://needforblood-362e3.firebaseio.com/users");

        fb1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String user_key=data.getKey();
                    //Log.e("* * * * * * * * * *",user_key);
                    //boolean ans=alreadyUser(user_key,username1);
                    addUser(user_key);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        //Log.e("- - - - - - - - - - ",users.size()+"");
       /* for(int i=0;i<users.size();i++){
            Log.e("- - - - - - - - - - ",users.get(i));
        }

        if(users.contains(username1)){
            return false;
        }else{
            return true;
        }*/

    }

    private void addUser(String user_key) {

        Firebase fb2=new Firebase("https://needforblood-362e3.firebaseio.com/users/"+user_key);

        fb2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1=dataSnapshot.getValue(User.class);
                int len=users.size();
                users.add(len,user1.getUsename());
                Log.e("# # # # # ",users.get(len));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /*
    private boolean alreadyUser(String user_key, final String username1) {
        Firebase fb2=new Firebase("https://needforblood-ae0b1.firebaseio.com/users/"+user_key);

        fb2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user12=dataSnapshot.getValue(User.class);
                if(user12.getUsename().equals(username1)){
                   // return true;
                }else{
                    Log.e("_ _ _ _ _ _ _ _ _ _ _"," In the else");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }*/
}
