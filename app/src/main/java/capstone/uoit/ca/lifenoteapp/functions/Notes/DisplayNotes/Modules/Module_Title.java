package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules;

import android.support.v4.app.Fragment;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentTitle;

/**
 * Created by Peter on 14/01/16.
 */
public class Module_Title extends NoteModule {
    String noteTitle;

    public Module_Title(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public Module_Title() {}

    @Override
    public Fragment getFragment(String mode) {
        return NoteFragmentTitle.newInstance(noteTitle, mode);
    }

    @Override
    public void getData(Fragment frag) {
        NoteFragmentTitle fragment = (NoteFragmentTitle) frag;
        noteTitle = fragment.getTitle();
    }


    public String getNoteName() {
        return noteTitle;
    }

    public void setNoteName(String noteTitle) {
        this.noteTitle = noteTitle;
    }
}
