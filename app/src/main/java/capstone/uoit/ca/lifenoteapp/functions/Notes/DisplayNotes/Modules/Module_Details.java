package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules;

import android.support.v4.app.Fragment;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentCodify;

/**
 * Created by Peter on 14/01/16.
 */
public class Module_Details extends NoteModule {
    String details;

    @Override
    public Fragment getFragment(String mode) {
        return NoteFragmentCodify.newInstance(mode, details);
    }

    @Override
    public void getData(Fragment frag) {
        NoteFragmentCodify fragment = (NoteFragmentCodify) frag;
        details = fragment.getDetails();
    }

    public Module_Details(String details) {
        this.details = details;
    }

    public Module_Details() {}

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
