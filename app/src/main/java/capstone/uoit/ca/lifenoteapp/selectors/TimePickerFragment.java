package capstone.uoit.ca.lifenoteapp.selectors;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNoteActivity;

/**
 * Created by Peter on 28/11/15.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int amOrpm = c.get(Calendar.AM_PM);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), (CreateNoteActivity) getActivity(), hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
    }
}