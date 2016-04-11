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
import java.util.Locale;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {//BaseAdapter
    static DoctorClickListener lsnr;
    static DoctorsFragment doctorsFragment;
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
        return new DoctorViewHolder(v,doctors);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder doctorViewHolder, int i) {
        Doctor doctor = doctors.get(i);
        doctorViewHolder.doctorName.setText(doctor.getName());
        doctorViewHolder.doctorPhone.setText(doctor.getPhone());
        doctorViewHolder.doctorTitle.setText(doctor.getTitle());
        doctorViewHolder.doctorId.setText(Long.toString(doctor.getId()));
        doctorViewHolder.doctor = doctor;
        //doctorViewHolder.doctorPhoto.setImageResource(doctor.getPhoto());
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        public Doctor doctor;
        List<Doctor> doctors = new ArrayList<>();
        CardView card_view;
        protected TextView doctorName;
        protected TextView doctorPhone;
        protected TextView doctorAddress;
        protected TextView doctorEmail;
        protected TextView doctorTitle;
        protected TextView doctorLocation;
        protected TextView doctorId;
        protected ImageView doctorPhoto;
        //Constructor
        public DoctorViewHolder(View v, List<Doctor> doctors){
            super(v);
            this.doctors = doctors;
            doctorName = (TextView) v.findViewById(R.id.doctorName);
            doctorPhone = (TextView) v.findViewById(R.id.doctorPhone);
            doctorTitle = (TextView) v.findViewById(R.id.doctorTitle);
            doctorId = (TextView) v.findViewById(R.id.doctor_id);
            doctorPhoto = (ImageView) v.findViewById(R.id.doctor_photo);

            card_view = (CardView) v.findViewById(R.id.CardView_doctor_item);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView idText = (TextView) view.findViewById(R.id.doctor_id);
                    Long docID = Long.parseLong(idText.getText().toString());
                    Log.d("id", Long.toString(docID));
                    doctorsFragment.viewDoctor(docID);
                }
            });

            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lsnr.viewDoctor(doctor);
                    Log.d("check", "Spahgett");
*//*                    ViewDoctor fragment = ViewDoctor.newInstance(doctor.getId());
                    FragmentManager fragmentManager = parent.getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content, fragment)
                            .addToBackStack(null)
                            .commit();*//*
                }
            });*/
        }


    }
}
