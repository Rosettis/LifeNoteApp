package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.selectors.TimePickerFragment;
import capstone.uoit.ca.lifenoteapp.selectors.DatePickerFragment;


/**
 * Created by Peter on 07/01/16.
 */
public class NoteFragmentDateAndTime extends Fragment {
    TextView dateEditText;
    TextView timeEditText;

    public static NoteFragmentDateAndTime newInstance(String mode) {
        NoteFragmentDateAndTime newInstance = new NoteFragmentDateAndTime();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public static NoteFragmentDateAndTime newInstance(String mode, String date, String time) {
        NoteFragmentDateAndTime newInstance = new NoteFragmentDateAndTime();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("date", date);
        bundle.putString("time", time);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public String getDate() {
        return dateEditText.getText().toString();
    }

    public String getTime() {
        return timeEditText.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        if ("view".equals(bundle.getString("mode"))) {
            View rootView = inflater.inflate(R.layout.fragment_note_date_and_time_view, container, false);
            dateEditText = (TextView) rootView.findViewById(R.id.TextView_viewNoteDate);
            timeEditText = (TextView) rootView.findViewById(R.id.TextView_viewNoteTime);

            dateEditText.setText(bundle.getString("date"));
            timeEditText.setText(bundle.getString("time"));

            return rootView;

        } else if ("create".equals(bundle.getString("mode"))) {
            View rootView = inflater.inflate(R.layout.fragment_note_date_and_time_create, container, false);
            dateEditText = (TextView) rootView.findViewById(R.id.editText_enterNoteDate);
            timeEditText = (TextView) rootView.findViewById(R.id.editText_enterNoteTime);

            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            Date date = new Date(year, month, day);
            dateEditText.setText(formatDate(date, year));

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            timeEditText.setText(formatTime(hour, min));

            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment dpf = new DatePickerFragment();
                    dpf.setCallBack(onDateSetLsnr);
                    dpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
                }
            });


            timeEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerFragment tpf = new TimePickerFragment();
                    tpf.setCallBack(onTimeSetLsnr);
                    tpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
                }
            });
            return rootView;

        } else {
            System.out.println("Error Invalid mod Entered");
            return null;
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetLsnr = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            dateEditText = (TextView) getActivity().findViewById(R.id.editText_enterNoteDate);
            Date date = new Date(year, month, day);
            dateEditText.setText(formatDate(date, year));
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetLsnr = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {

            timeEditText = (TextView) getActivity().findViewById(R.id.editText_enterNoteTime);
            timeEditText.setText(formatTime(hour, min));
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
}


