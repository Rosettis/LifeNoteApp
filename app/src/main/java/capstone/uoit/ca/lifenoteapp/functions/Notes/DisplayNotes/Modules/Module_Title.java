package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules;

import android.support.v4.app.Fragment;

import java.io.Serializable;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentTitle;

/**
 * Created by Peter on 14/01/16.
 */
public class Module_Title extends NoteModule implements Serializable {
    String noteTitle;
    String noteDate;
    String noteTime;
    String layoutName;

    public Module_Title(String noteTitle, String noteDate, String noteTime, String layoutName) {
        this.noteTitle = noteTitle;
        this.noteDate = noteDate;
        this.noteTime = noteTime;
        this.layoutName = layoutName;
    }

    public Module_Title() {}

    @Override
    public Fragment getFragment(String mode) {
        return NoteFragmentTitle.newInstance(mode, noteTitle, noteDate, noteTime );
    }

    @Override
    public void getData(Fragment frag) {
        NoteFragmentTitle fragment = (NoteFragmentTitle) frag;
        noteTitle = fragment.getTitle();
        noteDate = fragment.getDate();
        noteTime = fragment.getTime();
        layoutName = fragment.getLayoutName();
    }


    public String getNoteName() {
        return noteTitle;
    }

    public void setNoteName(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }
}
