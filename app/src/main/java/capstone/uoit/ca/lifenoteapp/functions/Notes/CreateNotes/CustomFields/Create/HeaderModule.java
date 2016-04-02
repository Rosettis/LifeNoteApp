package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayout;
import capstone.uoit.ca.lifenoteapp.selectors.DatePickerFragment;
import capstone.uoit.ca.lifenoteapp.selectors.TimePickerFragment;

/**
 * Created by Peter on 03/03/16.
 */
public class HeaderModule extends LinearLayout implements AdapterView.OnItemClickListener {
    EditText titleEditText;
    TextView dateEditText;
    TextView timeEditText;
    FragmentActivity context;
    String noteName = "";
    String date;
    String time;
    ArrayList<NoteLayout> layouts;
    NoteLayout currentLayout;
    int lastEntryInSpinner;
    boolean editMode = false;
    private BetterSpinner noteTypeSpinner;
    int selection = 0;


    public HeaderModule(Context context, String noteName, NoteLayout currentLayout, ArrayList<NoteLayout> layouts) {
        super(context);
        this.noteName = noteName;
        this.currentLayout = currentLayout;
        this.layouts = layouts;
        this.context = (FragmentActivity) context;
        initializeViews(context);
    }

    public HeaderModule(Context context, String noteName, NoteLayout currentLayout, ArrayList<NoteLayout> layouts, String date, String time) {
        super(context);
        this.noteName = noteName;
        this.currentLayout = currentLayout;
        this.layouts = layouts;
        this.context = (FragmentActivity) context;
        this.date = date;
        this.time = time;
        this.editMode = true;
        initializeViews(context);
    }

    public HeaderModule(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = (FragmentActivity) context;
        initializeViews(context);
    }

    public HeaderModule(Context context,
                        AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
        this.context = (FragmentActivity) context;
        initializeViews(context);
    }

    OnLayoutSetListener onLayoutSet;

    public void setCallBack(OnLayoutSetListener onLayout) {
        onLayoutSet = onLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == lastEntryInSpinner) {
            onLayoutSet.displayCreateLayoutFrag();
//            noteTypeSpinner.setText(currentLayout.getLayoutName());

        } else {
            NoteLayout selectedLayout = layouts.get(position); //temp
            layouts.remove(position);
            layouts.add(0, currentLayout);
            currentLayout = selectedLayout;
            onLayoutSet.displayLayout(currentLayout, layouts);
        }
    }

    public interface OnLayoutSetListener{
        void displayLayout(NoteLayout currentLayout, ArrayList<NoteLayout> layouts);
        void displayCreateLayoutFrag();
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_header_module, this);
        dateEditText = (TextView) this.findViewById(R.id.editText_enterNoteDate);
        timeEditText = (TextView) this.findViewById(R.id.editText_enterNoteTime);
        dateEditText.setTextColor(Color.parseColor("#808080"));
        timeEditText.setTextColor(Color.parseColor("#808080"));

        if (!editMode) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            Date date = new Date(year, month, day);
            dateEditText.setText(formatDate(date, year));


            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            timeEditText.setText(formatTime(hour, min));
        } else {
            dateEditText.setText(date);
            timeEditText.setText(time);
        }

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.setCallBack(onDateSetLsnr);
                FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                dpf.show(fragmentActivity.getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

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

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment tpf = new TimePickerFragment();
                tpf.setCallBack(onTimeSetLsnr);
                FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                tpf.show(fragmentActivity.getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
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

        titleEditText = (EditText) this.findViewById(R.id.editText_enterNoteTitle);
        titleEditText.setText(noteName);

        noteTypeSpinner = (BetterSpinner) this.findViewById(R.id.betterSpinner_noteLayout);
        titleEditText = (EditText) this.findViewById(R.id.editText_enterNoteTitle);

        List<String> layoutNames = new ArrayList<>();
        for (NoteLayout i : layouts){
            layoutNames.add(i.getLayoutName());
        }
        layoutNames.add("Create new Note layout");
//            layoutNames.remove(0);


        lastEntryInSpinner = layoutNames.size() - 1;
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, layoutNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteTypeSpinner.setAdapter(spinnerArrayAdapter);
        noteTypeSpinner.setOnItemClickListener(this);
        noteTypeSpinner.setText(currentLayout.getLayoutName());
        noteTypeSpinner.setTextColor(getResources().getColor(R.color.colorPrimary));
        noteTypeSpinner.setTypeface(noteTypeSpinner.getTypeface(), Typeface.BOLD);
        noteTypeSpinner.setTextSize(24);
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
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
}