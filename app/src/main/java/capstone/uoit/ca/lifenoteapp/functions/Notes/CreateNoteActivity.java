package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.microedition.khronos.egl.EGLDisplay;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.selectors.DatePickerFragment;
import capstone.uoit.ca.lifenoteapp.selectors.TimePickerFragment;


/**
 * Created by Peter Little tuesday Nov 24 2015
 */
public class CreateNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    RelativeLayout detailedNoteLayout;
    RelativeLayout quickNoteLayout;
    RelativeLayout doctorsVisitLayout;
    ViewGroup currLayout;
    private int nextNoteNumber;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nextNoteNumber = getIntent().getIntExtra("nextnotenumber", 1);
        setContentView(R.layout.activity_create_detailed_note);
        currLayout = (ViewGroup) findViewById(R.id.layout_quickNote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        setTitle(getResources().getString(R.string.title_createQuickNote_createNewNotePage));

        quickNoteLayout = (RelativeLayout) findViewById(R.id.layout_quickNote);
        detailedNoteLayout = (RelativeLayout) findViewById(R.id.layout_detailedNote);
        doctorsVisitLayout = (RelativeLayout) findViewById(R.id.layout_doctorsNote);

        currLayout.setVisibility(View.VISIBLE);
        setUpquickNotespinner();

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_doctor);
        String[] doctors = getResources().getStringArray(R.array.doctors_array);
    // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctors);
        textView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (parent.getId()) {
            case R.id.spinner_enterNoteType_createQuickNoteAcivity:
                switch (pos) {
                    case 1:
                        currLayout.setVisibility(View.GONE);
                        detailedNoteLayout.setVisibility(View.VISIBLE);
                        currLayout = detailedNoteLayout;
                        setTitle(getResources().getString(R.string.title_createDetailedNote_createNewNotePage));
                        setUpDetailedNotespinner();
                        break;
                    case 2:
                        currLayout.setVisibility(View.GONE);
                        doctorsVisitLayout.setVisibility(View.VISIBLE);
                        currLayout = doctorsVisitLayout;
                        setTitle(getResources().getString(R.string.title_createDocNote_createNewNotePage));
                        setUpDocNotespinner();
                        break;
                }
                break;
            case R.id.spinner_enterNoteType_createDetailedNoteAcivity:
                switch (pos) {
                    case 1: //standard note
                        currLayout.setVisibility(View.GONE);
                        quickNoteLayout.setVisibility(View.VISIBLE);
                        currLayout = quickNoteLayout;
                        setTitle(getResources().getString(R.string.title_createQuickNote_createNewNotePage));
                        setUpquickNotespinner();
                        break;
                    case 2:
                        currLayout.setVisibility(View.GONE);
                        doctorsVisitLayout.setVisibility(View.VISIBLE);
                        currLayout = doctorsVisitLayout;
                        setTitle(getResources().getString(R.string.title_createDocNote_createNewNotePage));
                        setUpDocNotespinner();
                        break;
                }
                break;
            case R.id.spinner_enterNoteType_createDocNoteAcivity:
                switch (pos) {
                    case 1:
                        currLayout.setVisibility(View.GONE);
                        quickNoteLayout.setVisibility(View.VISIBLE);
                        currLayout = quickNoteLayout;
                        setTitle(getResources().getString(R.string.title_createQuickNote_createNewNotePage));
                        setUpquickNotespinner();
                        break;
                    case 2:
                        currLayout.setVisibility(View.GONE);
                        detailedNoteLayout.setVisibility(View.VISIBLE);
                        currLayout = detailedNoteLayout;
                        setTitle(getResources().getString(R.string.title_createDetailedNote_createNewNotePage));
                        setUpDetailedNotespinner();
                        break;
                }
                break;
        }
    }

    private void setUpquickNotespinner(){
        Spinner quicknoteTypeSpinner = (Spinner) findViewById(R.id.spinner_enterNoteType_createQuickNoteAcivity);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.switchNoteTypeArray_quickNote, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quicknoteTypeSpinner.setAdapter(adapter1);
        quicknoteTypeSpinner.setOnItemSelectedListener(this);
    }

    private void setUpDocNotespinner(){
        Spinner docNoteTypeSpinner = (Spinner) findViewById(R.id.spinner_enterNoteType_createDocNoteAcivity);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.switchNoteTypeArray_docNote, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docNoteTypeSpinner.setAdapter(adapter3);
        docNoteTypeSpinner.setOnItemSelectedListener(this);
    }

    private void setUpDetailedNotespinner(){
        Spinner detailednoteTypeSpinner = (Spinner) findViewById(R.id.spinner_enterNoteType_createDetailedNoteAcivity);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.switchNoteTypeArray_detailedNote, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detailednoteTypeSpinner.setAdapter(adapter2);
        detailednoteTypeSpinner.setOnItemSelectedListener(this);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private String getUserName(){
        //TODO implement get username function
        return "username";
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ((EditText) findViewById(R.id.EditText_time)).setText(formatTime(hourOfDay, minute));
    }

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        Log.w("DatePicker", "Date = " + year);
        Date date = new Date(year, month, day);
        ((EditText) findViewById(R.id.EditText_date)).setText(formatDate(date, year));
    }

    public String formatDate(Date date, int year) {
        String dateAsString = date.toString();
        String[] dateParts = dateAsString.split(" ");
        return dateParts[0] + " " + dateParts[1] + " " + dateParts[2] + " " + year;
    }

    public void submitQuickNewNote(View submitOrCancelBtn) {
        Intent returnIntent = new Intent();
        switch (submitOrCancelBtn.getId()) {
            case R.id.btn_submitCreatedNote_createQuickNoteAcivity:
                EditText nameEditText = (EditText) findViewById(R.id.editText_enterNoteName_createQuickNoteAcivity);
                String name = nameEditText.getText().toString();
                if (name.matches("")) name = "Note " + nextNoteNumber;
                EditText symptomEditText = (EditText) findViewById(R.id.editText_illness);
                String illness = symptomEditText.getText().toString();
                SeekBar severitySeekBar = (SeekBar) findViewById(R.id.seekBar_symptomSeverity);
                int severity = severitySeekBar.getProgress();
                EditText descriptionEditText = (EditText) findViewById(R.id.textView_noteDescription);
                String description = descriptionEditText.getText().toString();
                QuickNote newNote = new QuickNote(name, "quick", getUserName(), illness, severity, description);
                AssetManager am = context.getAssets();
                OutputStream outputStream = null;
                try{
                    File f = context.getFileStreamPath("notes.txt");
                    outputStream = new FileOutputStream(f);
                    newNote.writeToFile(outputStream);
                } catch (IOException unableToOpenFile) {
                    //TODO handle unable to find file error (toast?)
                    unableToOpenFile.printStackTrace();
                } finally {
                    try {
                        outputStream.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                System.out.println("READING NOWWWW, READING NOWWWW");

                setResult(RESULT_OK,returnIntent);

                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(
                            new InputStreamReader(getAssets().open("notes.txt")));

                    // do reading, usually loop until end of file reading
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxREADING NOW, READING NOWxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    String mLine;
                    while ((mLine = reader.readLine()) != null) {
                        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + mLine);
                    }
                } catch (IOException e) {
                    //log the exception
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            //log the exception
                        }
                    }
                }



                break;
//            case R.id.btn_cancelCreatedNote_createDetailedNoteAcivity: //cancel creation of new note
//                setResult(RESULT_CANCELED, returnIntent);
//                break;
        }
        finish();
    }

}
