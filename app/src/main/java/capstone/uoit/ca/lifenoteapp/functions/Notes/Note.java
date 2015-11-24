package capstone.uoit.ca.lifenoteapp.functions.Notes;

import java.util.Date;

/**
 * Created by ubuntu on 16/11/15.
 */
public class Note {
    String name;
    Date dateCreated;
    Date dateModified;
    String noteType;
    String CreatedBy;

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedAsString(){
        return dateCreated.toString();
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public String getDateModifiedAsString(){
        return dateModified.toString();
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}
