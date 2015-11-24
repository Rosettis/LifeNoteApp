package capstone.uoit.ca.lifenoteapp.functions.FindHelp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import android.support.v4.app.Fragment;

import capstone.uoit.ca.lifenoteapp.R;

//GOOGLE API KEY: AIzaSyCAc9psQCYyWunwg_aYR4Eva7HsBg7mZMI

/**
 * MapPane
 *
 * @author Matthew Rosettis
 */

public class MapPane extends FragmentActivity {
    private double latitude, longitude;
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        Intent callingIntent = getIntent();
        latitude = callingIntent.getDoubleExtra("latitude", 0.0);
        longitude = callingIntent.getDoubleExtra("longitude", 0.0);
        String address = callingIntent.getStringExtra("location");

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if(map != null){
            LatLng pos = new LatLng(latitude,longitude);
            //show the pos as a marker
            map.addMarker(new MarkerOptions().position(pos).title(address));

            //scroll the map to show pos centered
            map.animateCamera(CameraUpdateFactory.newLatLng(pos));

            //configure the map
            map.setTrafficEnabled(true);
            map.setBuildingsEnabled(true); //for v3
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            /*map.getUISettings().setZoomControlsEnabled(true);
            map.getUISettings().setZoomGesturesEnabled(true);*/
        }
    }
}