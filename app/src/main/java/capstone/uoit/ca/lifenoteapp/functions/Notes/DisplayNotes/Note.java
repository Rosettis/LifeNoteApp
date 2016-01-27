package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.Module_Title;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;

/**
 * Created by Peter on 16/01/16.
 */
public class Note {
    private long id;
    private ArrayList<NoteModule> modules;
    private Module_Title header;

    public Note(Module_Title header, ArrayList<NoteModule> modules) {
        this.header = header;
        this.modules = modules;
    }

    public Note(ArrayList<NoteModule> modules) {
        this.modules = modules;
    }

    public Module_Title getHeader() {
        return header;
    }

    public void setHeader(Module_Title header) {
        this.header = header;
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
        return header.getNoteName();
    }

    public String getDate() {
        return header.getNoteDate();
    }

    public String getTime() {
        return header.getNoteTime();
    }

    public String getLayout() {
        return  header.getLayoutName();
    }
}
