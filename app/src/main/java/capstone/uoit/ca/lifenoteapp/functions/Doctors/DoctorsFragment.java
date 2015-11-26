package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * DoctorsFragment
 *
 * @author Matthew Rosettis
 */
public class DoctorsFragment extends Fragment implements DoctorDataListener{
    private final String fileName = "doctors.txt";
    View view;

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
        Log.d("File Name",fileName);
        downloadDocs(fileName);
    }

    @Override
    public void onResume(){
        super.onResume();
        downloadDocs(fileName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctors, container, false);
        return view;
    }


    public void downloadDocs(String fileName){
        DownloadDoctorsTask task = new DownloadDoctorsTask(this);
        task.execute(fileName);
    }

    @Override
    public void showDoctors(ArrayList<Doctor> data) {
        DoctorAdapter output = new DoctorAdapter(getContext(), data);
        ListView list = (ListView) view.findViewById(R.id.doctorListView);
        list.setAdapter(output);
    }
}
