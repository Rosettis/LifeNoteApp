package capstone.uoit.ca.lifenoteapp.functions.Notes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by ubuntu on 16/11/15.
 */
public class QuickNote extends Note {
    String description;
    String illness;
    int severity;

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

    public void writeToFile(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        BufferedWriter bw = new BufferedWriter(printWriter);
        try {
            System.out.print("Writing:" + getNoteAsString());
            bw.write(getNoteAsString());
        } catch (IOException unableToWriteToFile) {
            //TODO handle unable to write to file exceptiom
            unableToWriteToFile.printStackTrace();
        }
    }

    private String getNoteAsString() {
        return    super.name + "^"
                + super.noteType + "^"
                + super.createdBy + "^"
                + super.dateCreated.toString() + "^"
                + super.dateModified.toString() + "^"
                + illness + "^"
                + severity + "^"
                + description;
    }
}
