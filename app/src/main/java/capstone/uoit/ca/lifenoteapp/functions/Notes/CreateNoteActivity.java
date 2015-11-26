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
public class CreateNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RelativeLayout detailedNoteLayout;
    RelativeLayout quickNoteLayout;
    RelativeLayout doctorsVisitLayout;
    ViewGroup currLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_detailed_note);
        currLayout = (ViewGroup) findViewById(R.id.layout_quickNote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.title_createQuickNote_createNewNotePage));

        quickNoteLayout = (RelativeLayout) findViewById(R.id.layout_quickNote);
        detailedNoteLayout = (RelativeLayout) findViewById(R.id.layout_detailedNote);
        doctorsVisitLayout = (RelativeLayout) findViewById(R.id.layout_doctorsNote);

        setUpquickNotespinner();
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
