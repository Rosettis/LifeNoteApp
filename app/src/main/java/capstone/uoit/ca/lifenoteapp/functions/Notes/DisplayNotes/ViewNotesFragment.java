package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CreateNoteHome;

/**
 * Created by Peter on 18/01/16.
 */
public class ViewNotesFragment extends Fragment implements NoteItemAdaptor.NoteViewHolder.OnNoteSelectedListener{
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_notes, container, false);

//        ImageView icon = new ImageView(this.getContext()); // Create an icon
//        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_image_control_point));

//        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
//                .setContentView(icon)
//                .build();

        rv = (RecyclerView)rootView.findViewById(R.id.RV_notes_list);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);


        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this.getContext());

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_createNewNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction
                        .replace(R.id.content, new CreateNoteHome())
                        .addToBackStack(null)
                        .commit();
            }
        });
//        dbHelper.deleteAllNotes();


//        ArrayList<NoteModule> testModule = new ArrayList<>();
//        testModule.add(new Module_DateAndTime("today", "right now"));
//        testModule.add(new Module_Doctor("hard"));
//        testModule.add(new Module_Illness("cancer"));
//        testModule.add(new Module_Details("i now have cancer"));
//        Module_Title titleMod = new Module_Title("Test Title", "a date", "a time", "Detailed Note");
//        Note testNote2 = new Note(titleMod, testModule);
//        dbHelper.createNote(testNote2);


        ArrayList<Note> notes = dbHelper.getAllNotes();
        NoteItemAdaptor adapter = new NoteItemAdaptor(notes, this);
        rv.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this.getContext());
        ArrayList<Note> notes = dbHelper.getAllNotes();
        NoteItemAdaptor adapter = new NoteItemAdaptor(notes, this);
        rv.setAdapter(adapter);
    }

    @Override
    public void displayNote(Note note) {
        NotesFragment frag = NotesFragment.newInstance(note);
        FragmentManager fragManager = getFragmentManager();

        FragmentTransaction transaction = fragManager.beginTransaction();
        transaction
                .replace(R.id.content, frag)
                .addToBackStack(null)
                .commit();
    }

}
