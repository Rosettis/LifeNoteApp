package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class Doctor implements Parcelable{
    private long id = -1;
    private String name = null;
    private String phone = null;
    private String address = null;
    private String email = null;
    private String title = null;
    private LatLng location;
    private Image photo;

    //Constructor
    public Doctor(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setPhone(builder.phone);
        setAddress(builder.address);
        setEmail(builder.email);
        setTitle(builder.title);
        setLocation(builder.location);
    }

    protected Doctor(Parcel in) {
        id = in.readLong();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        email = in.readString();
        title = in.readString();
        location = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*Object object =  ;
        dest.writeValue(object);*/
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(title);
        dest.writeParcelable(location, flags);
    }

    //Builder Factory for Doctors with different build parameters
    public static class Builder{
        private String name;
        private String phone;
        private String address;
        private String email;
        private String title;
        private long id;
        private LatLng location = new LatLng(-0.0,0.0);

        public Builder(String name, String phone){
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

        public Builder id(long id){
            this.id = id;
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
