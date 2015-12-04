package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

public class ShowNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ArrayList<Note> listOfNotes = new ArrayList<>();
        listOfNotes.add(new Note("Visited Doctor", "docn", "gave pain pills, take twice a day with food, once in morning and before bed", "Last Modified: Thu Dec 3rd 2015"));
        listOfNotes.add(new Note("Hurt foot at Soccer", "qn", "Severity: 8/10", "Last Modified: Wed Dec 2nd 2015"));
        listOfNotes.add(new Note("Feeling Better", "dn", "headache is gone, meds seem to be working", "Last Modified: Sat Nov 17th 2015"));
        listOfNotes.add(new Note("Visited Doctor", "docn", "skiped work to see doctor, says I have the flu", "Last Modified: Mon Nov 15th 2015"));
        listOfNotes.add(new Note("Feeling worse", "dn", "woke up with cough, fever, and chills", "Last Modified: Sun Nov 14th 2015"));
        listOfNotes.add(new Note("HeadAche", "qn", "Severity: 6/10", "Last Modified: Sat Nov 13th 2015"));
        listOfNotes.add(new Note("Migraine", "qn", "Severity: 4/10", "Last Modified: Tues Aug 29th 2015"));
        listOfNotes.add(new Note("Migraine", "qn", "Severity: 7/10", "Last Modified: Fri Aug 24th 2015"));
        listOfNotes.add(new Note("Migraine", "qn", "Severity: 8/10", "Last Modified: Thur Aug 23rd 2015"));




        NoteAdapter adapter = new NoteAdapter(this, listOfNotes);
        ListView notesList = (ListView) findViewById(R.id.listView_notes);
        notesList.setAdapter(adapter);
    }

}
