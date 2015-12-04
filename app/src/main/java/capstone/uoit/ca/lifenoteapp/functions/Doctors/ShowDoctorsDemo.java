package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Appointments.Appointment;
import capstone.uoit.ca.lifenoteapp.functions.Appointments.AppointmentAdapter;
import capstone.uoit.ca.lifenoteapp.functions.Appointments.CreateAppointment;
import capstone.uoit.ca.lifenoteapp.functions.Notes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.NoteAdapter;

public class ShowDoctorsDemo extends AppCompatActivity {
    public final int NEW_DOC_RETURN_VAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctors_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewDocIntent = new Intent(getBaseContext(), CreateDoctor.class);
                startActivityForResult(createNewDocIntent, NEW_DOC_RETURN_VAL);
            }
        });

        ArrayList<Doctor> listOfDoctors = new ArrayList<>();
        listOfDoctors.add(new Doctor("Dr. Sperber", "905 449 5555", "Whitby Orthodontics"));
        listOfDoctors.add(new Doctor("Dr. Kim", "905 665 5555", "Pickering Dental"));

        DoctorAdaptorDemo adapter = new DoctorAdaptorDemo(this, listOfDoctors);
        ListView doctorsList = (ListView) findViewById(R.id.listView_doctorsDemo);
        doctorsList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_DOC_RETURN_VAL) {
            ArrayList<Doctor> listOfDoctors = new ArrayList<>();
            listOfDoctors.add(new Doctor("Dr. Makrehchi", "905 639 4823", "North Oshawa Clinic"));
            listOfDoctors.add(new Doctor("Dr. Sperber", "905 449 5555", "Whitby Orthodontics"));
            listOfDoctors.add(new Doctor("Dr. Kim", "905 665 5555", "Pickering Dental"));
            DoctorAdaptorDemo adapter = new DoctorAdaptorDemo(getBaseContext(), listOfDoctors);
            ListView appointmentsList = (ListView) findViewById(R.id.listView_doctorsDemo);
            appointmentsList.setAdapter(adapter);
        }
    }

}