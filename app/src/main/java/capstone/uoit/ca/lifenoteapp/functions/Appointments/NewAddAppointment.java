package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Graphs.CodifiedHashMapManager;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;
import capstone.uoit.ca.lifenoteapp.selectors.DatePickerFragment;
import capstone.uoit.ca.lifenoteapp.selectors.TimePickerFragment;

/**
 * Created by Peter on 09/04/16.
 */
public class NewAddAppointment extends Fragment {
    private EditText nameEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText clinicEditText;
    private EditText docEditText;
    private boolean editMode = false;
    private NewAppointment currAppointment;

    public static NewAddAppointment newInstance(long appointmentId) {
        NewAddAppointment instance = new NewAddAppointment();
        Bundle args = new Bundle();
        args.putBoolean("editMode", true);
        args.putLong("appointmentId", appointmentId);
        instance.setArguments(args);
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        try {
            editMode = args.getBoolean("editMode");
        } catch (NullPointerException noParam) {
            //no parameter passed default to create mode
            noParam.printStackTrace();
        }

        View rootView;

        if (!editMode) {
            ((MainActivity) getActivity()).setActionBarTitle("Create Appointment");
            rootView = inflater.inflate(R.layout.fragment_new_add_appointment, container, false);

            NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(getContext());
            ArrayList<NewAppointment> list = dbHelper.getAllAppointments();

            System.out.println("SIZE: " + list.size());

            for (NewAppointment appointment : list) {
                appointment.printAppointment();
            }
            nameEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentTitle);
            dateEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentDate);
            timeEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentTime);
            clinicEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentClinic);
            docEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentDoctor);

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            Date date = new Date(year, month, day);
            dateEditText.setText(formatDate(date, year));
            dateEditText.setFocusable(false);


            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            timeEditText.setText(formatTime(hour, min));
            timeEditText.setFocusable(false);



            dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        DatePickerFragment dpf = new DatePickerFragment();
                        dpf.setCallBack(onDateSetLsnr);
                        FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                        dpf.show(fragmentActivity.getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
                    }
                }
            });

            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment dpf = new DatePickerFragment();
                    dpf.setCallBack(onDateSetLsnr);
                    FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                    dpf.show(fragmentActivity.getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
                }
            });

            timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        TimePickerFragment tpf = new TimePickerFragment();
                        tpf.setCallBack(onTimeSetLsnr);
                        FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                        tpf.show(fragmentActivity.getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
                    }
                }
            });


            timeEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerFragment tpf = new TimePickerFragment();
                    tpf.setCallBack(onTimeSetLsnr);
                    FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                    tpf.show(fragmentActivity.getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.fragment_view_appointment, container, false);
            NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(getContext());
            currAppointment = dbHelper.getAppointment(args.getLong("appointmentId"));

            ((MainActivity) getActivity()).setActionBarTitle("Edit Appointment");

            nameEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentTitle);
            nameEditText.setText(currAppointment.getName());
            dateEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentDate);
            dateEditText.setText(currAppointment.getDate());
            timeEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentTime);
            timeEditText.setText(currAppointment.getTime());
            clinicEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentClinic);
            clinicEditText.setText(currAppointment.getClinic());
            docEditText = (EditText) rootView.findViewById(R.id.editText_enterAppointmentDoctor);
            docEditText.setText(currAppointment.getDoc());


            OnDeleteListener deleteListener = new OnDeleteListener(currAppointment);
            Button deleteBtn = (Button) rootView.findViewById(R.id.btn_deleteNote);
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setOnClickListener(deleteListener);

        }


        Button saveBtn = (Button) rootView.findViewById(R.id.btn_saveCreateNoteTwo);
        Button cancelBtn = (Button) rootView.findViewById(R.id.btn_cancelCreateNoteTwo);
        saveBtn.setOnClickListener(btnLsnr);
        cancelBtn.setOnClickListener(btnLsnr);


        return rootView;
    }

    DatePickerDialog.OnDateSetListener onDateSetLsnr = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Date date = new Date(year, month, day);
            dateEditText.setText(formatDate(date, year));
            dateEditText.setTextColor(Color.BLACK);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetLsnr = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {
            timeEditText.setText(formatTime(hour, min));
            Log.e("tag", "onTimeSet: wow1");
            timeEditText.setTextColor(Color.BLACK);
            Log.e("tag", "onTimeSet: wow2");
        }
    };

    public String formatTime(int hour, int min){
        String amOrPm;
        String minString;
        if (hour > 12) {
            amOrPm = "PM";
            hour -= 12;
        }
        else amOrPm = "AM";
        if (min < 10) minString = "0" + min;
        else minString = Integer.toString(min);
        return hour + ":" + minString + " " + amOrPm;
    }


    public String formatDate(Date date, int year) {
        String dateAsString = date.toString();
        String[] dateParts = dateAsString.split(" ");
        return dateParts[0] + " " + dateParts[1] + " " + dateParts[2] + " " + year;
    }

    private View.OnClickListener btnLsnr = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancelCreateNoteTwo:
                    getFragmentManager().popBackStack();
                    break;
                case R.id.btn_saveCreateNoteTwo:
                    if (!editMode) {

                        if (nameEditText.getText().toString().equals("") || dateEditText.getText().toString().equals("") || timeEditText.getText().toString().equals("") || clinicEditText.getText().toString().equals("") || docEditText.getText().toString().equals("")) {
                            Toast.makeText(getContext(),
                                    "Please enter all required fields",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }

                        NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(getContext());
                        dbHelper.createAppointment(new NewAppointment(
                                        nameEditText.getText().toString(),
                                        dateEditText.getText().toString(),
                                        timeEditText.getText().toString(),
                                        clinicEditText.getText().toString(),
                                        docEditText.getText().toString())
                        );
                        getFragmentManager().popBackStack();
                    } else {

                        if (nameEditText.getText().toString().equals("") || dateEditText.getText().toString().equals("") || timeEditText.getText().toString().equals("") || clinicEditText.getText().toString().equals("") || docEditText.getText().toString().equals("")) {
                            Toast.makeText(getContext(),
                                    "Please enter all required fields",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }

                        NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(getContext());
                        currAppointment.setName(nameEditText.getText().toString());
                        currAppointment.setDate(dateEditText.getText().toString());
                        currAppointment.setTime(timeEditText.getText().toString());
                        currAppointment.setClinic(clinicEditText.getText().toString());
                        currAppointment.setDoc(docEditText.getText().toString());
                        dbHelper.updateNote(currAppointment);
                        getFragmentManager().popBackStack();

                    }
                    break;
            }
        }
    };

    private class OnDeleteListener implements View.OnClickListener {
        NewAppointment appointment;

        public OnDeleteListener(NewAppointment appoitment) {
            this.appointment = appoitment;
        }

        @Override
        public void onClick(View v) {
            DialogInterface.OnClickListener dialogClickListener = new OnDeleteConfirmListener(appointment);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you wish to delete this appointment?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    public class OnDeleteConfirmListener implements DialogInterface.OnClickListener {
        NewAppointment appointment;

        public OnDeleteConfirmListener(NewAppointment appointment) {
            this.appointment = appointment;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    NewAppointmentsDBHelper dbHelper = NewAppointmentsDBHelper.getInstance(getContext());
                    dbHelper.deleteAppointment(appointment.getId());
                    getFragmentManager().popBackStack();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    }


}

