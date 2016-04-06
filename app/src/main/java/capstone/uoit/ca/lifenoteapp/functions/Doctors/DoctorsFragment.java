package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * DoctorsFragment
 *
 * @author Matthew Rosettis
 */
public class DoctorsFragment extends Fragment implements DoctorAddDialogListener { // implements DoctorDataListener for File, implements DoctorAddDialogListener for dialog return info
    private RecyclerView rv;
    DoctorAdapter adapter;
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
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        rv = (RecyclerView)view.findViewById(R.id.rv_doctor_view);
        //using a Linear Layout manager
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new DoctorAdapter(createDoctorList(10));
        rv.setAdapter(adapter);
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

    private List<Doctor> createDoctorList(int size) {
        //Add these doctors specifically
       /* <string-array name="doctors_array">
        <item>Dr. Khlid (Doctor)</item>
        <item>Dr. Sperber (Orthodontist)</item>
        <item>Dr. Kim (Dentist)</item>
        <item>Dr. Anderson (Specialist)</item>
        </string-array>*/
        for (int i=1; i <= size; i++) {
            Doctor doctor = new Doctor.Builder("Name_"+i,"Phone_"+i)
                    .address("Address_" + i).email("Email_" + i).build();
            doctors.add(doctor);
        }
        return doctors;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        adapter.notifyDataSetChanged();
    }

    public void removeDoctor(int position){
        doctors.remove(position);
        rv.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, doctors.size());
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
        addDoctor(new Doctor.Builder(addName, addPhone).address(addAddress)
                .email(addEmail).title(addTitle).build());
    }
}
