package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.selectors.DatePickerFragment;
import capstone.uoit.ca.lifenoteapp.selectors.TimePickerFragment;

/**
 * Created by Peter on 04/01/16.
 */
public class NoteFragmentTitle extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<NoteLayout> layouts;
    String currTitle;
    int lastEntryInSpinner;
    EditText titleEditText;
    TextView dateEditText;
    TextView timeEditText;
    String currLayoutName;

    OnLayoutSetListener onLayoutSet;


    //test
    public static NoteFragmentTitle newInstance(String mode, String title, String date, String time) {
        NoteFragmentTitle newInstance = new NoteFragmentTitle();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("title", title);
        bundle.putString("date", date);
        bundle.putString("time", time);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public static NoteFragmentTitle newInstance(String mode, String title, ArrayList<NoteLayout> layouts) {
        NoteFragmentTitle newInstance = new NoteFragmentTitle();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("mode", mode);
        bundle.putSerializable("layouts", layouts);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public String getTitle() {
        return titleEditText.getText().toString();
    }

    public String getDate() {
        return dateEditText.getText().toString();
    }

    public String getTime() {
        return timeEditText.getText().toString();
    }

    public String getLayoutName() {
        return currLayoutName;
    }

    public void setCallBack(OnLayoutSetListener onLayout) {
        onLayoutSet = onLayout;
    }

    public interface OnLayoutSetListener{
        void displayLayout(ArrayList<NoteLayout> layouts);
        void displayCreateLayoutFrag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if ("view".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_title_create, container, false);
            dateEditText = (TextView) view.findViewById(R.id.editText_enterNoteDate);
            timeEditText = (TextView) view.findViewById(R.id.editText_enterNoteTime);
            titleEditText = (EditText) view.findViewById(R.id.editText_enterNoteTitle);
            BetterSpinner spinner = (BetterSpinner) view.findViewById(R.id.betterSpinner_noteLayout);

            spinner.setVisibility(View.GONE);
            dateEditText.setText(bundle.getString("date"));
            timeEditText.setText(bundle.getString("time"));
            currTitle = bundle.getString("title");
            titleEditText.setText(currTitle);
            return view;
        }else {
            View view = inflater.inflate(R.layout.fragment_note_title_create, container, false);
            layouts = (ArrayList<NoteLayout>) bundle.getSerializable("layouts");
            currTitle = bundle.getString("title");


            dateEditText = (TextView) view.findViewById(R.id.editText_enterNoteDate);
            dateEditText.setTextColor(Color.parseColor("#808080"));
            timeEditText = (TextView) view.findViewById(R.id.editText_enterNoteTime);
            timeEditText.setTextColor(Color.parseColor("#808080"));


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


            BetterSpinner noteTypeSpinner = (BetterSpinner) view.findViewById(R.id.betterSpinner_noteLayout);
            titleEditText = (EditText) view.findViewById(R.id.editText_enterNoteTitle);

            List<String> layoutNames = new ArrayList<>();
            for (NoteLayout currLayout : layouts){
                layoutNames.add(currLayout.getLayoutName());
            }
            layoutNames.add("Create new Note layout");
//            layoutNames.remove(0);


            lastEntryInSpinner = layoutNames.size() - 1;
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, layoutNames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            noteTypeSpinner.setAdapter(spinnerArrayAdapter);
            noteTypeSpinner.setOnItemClickListener(this);
            return view;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("pos:" + position + "|entries:" + lastEntryInSpinner);
        if (position == lastEntryInSpinner) {
            onLayoutSet.displayCreateLayoutFrag();
        } else if (position != 0) {
            NoteLayout layout = layouts.get(position);
            currLayoutName = layout.getLayoutName();
            layouts.remove(position);
            layouts.add(0, layout);
            onLayoutSet.displayLayout(layouts);
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetLsnr = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            dateEditText = (TextView) getActivity().findViewById(R.id.editText_enterNoteDate);
            Date date = new Date(year, month, day);
            dateEditText.setText(formatDate(date, year));
            dateEditText.setTextColor(Color.BLACK);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetLsnr = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {

            timeEditText = (TextView) getActivity().findViewById(R.id.editText_enterNoteTime);
            timeEditText.setText(formatTime(hour, min));
            timeEditText.setTextColor(Color.BLACK);

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
