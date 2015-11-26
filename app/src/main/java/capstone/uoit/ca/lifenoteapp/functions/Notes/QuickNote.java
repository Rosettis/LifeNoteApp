package capstone.uoit.ca.lifenoteapp.functions.Notes;

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

//    public writeTofile();
}
