package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

/**
 * Created by 100490515 on 11/16/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentTitle;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Title;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;

/**
 * NotesFragment
 *
 * @author Matthew Rosettis
 * @author Peter Little
 */
public class NotesFragment extends Fragment {
    View rootView;

    public static NotesFragment newInstance(Note note) {
        NotesFragment fragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("modules", note.getModules());
        bundle.putSerializable("header", note.getHeader());
        fragment.setArguments(bundle);
        return fragment;
    }

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notes, container, false);
//
//        ArrayList<Note> listOfNotes = new ArrayList<>();
//        listOfNotes.add(new Note("Sore foot", "qn", "hurt foot during football, very swollen, applying ice", "Fri Dec 2nd 2015 4:30"));
//
////        NoteAdapter adaptor = new NoteAdapter(getActivity(), R.layout.listview_item_row, listOfNotes);
//        NoteAdapter adaptor = new NoteAdapter(getActivity(), listOfNotes);
//
//        setListAdapter(adaptor);

        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this.getContext());
        Bundle bundle = getArguments();
        ArrayList<NoteModule> modules = (ArrayList<NoteModule>) bundle.getSerializable("modules");
        Module_Title header = (Module_Title) bundle.getSerializable("header");

        Note testNote = dbHelper.getAllNotes().get(0);

        getChildFragmentManager().beginTransaction()
                .add(R.id.linearLayout_notes_fragment, header.getFragment("view")).commit();
        for (NoteModule currModule : modules) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.linearLayout_notes_fragment, currModule.getFragment("view")).commit();

        }
        return rootView;
    }

}