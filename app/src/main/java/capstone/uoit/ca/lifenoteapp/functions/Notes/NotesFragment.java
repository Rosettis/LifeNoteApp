package capstone.uoit.ca.lifenoteapp.functions.Notes;

/**
 * Created by 100490515 on 11/16/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * NotesFragment
 *
 * @author Matthew Rosettis
 */
public class NotesFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    public void createNewDetailedNote (View btn) {

    }
}
