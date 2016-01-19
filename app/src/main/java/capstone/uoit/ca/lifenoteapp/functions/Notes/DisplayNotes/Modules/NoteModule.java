package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by Peter on 14/01/16.
 */
public abstract class NoteModule implements Serializable {
    public abstract Fragment getFragment(String mode);
    public abstract void getData(Fragment frag);
}