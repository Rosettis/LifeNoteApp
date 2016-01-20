package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_DateAndTime;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Details;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Doctor;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Illness;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Title;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;

public class CreateNoteHome extends FragmentActivity implements NoteFragmentTitle.OnLayoutSetListener, NewLayoutFragment.OnSectionDoneListener {
    private ArrayList<NoteLayout> layouts = null;
    private ArrayList<Fragment> currFragments = new ArrayList<>();
    private Fragment titleFragment;
    private Note note = new Note(new ArrayList<NoteModule>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note_home);

        NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this);

//        To Reset database
//        dbHelper.deleteAllNoteLayouts();
//        dbHelper.createNoteLayout("Quick Note", true, false, false, true);
//        dbHelper.createNoteLayout("Detailed Note", true, true, true, true);
//        dbHelper.createNoteLayout("Doctors Note", true, true, false, true);

        layouts = dbHelper.getAllLayouts();

        if (findViewById(R.id.linearLayout_createNoteHome_FragmentHolder) != null) {
            if (savedInstanceState != null) {
                return;
            }
            displayLayout(layouts);
        }
    }

    @Override
    public void displayLayout(ArrayList<NoteLayout> layouts) {
        for (Fragment frag : currFragments) getSupportFragmentManager().beginTransaction().remove(frag).commit();
        currFragments.clear();
        if (note.getModules() != null) note.getModules().clear();
        addTitleFragment(layouts);
        NoteLayout layout = layouts.get(0);
        if (layout.hasDateAndTimeFrag()) addDateAndTimeFragment();
        if (layout.hasDoctorFrag()) addDoctorFragment();
        if (layout.hasIllnessFrag()) addIllnessFragment();
        if (layout.hasCodifyFrag()) addCodifyFragment();
    }


    @Override
    public void createLayout(BitSet selected) {
        System.out.println("Creating layout here");
        NoteLayout newLayout = new NoteLayout("NewNote 1"); //todo add custom layout name

        for (int i = 0; i < selected.length(); i ++) {
            boolean hasFrag = selected.get(i);
            switch (i) {
                case 0:
                    newLayout.setHasDateAndTimeFrag(hasFrag);
                    System.out.println("adding date and time");
                    break;
                case 1:
                    newLayout.setHasDoctorFrag(hasFrag);
                    System.out.println("adding Doctor");
                    break;
                case 2:
                    newLayout.setHasIllnessFrag(hasFrag);
                    System.out.println("adding Illness");
                    break;
                case 3:
                    newLayout.setHasCodifyFrag(hasFrag);
                    System.out.println("adding Codify");
                    break;
            }
        }

        layouts.add(0, newLayout);
        NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this);
        dbHelper.createNoteLayout(newLayout);
        displayLayout(layouts);
    }

    @Override
    public void displayCreateLayoutFrag() {
        System.out.println("displayCreateLayoutFrag");


        NewLayoutFragment newLayoutFragment = NewLayoutFragment.newInstance(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.NoteFragments_array))));
        newLayoutFragment.setCallBack(this);
        newLayoutFragment.show(getSupportFragmentManager(), "Create new Layout Fragment");
    }

    private void addTitleFragment(ArrayList<NoteLayout> layouts){
        //todo implement NoteItemAdaptor naming systems
        NoteFragmentTitle noteFragmentTitle = NoteFragmentTitle.newInstance("create", "New Note 1", layouts);
        noteFragmentTitle.setCallBack(this);
        titleFragment = noteFragmentTitle;
        note.setHeader(new Module_Title());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentTitle).commit();
    }

    private void addDateAndTimeFragment(){
        NoteFragmentDateAndTime noteFragmentDateAndTime = NoteFragmentDateAndTime.newInstance("create");
        currFragments.add(noteFragmentDateAndTime);
        note.add(new Module_DateAndTime());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentDateAndTime).commit();
    }

    private void addDoctorFragment(){
        NoteFragmentDoctor noteFragmentDoctor = NoteFragmentDoctor.newInstance("create");
        currFragments.add(noteFragmentDoctor);
        note.add(new Module_Doctor());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentDoctor).commit();
    }

    private void addIllnessFragment(){
        NoteFragmentIllness noteFragmentIllness = NoteFragmentIllness.newInstance("create");
        currFragments.add(noteFragmentIllness);
        note.add(new Module_Illness());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentIllness).commit();
    }

    private void addCodifyFragment(){
        NoteFragmentCodify noteFragmentCodify = NoteFragmentCodify.newInstance("create");
        currFragments.add(noteFragmentCodify);
        note.add(new Module_Details());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentCodify).commit();
    }

    public void saveNewNote(View view) {
        NoteDBHelper noteDBHelper = NoteDBHelper.getInstance(this);
        int i = 0;

        note.getHeader().getData(titleFragment);
        for (NoteModule module : note.getModules()) {
            module.getData(currFragments.get(i));
            i ++;
        }
        noteDBHelper.createNote(note);
    }
}
