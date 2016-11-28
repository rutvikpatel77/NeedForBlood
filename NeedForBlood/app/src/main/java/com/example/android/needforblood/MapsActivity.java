package com.example.android.needforblood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<User> users;
    Firebase fb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TAG","-------------------------------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.i("TAG2","-------------------------------");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Log.i("TAG3","-------------------------------");
        mapFragment.getMapAsync(this);





    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(33.388414, -111.931782);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Aakanxu"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng sydney1 = new LatLng(33.4, -111.8);
        mMap.addMarker(new MarkerOptions().position(sydney1).title("Rutvik"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1));

        LatLng sydney2 = new LatLng(33.2, -111.7);
        mMap.addMarker(new MarkerOptions().position(sydney2).title("User"));*/

        fb1 = new Firebase("https://needforblood-362e3.firebaseio.com/users");

        fb1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    final User user12=data.getValue(User.class);
                    LatLng sydney = new LatLng(Double.parseDouble(user12.getLat()),Double.parseDouble(user12.getLon()));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(user12.getName()+" :"+user12.getBg()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                   /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            *//*Intent login=new Intent(MapsActivity.this,LoginActivity.class);
                            MapsActivity.this.startActivity(login);
                            return false;*//*


                            Intent i = new Intent();
                            Bundle b = new Bundle();
                            b.putSerializable("user",user12);
                            i.putExtras(b);
                            i.setClass(MapsActivity.this, UserInfo.class);
                            startActivity(i);
                            return false;
                        }
                    });*/

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        LatLng l=new LatLng(33.424564,-111.928001);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l,14));
       // mMap.animateCamera(CameraUpdateFactory.zoomIn());
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);


    }

    // TO DO : Array List of LatLang to project multiple markers

    /*

    ArrayList<MarkerData> markersArray = new ArrayList<MarkerData>();

for(int i = 0 ; i < markersArray.size() ; i++ ) {

    createMarker(markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet(), markersArray.get(i).getIconResID());
}

...

protected void createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

    return googleMap.addMarker(new MarkerOptions()
            .position(new LatLng(latitude, longitude))
            .anchor(0.5f, 0.5f)
            .title(title)
            .snippet(snippet);
            .icon(BitmapDescriptorFactory.fromResource(iconResID)));
}
     */
}
