package capstone.uoit.ca.lifenoteapp.functions.Notes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by ubuntu on 16/11/15.
 */
public class QuickNote extends Note {
    String description;
    String illness;
    int severity;
//    File notefile = new File("/home/ubuntu/AndroidStudioProjects/LifeNoteApp/app/src/main/res/raw/notes.txt")


    public QuickNote(String name, String noteType, String createdBy, String illness, int severity, String description){
        super(name, noteType, createdBy);
        this.illness = illness;
        this.severity = severity;
        this.description = description;
    }

    public String getTextContent() {
        return description;
    }

    public void setTextContent(String description) {
        this.description = description;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

//    public void writeTofile() {
//        FileWriter fw = new FileWriter(file.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//    }
}
