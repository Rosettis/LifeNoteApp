package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 06/01/16.
 */
public class NoteFragmentIllness extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_enter_illness, container, false);
        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_illness);
        String[] illnesses = getResources().getStringArray(R.array.illnesses_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, illnesses);
        textView.setAdapter(adapter);
        return view;
    }
}
