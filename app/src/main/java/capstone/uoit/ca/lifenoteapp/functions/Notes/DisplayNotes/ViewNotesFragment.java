package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_DateAndTime;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Details;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Doctor;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Illness;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Title;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;

/**
 * Created by Peter on 18/01/16.
 */
public class ViewNotesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_notes, container, false);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.RV_notes_list);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);

        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this.getContext());
        dbHelper.deleteAllNotes();


//        ArrayList<NoteModule> testModule = new ArrayList<>();
//        testModule.add(new Module_DateAndTime("today", "right now"));
//        testModule.add(new Module_Doctor("hard"));
//        testModule.add(new Module_Illness("cancer"));
//        testModule.add(new Module_Details("i now have cancer"));
//        Module_Title titleMod = new Module_Title("Test Title", "a date", "a time", "Detailed Note");
//        Note testNote2 = new Note(titleMod, testModule);
//        dbHelper.createNote(testNote2);


        ArrayList<Note> notes = dbHelper.getAllNotes();
        NoteItemAdaptor adapter = new NoteItemAdaptor(notes);
        rv.setAdapter(adapter);
        return rootView;
    }
}
