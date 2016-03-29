package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Graphs.CodifiedHashMapManager;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CreateNoteHome;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.AutoCompleteField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DetailsField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DoctorModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.IllnessModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View.HeaderModuleView;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View.SymptomsView;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayout;

public class ViewNote extends Fragment {
    private Note note;
    private LinearLayout parent;


    public ViewNote() {
        // Required empty public constructor
    }

    public static ViewNote newInstance(Note note) {
        ViewNote fragment = new ViewNote();
        fragment.note = note;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        NoteDBHelper dbHelper = NoteDBHelper.getInstance(getContext());
        if (dbHelper.getNote(note.getId()) == null) getFragmentManager().popBackStack();


        View rootView = inflater.inflate(R.layout.fragment_view_note, container, false);
        parent = (LinearLayout) rootView.findViewById(R.id.linearLayout_view_note);

        Button editBtn = (Button) rootView.findViewById(R.id.btn_editNote);
        Button backBtn = (Button) rootView.findViewById(R.id.btn_backToNotes);
        Button deleteBtn = (Button) rootView.findViewById(R.id.btn_deleteNote);
        editBtn.setOnClickListener(editLsnr);
        backBtn.setOnClickListener(editLsnr);
        OnDeleteListener deleteLsnr = new OnDeleteListener(note);
        deleteBtn.setOnClickListener(deleteLsnr);
        NoteLayout layout = note.getLayout();
        if (layout.containsHeaderModule()) {
            View header = new HeaderModuleView(getContext(), note.getName(), layout, note.getDate(), note.getTime());
            header.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(header);
        }


        if (layout.containsDoctorModule()) {
            View doctorModule = new DoctorModule(getContext(), true);
            doctorModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(doctorModule);
            LinearLayout doctorParentgroup = (LinearLayout) parent.findViewById(R.id.linearLayout_doctorsFields);

            if (layout.containsDocNameField()) {
                AutoCompleteField autotextView = new AutoCompleteField(getContext(), "view", note.getDocName(), "Doctor's Name: ");
                doctorParentgroup.addView(autotextView);
            }

            if (layout.containsDocDetailsField()) {
                DetailsField doctorsDetailsField = new DetailsField(getContext(), note.getDocDetails(), "Diagnosis: ", "view");
                doctorParentgroup.addView(doctorsDetailsField);
            }
        }



        if (layout.containsIllnessModule()) {
            View illnessModule = new IllnessModule(getContext());
            illnessModule.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(illnessModule);
            LinearLayout illnessParentGroup = (LinearLayout) parent.findViewById(R.id.linearLayout_illnessFields);


            if (layout.containsIllNameField()) {
                DetailsField illnessDetailsField = new DetailsField(getContext(), note.getIllName(), "view");
                illnessParentGroup.addView(illnessDetailsField);
            }

            if (layout.containsIllSymptomsField() && layout.containsIllSeverityField()) {
                View symptomsView = new SymptomsView(getContext(), note.getIllSeverity());
                symptomsView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                illnessParentGroup.addView(symptomsView);

                LinearLayout symptomParentGroup = (LinearLayout) parent.findViewById(R.id.linearLayout_symptoms_container);
                DetailsField symptomDetailsField = new DetailsField(getContext(), note.getIllSymptoms(), "view");
                symptomParentGroup.addView(symptomDetailsField);


            }
        }

        return rootView;
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

    private View.OnClickListener editLsnr = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_backToNotes:
                    getFragmentManager().popBackStack();
                    break;
                case R.id.btn_editNote:
                    FragmentManager fragmentManager = getFragmentManager();
                    CreateNoteHome createNoteFrag = CreateNoteHome.newInstance(note.getId());
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction
                            .replace(R.id.content, createNoteFrag)
                            .addToBackStack(null)
                            .commit();
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
