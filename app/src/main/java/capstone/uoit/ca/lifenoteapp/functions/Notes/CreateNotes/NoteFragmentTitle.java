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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/01/16.
 */
public class NoteFragmentTitle extends Fragment implements AdapterView.OnItemSelectedListener {
    ArrayList<NoteLayout> layouts;
    String currTitle;
    int lastEntryInSpinner;
    EditText titleEditText;

    OnLayoutSetListener onLayoutSet;


    //test
    public static NoteFragmentTitle newInstance(String title, String mode) {
        NoteFragmentTitle newInstance = new NoteFragmentTitle();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("mode", mode);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public static NoteFragmentTitle newInstance(String title, String mode, ArrayList<NoteLayout> layouts) {
        NoteFragmentTitle newInstance = new NoteFragmentTitle();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("mode", mode);
        bundle.putSerializable("layouts", layouts);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public String getTitle() {
        return titleEditText.getText().toString();
    }

    public void setCallBack(OnLayoutSetListener onLayout) {
        onLayoutSet = onLayout;
    }

    public interface OnLayoutSetListener{
        void displayLayout(ArrayList<NoteLayout> layouts);
        void displayCreateLayoutFrag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if ("view".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_title_view, container, false);
            currTitle = bundle.getString("title");
            TextView titleTextView = (TextView) view.findViewById(R.id.TextView_viewNoteTitle);
            titleTextView.setText(currTitle);
            return view;
        }else {
            View view = inflater.inflate(R.layout.fragment_note_title_create, container, false);
            layouts = (ArrayList<NoteLayout>) bundle.getSerializable("layouts");
            currTitle = bundle.getString("title");
            Spinner noteTypeSpinner = (Spinner) view.findViewById(R.id.spinner_enterNoteType);
            titleEditText = (EditText) view.findViewById(R.id.editText_enterNoteTitle);

            //todo implement global NoteItemAdaptor incremental naming system
            NoteLayout layout = layouts.get(0);
            titleEditText.setText(currTitle);
            List<String> layoutNames = new ArrayList<>();
            for (NoteLayout currLayout : layouts){
                layoutNames.add(currLayout.getLayoutName());
            }
            layoutNames.add("Create new NoteItemAdaptor layout");


            //todo refer to createNoteActivity for how to add array to arrayadapter
            lastEntryInSpinner = layoutNames.size() - 1;
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, layoutNames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            noteTypeSpinner.setAdapter(spinnerArrayAdapter);
            noteTypeSpinner.setOnItemSelectedListener(this);
            return view;
        }
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
