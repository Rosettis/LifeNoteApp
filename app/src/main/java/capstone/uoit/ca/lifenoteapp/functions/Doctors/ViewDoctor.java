package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;

/**
 * Created by Matthew on 4/6/2016.
 */
public class ViewDoctor extends Fragment {
    Long doctorId = 0L;
    Doctor doctor;

    public static ViewDoctor newInstance(Long doctorId) {
        ViewDoctor fragment = new ViewDoctor();
        //fragment.doctorId = doctorId;
        Bundle args = new Bundle();
        args.putLong("id",doctorId);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewDoctor() {
    }

    protected TextView doctorName;
    protected TextView doctorPhone;
    protected TextView doctorTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        doctorId = getArguments().getLong("id");
        DoctorDBHelper dbHelper = DoctorDBHelper.getInstance(getContext());
        doctor = dbHelper.getDoctor(doctorId);
        Log.d("DoctorTestValue",Long.toString(doctor.getId()));
        if (dbHelper.getDoctor(doctor.getId()) == null) getFragmentManager().popBackStack();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_doctor, container, false);
        /*Bundle bundle = this.getArguments();
        if(bundle != null) {
            doctor = bundle.getParcelable("doctor");
            Log.d("docValue", Long.toString(doctor.getId()));
        }else{
            Log.d("test","Bundle is empty");
        }*/

        doctorName = (TextView) view.findViewById(R.id.viewDoctorName);
        doctorPhone = (TextView) view.findViewById(R.id.viewDoctorPhone);
        doctorTitle = (TextView) view.findViewById(R.id.viewDoctorTitle);

        doctorName.setText(doctor.getName());
        doctorPhone.setText(doctor.getPhone());
        doctorTitle.setText(doctor.getTitle());
        /*String name = "Dr. Anderson";
        doctorName.setText(name);
        String phone = "416-023-0338";
        doctorPhone.setText(phone);
        String title = "Specialist";
        doctorTitle.setText(title);*/
        //Button to edit doctor
        /*Button btnEditDoctor = (Button) view.findViewById(R.id.btn_editDoctor);
        btnEditDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Add A Doctor", Toast.LENGTH_SHORT).show();
                editDoctor(doctor);
            }
        });*/
        //Button to delete doctor
        Button btnDeleteDoctor = (Button) view.findViewById(R.id.btn_deleteDoctor);
        btnDeleteDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Add A Doctor", Toast.LENGTH_SHORT).show();
                //TODO Go back to the DoctorsFragment and run the removeDoctor function for this doctor
                DoctorDBHelper dbHelper =  DoctorDBHelper.getInstance(getContext());
                dbHelper.deleteDoctor(doctor.getId());
                getFragmentManager().popBackStack();
            }
        });
        //Button to go back doctor
        Button btnCancel = (Button) view.findViewById(R.id.btn_backToDoctors);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FragmentManager fm;
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void editDoctor(Doctor doctor){

    }
}


