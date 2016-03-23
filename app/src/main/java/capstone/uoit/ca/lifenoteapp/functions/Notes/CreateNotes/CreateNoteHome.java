package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DocNameField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DoctorModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.HeaderModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.IllnessModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.SeverityField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;

public class CreateNoteHome extends Fragment implements HeaderModule.OnLayoutSetListener, NewLayoutFragment.OnSectionDoneListener {
    private LinearLayout parent;
    private ArrayList<NoteLayout> layouts = null;
    private NoteLayout currentLayout;
    private View rootView;
    private DetailsField doctorsDetailsField;
    private DetailsField illnessDetailsField;
    private DetailsField symptomsDetailsField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_create_note_home, container, false);
        parent = (LinearLayout) rootView.findViewById(R.id.linearLayout_createNoteHometwo);

//        resetAllApplicationDataBases(); //uncomment to reset application databases (TESTING ONLY)

        NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this.getContext());
        layouts = dbHelper.getAllLayouts();
        NoteLayout currentLayout = layouts.remove(0);
        displayLayout(currentLayout, layouts);
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
                DocNameField autotextView = new DocNameField(getContext());
                doctorParentgroup.addView(autotextView);
            }

            if (currentLayout.containsDocDetailsField()) {
                doctorsDetailsField = new DetailsField(getContext(), "Enter Visit Details");
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
                illnessDetailsField = new DetailsField(getContext(), "Enter Suspected Illness");
                illnessParentgroup.addView(illnessDetailsField);
            }

            if (currentLayout.containsIllSymptomsField()) {
                symptomsDetailsField = new DetailsField(getContext(), "Enter Symptoms");
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
                    break;
            }
        }
    };
}
