package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.util.Log;

/**
 * Created by Peter on 09/04/16.
 */
public class NewAppointment {
    private String TAG = "NEW APPOINTMENT";
    private long id;
    private String name;
    private String date;
    private String time;
    private String clinic;
    private String doc;

    public NewAppointment(String name, String date, String time, String clinic, String doc) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.clinic = clinic;
        this.doc = doc;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public void printAppointment() {
        Log.i(TAG, "*************************************************************");
        Log.i(TAG, "id: " + id);
        Log.i(TAG, "name: " + name);
        Log.i(TAG, "date: " + date);
        Log.i(TAG, "time: " + time);
        Log.i(TAG, "clinic: " + clinic);
        Log.i(TAG, "doc: " + doc);
        Log.i(TAG, "**************************************************************");
    }
}
