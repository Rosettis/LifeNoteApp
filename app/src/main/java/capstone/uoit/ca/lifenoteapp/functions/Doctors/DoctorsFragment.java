package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
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
import java.util.List;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.DividerItemDecoration;

/**
 * DoctorsFragment
 *
 * @author Matthew Rosettis
 */
public class DoctorsFragment extends Fragment { // implements DoctorDataListener for File
    private final String fileName = "doctors.txt";
    View view;
    private RecyclerView rv;
//    DoctorAdapter.DoctorViewHolder.OnDoctorSelectedListener lsnr;
    ArrayList<Doctor> doctors;

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
        /*//Code for Doctors from File
        Log.d("File Name", fileName);
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
        /*//Code for Doctors from File
        try {
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
//        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        //using a Linear Layout manager
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());

        /*try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadDocs(is);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        DoctorAdapter adapter = new DoctorAdapter(createDoctorList(10));
        rv.setAdapter(adapter);

        return view;
    }

    private List<Doctor> createDoctorList(int size) {

        List<Doctor> result = new ArrayList<Doctor>();
        for (int i=1; i <= size; i++) {
            Doctor doctor = new Doctor.Builder("Name_"+i,"Phone_"+i)
                    .address("Address_"+i).email("Email_"+i).build();
            result.add(doctor);
        }
        return result;
    }

    /*@Override
    public void displayDoctor(Doctor doctor) {
        DoctorsFragment frag = DoctorsFragment.newInstance();
        FragmentManager fragManager = getFragmentManager();

        FragmentTransaction transaction = fragManager.beginTransaction();
        transaction
                .replace(R.id.content, frag)
                .addToBackStack(null)
                .commit();
    }*/

    /*//TODO: RecyclerView Integration
    //Populating Doctors from a text file
    public void downloadDocs(InputStream fileName){
        DownloadFeedTask task = new DownloadFeedTask(this);
        task.execute(fileName);
    }*/

    /*@Override
    public void showDoctors(ArrayList<Doctor> doctors) {
        DoctorDBHelper dbHelper = DoctorDBHelper.getInstance(this.getContext());
        dbHelper.deleteAllDoctors();
        for(int i = 0; i < doctors.size(); i++){
            dbHelper.createDoctor(doctors.get(i).getName(),doctors.get(i).getPhone(),doctors.get(i).getAddress(),doctors.get(i).getEmail(),doctors.get(i).getLocation().toString());
        }
        this.doctors = dbHelper.getAllDoctors();
    }*/
}
