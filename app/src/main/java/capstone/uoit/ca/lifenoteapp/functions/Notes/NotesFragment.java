package capstone.uoit.ca.lifenoteapp.functions.Notes;

/**
 * Created by 100490515 on 11/16/2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * NotesFragment
 *
 * @author Matthew Rosettis
 */
public class NotesFragment extends ListFragment {
    View rootView;

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        ArrayList<Note> listOfNotes = new ArrayList<>();
        listOfNotes.add(new Note("Sore foot", "qn", "hurt foot during football, very swollen, applying ice", "Fri Dec 2nd 2015 4:30"));

//        NoteAdapter adaptor = new NoteAdapter(getActivity(), R.layout.listview_item_row, listOfNotes);
        NoteAdapter adaptor = new NoteAdapter(getActivity(), listOfNotes);

        setListAdapter(adaptor);
        return rootView;
    }

}