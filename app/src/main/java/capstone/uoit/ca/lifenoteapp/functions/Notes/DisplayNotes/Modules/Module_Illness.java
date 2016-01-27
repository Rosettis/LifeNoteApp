package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules;

import android.support.v4.app.Fragment;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentIllness;

/**
 * Created by Peter on 14/01/16.
 */
public class Module_Illness extends NoteModule {

    String illnessName;
    String symptoms;
    int severity;


    @Override
    public Fragment getFragment(String mode) {
        return NoteFragmentIllness.newInstance(mode, illnessName, symptoms, severity);
    }

    @Override
    public void getData(Fragment frag) {
        NoteFragmentIllness fragment = (NoteFragmentIllness) frag;
        illnessName = fragment.getIllness();
        symptoms = fragment.getSymptoms();
        severity = fragment.getSeverity();
    }

    public Module_Illness(String illnessName) {
        this.illnessName = illnessName;
    }

    public Module_Illness() {}

    public String getillnessName() {
        return illnessName;
    }

    public void setillnessName(String illnessName) {
        illnessName = illnessName;
    }
}
