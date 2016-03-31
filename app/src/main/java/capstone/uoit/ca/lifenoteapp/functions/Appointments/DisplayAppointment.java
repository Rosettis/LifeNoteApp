package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;


public class DisplayAppointment extends FragmentActivity {

    private AppointmentsDBHelper db;
    int Value;
    TextView header;
    EditText apt_name, apt_date, apt_time, apt_clinic, apt_doctor;
    Button editAppointment, deleteAppointment, saveAppointment;

    int idUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_display_appointment);

        editAppointment = (Button)findViewById(R.id.edit_apt);
        deleteAppointment = (Button)findViewById(R.id.delete_apt);
        saveAppointment = (Button) findViewById(R.id.delete_apt);


        apt_name = (EditText)findViewById(R.id.editName);
        apt_date = (EditText)findViewById(R.id.editDate);
        apt_time = (EditText)findViewById(R.id.editTime);
        apt_clinic = (EditText)findViewById(R.id.editClinic);
        apt_doctor = (EditText)findViewById(R.id.editDoctor);

        saveAppointment = (Button)findViewById(R.id.saveEvent);
        deleteAppointment = (Button)findViewById(R.id.delete_apt);
        editAppointment = (Button)findViewById(R.id.edit_apt);

        header = (TextView)findViewById(R.id.aptHeader);


        db = new AppointmentsDBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int Value = extras.getInt("id");

            if(Value > 0){
                Cursor cursor = db.getAppointment(Value);
                idUpdate = Value;
                cursor.moveToFirst();


                saveAppointment.setVisibility(View.INVISIBLE);
                editAppointment.setVisibility(View.VISIBLE);
                deleteAppointment.setVisibility(View.VISIBLE);
                String aptName = cursor.getString(cursor.getColumnIndex(db.column_aptName));
                String aptDate = cursor.getString(cursor.getColumnIndex(db.column_aptDate));
                String aptTime = cursor.getString(cursor.getColumnIndex(db.column_aptTime));
                String aptClinic = cursor.getString(cursor.getColumnIndex(db.column_aptClinic));
                String aptDoctor = cursor.getString(cursor.getColumnIndex(db.column_aptDoctor));


                if(!cursor.isClosed()){
                    cursor.close();
                }



                header.setText((CharSequence) aptName);
                header.setFocusable(false);
                header.setClickable(false);

                apt_name.setText((CharSequence) aptName);
                apt_name.setFocusable(false);
                apt_name.setClickable(false);

                apt_date.setText((CharSequence)aptDate);
                apt_date.setFocusable(false);
                apt_date.setClickable(false);

                apt_time.setText((CharSequence)aptTime);
                apt_time.setFocusable(false);
                apt_time.setClickable(false);

                apt_clinic.setText((CharSequence)aptClinic);
                apt_clinic.setFocusable(false);
                apt_clinic.setClickable(false);

                apt_doctor.setText((CharSequence)aptDoctor);
                apt_doctor.setFocusable(false);
                apt_doctor.setClickable(false);


            }
        }
    }




    public boolean editEvent(View v){

        header.setVisibility(View.INVISIBLE);
        apt_name.setVisibility(View.VISIBLE);
        saveAppointment.setVisibility(View.VISIBLE);
        editAppointment.setVisibility(View.INVISIBLE);
        deleteAppointment.setVisibility(View.INVISIBLE);




        apt_name.setEnabled(true);
        apt_name.setFocusableInTouchMode(true);
        apt_name.setClickable(true);

        apt_date.setEnabled(true);
        apt_date.setFocusableInTouchMode(true);
        apt_date.setClickable(true);

        apt_time.setEnabled(true);
        apt_time.setFocusableInTouchMode(true);
        apt_time.setClickable(true);

        apt_clinic.setEnabled(true);
        apt_clinic.setFocusableInTouchMode(true);
        apt_clinic.setClickable(true);

        apt_doctor.setEnabled(true);
        apt_doctor.setFocusableInTouchMode(true);
        apt_doctor.setClickable(true);

        return true;
    }

    public boolean deleteEvent(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteAppointment).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteAppointment(idUpdate);
                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ShowAppointments.class);
                startActivity(intent);
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog d = builder.create();
        d.setTitle("Are you sure?");
        d.show();

        return true;

    }

    public boolean saveEvent(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        db = new AppointmentsDBHelper(this);
        apt_name = (EditText)findViewById(R.id.eventName);
        apt_date = (EditText)findViewById(R.id.dateView);

        apt_time = (EditText)findViewById(R.id.timeView);

        apt_clinic = (EditText)findViewById(R.id.clinicView);
        apt_doctor = (EditText)findViewById(R.id.doctorView);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){

            Value = extras.getInt("id");

            if (Value >= 0) {

                builder.setMessage(R.string.saveAppointment).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = apt_name.getText().toString();
                        String date = apt_date.getText().toString();
                        String time = apt_time.getText().toString();
                        String clinic = apt_clinic.getText().toString();
                        String doctor = apt_doctor.getText().toString();

                        ContentValues cv = new ContentValues();
                        cv.put("title", name);

                        db.updateAppointment(Value, name, date, time, clinic, doctor);
                        Log.i("SaveAppointment", "Appointment Updated!");
                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ShowAppointments.class);
                        startActivity(intent);

                    }

                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure?");
                d.show();



            }}

        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_display_appointment, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
