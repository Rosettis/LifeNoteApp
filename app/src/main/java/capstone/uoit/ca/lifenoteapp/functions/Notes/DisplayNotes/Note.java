package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;

/**
 * Created by Peter on 16/01/16.
 */
public class Note {
    private long id;

    private ArrayList<NoteModule> modules;

    public Note(ArrayList<NoteModule> modules) {
        this.modules = modules;
    }

    public Note(NoteModule... modules) {
        for (NoteModule module: modules) {
            this.modules.add(module);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<NoteModule> getModules() {
        return modules;
    }

    public void setModules(ArrayList<NoteModule> modules) {
        this.modules = modules;
    }

    public void add(NoteModule module) {
        this.modules.add(module);
    }

    public String getTitle() {
        return "test Note Name";
    }
}
