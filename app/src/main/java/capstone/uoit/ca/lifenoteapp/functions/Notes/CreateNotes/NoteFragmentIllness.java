package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 06/01/16.
 */
public class NoteFragmentIllness extends Fragment {
    AutoCompleteTextView textView;
    EditText symptomEditText;
    SeekBar severitySeekBar;

    public static NoteFragmentIllness newInstance(String mode, String illness, String symptoms, int severity) {
        NoteFragmentIllness newInstance = new NoteFragmentIllness();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("illness", illness);
        bundle.putString("symptoms", symptoms);
        bundle.putInt("severity", severity);
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

    public String getSymptoms() {
        return symptomEditText.getText().toString();
    }

    public int getSeverity() {
        return severitySeekBar.getProgress();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if ("view".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_illness_create, container, false);
            textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_illness);
            textView.setText(bundle.getString("illness"));

            symptomEditText = (EditText) view.findViewById(R.id.editText_symptoms);
            symptomEditText.setText(bundle.getString("symptoms"));

            severitySeekBar = (SeekBar) view.findViewById(R.id.seekBar_symptomSeverity);
            severitySeekBar.setProgress(bundle.getInt("severity"));

            return view;
        } else if ("create".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_illness_create, container, false);
            textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_illness);
            symptomEditText = (EditText) view.findViewById(R.id.editText_symptoms);
            severitySeekBar = (SeekBar) view.findViewById(R.id.seekBar_symptomSeverity);

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
