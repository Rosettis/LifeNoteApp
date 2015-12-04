package capstone.uoit.ca.lifenoteapp.functions.Notes;

/**
 * Created by 100490515 on 11/16/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * NotesFragment
 *
 * @author Matthew Rosettis
 */
public class NotesFragment extends Fragment implements NoteListener {
    private final String fileName = "notes.txt";
    View view;

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
        Log.d("File Name", fileName);
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadNotes(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadNotes(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        return view;
    }

    public void downloadNotes(InputStream fileName){
        DownloadNotesTask task = new DownloadNotesTask(this);
        task.execute(fileName);
    }

    @Override
    public void showNotes(ArrayList<Note> data) {
        Log.d("Note",data.toString());
//        NoteAdapter output = new NoteAdapter(getContext(),data);
//        ListView listView = (ListView) view.findViewById(R.id.noteListView);
//        listView.setAdapter(output);
    }
}
