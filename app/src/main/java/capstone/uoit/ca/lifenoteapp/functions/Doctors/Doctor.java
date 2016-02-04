package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import com.google.android.gms.maps.model.LatLng;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class Doctor {
    private String name;
    private String phone;
    private String address;
    private String email;
    private LatLng location;

    public Doctor(String name){
        this.name = name;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Doctor(String name, String phone, String address, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.location = new LatLng(-0.0,0.0);
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

    public String getEmail() {
        return email;
    }

    public LatLng getLocation() {
        return location;
    }
}
