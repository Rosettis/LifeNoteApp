package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

/**
 * Created by Peter on 03/03/16.
 */
public class NoteLayout {
    private long id;
    private String layoutName;
    private boolean containsHeaderModule = false;
        private boolean containsLayoutNameField = false;
        private boolean containsNoteNameField = false;
        private boolean containsDateField = false;
        private boolean containsTimeField = false;
    private boolean containsDoctorModule = false;
        private boolean containsDocNameField = false;
        private boolean containsDocDetailsField = false;
    private boolean containsIllnessModule = false;
        private boolean containsIllNameField = false;
        private boolean containsIllSymptomsField = false;
        private boolean containsIllSeverityField = false;
    private boolean containsWeightModule = false;


    public NoteLayout(String layoutName) {
        this.layoutName = layoutName;
    }

    public NoteLayout(String layoutName,
                      boolean containsHeaderModule,
                      boolean containsLayoutNameField,
                      boolean containsNoteNameField,
                      boolean containsDateField,
                      boolean containsTimeField,
                      boolean containsDoctorModule,
                      boolean containsDocNameField,
                      boolean containsDocDetailsField,
                      boolean containsIllnessModule,
                      boolean containsIllNameField,
                      boolean containsIllSymptomsField,
                      boolean containsIllSeverityField,
                      boolean containsWeightModule) {
        this.layoutName = layoutName;
        this.containsHeaderModule = containsHeaderModule;
            this.containsLayoutNameField = containsLayoutNameField;
            this.containsNoteNameField = containsNoteNameField;
            this.containsDateField = containsDateField;
            this.containsTimeField = containsTimeField;
        this.containsDoctorModule = containsDoctorModule;
            this.containsDocNameField = containsDocNameField;
            this.containsDocDetailsField = containsDocDetailsField;
        this.containsIllnessModule = containsIllnessModule;
            this.containsIllNameField = containsIllNameField;
            this.containsIllSymptomsField = containsIllSymptomsField;
            this.containsIllSeverityField = containsIllSeverityField;
        this.containsWeightModule = containsWeightModule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public boolean containsHeaderModule() {
        return containsHeaderModule;
    }

    public int containsHeaderModuleAsInt() {
        if (containsHeaderModule) return 1;
        else return 0;
    }

    public void setContainsHeaderModule(boolean containsHeaderModule) {
        this.containsHeaderModule = containsHeaderModule;
        this.containsLayoutNameField = containsHeaderModule;
        this.containsNoteNameField = containsHeaderModule;
        this.containsDateField = containsHeaderModule;
        this.containsTimeField = containsHeaderModule;
    }

    public boolean containsDoctorModule() {
        return containsDoctorModule;
    }

    public int containsDoctorModuleAsInt() {
        if (containsDoctorModule) return 1;
        else return 0;
    }

    public void setContainsDoctorModule(boolean containsDoctorModule) {
        this.containsDoctorModule = containsDoctorModule;
        this.containsDocNameField = containsDoctorModule;
        this.containsDocDetailsField = containsDoctorModule;
    }

    public boolean containsIllnessModule() {
        return containsIllnessModule;
    }

    public int containsIllnessModuleAsInt() {
        if (containsIllnessModule) return 1;
        else return 0;
    }

    public void setContainsIllnessModule(boolean containsIllnessModule) {
        this.containsIllnessModule = containsIllnessModule;
        this.containsIllNameField = containsIllnessModule;
        this.containsIllSymptomsField = containsIllnessModule;
        this.containsIllSeverityField = containsIllnessModule;
    }

    public boolean containsWeightModule() {
        return containsWeightModule;
    }

    public int containsWeightModuleAsInt() {
        if (containsWeightModule) return 1;
        else return 0;
    }

    public void setContainsWeightModule(boolean containsWeightModule) {
        this.containsWeightModule = containsWeightModule;
    }

    public boolean containsLayoutNameField() {
        return containsLayoutNameField;
    }

    public void setContainsLayoutNameField(boolean containsLayoutNameField) {
        this.containsLayoutNameField = containsLayoutNameField;
        containsHeaderModule = true;
    }

    public boolean containsNoteNameField() {
        return containsNoteNameField;
    }

    public void setContainsNoteNameField(boolean containsNoteNameField) {
        this.containsNoteNameField = containsNoteNameField;
        containsHeaderModule = true;
    }

    public boolean containsDateField() {
        return containsDateField;
    }

    public void setContainsDateField(boolean containsDateField) {
        this.containsDateField = containsDateField;
        containsHeaderModule = true;
    }

    public boolean containsTimeField() {
        return containsTimeField;
    }

    public void setContainsTimeField(boolean containsTimeField) {
        this.containsTimeField = containsTimeField;
        containsHeaderModule = true;
    }

    public boolean containsDocNameField() {
        return containsDocNameField;
    }

    public void setContainsDocNameField(boolean containsDocNameField) {
        this.containsDocNameField = containsDocNameField;
        containsDoctorModule = true;
    }

    public boolean containsDocDetailsField() {
        return containsDocDetailsField;
    }

    public void setContainsDocDetailsField(boolean containsDocDetailsField) {
        this.containsDocDetailsField = containsDocDetailsField;
        containsDoctorModule = true;
    }

    public boolean containsIllNameField() {
        return containsIllNameField;
    }

    public void setContainsIllNameField(boolean containsIllNameField) {
        this.containsIllNameField = containsIllNameField;
        containsIllnessModule = true;
    }

    public boolean containsIllSymptomsField() {
        return containsIllSymptomsField;
    }

    public void setContainsIllSymptomsField(boolean containsIllSymptomsField) {
        this.containsIllSymptomsField = containsIllSymptomsField;
        containsIllnessModule = true;
    }

    public boolean containsIllSeverityField() {
        return containsIllSeverityField;
    }

    public void setContainsIllSeverityField(boolean containsIllSeverityField) {
        this.containsIllSeverityField = containsIllSeverityField;
        containsIllnessModule = true;
    }
}
