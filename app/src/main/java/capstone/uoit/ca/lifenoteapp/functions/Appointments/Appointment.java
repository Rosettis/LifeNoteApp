package capstone.uoit.ca.lifenoteapp.functions.Appointments;

/**
 * Created by Peter on 03/12/15.
 */
public class Appointment {
    String doctorsName;
    String date;
    String description;
    String clinicName;

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Appointment(String doctorsName, String date, String description, String clinicName) {
        this.doctorsName = doctorsName;
        this.date = date;
        this.description = description;
        this.clinicName = clinicName;
    }

    public String getDoctorsName() {
        return doctorsName;
    }

    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
