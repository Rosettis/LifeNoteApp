package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

/**
 * Created by 100490515 on 11/16/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_DateAndTime;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Details;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Doctor;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Illness;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Title;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;

/**
 * NotesFragment
 *
 * @author Matthew Rosettis
 */
public class NotesFragment extends Fragment {
    View rootView;

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
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

        ArrayList<NoteModule> testModule = new ArrayList<>();
        testModule.add(new Module_Title("Test Title"));
        testModule.add(new Module_DateAndTime("today", "right now"));
        testModule.add(new Module_Doctor("hard"));
        testModule.add(new Module_Illness("cancer"));
        testModule.add(new Module_Details("i now have cancer"));
        Note testNote2 = new Note(testModule);

        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this.getContext());
        dbHelper.createNote(testNote2);
        Note testNote = dbHelper.getAllNotes().get(0);

        for (NoteModule currModule : testNote.getModules()) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.linearLayout_notes_fragment, currModule.getFragment("view")).commit();

        }
        return rootView;
    }

}