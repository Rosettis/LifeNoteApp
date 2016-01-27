package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Details;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Doctor;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Illness;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Title;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;

public class CreateNoteHome extends Fragment implements NoteFragmentTitle.OnLayoutSetListener, NewLayoutFragment.OnSectionDoneListener {
    private ArrayList<NoteLayout> layouts = null;
    private ArrayList<Fragment> currFragments = new ArrayList<>();
    private Fragment titleFragment;
    private Note note = new Note(new ArrayList<NoteModule>());
    private Context context;
    Button saveBtn;
    Button cancelBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_create_note_home, container, false);
        NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this.getContext());


//        To Reset database
//        dbHelper.deleteAllNoteLayouts();
//        dbHelper.createNoteLayout("Quick Note", false, false, true);
//        dbHelper.createNoteLayout("Detailed Note", true, true, true);
//        dbHelper.createNoteLayout("Doctors Note", true, false, true);
//
//        NoteDBHelper noteDBHelper = NoteDBHelper.getInstance(this.getContext());
//        noteDBHelper.deleteAllNotes();

        context = this.getContext();
        layouts = dbHelper.getAllLayouts();

        if (rootView.findViewById(R.id.linearLayout_createNoteHome_FragmentHolder) != null) {
            if (savedInstanceState != null) {
                return rootView;
            }
            displayLayout(layouts);
        }
        saveBtn = (Button) rootView.findViewById(R.id.btn_saveCreateNote);
        cancelBtn = (Button) rootView.findViewById(R.id.btn_cancelCreateNote);
        saveBtn.setOnClickListener(saveLsnr);
        cancelBtn.setOnClickListener(saveLsnr);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void displayLayout(ArrayList<NoteLayout> layouts) {
        for (Fragment frag : currFragments) this.getChildFragmentManager().beginTransaction().remove(frag).commit();
        if (titleFragment != null) this.getChildFragmentManager().beginTransaction().remove(titleFragment).commit();
        currFragments.clear();
        if (note.getModules() != null) note.getModules().clear();
        addTitleFragment(layouts);
        NoteLayout layout = layouts.get(0);
        if (layout.hasDoctorFrag()) addDoctorFragment();
        if (layout.hasIllnessFrag()) addIllnessFragment();
        if (layout.hasCodifyFrag()) addCodifyFragment();
    }


    @Override
    public void createLayout(BitSet selected) {
        NoteLayout newLayout = new NoteLayout("NewNote 1"); //todo add custom layout name

        for (int i = 0; i < selected.length(); i ++) {
            boolean hasFrag = selected.get(i);
            switch (i) {
                case 0:
                    newLayout.setHasDoctorFrag(hasFrag);
                    break;
                case 1:
                    newLayout.setHasIllnessFrag(hasFrag);
                    break;
                case 2:
                    newLayout.setHasCodifyFrag(hasFrag);
                    break;
            }
        }

        layouts.add(0, newLayout);
        NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(this.getContext());
        dbHelper.createNoteLayout(newLayout);
        displayLayout(layouts);
    }

    @Override
    public void displayCreateLayoutFrag() {
        System.out.println("displayCreateLayoutFrag");


        NewLayoutFragment newLayoutFragment = NewLayoutFragment.newInstance(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.NoteFragments_array))));
        newLayoutFragment.setCallBack(this);
        newLayoutFragment.show(this.getChildFragmentManager(), "Create new Layout Fragment");
    }

    private void addTitleFragment(ArrayList<NoteLayout> layouts){
        //todo implement NoteItemAdaptor naming systems
            NoteFragmentTitle noteFragmentTitle = NoteFragmentTitle.newInstance("create", "New Note 1", layouts);
            noteFragmentTitle.setCallBack(this);
            titleFragment = noteFragmentTitle;
            note.setHeader(new Module_Title());
            this.getChildFragmentManager().beginTransaction()
                    .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentTitle).commit();
    }


    private void addDoctorFragment(){
        NoteFragmentDoctor noteFragmentDoctor = NoteFragmentDoctor.newInstance("create");
        currFragments.add(noteFragmentDoctor);
        note.add(new Module_Doctor());
        this.getChildFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentDoctor).commit();
    }

    private void addIllnessFragment(){
        NoteFragmentIllness noteFragmentIllness = NoteFragmentIllness.newInstance("create");
        currFragments.add(noteFragmentIllness);
        note.add(new Module_Illness());
        this.getChildFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentIllness).commit();
    }

    private void addCodifyFragment(){
        NoteFragmentCodify noteFragmentCodify = NoteFragmentCodify.newInstance("create");
        currFragments.add(noteFragmentCodify);
        note.add(new Module_Details());
        this.getChildFragmentManager().beginTransaction()
                .add(R.id.linearLayout_createNoteHome_FragmentHolder, noteFragmentCodify).commit();
    }

    private View.OnClickListener saveLsnr = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancelCreateNote: break;
                case R.id.btn_saveCreateNote:
                    int i = 0;
                    System.out.println("BAM: " + note.getModules().size());
                    note.getHeader().getData(titleFragment);
                    for (NoteModule module : note.getModules()) {
                        module.getData(currFragments.get(i));
                        i ++;
                    }
                    NoteDBHelper noteDBHelper = NoteDBHelper.getInstance(context);
                    noteDBHelper.createNote(note);
                    break;
            }
        }
    };
}
