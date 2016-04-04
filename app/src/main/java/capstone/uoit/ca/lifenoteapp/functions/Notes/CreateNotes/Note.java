package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    private int weight;
    private int height;
    private ArrayList<String> tags = new ArrayList<>();

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Note(){};

    public Note(NoteLayout layout, String name, String date, String time, String docName, String docDetails, String illName, String illSymptoms, int illSeverity, int weight, int height, ArrayList<String> tags) {
        this.layout = layout;
        this.name = name;
        this.date = date;
        this.time = time;
        this.docName = docName;
        this.docDetails = docDetails;
        this.illName = illName;
        this.illSymptoms = illSymptoms;
        this.illSeverity = illSeverity;
        this.weight = weight;
        this.height = height;
        this.tags = tags;
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

    public void setWeightFields (int weight, int height) {
        this.weight = weight;
        this.height = height;
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

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public void addCodifiedWords(ArrayList<String> words) {
        tags.addAll(words);
    }

    public ArrayList<String> getCodifiedWords() {
        return tags;
    }

    public void printNote() { //for testing purposes
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
        Log.i(TAG, "weight: " + weight);
        Log.i(TAG, "height: " + height);
        Log.i(TAG, "tags: ");
        for(String currTag : tags) {
            Log.i("TAG", currTag);
        }
        Log.i(TAG, "**************************************************************");
    }
}
