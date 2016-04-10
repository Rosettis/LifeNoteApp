package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Graphs.CodifiedHashMapManager;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CreateNoteHome;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.DividerItemDecoration;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;

/**
 * Created by Peter on 09/04/16.
 */
public class NewAppointmentsFragment extends Fragment implements AppointmentItemAddapter.AppointmentViewHolder.OnAppointmentSelectedListener{
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_notes, container, false);

        rv = (RecyclerView)rootView.findViewById(R.id.RV_notes_list);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_createNewNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction
                        .replace(R.id.content, new NewAddAppointment(), "AddAppointment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(this.getContext());
        ArrayList<NewAppointment> appointments = dbHelper.getAllAppointments();
        ArrayList<NewAppointment> reversed = new ArrayList<>();


        AppointmentItemAddapter adapter = new AppointmentItemAddapter(reversed, this);
        rv.setAdapter(adapter);

        if(appointments.size() == 0) {
            rv.setVisibility(View.GONE);
            TextView emptyView = (TextView) rootView.findViewById(R.id.empty_view);
            emptyView.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(this.getContext());
        ArrayList<NewAppointment> appointments = dbHelper.getAllAppointments();
        AppointmentItemAddapter adapter = new AppointmentItemAddapter(appointments, this);
        rv.setAdapter(adapter);
    }

    @Override
    public void displayAppointment(NewAppointment appointment) {
        FragmentManager fragmentManager = getFragmentManager();
        NewAddAppointment appontmentFrag = NewAddAppointment.newInstance(appointment.getId());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(R.id.content, appontmentFrag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteAppointment(NewAppointment appointment) {
        System.out.println("DELETING APPOINTMENT");
        DialogInterface.OnClickListener dialogClickListener = new OnConfirmListener(appointment, this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you wish to delete this note?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public class OnConfirmListener implements DialogInterface.OnClickListener {
        NewAppointment appointment;
        AppointmentItemAddapter.AppointmentViewHolder.OnAppointmentSelectedListener lsnr;

        public OnConfirmListener(NewAppointment appointment, AppointmentItemAddapter.AppointmentViewHolder.OnAppointmentSelectedListener lsnr) {
            this.appointment = appointment;
            this.lsnr = lsnr;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(getContext());
                    dbHelper.deleteAppointment(appointment.getId());
                    ArrayList<NewAppointment> appointments = dbHelper.getAllAppointments();
                    AppointmentItemAddapter adapter = new AppointmentItemAddapter(appointments, lsnr);
                    rv.setAdapter(adapter);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    }
}
