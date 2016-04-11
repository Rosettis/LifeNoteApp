package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Doctors.AddDoctor.DoctorAddDialog;
import capstone.uoit.ca.lifenoteapp.functions.Doctors.AddDoctor.DoctorAddDialogListener;

/**
 * DoctorsFragment
 *
 * @author Matthew Rosettis
 */
public class DoctorsFragment extends Fragment implements DoctorAddDialogListener,DoctorClickListener { // implements DoctorDataListener for File, implements DoctorAddDialogListener for dialog return info
    private RecyclerView rv;
    private int doctorId = 0;
    DoctorAdapter adapter;
    static DoctorDBHelper dbHelper;
    private List<Doctor> doctors = new ArrayList<Doctor>();
    //    DoctorAdapter.DoctorViewHolder.OnDoctorSelectedListener lsnr;

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
        //Doctor Database Implementation
        dbHelper = DoctorDBHelper.getInstance(getContext());
        createDoctorList();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("View Doctors");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        rv = (RecyclerView)view.findViewById(R.id.rv_doctor_view);
        //Doctor Database Implementation

        //using a Linear Layout manager
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new DoctorAdapter(this,doctors);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //view.getContext();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
        //Button to add more doctors
        FloatingActionButton btnFab = (FloatingActionButton) view.findViewById(R.id.btnFloatingAddDoctor);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Add A Doctor", Toast.LENGTH_SHORT).show();
                showDoctorAddDialog();
            }
        });
        return view;
    }

    private void createDoctorList() {
        // delete any products from a previous execution (If requested)
        dbHelper.deleteAllDoctors();
        //Adding Dr. Khalid
        dbHelper.createDoctor("Dr. Khalid", "416-524-1230", "", "", "Doctor", null);
        //Adding Dr. Sperber
        dbHelper.createDoctor("Dr. Sperber","905-272-8091","","","Orthodontist", null);
        //Adding Dr. Kim
        dbHelper.createDoctor("Dr. Kim","647-232-1884","","","Dentist", null);
        //Adding Dr. Anderson
        dbHelper.createDoctor("Dr. Anderson","416-023-0338","","","Specialist", null);
        //Populate list with database stored doctors
        doctors = dbHelper.getAllDoctors();
    }

    private void showDoctorAddDialog(){
        DoctorAddDialog editNameDialog = new DoctorAddDialog();
        editNameDialog.setCallBack(this);
        editNameDialog.show(this.getChildFragmentManager(), "DoctorAddDialog");
    }

    @Override
    public void onFinishAddDialog(String addName, String addPhone, String addAddress,
                                  String addEmail, String addTitle) {
//        Toast.makeText(this.getContext(),addName+" added!", Toast.LENGTH_SHORT).show();
        dbHelper.createDoctor(addName, addPhone, addAddress,addEmail,addTitle,"");
        doctors = dbHelper.getAllDoctors();
        adapter.notifyDataSetChanged();
    }

    public void viewDoctor(Doctor doctor){
        ViewDoctor fragment = ViewDoctor.newInstance(doctor.getId());
        /*Bundle bundle = new Bundle();
        bundle.putParcelable("doctor", doctor);*/
        Log.d("check", "Spahget");
        //fragment.setArguments(bundle);
        FragmentManager fragmentManager = this.getChildFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}
