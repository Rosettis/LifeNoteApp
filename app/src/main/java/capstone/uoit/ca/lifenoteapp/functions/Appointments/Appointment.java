package capstone.uoit.ca.lifenoteapp.functions.Appointments;

/**
 * Created by Peter on 03/12/15.
 */
public class Appointment {
    private int id;
    private String aptName;
    private String aptDate;
    private String aptTime;
    private String aptClinic;
    private String aptDoctor;

    public Appointment(String aptName, String aptDate, String aptTime, String aptClinic, String aptDoctor){
        this.id = -1;
        this.aptName = aptName;
        this.aptDate = aptDate;
        this.aptTime = aptTime;
        this.aptClinic = aptClinic;
        this.aptDoctor = aptDoctor;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public String getAptDate() {
        return aptDate;
    }

    public void setAptDate(String aptDate) {
        this.aptDate = aptDate;
    }

    public String getAptTime() {
        return aptTime;
    }

    public void setAptTime(String aptTime) {
        this.aptTime = aptTime;
    }

    public String getAptClinic() {
        return aptClinic;
    }

    public void setAptClinic(String aptClinic) {
        this.aptClinic = aptClinic;
    }

    public String getAptDoctor() {
        return aptDoctor;
    }

    public void setAptDoctor(String aptDoctor) {
        this.aptDoctor = aptDoctor;
    }}
