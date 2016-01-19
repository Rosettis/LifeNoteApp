package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules;

import android.support.v4.app.Fragment;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentDateAndTime;

/**
 * Created by Peter on 14/01/16.
 */
public class Module_DateAndTime extends NoteModule {

    String date;
    String time;

    @Override
    public Fragment getFragment(String mode) {
        return NoteFragmentDateAndTime.newInstance(mode, date, time);
    }

    @Override
    public void getData(Fragment frag) {
        NoteFragmentDateAndTime fragment = (NoteFragmentDateAndTime) frag;
        date = fragment.getDate();
        time = fragment.getTime();
    }

    public Module_DateAndTime(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public Module_DateAndTime() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
