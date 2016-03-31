package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import capstone.uoit.ca.lifenoteapp.R;


public class AddAppointment extends FragmentActivity implements View.OnClickListener {

    EditText dateText;
    EditText timeText;
    Calendar eventCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute, mAMPM;
    EditText apt_name, apt_date, apt_time, apt_clinic, apt_doctor;
    Button addButton;
    AppointmentsDBHelper db;
    FragmentsAvailable newFragmentType;
    Fragment fragment = new Fragment();
    AppointmentsFragment fragment1 = new AppointmentsFragment();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_appointment);

    }

    public enum FragmentsAvailable {
        HISTORY
    }

    public void setDate(View v){

        dateText= (EditText)findViewById(R.id.dateView);
        mYear = eventCalendar.get(Calendar.YEAR);
        mMonth = eventCalendar.get(Calendar.MONTH);
        mDay = eventCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog date = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){

                        dateText.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                    }},mYear,mMonth,mDay);
        date.show();

    }



    public void setTime(View v){

        timeText = (EditText)findViewById(R.id.timeView);
        mHour = eventCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = eventCalendar.get(Calendar.MINUTE);
        //mAMPM = eventCalendar.get(Calendar.AM_PM);

        TimePickerDialog time = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener(){

                    public void onTimeSet(TimePicker view, int houtOfDay, int minute){
                        String output = String.format("%02d:%02d", houtOfDay, minute);
                        if(houtOfDay > 12){
                            timeText.setText(output + " " + "PM" );
                        }
                        if(houtOfDay == 12){
                            timeText.setText("12" + ":" + minute + " PM" );
                        }
                        if(houtOfDay < 12){
                            if(houtOfDay != 0){
                                timeText.setText(output + " " + "AM");
                            } else {
                                timeText.setText("12" + ":" + minute + " AM");
                            }
                        }

                    }
                }, mHour, mMinute, false);
        time.show();

    }

    public void createNewEvent(View v){

        db = new AppointmentsDBHelper(this);
        apt_name = (EditText)findViewById(R.id.eventName);
        apt_date = (EditText)findViewById(R.id.dateView);
        apt_date.setFocusable(false);

        apt_time = (EditText)findViewById(R.id.timeView);
        apt_time.setFocusable(false);


        apt_clinic = (EditText)findViewById(R.id.clinicView);
        apt_doctor = (EditText)findViewById(R.id.doctorView);
        addButton = (Button)findViewById(R.id.createEvent);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){

            int Value = extras.getInt("id");

            if (Value <= 0){

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = apt_name.getText().toString();
                        String date = apt_date.getText().toString();
                        String time = apt_time.getText().toString();
                        String clinic = apt_clinic.getText().toString();
                        String doctor = apt_doctor.getText().toString();

                        ContentValues cv = new ContentValues();
                        cv.put("title", name);

                        db.createAppointment(name, date, time, clinic, doctor);
                        Log.i("NewAppointment", "New Appointment Saved!");
                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time)

                                .putExtra(CalendarContract.Events.TITLE, name)
                                .putExtra(CalendarContract.Events.DESCRIPTION, doctor)
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, clinic);
                        startActivity(intent);
                        Intent i = new Intent(getApplicationContext(), ShowAppointments.class);
                        startActivity(i);
                    }
                });
            }
        }


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_new_appointment, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
