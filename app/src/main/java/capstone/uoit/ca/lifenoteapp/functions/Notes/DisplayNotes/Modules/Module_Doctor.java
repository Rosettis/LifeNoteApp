package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules;

import android.support.v4.app.Fragment;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentDoctor;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteFragmentTitle;

/**
 * Created by Peter on 14/01/16.
 */
public class Module_Doctor extends NoteModule {
    String doctorName;

    @Override
    public Fragment getFragment(String mode) {
        return NoteFragmentDoctor.newInstance(mode, doctorName);

    }

    @Override
    public void getData(Fragment frag) {
        NoteFragmentDoctor fragment = (NoteFragmentDoctor) frag;
        doctorName = fragment.getDoctor();
    }

    public Module_Doctor(String doctorName) {
        this.doctorName = doctorName;
    }

    public Module_Doctor() {}

    public String getdoctorName() {
        return doctorName;
    }

    public void setdoctorName(String doctorName) {
        doctorName = doctorName;
    }
}
