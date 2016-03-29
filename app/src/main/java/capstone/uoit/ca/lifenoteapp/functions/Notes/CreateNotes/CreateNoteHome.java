package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Graphs.CodifiedHashMapManager;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DetailsField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.AutoCompleteField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DoctorModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.HeaderModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.IllnessModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.SeverityField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View.HeaderModuleView;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteItemAdaptor;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.ViewNotesFragment;

public class CreateNoteHome extends Fragment implements HeaderModule.OnLayoutSetListener, NewLayoutFragment.OnSectionDoneListener {
    private LinearLayout parent;
    private ArrayList<NoteLayout> layouts = null;
    private NoteLayout currentLayout;
    private View rootView;
    private DetailsField doctorsDetailsField;
    private DetailsField illnessDetailsField;
    private DetailsField symptomsDetailsField;
    private boolean editMode = false;

    public static CreateNoteHome newInstance(long noteId) {
        CreateNoteHome instance = new CreateNoteHome();
        Bundle args = new Bundle();
        args.putBoolean("editMode", true);
        args.putLong("noteId", noteId);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_create_note_home, container, false);
        parent = (LinearLayout) rootView.findViewById(R.id.linearLayout_createNoteHometwo);
        Bundle args = getArguments();
        try {
            editMode = args.getBoolean("editMode");
        } catch (NullPointerException noParam) {
            //no parameter passed default to create mode
            noParam.printStackTrace();
        }

//        resetAllApplicationDataBases(); //uncomment to reset application databases (TESTING ONLY)


