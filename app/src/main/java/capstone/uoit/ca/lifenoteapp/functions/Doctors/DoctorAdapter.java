package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {//BaseAdapter
    static DoctorClickListener lsnr;
    DoctorsFragment doctorsFragment;
    private List<Doctor> doctors = new ArrayList<>();

    DoctorAdapter(DoctorsFragment doctorsFragment, List<Doctor> doctors){
        this.doctorsFragment = doctorsFragment;
        this.doctors = doctors;
    }

    public void setCallBack(DoctorClickListener onRemove) {
        lsnr = onRemove;
    }


    @Override
    public int getItemCount() {
        return doctors.size();
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_view_doctor_item, viewGroup, false);
        return new DoctorViewHolder(doctorsFragment,v);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder doctorViewHolder, int i) {
        Doctor doctor = doctors.get(i);
        doctorViewHolder.doctorName.setText(doctor.getName());
        doctorViewHolder.doctorPhone.setText(doctor.getPhone());
        doctorViewHolder.doctorTitle.setText(doctor.getTitle());
        doctorViewHolder.doctor = doctor;
        //doctorViewHolder.doctorPhoto.setImageResource(doctor.getPhoto());
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder{
        public Doctor doctor;
        protected TextView doctorName;
        protected TextView doctorPhone;
        protected TextView doctorAddress;
        protected TextView doctorEmail;
        protected TextView doctorTitle;
        protected TextView doctorLocation;
        protected ImageView doctorPhoto;
        //Constructor
        public DoctorViewHolder(final DoctorsFragment parent,View v){
            super(v);
            doctorName = (TextView) v.findViewById(R.id.doctorName);
            doctorPhone = (TextView) v.findViewById(R.id.doctorPhone);
            doctorTitle = (TextView) v.findViewById(R.id.doctorTitle);
            doctorPhoto = (ImageView) v.findViewById(R.id.doctor_photo);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    lsnr.viewDoctor(doctor);
//                    Log.d("check", "Spahgett");
                    ViewDoctor fragment = new ViewDoctor();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("doctor", doctor);
                    Log.d("check", "Spahgett");
                    //fragment.setArguments(bundle);
                    FragmentManager fragmentManager = parent.getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}
