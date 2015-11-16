package capstone.uoit.ca.mobileapp.functions.Notes;

import java.util.Date;

/**
 * Created by ubuntu on 16/11/15.
 */
public class Note {
    String name;
    Date dateCreated;
    Date dateModified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}
