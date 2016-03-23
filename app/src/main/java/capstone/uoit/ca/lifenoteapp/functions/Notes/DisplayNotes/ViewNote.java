package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DoctorModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.HeaderModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View.HeaderModuleView;
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
        View rootView = inflater.inflate(R.layout.fragment_view_note, container, false);
        parent = (LinearLayout) rootView.findViewById(R.id.linearLayout_view_note);

        NoteLayout layout = note.getLayout();
        if (layout.containsHeaderModule()) {
            View header = new HeaderModuleView(getContext(), note.getName(), layout, note.getDate(), note.getTime());
            header.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.addView(header);
        }

//
//        if(layout.containsDoctorModule()) {
//            View DoctorModule = new DoctorModule(()
//        }
        return rootView;
    }
}
