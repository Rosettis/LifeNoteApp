package capstone.uoit.ca.lifenoteapp.functions.Notes;

/**
 * Created by ubuntu on 26/11/15.
 */
public class DoctorsNote extends Note {
    String doctorsName;
    String visitReason;
    String diagnosis;

    public String getDoctorsName() {
        return doctorsName;
    }

    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
