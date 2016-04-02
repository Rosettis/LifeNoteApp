package capstone.uoit.ca.lifenoteapp.functions.FindHelp;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Matthew on 4/2/2016.
 */
public class ClinicFragment extends Fragment implements OnMapReadyCallback {
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    static final LatLng TORONTO = new LatLng(43.7,79.4);
    private double latitude, longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentManager fragManager = myContext.getFragmentManager();

        View view = inflater.inflate(R.layout.fragment_help_clinic, container, false);

//        MapFragment mapFragment = (MapFragment) HospitalsFragment.getFragmentManager().findFragmentById(R.id.mapHospital);
        MapFragment mapFragment = (MapFragment) fragManager.findFragmentById(R.id.mapClinic);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mapClinic));
       // ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}
