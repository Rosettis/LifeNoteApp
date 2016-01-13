package capstone.uoit.ca.lifenoteapp.functions.Notes;

import java.io.Serializable;

/**
 * Created by Peter on 09/01/16.
 */
public class NoteLayout implements Serializable {
    private long id;
    private String layoutName;
    private final int TRUE = 1;
    private final int FALSE = 0;

    private boolean containsCodifyFrag = false;
    private boolean containsDateAndTimeFrag = false;
    private boolean containsDoctorFrag = false;
    private boolean containsIllnessFrag = false;

    public NoteLayout(String layoutName) {
        this.layoutName = layoutName;
    }

    public NoteLayout(String layoutName, boolean containsDateAndTimeFrag, boolean containsDoctorFrag, boolean containsIllnessFrag , boolean containsCodifyFrag) {
        this.layoutName = layoutName;
        this.containsCodifyFrag = containsCodifyFrag;
        this.containsDateAndTimeFrag = containsDateAndTimeFrag;
        this.containsDoctorFrag = containsDoctorFrag;
        this.containsIllnessFrag = containsIllnessFrag;
    }

    public NoteLayout(String layoutName, int containsDateAndTimeFrag, int containsDoctorFrag, int containsIllnessFrag , int containsCodifyFrag) {
        this.layoutName = layoutName;
        this.containsCodifyFrag = toBool(containsCodifyFrag);
        this.containsDateAndTimeFrag = toBool(containsDateAndTimeFrag);
        this.containsDoctorFrag = toBool(containsDoctorFrag);
        this.containsIllnessFrag = toBool(containsIllnessFrag);
    }

    public String getLayoutName() {
        return layoutName;
    }
    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public boolean hasDateAndTimeFrag() {
        return containsDateAndTimeFrag;
    }
    public int hasDateAndTimeFragAsInt() {
        if (containsDateAndTimeFrag) return TRUE;
        else return FALSE;
    }
    public void setHasDateAndTimeFrag(boolean containsDateAndTimeFrag) {
        this.containsDateAndTimeFrag = containsDateAndTimeFrag;
    }

    public boolean hasDoctorFrag() {
        return containsDoctorFrag;
    }
    public int hasDoctorFragAsInt() {
        if (containsDoctorFrag) return TRUE;
        else return FALSE;
    }
    public void setHasDoctorFrag(boolean containsDoctorFrag) {
        this.containsDoctorFrag = containsDoctorFrag;
    }

    public boolean hasIllnessFrag() {
        return containsIllnessFrag;
    }
    public int hasIllnessFragAsInt() {
        if (containsIllnessFrag) return TRUE;
        else return FALSE;
    }
    public void setHasIllnessFrag(boolean containsIllnessFrag) {
        this.containsIllnessFrag = containsIllnessFrag;
    }

    public boolean hasCodifyFrag() {
        return containsCodifyFrag;
    }
    public int hasCodifyFragAsInt() {
        if (containsCodifyFrag) return TRUE;
        else return FALSE;
    }
    public void setHasCodifyFrag(boolean containsCodifyFrag) {
        this.containsCodifyFrag = containsCodifyFrag;
    }

    private boolean toBool(int i) {
        return i == 1;
    }

}
