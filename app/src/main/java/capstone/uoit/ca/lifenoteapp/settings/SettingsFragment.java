package capstone.uoit.ca.lifenoteapp.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Appointments.NewAppointmentsDBHelper;
import capstone.uoit.ca.lifenoteapp.functions.Doctors.DoctorDBHelper;
import capstone.uoit.ca.lifenoteapp.functions.Graphs.CodifiedHashMapManager;
import capstone.uoit.ca.lifenoteapp.functions.Medication.Medication;
import capstone.uoit.ca.lifenoteapp.functions.Medication.NewMedicationDBHelper;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayoutDBHelper;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Settings");



        Button resetBtn = (Button) rootView.findViewById(R.id.btn_reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RESETTING APPLICATION", "resetAllApplicationDataBases");
                NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(getContext());
                dbHelper.deleteAllNoteLayouts();

                NoteDBHelper noteDBHelper = NoteDBHelper.getInstance(getContext());
                noteDBHelper.deleteAllNotes();

                CodifiedHashMapManager hashMapManager = CodifiedHashMapManager.getInstance(getContext());
                hashMapManager.clearHashTable();

                NewMedicationDBHelper medDBHelper = NewMedicationDBHelper.getInstance(getContext());
                medDBHelper.deleteAllMedications();

                NewAppointmentsDBHelper appDBHelper = NewAppointmentsDBHelper.getInstance(getContext());
                appDBHelper.deleteAllAppointments();

                DoctorDBHelper dochelper = DoctorDBHelper.getInstance(getContext());
                dochelper.deleteAllDoctors();
            }
        });


        return rootView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
////            mListener.onFragmentInteraction(uri);
//        }
//    }


}
