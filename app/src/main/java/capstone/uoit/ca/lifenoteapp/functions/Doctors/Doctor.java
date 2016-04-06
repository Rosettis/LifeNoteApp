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
    private String title;
    private LatLng location;
    private Image photo;

    //Constructor
    public Doctor(Builder builder) {
        this.id = -1;
        name = builder.name;
        phone = builder.phone;
        address = builder.address;
        email = builder.email;
        location = builder.location;
    }

    public static class Builder{
        private String name;
        private String phone;
        private String address;
        private String email;
        private String title;
        private LatLng location = new LatLng(-0.0,0.0);

        public Builder( String name, String phone){
            this.name = name;
            this.phone = phone;
        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder location(LatLng location){
            this.location = location;
            return this;
        }

        public Doctor build(){
            return new Doctor(this);
        }
    }

    //Getters
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

    public String getTitle() {
        return title;
    }

    public LatLng getLocation() {
        return location;
    }

    public Image getPhoto() {
        return photo;
    }

    public long getId() {
        return id;
    }


    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setId(long id) {
        this.id = id;
    }

}
