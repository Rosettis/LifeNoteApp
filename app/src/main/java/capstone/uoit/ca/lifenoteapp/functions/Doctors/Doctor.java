package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class Doctor {
    private long id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private LatLng location;
    private Image photo;

    public Doctor(String name){
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Doctor(String name, String phone, String address, String email, LatLng location) {
        this.id = -1;
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

    public Image getPhoto() {
        return photo;
    }
}
