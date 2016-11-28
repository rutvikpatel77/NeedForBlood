package com.example.android.needforblood;

import android.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView user,age,bg;
    private BroadcastReceiver broadcastReceiver;
    private User current_user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.i("TAG-AAKANXU","---------------------------------------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        current_user = (User)getIntent().getSerializableExtra("user");

       user = (TextView)findViewById(R.id.username);
       /* age = (TextView) findViewById(R.id.agev);
        bg=(TextView) findViewById(R.id.bgv);
*/
        user.setText(current_user.getName());
        //age.setText(current_user.getKey());
        //bg.setText(current_user.getBg());


        if(!runtimePermissions())
            start_service();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //broadcast receiver is registered in onResume method
    @Override
    protected void onResume() {
        super.onResume();
        //if the broadcast receiver doesnt exist, we create one
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                //when broadcast will receive content it will be captured on onReceive method
                @Override
                public void onReceive(Context context, Intent intent) {

                  /*  tv.append("\n" +intent.getExtras().get("coordinates"));
                    //clear previous values
                    longitude.setText(" ");
                    latitude.setText(" ");

                    longitude.setText(intent.getExtras().get("longitude").toString());
                    latitude.setText(intent.getExtras().get("latitude").toString());
*/
                    String lon = intent.getExtras().get("longitude").toString();
                    String lat= intent.getExtras().get("latitude").toString();

                    current_user.setLon(lon);
                    current_user.setLat(lat);
                    //Toast toast = Toast.makeText(getApplicationContext(), lon+" * * "+lat, Toast.LENGTH_LONG);
                    //toast.show();



                    Firebase ref = new Firebase("https://needforblood-362e3.firebaseio.com/users/").child(current_user.getKey());

                    //Firebase fb1=new Firebase("https://needforblood-362e3.firebaseio.com/users/"+current_user.getKey());

                    //mDatabase = FirebaseDatabase.getInstance().getReference("https://needforblood-362e3.firebaseio.com/users/"+current_user.getKey());

                    ref.setValue(current_user);



                }
            };
        }

        registerReceiver(broadcastReceiver,new IntentFilter("locationUpdate"));
    }

    //unregister the receiver
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if receiver exits, destroy it
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }


    private void start_service() {
        Intent i =new Intent(getApplicationContext(),locationService.class);
        Toast toast = Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG);
        toast.show();
        startService(i);
    }


    private boolean runtimePermissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},100);

            //if we need permission, returns true
            return true;
        }
        //if we dont need permission, returns false
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            //if permissionGranted enable buttons, else ask for permission again
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                start_service();
            }else {
                runtimePermissions();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();




        if (id == R.id.nav_first_layout) {
            // Handle
            fragmentManager.beginTransaction().replace(R.id.content_frame,new FirstFragment()).commit();

        } else if (id == R.id.nav_second_layout) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new SecondFragment()).commit();

        } else if (id == R.id.nav_third_layout) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new ThirdFragment()).commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send_invite) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new SendInvite()).commit();

        } else if (id == R.id.nav_eligibility) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new Eligibility()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
