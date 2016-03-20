package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.util.Log;

/**
 * Created by Peter on 03/03/16.
 */
public class Note {
    private long id;
    private NoteLayout layout;
    private String name;
    private String date;
    private String time;
    private String docName;
    private String docDetails;
    private String illName;
    private String illSymptoms;
    private int illSeverity;
    private String additionalDetails;

    public Note(){};

    public Note(NoteLayout layout, String name, String date, String time, String docName, String docDetails, String illName, String illSymptoms, int illSeverity, String additionalDetails) {
        this.layout = layout;
        this.name = name;
        this.date = date;
        this.time = time;
        this.docName = docName;
        this.docDetails = docDetails;
        this.illName = illName;
        this.illSymptoms = illSymptoms;
        this.illSeverity = illSeverity;
        this.additionalDetails = additionalDetails;
    }

    public void setHeaderFields(NoteLayout layout, String name, String date, String time) {
        this.layout = layout;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void setDoctorFields(String docName, String docDetails) {
        this.docName = docName;
        this.docDetails = docDetails;
    }

    public void setIllnessFields(String illName, String illSymptoms, int illSeverity) {
        this.illName = illName;
        this.illSymptoms = illSymptoms;
        this.illSeverity = illSeverity;
    }

    public void setAdditionalDetails (String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NoteLayout getLayout() {
        return layout;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocDetails() {
        return docDetails;
    }

    public String getIllName() {
        return illName;
    }

    public String getIllSymptoms() {
        return illSymptoms;
    }

    public int getIllSeverity() {
        return illSeverity;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void printNote() {
        String TAG = "Displaying Note:";
        Log.i(TAG, "*************************************************************");
        Log.i(TAG, "id: " + id);
        Log.i(TAG, "name: " + name);
        Log.i(TAG, "date: " + date);
        Log.i(TAG, "time: " + time);
        Log.i(TAG, "docName: " + docName);
        Log.i(TAG, "docDetails: " + docDetails);
        Log.i(TAG, "illName: " + illName);
        Log.i(TAG, "illSymptoms: " + illSymptoms);
        Log.i(TAG, "illSeverity: " + illSeverity);
        Log.i(TAG, "additionalDetails: " + additionalDetails);
        Log.i(TAG, "**************************************************************");
    }
}
