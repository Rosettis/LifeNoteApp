package capstone.uoit.ca.lifenoteapp.functions.FindHelp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import capstone.uoit.ca.lifenoteapp.R;

//GOOGLE API KEY: AIzaSyCAc9psQCYyWunwg_aYR4Eva7HsBg7mZMI

/**
 * MapPane
 *
 * @author Matthew Rosettis
 */

public class MapPane extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        long minTime = 2000;
        float minDist = 10f;
        mMap = googleMap;

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDist, locationListener);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        //mMap.getUiSettings().
        // Add a marker in Sydney and move the camera
        LatLng current = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(current).title("You are Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
        //Add markers
        LatLng clinic1 = new LatLng(latitude-0.01,longitude+0.006);
        mMap.addMarker(new MarkerOptions().position(clinic1).title("Sunny Side Medical"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}