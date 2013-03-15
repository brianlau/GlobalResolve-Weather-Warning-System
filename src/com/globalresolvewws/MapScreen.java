package com.globalresolvewws;

//import android.app.Activity;
import com.globalresolvewws.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class MapScreen extends FragmentActivity {
	private GoogleMap myMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        
        FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment 
         = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
        myMap = mySupportMapFragment.getMap();
        
        myMap.setMyLocationEnabled(true);
        
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}