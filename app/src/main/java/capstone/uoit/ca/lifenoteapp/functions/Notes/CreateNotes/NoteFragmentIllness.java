package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import org.w3c.dom.Text;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 06/01/16.
 */
public class NoteFragmentIllness extends Fragment {
    AutoCompleteTextView textView;

    public static NoteFragmentIllness newInstance(String mode, String illness) {
        NoteFragmentIllness newInstance = new NoteFragmentIllness();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("illness", illness);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public static NoteFragmentIllness newInstance(String mode) {
        NoteFragmentIllness newInstance = new NoteFragmentIllness();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        newInstance.setArguments(bundle);
        return newInstance;
    }
    
    public String getIllness() {
        return textView.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if ("view".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_illness_view, container, false);
            TextView textView = (TextView) view.findViewById(R.id.textView_view_illness);
            textView.setText(bundle.getString("illness"));
            return view;
        } else if ("create".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_illness_create, container, false);
            textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_illness);
            String[] illnesses = getResources().getStringArray(R.array.illnesses_array);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, illnesses);
            textView.setAdapter(adapter);
            return view;
        } else {
            System.out.println("Error Invalid mod Entered");
            return null;
        }
    }
}
