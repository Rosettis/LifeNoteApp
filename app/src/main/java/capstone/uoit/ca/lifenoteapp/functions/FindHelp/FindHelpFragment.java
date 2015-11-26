package capstone.uoit.ca.lifenoteapp.functions.FindHelp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.LatLng;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * FindHelpFragment
 *
 * @author Matthew Rosettis
 */
public class FindHelpFragment extends Fragment {
    private static View v;
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    static final LatLng TORONTO = new LatLng(43.7,79.4);
    private double latitude, longitude;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.fragment_findhelp, container, false);
        } catch (InflateException e) {
            return v;
        }
        return v;
    }
}
