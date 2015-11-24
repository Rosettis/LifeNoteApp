package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import capstone.uoit.ca.lifenoteapp.R;


/**
 * Created by Peter Little tuesday Nov 24 2015
 */
public class CreateDetailedNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RelativeLayout standardNoteLayout;
    RelativeLayout quickNoteLayout;
    RelativeLayout symptomNoteLayout;
    RelativeLayout doctorsVisitLayout;
    ViewGroup currLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_detailed_note);
        currLayout = (ViewGroup) findViewById(R.id.layout_stardardNote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        standardNoteLayout = (RelativeLayout) findViewById(R.id.layout_stardardNote);
        quickNoteLayout = (RelativeLayout) findViewById(R.id.layout_quickNote);
//        symptomNoteLayout = (RelativeLayout) findViewById();
//        doctorsVisitLayout = (RelativeLayout) findViewById();

        Spinner noteTypeSpinner = (Spinner) findViewById(R.id.spinner_enterNoteType_createDetailedNoteAcivity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.note_Types_Array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteTypeSpinner.setAdapter(adapter);
        noteTypeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (pos) {
            case 0: //standard note
                currLayout.setVisibility(View.GONE);
                standardNoteLayout.setVisibility(View.VISIBLE);
                currLayout = standardNoteLayout;
                break;
            case 1:
                currLayout.setVisibility(View.GONE);
                quickNoteLayout.setVisibility(View.VISIBLE);
                currLayout = standardNoteLayout;
                break;
            case 2:
                currLayout.setVisibility(View.GONE);
                quickNoteLayout.setVisibility(View.VISIBLE);
                currLayout = standardNoteLayout;
                break;
            case 3:
                currLayout.setVisibility(View.GONE);
                quickNoteLayout.setVisibility(View.VISIBLE);
                currLayout = standardNoteLayout;
                break;
            case 4:
                currLayout.setVisibility(View.GONE);
                quickNoteLayout.setVisibility(View.VISIBLE);
                currLayout = standardNoteLayout;
                break;

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void submitNewNote(View submitOrCancelBtn) {
        Intent returnIntent = new Intent();
        switch (submitOrCancelBtn.getId()) {
            case R.id.btn_submitCreatedNote_createDetailedNoteAcivity: //create new note here

                DetailedNote newNote = new DetailedNote();
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                Date date = new Date();
                newNote.setDateCreated(date);
                newNote.setDateModified(date);
                EditText nameEditText = (EditText) findViewById(R.id.editText_enterNoteName_createDetailedNoteAcivity);
                newNote.setName(nameEditText.getText().toString());
                EditText contentEditText = (EditText) findViewById(R.id.editText_enterNoteContent_createDetailedNoteAcivity);
                newNote.setTextContent(contentEditText.getText().toString());

                returnIntent.putExtra("result", "yes");
                break;
            case R.id.btn_cancelCreatedNote_createDetailedNoteAcivity: //cancel creation of new note
                returnIntent.putExtra("result", "no");
                break;
        }
        setResult(RESULT_OK,returnIntent);
        finish();
    }

}
