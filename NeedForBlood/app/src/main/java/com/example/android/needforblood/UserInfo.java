package com.example.android.needforblood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfo extends AppCompatActivity {

    private User current_user;
    private TextView name,bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        current_user = (User)getIntent().getSerializableExtra("user");

        name = (TextView)findViewById(R.id.name_value);
        bg = (TextView)findViewById(R.id.bg_value);

        name.setText(current_user.getName());
        bg.setText(current_user.getBg());
    }
}
