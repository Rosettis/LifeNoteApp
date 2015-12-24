//package capstone.uoit.ca.lifenoteapp.functions.Appointments;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//
//import capstone.uoit.ca.lifenoteapp.R;
//
///**
// * Created by Peter on 03/12/15.
// */
//public class ShowAppointments extends AppCompatActivity {
//    ArrayList<Appointment> listOfAppointments;
//    private final int NEW_APPOINTMENT_RETURN_VAL = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_appointments);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent createNewAppointmentIntent = new Intent(getBaseContext(), CreateAppointment.class);
//                startActivityForResult(createNewAppointmentIntent, NEW_APPOINTMENT_RETURN_VAL);
//
//            }
//        });
//
//        listOfAppointments = new ArrayList<>();
//        listOfAppointments.add(new Appointment("Dr.Sperber", "Thurs Jan 7th 2016 5:30pm", "", "Whitby Orthodontics"));
//
//        AppointmentAdapter adapter = new AppointmentAdapter(this, listOfAppointments);
//        ListView appointmentsList = (ListView) findViewById(R.id.listView_appointments);
//        appointmentsList.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == NEW_APPOINTMENT_RETURN_VAL) {
//            ArrayList<Appointment> listOfAppointments2 = new ArrayList<>();
//            listOfAppointments2.add(new Appointment("Dr.Kalid", "Fri Dec 4th 2015 12:30pm", "", "Sunny Side Medical"));
//            listOfAppointments2.add(new Appointment("Dr.Sperber", "Thurs Jan 7th 2016 5:30pm", "", "Whitby Orthodontics"));
//            AppointmentAdapter adapter = new AppointmentAdapter(getBaseContext(), listOfAppointments2);
//            ListView appointmentsList = (ListView) findViewById(R.id.listView_appointments);
//            appointmentsList.setAdapter(adapter);
//        }
//    }
//}
