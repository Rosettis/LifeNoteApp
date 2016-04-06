package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>{//BaseAdapter

    private List<Doctor> doctors = new ArrayList<>();

    DoctorAdapter(List<Doctor> doctors){
        this.doctors = doctors;
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder doctorViewHolder, int i) {
        Doctor doctor = doctors.get(i);
        doctorViewHolder.doctorName.setText(doctor.getName());
        doctorViewHolder.doctorPhone.setText(doctor.getPhone());
        doctorViewHolder.doctorAddress.setText(doctor.getAddress());
        //doctorViewHolder.doctorPhoto.setImageResource(doctor.getPhoto());
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_view_doctor_item, viewGroup, false);
        return new DoctorViewHolder(v);
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder{
        protected TextView doctorName;
        protected TextView doctorPhone;
        protected TextView doctorAddress;
        protected TextView doctorEmail;
        protected TextView doctorLocation;
        protected ImageView doctorPhoto;
        //Constructor
        public DoctorViewHolder(View v){
            super(v);
            doctorName = (TextView) v.findViewById(R.id.doctorName);
            doctorPhone = (TextView) v.findViewById(R.id.doctorPhone);
            doctorAddress = (TextView) v.findViewById(R.id.doctorAddress);
            doctorPhoto = (ImageView) v.findViewById(R.id.doctor_photo);
        }
    }
}
