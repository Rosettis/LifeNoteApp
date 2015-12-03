package capstone.uoit.ca.lifenoteapp.functions.Notes;

import java.util.Date;

/**
 * Created by ubuntu on 16/11/15.
 */
public class Note {
    String name;
    Date dateCreated;
    Date dateModified;
    String dateAsString; //for demo only
    String noteType;
    String createdBy;
    String description; //for demo only

    protected Note(String name, String noteType, String createdBy) {
        this.name = name;
        this.dateCreated = new Date();
        this.dateModified = new Date();
        this.noteType = noteType;
        this.createdBy = createdBy;
    }

    protected Note(String name, String noteType, String description, String dateAsString) {
        this.name = name;
        this.noteType = noteType;
        this.description = description;
        this.dateAsString = dateAsString;
    }

    public String getDateAsString() {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected Note(){
        //placeholder
    }

    protected String getNoteType() {
        return noteType;
    }

    protected void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    protected String getCreatedBy() {
        return createdBy;
    }

    protected void setCreatedBy(String createdBy) {
        createdBy = createdBy;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected Date getDateCreated() {
        return dateCreated;
    }

    protected String getDateCreatedAsString(){
        return dateCreated.toString();
    }

    protected void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    protected Date getDateModified() {
        return dateModified;
    }

    protected String getDateModifiedAsString(){
        return dateModified.toString();
    }

    protected void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}
