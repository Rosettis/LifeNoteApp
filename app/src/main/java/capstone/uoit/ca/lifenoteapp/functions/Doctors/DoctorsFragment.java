package capstone.uoit.ca.lifenoteapp.functions.Doctors;/**
 * Created by 100490515 on 11/16/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * DoctorsFragment
 *
 * @author Matthew Rosettis
 */
public class DoctorsFragment extends Fragment {

    public static DoctorsFragment newInstance() {
        DoctorsFragment fragment = new DoctorsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }
}
