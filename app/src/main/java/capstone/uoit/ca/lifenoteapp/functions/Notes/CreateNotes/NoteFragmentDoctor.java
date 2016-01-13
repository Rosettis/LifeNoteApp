package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 07/01/16.
 */
public class NoteFragmentDoctor extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_enter_doctor, container, false);
        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_doctor);
        String[] doctors = getResources().getStringArray(R.array.doctors_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, doctors);
        textView.setAdapter(adapter);
        return view;
    }
}
