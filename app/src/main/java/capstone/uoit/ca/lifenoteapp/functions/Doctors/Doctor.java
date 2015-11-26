package capstone.uoit.ca.lifenoteapp.functions.Doctors;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class Doctor {
    private String name;
    private String phone;
    private String address;

    public Doctor(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {

        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