        if(!editMode) {
            NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this.getContext());
            layouts = dbHelper.getAllLayouts();
            NoteLayout currentLayout = layouts.remove(0);
            displayLayout(currentLayout, layouts);
        } else {
            NoteDBHelper notedbHelper = NoteDBHelper.getInstance(getContext());
            Note note = notedbHelper.getNote(args.getLong("noteId"));
            displayNote(note);
            OnDeleteListener deleteListener = new OnDeleteListener(note);
            Button deleteBtn = (Button) rootView.findViewById(R.id.btn_deleteNote);
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setOnClickListener(deleteListener);
        }
        Button saveBtn = (Button) rootView.findViewById(R.id.btn_saveCreateNoteTwo);
        Button cancelBtn = (Button) rootView.findViewById(R.id.btn_cancelCreateNoteTwo);
        saveBtn.setOnClickListener(saveLsnr);
        cancelBtn.setOnClickListener(saveLsnr);
        return rootView;
    }

    private void resetAllApplicationDataBases() { //FOR TESTING USE ONLY
        Log.i("RESETTING APPLICATION", "resetAllApplicationDataBases");
        NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this.getContext());
        dbHelper.deleteAllNoteLayouts();
        dbHelper.createNoteLayout(new NoteLayout("Quick Note", true, true, true, true, true, false, false, false, false, false, false, false, true, true));
        dbHelper.createNoteLayout(new NoteLayout("Detailed Note", true, true, true, true, true, true, true, true, true, true, true, true, true, true));
        dbHelper.createNoteLayout(new NoteLayout("Doctor's Note", true, true, true, true, true, true, true, true, false, false, false, false, true, true));

        NoteDBHelper noteDBHelper = NoteDBHelper.getInstance(this.getContext());
        noteDBHelper.deleteAllNotes();

        CodifiedHashMapManager hashMapManager = CodifiedHashMapManager.getInstance(getContext());
        hashMapManager.clearHashTable();
    }

    public void displayNote(Note note) {
        this.currentLayout = note.getLayout();
        NoteLayoutDBHelper layoutDBHelper = NoteLayoutDBHelper.getInstance(getContext());
        ArrayList<NoteLayout> layouts = layoutDBHelper.getAllLayouts();
        layouts.remove(currentLayout);
        this.layouts = layouts;

        if(((LinearLayout) parent).getChildCount() > 0)
            ((LinearLayout) parent).removeAllViews();
        if (currentLayout.containsHeaderModule()) {
            View header = new HeaderModuleView(getContext(), note.getName(), currentLayout, note.getDate(), note.getTime());
            header.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(header);
//            ((HeaderModule) header).setCallBack(this);
        }


        if (currentLayout.containsDoctorModule()) {
            View doctorModule = new DoctorModule(getContext());
            doctorModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(doctorModule);
            LinearLayout doctorParentgroup = (LinearLayout) parent.findViewById(R.id.linearLayout_doctorsFields);

            if (currentLayout.containsDocNameField()) {
                AutoCompleteField autotextView = new AutoCompleteField(getContext(), "edit", note.getDocName(), "Doctor's Name: ");
                doctorParentgroup.addView(autotextView);
            }

            if (currentLayout.containsDocDetailsField()) {
                doctorsDetailsField = new DetailsField(getContext(), note.getDocDetails(), "Doctor's Diagnosis: ", "edit");
                doctorParentgroup.addView(doctorsDetailsField);
            }
        }
//
        if (currentLayout.containsIllnessModule()) {
            View illnessModule = new IllnessModule(getContext());
            illnessModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(illnessModule);
            LinearLayout illnessParentgroup = (LinearLayout) parent.findViewById(R.id.linearLayout_illnessFields);

            if (currentLayout.containsIllNameField()) {
                illnessDetailsField = new DetailsField(getContext(),  note.getIllName(), "Illness: ", "edit");
                illnessParentgroup.addView(illnessDetailsField);
            }

            if (currentLayout.containsIllSymptomsField()) {
                symptomsDetailsField = new DetailsField(getContext(), note.getIllSymptoms(), "Symptoms: ",  "edit");
                illnessParentgroup.addView(symptomsDetailsField);
            }

            if (currentLayout.containsIllSeverityField()) {
                SeverityField customSeekBar = new SeverityField(getContext(), "Enter Symptom Severity:", note.getIllSeverity());
                illnessParentgroup.addView(customSeekBar);
            }
        }
    }

    /**
     * Displays layout
     * @param layouts
     */
    public void displayLayout(NoteLayout currentLayout, ArrayList<NoteLayout> layouts) {
        this.currentLayout = currentLayout;
        if(((LinearLayout) parent).getChildCount() > 0)
            ((LinearLayout) parent).removeAllViews();
        if (currentLayout.containsHeaderModule()) {
            View header = new HeaderModule(getContext(), "testName", currentLayout, layouts);
            header.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(header);
            ((HeaderModule) header).setCallBack(this);
        }
        if (currentLayout.containsDoctorModule()) {
            Log.i("agag", "displayLayout: Bam");
            View doctorModule = new DoctorModule(getContext());
            doctorModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(doctorModule);
            LinearLayout doctorParentgroup = (LinearLayout) getActivity().findViewById(R.id.linearLayout_doctorsFields);

            if (currentLayout.containsDocNameField()) {
                AutoCompleteField autotextView = new AutoCompleteField(getContext(), "create", "Enter Doctors Name");
                doctorParentgroup.addView(autotextView);
            }

            if (currentLayout.containsDocDetailsField()) {
                doctorsDetailsField = new DetailsField(getContext(), "Enter Visit Details", "create");
                doctorParentgroup.addView(doctorsDetailsField);
            }
        }

        if (currentLayout.containsIllnessModule()) {
            View illnessModule = new IllnessModule(getContext());
            illnessModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(illnessModule);
            LinearLayout illnessParentgroup = (LinearLayout) getActivity().findViewById(R.id.linearLayout_illnessFields);

            if (currentLayout.containsIllNameField()) {
                illnessDetailsField = new DetailsField(getContext(), "Enter Suspected Illness", "create");
                illnessParentgroup.addView(illnessDetailsField);
            }

            if (currentLayout.containsIllSymptomsField()) {
                symptomsDetailsField = new DetailsField(getContext(), "Enter Symptoms", "create");
                illnessParentgroup.addView(symptomsDetailsField);
            }

            if (currentLayout.containsIllSeverityField()) {
                SeverityField customSeekBar = new SeverityField(getContext(), "Enter Symptom Severity:");
                illnessParentgroup.addView(customSeekBar);
            }
        }
    }

    @Override
    public void displayCreateLayoutFrag() {
        NewLayoutFragment newLayoutFragment = NewLayoutFragment.newInstance(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.NoteFragments_array))));
        newLayoutFragment.setCallBack(this);
        newLayoutFragment.show(this.getChildFragmentManager(), "Create new Layout Fragment");
    }

    @Override
    public void createLayout(BitSet selected, String layoutName) {
        NoteLayout newLayout = new NoteLayout(layoutName);

        for (int i = 0; i < selected.length(); i ++) {
            switch (i) {
                case 0:
                    newLayout.setContainsDoctorModule(selected.get(i));
                    break;
                case 1:
                    newLayout.setContainsIllnessModule(selected.get(i));
                    break;
                case 2:
                    newLayout.setContainsAdditionDetailsModule(selected.get(i));
                    break;
            }
        }

        NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this.getContext());
        dbHelper.createNoteLayout(newLayout);
        displayLayout(newLayout, layouts);
    }

    private class OnDeleteListener implements View.OnClickListener {
        Note note;

        public OnDeleteListener(Note note) {
            this.note = note;
        }

        @Override
        public void onClick(View v) {
            DialogInterface.OnClickListener dialogClickListener = new OnDeleteConfirmListener(note);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you wish to delete this note?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    private View.OnClickListener saveLsnr = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancelCreateNoteTwo:
                    getFragmentManager().popBackStack();
                    break;
                case R.id.btn_saveCreateNoteTwo:
                    EditText title = (EditText) rootView.findViewById(R.id.editText_enterNoteTitle);
                    if (title.getText().toString().equals("")) {
                        Toast.makeText(getContext(),
                                "Please enter the name of the note",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    CodifiedHashMapManager hashMapManager = CodifiedHashMapManager.getInstance(getContext());
                    Note newNote = new Note();
                    if (currentLayout.containsHeaderModule()) {
                        newNote.setHeaderFields(
                                currentLayout,
                                ((EditText) rootView.findViewById(R.id.editText_enterNoteTitle)).getText().toString(),
                                ((TextView) rootView.findViewById(R.id.editText_enterNoteDate)).getText().toString(),
                                ((TextView) rootView.findViewById(R.id.editText_enterNoteTime)).getText().toString()
                                );
                    }

                    if (currentLayout.containsDoctorModule()) {
                        newNote.setDoctorFields(
                                ((AutoCompleteTextView) rootView.findViewById(R.id.autocomplete_doctor_two)).getText().toString(),
                                doctorsDetailsField.getCodifiedText()
                        );
                        hashMapManager.addEntries(doctorsDetailsField.getTaggedWords());
                        newNote.addCodifiedWords(doctorsDetailsField.getTaggedWords());
                    }

                    if (currentLayout.containsIllnessModule()) {
                        newNote.setIllnessFields(
                                illnessDetailsField.getCodifiedText(),
                                symptomsDetailsField.getCodifiedText(),
                                ((SeekBar) rootView.findViewById(R.id.severity_seekBar)).getProgress()
                        );
                        hashMapManager.addEntries(illnessDetailsField.getTaggedWords());
                        hashMapManager.addEntries(symptomsDetailsField.getTaggedWords());
                        newNote.addCodifiedWords(illnessDetailsField.getTaggedWords());
                        newNote.addCodifiedWords(symptomsDetailsField.getTaggedWords());
                    }
                    newNote.printNote();
                    Log.i("Saving Note", "onClick: Saving note ");
                    NoteDBHelper noteDB = NoteDBHelper.getInstance(getContext());
                    noteDB.createNote(newNote);
                    getFragmentManager().popBackStack();
                    break;
            }
        }
    };

    public class OnDeleteConfirmListener implements DialogInterface.OnClickListener {
        Note note;

        public OnDeleteConfirmListener(Note note) {
            this.note = note;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    NoteDBHelper dbHelper = NoteDBHelper.getInstance(getContext());
                    dbHelper.deleteNote(note.getId());
                    CodifiedHashMapManager.getInstance(getContext()).removeEntries(note);
                    getFragmentManager().popBackStack();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    }
}
