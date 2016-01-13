package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;

public class CreateNoteHome extends FragmentActivity implements NoteFragmentTitle.OnLayoutSetListener, NewLayoutFragment.OnSectionDoneListener {
    private ArrayList<NoteLayout> layouts = null;
    private ArrayList<Fragment> currFragments = new ArrayList<>();

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

        if (findViewById(R.id.linearLayout_createNoteHome) != null) {
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
        NoteFragmentTitle noteFragmentTitle = new NoteFragmentTitle();
        noteFragmentTitle.setCallBack(this);
        Bundle bundle = new Bundle();
        bundle.putSerializable("layouts", layouts);
        noteFragmentTitle.setArguments(bundle);
        currFragments.add(noteFragmentTitle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome, noteFragmentTitle).commit();
    }

    private void addCodifyFragment(){
        NoteFragmentCodify noteFragmentCodify = new NoteFragmentCodify();
        noteFragmentCodify.setArguments(getIntent().getExtras());
        currFragments.add(noteFragmentCodify);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome, noteFragmentCodify).commit();
    }

    private void addIllnessFragment(){
        NoteFragmentIllness noteFragmentIllness = new NoteFragmentIllness();
        noteFragmentIllness.setArguments(getIntent().getExtras());
        currFragments.add(noteFragmentIllness);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome, noteFragmentIllness).commit();
    }

    private void addDoctorFragment(){
        NoteFragmentDoctor noteFragmentDoctor = new NoteFragmentDoctor();
        noteFragmentDoctor.setArguments(getIntent().getExtras());
        currFragments.add(noteFragmentDoctor);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome, noteFragmentDoctor).commit();
    }

    private void addDateAndTimeFragment(){
        NoteFragmentDateAndTime noteFragmentDateAndTime = new NoteFragmentDateAndTime();
        noteFragmentDateAndTime.setArguments(getIntent().getExtras());
        currFragments.add(noteFragmentDateAndTime);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome, noteFragmentDateAndTime).commit();
    }
}
