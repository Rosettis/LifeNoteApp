package capstone.uoit.ca.lifenoteapp.selectors;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;


/**
 * Created by Peter on 28/11/15.
 */
public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener onDateSet;

    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        onDateSet = onDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), onDateSet, year, month, day);
    }
}