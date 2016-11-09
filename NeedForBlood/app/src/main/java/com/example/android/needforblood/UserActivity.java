package com.example.android.needforblood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    private TextView user,age,bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        User current_user = (User)getIntent().getSerializableExtra("user");

        user = (TextView)findViewById(R.id.username);
        age = (TextView) findViewById(R.id.agev);
        bg=(TextView) findViewById(R.id.bgv);

        user.setText(current_user.getUsename());
        age.setText(current_user.getAge());
        bg.setText(current_user.getBg());





    }
}
