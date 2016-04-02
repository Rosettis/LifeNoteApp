package capstone.uoit.ca.lifenoteapp.functions.FindHelp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;

/**
 * FindHelpFragment
 *
 * @author Matthew Rosettis
 */
public class FindHelpFragment extends Fragment {

    public FindHelpFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_findhelp, container, false);

        Button findHosptialButton = (Button) v.findViewById(R.id.button_hospital);
        findHosptialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callHospital();
            }
        });

        Button findClinicButton = (Button) v.findViewById(R.id.button_clinic);
        findClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callClinic();
            }
        });
       /* //Code for Implementing tabs of GoogleMap Inline Objects
       FragmentTabHost mTabHost = (FragmentTabHost)v.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("Hospitals").setIndicator("Hospitals"),
                HospitalsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Clinics").setIndicator("Clinics"),
                ClinicFragment.class, null);*/
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void callHospital(){
// Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=hospital");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    public void callClinic(){
// Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=clinic");
//        Uri gmmIntentUri = Uri.parse("geo:43.945604,-78.896326?q=clinics");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }
}
