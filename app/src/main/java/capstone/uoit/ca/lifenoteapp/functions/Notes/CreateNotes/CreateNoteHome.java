package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
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
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.WeightModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View.HeaderModuleView;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View.SideLabel;
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
    private SeverityField customSeekBar;
    private boolean editMode = false;
    private Note currentNote;

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

        NoteDBHelper noteDBHelper = NoteDBHelper.getInstance(this.getContext());
        noteDBHelper.deleteAllNotes();

        CodifiedHashMapManager hashMapManager = CodifiedHashMapManager.getInstance(getContext());
        hashMapManager.clearHashTable();
    }

    public void displayNote(Note note) {
        this.currentLayout = note.getLayout();
        this.currentNote = note;
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


//            LinearLayout doctorParentgroup = (LinearLayout) parent.findViewById(R.id.linearLayout_doctorsFields);
//            LinearLayout sidePanelGroup = (LinearLayout) parent.findViewById(R.id.linearLayout_sidepanel);

            if (currentLayout.containsDocNameField()) {
                AutoCompleteField autotextView = new AutoCompleteField(getContext(), "edit", note.getDocName());
                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Name:");

                newRow.addView(sideLabel);
                newRow.addView(autotextView);
                parent.addView(newRow);
            }

            if (currentLayout.containsDocDetailsField()) {
                doctorsDetailsField = new DetailsField(getContext(), note.getDocDetails(), "edit");
                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Details:");
                newRow.addView(sideLabel);
                newRow.addView(doctorsDetailsField);
                parent.addView(newRow);
            }
        }
//
        if (currentLayout.containsIllnessModule()) {
            View illnessModule = new IllnessModule(getContext());
            illnessModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(illnessModule);

            if (currentLayout.containsIllNameField()) {
                illnessDetailsField = new DetailsField(getContext(),  note.getIllName(), "edit");

                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Illness:");
                newRow.addView(sideLabel);
                newRow.addView(illnessDetailsField);
                parent.addView(newRow);
            }

            if (currentLayout.containsIllSymptomsField()) {
                symptomsDetailsField = new DetailsField(getContext(), note.getIllSymptoms(),  "edit");

                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Symptoms:");
                newRow.addView(sideLabel);
                newRow.addView(symptomsDetailsField);
                parent.addView(newRow);
            }

            if (currentLayout.containsIllSeverityField()) {
                customSeekBar = new SeverityField(getContext(), "Enter Symptom Severity:", note.getIllSeverity());

                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Severity:");
                newRow.addView(sideLabel);
                newRow.addView(customSeekBar);
                parent.addView(newRow);
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
            View header = new HeaderModule(getContext(), "", currentLayout, layouts);
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

            if (currentLayout.containsDocNameField()) {
                AutoCompleteField autotextView = new AutoCompleteField(getContext(), "create", "Enter Doctors Name");
                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Name:");

                newRow.addView(sideLabel);
                newRow.addView(autotextView);
                parent.addView(newRow);

            }

            if (currentLayout.containsDocDetailsField()) {
                doctorsDetailsField = new DetailsField(getContext(), "Enter Visit Details", "create");
                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Details:");
                newRow.addView(sideLabel);
                newRow.addView(doctorsDetailsField);
                parent.addView(newRow);
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
                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Illness:");
                newRow.addView(sideLabel);
                newRow.addView(illnessDetailsField);
                parent.addView(newRow);
            }

            if (currentLayout.containsIllSymptomsField()) {
                symptomsDetailsField = new DetailsField(getContext(), "Enter Symptoms", "create");
                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Symptoms:");
                newRow.addView(sideLabel);
                newRow.addView(symptomsDetailsField);
                parent.addView(newRow);
            }

            if (currentLayout.containsIllSeverityField()) {
                customSeekBar = new SeverityField(getContext(), "Enter Symptom Severity:");
                TableRow newRow = new TableRow(getContext());
                newRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                SideLabel sideLabel = new SideLabel(getContext(), "Severity:");
                newRow.addView(sideLabel);
                newRow.addView(customSeekBar);
                parent.addView(newRow);
            }
        }

        if (currentLayout.containsHeaderModule()) {
            View weightModule = new WeightModule(getContext());
            weightModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(weightModule);
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
        NoteLayout newLayout = new NoteLayout(layoutName.substring(0, 1).toUpperCase() + layoutName.substring(1));
        newLayout.setContainsHeaderModule(true);

        for (int i = 0; i < selected.length(); i ++) {
            switch (i) {
                case 0:
                    newLayout.setContainsDoctorModule(selected.get(i));
                    break;
                case 1:
                    newLayout.setContainsIllnessModule(selected.get(i));
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
                    if (!editMode) {
                        EditText title = (EditText) rootView.findViewById(R.id.editText_enterNoteTitle);
                        if (title.getText().toString().equals("")) {
                            Toast.makeText(getContext(),
                                    "Please enter the name of the note",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }
                        if (currentLayout.containsIllnessModule() && ((SeekBar) rootView.findViewById(R.id.severity_seekBar)).getProgress() == 0) {
                            Toast.makeText(getContext(),
                                    "Please enter a minimum severity of 1",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }
                        CodifiedHashMapManager hashMapManager = CodifiedHashMapManager.getInstance(getContext());
                        Note newNote = new Note();
                        if (currentLayout.containsHeaderModule()) {
                            String noteName = ((EditText) rootView.findViewById(R.id.editText_enterNoteTitle)).getText().toString();
                            newNote.setHeaderFields(
                                    currentLayout,
                                    noteName.substring(0, 1).toUpperCase() + noteName.substring(1),
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
                    } else {

                        if (currentLayout.containsHeaderModule()) {
                            currentNote.setName(((EditText) rootView.findViewById(R.id.editText_enterNoteTitle_view)).getText().toString());
                        }

                        ArrayList<String> prevListOfTerms = currentNote.getCodifiedWords();
                        ArrayList<String> newListOfTerms = new ArrayList<>();
                        if (currentLayout.containsDoctorModule()) {
                            currentNote.setDoctorFields(
                                    ((AutoCompleteTextView) rootView.findViewById(R.id.autocomplete_doctor_two)).getText().toString(),
                                    doctorsDetailsField.getCodifiedText()
                            );
                            newListOfTerms.addAll(doctorsDetailsField.getTaggedWords());
                        }

                        if (currentLayout.containsIllnessModule()) {
                            currentNote.setIllnessFields(
                                    illnessDetailsField.getCodifiedText(),
                                    symptomsDetailsField.getCodifiedText(),
                                    ((SeekBar) rootView.findViewById(R.id.severity_seekBar)).getProgress()
                            );
                            newListOfTerms.addAll(illnessDetailsField.getTaggedWords());
                        }
                        ArrayList<String> addTermList = new ArrayList<>();
                        ArrayList<String> deleteTermList = new ArrayList<>();
                        for (String currTerm : prevListOfTerms) {
                            if (!newListOfTerms.contains(currTerm)) deleteTermList.add(currTerm);
                        }

                        for (String currTerm : newListOfTerms) {
                            if (!prevListOfTerms.contains(currTerm)) addTermList.add(currTerm);
                        }

                        CodifiedHashMapManager hashMapManager = CodifiedHashMapManager.getInstance(getContext());
                        hashMapManager.addEntries(addTermList);
                        hashMapManager.removeEntries(deleteTermList);
                        currentNote.setTags(newListOfTerms);
                        NoteDBHelper noteDB = NoteDBHelper.getInstance(getContext());
                        noteDB.updateNote(currentNote);
                        getFragmentManager().popBackStack();
                        break;
                    }
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
