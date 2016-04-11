package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Graphs.CodifiedHashMapManager;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CreateNoteHome;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;

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

        rv = (RecyclerView)rootView.findViewById(R.id.RV_notes_list);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);

        ((MainActivity) getActivity()).setActionBarTitle("View Notes");

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_createNewNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction
                        .replace(R.id.content, new CreateNoteHome(), "createNoteHome")
                        .addToBackStack(null)
                        .commit();
            }
        });

        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this.getContext());
        ArrayList<Note> notes = dbHelper.getAllNotes();
        ArrayList<Note> reversed = new ArrayList<>();


        for (int i = notes.size() - 1; i >= 0; i --) {
            reversed.add(notes.get(i));
        }

        NoteItemAdaptor adapter = new NoteItemAdaptor(reversed, this);
        rv.setAdapter(adapter);

        if(notes.size() == 0) {
            rv.setVisibility(View.GONE);
            TextView emptyView = (TextView) rootView.findViewById(R.id.empty_view);
            emptyView.setVisibility(View.VISIBLE);
        }
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
//        System.out.println("displaying notes");
//
//        ViewNote frag = ViewNote.newInstance(note);
//        FragmentManager fragManager = getFragmentManager();
//
//        FragmentTransaction transaction = fragManager.beginTransaction();
//        transaction
//                .replace(R.id.content, frag)
//                .addToBackStack(null)
//                .commit();
        FragmentManager fragmentManager = getFragmentManager();
        CreateNoteHome createNoteFrag = CreateNoteHome.newInstance(note.getId());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(R.id.content, createNoteFrag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteNote(Note note) {
        System.out.println("DELETING NOTE");
        DialogInterface.OnClickListener dialogClickListener = new OnConfirmListener(note, this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you wish to delete this note?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public class OnConfirmListener implements DialogInterface.OnClickListener {
        Note note;
        NoteItemAdaptor.NoteViewHolder.OnNoteSelectedListener lsnr;

        public OnConfirmListener(Note note, NoteItemAdaptor.NoteViewHolder.OnNoteSelectedListener lsnr) {
            this.note = note;
            this.lsnr = lsnr;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    NoteDBHelper dbHelper = NoteDBHelper.getInstance(getContext());
                    dbHelper.deleteNote(note.getId());
                    ArrayList<Note> notes = dbHelper.getAllNotes();
                    NoteItemAdaptor adapter = new NoteItemAdaptor(notes, lsnr);
                    CodifiedHashMapManager.getInstance(getContext()).removeEntries(note);
                    rv.setAdapter(adapter);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    }
}
