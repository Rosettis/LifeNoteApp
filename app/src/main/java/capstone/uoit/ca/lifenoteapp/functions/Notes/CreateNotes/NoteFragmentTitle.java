package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/01/16.
 */
public class NoteFragmentTitle extends Fragment implements AdapterView.OnItemSelectedListener {
    ArrayList<NoteLayout> layouts;
    int lastEntryInSpinner;

    OnLayoutSetListener onLayoutSet;

    public void setCallBack(OnLayoutSetListener onLayout) {
        onLayoutSet = onLayout;
    }

    public interface OnLayoutSetListener{
        void displayLayout(ArrayList<NoteLayout> layouts);
        void displayCreateLayoutFrag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_title, container, false);
        Bundle bundle = getArguments();
        layouts = (ArrayList<NoteLayout>) bundle.getSerializable("layouts");

        Spinner noteTypeSpinner = (Spinner) view.findViewById(R.id.spinner_enterNoteType);
        EditText titleEditText = (EditText) view.findViewById(R.id.editText_enterNoteTitle);

        //todo implement global note incremental naming system
        NoteLayout layout = layouts.get(0);
        if (layout != null) titleEditText.setText(layout.getLayoutName());
        List<String> layoutNames = layoutNames = new ArrayList<>();
        for (NoteLayout currLayout : layouts){
            layoutNames.add(currLayout.getLayoutName());
        }
        layoutNames.add("Create new note layout");

        //todo refer to createNoteActivity for how to add array to arrayadapter
        lastEntryInSpinner = layoutNames.size() - 1;
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, layoutNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteTypeSpinner.setAdapter(spinnerArrayAdapter);
        noteTypeSpinner.setOnItemSelectedListener(this);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("pos:" + position + "|entries:" + lastEntryInSpinner);
        if (position == lastEntryInSpinner) {
            onLayoutSet.displayCreateLayoutFrag();
        } else if (position != 0) {
            NoteLayout layout = layouts.get(position);
            layouts.remove(position);
            layouts.add(0, layout);
            onLayoutSet.displayLayout(layouts);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
