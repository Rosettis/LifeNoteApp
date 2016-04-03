package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Matthew on 4/2/2016.
 */
public class DoctorViewHolder extends RecyclerView.ViewHolder {
    protected TextView doctorName;
    protected TextView doctorPhone;
    protected TextView doctorAddress;
    protected TextView doctorEmail;
    protected TextView doctorLocation;
    protected ImageView doctorPhoto;

    public DoctorViewHolder(View v){
        super(v);
        doctorName = (TextView) v.findViewById(R.id.doctorName);
        doctorPhone = (TextView) v.findViewById(R.id.doctorPhone);
        doctorAddress = (TextView) v.findViewById(R.id.doctorAddress);
        doctorPhoto = (ImageView) v.findViewById(R.id.doctor_photo);
    }
}
