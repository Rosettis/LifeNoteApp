package capstone.uoit.ca.lifenoteapp.functions.Medication;

import android.util.Log;

/**
 * Created by Peter on 10/04/16.
 */
public class Medication {
    private String TAG = "NEW MEDICATION";
    private long id;
    private String name;
    private String often;
    private String reason;
    private String start;
    private String dosage;
    private String repeats;

    public Medication(String name, String often, String reason, String start, String dosage, String repeats) {
        this.name = name;
        this.often = often;
        this.reason = reason;
        this.start = start;
        this.dosage = dosage;
        this.repeats = repeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOften() {
        return often;
    }

    public void setOften(String often) {
        this.often = often;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getRepeats() {
        return repeats;
    }

    public void setRepeats(String repeats) {
        this.repeats = repeats;
    }

    public void printMedication() {
        Log.i(TAG, "*************************************************************");
        Log.i(TAG, "id: " + id);
        Log.i(TAG, "name: " + name);
        Log.i(TAG, "howOfter: " + often);
        Log.i(TAG, "reason: " + reason);
        Log.i(TAG, "start: " + start);
        Log.i(TAG, "doseage: " + dosage);
        Log.i(TAG, "repeats: " + repeats);
        Log.i(TAG, "**************************************************************");
    }
}
