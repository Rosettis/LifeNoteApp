package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 07/01/16.
 */
public class NoteFragmentDoctor extends Fragment {
    AutoCompleteTextView textView;

    public static NoteFragmentDoctor newInstance(String mode) {
        NoteFragmentDoctor newInstance = new NoteFragmentDoctor();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public static NoteFragmentDoctor newInstance(String mode, String doctor) {
        NoteFragmentDoctor newInstance = new NoteFragmentDoctor();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("doctor", doctor);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public String getDoctor() {
        return textView.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if ("view".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_doctor_view, container, false);
            TextView textView = (TextView) view.findViewById(R.id.textView_viewDoctor);
            String doc = bundle.getString("doctor");
            textView.setText(doc);
            return view;
        } else if ("create".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_doctor_create, container, false);
            textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_doctor);
            String[] doctors = getResources().getStringArray(R.array.doctors_array);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, doctors);
            textView.setAdapter(adapter);
            return view;
        } else {
            System.out.println("Error Invalid mode Entered");
            return null;
        }
    }
}
