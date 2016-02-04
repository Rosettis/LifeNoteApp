package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.DividerItemDecoration;

/**
 * DoctorsFragment
 *
 * @author Matthew Rosettis
 */
public class DoctorsFragment extends Fragment implements DoctorAdapter.DoctorViewHolder.OnDoctorSelectedListener{ //implements DoctorDataListener for File
    private final String fileName = "doctors.txt";
    View view;
    private RecyclerView rv;

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
        //Code for Doctors from File
        /*Log.d("File Name", fileName);
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadDocs(is);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onResume(){
        super.onResume();
        //Code for Doctors from File
        /*try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadDocs(is);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctors, container, false);

        rv = (RecyclerView)view.findViewById(R.id.rv_doctor_view);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);

        DoctorDBHelper dbHelper = DoctorDBHelper.getInstance(this.getContext());

        ArrayList<Doctor> doctors = dbHelper.getAllDoctors();
        DoctorAdapter adapter = new DoctorAdapter(doctors,this);
        rv.setAdapter(adapter);




        return view;
    }

    @Override
    public void displayDoctor(Doctor doctor) {
        DoctorsFragment frag = DoctorsFragment.newInstance();
        FragmentManager fragManager = getFragmentManager();

        FragmentTransaction transaction = fragManager.beginTransaction();
        transaction
                .replace(R.id.content, frag)
                .addToBackStack(null)
                .commit();
    }

    //TODO: RecyclerView Integration
    //Populating Doctors from a text file
    /*public void downloadDocs(InputStream fileName){
        DownloadDoctorsTask task = new DownloadDoctorsTask(this);
        task.execute(fileName);
    }

    @Override
    public void showDoctors(ArrayList<Doctor> data) {
        DoctorAdapter output = new DoctorAdapter(getContext(), data);
        ListView list = (ListView) view.findViewById(R.id.doctorListView);
        list.setAdapter(output);
    }*/
}
