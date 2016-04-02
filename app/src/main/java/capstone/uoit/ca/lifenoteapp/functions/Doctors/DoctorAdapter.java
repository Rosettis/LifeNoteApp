package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>{//BaseAdapter

    DoctorViewHolder.OnDoctorSelectedListener onDoctorSelectedListener;


    public static class DoctorViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView doctorName;
        TextView doctorPhone;
        TextView doctorAddress;
        TextView doctorEmail;
        TextView doctorLocation;
        ImageView doctorPhoto;
        Doctor doctor;
        OnDoctorSelectedListener onDoctorSelected;

        public void setCallBack(OnDoctorSelectedListener onDoctorSelected) {
            this.onDoctorSelected = onDoctorSelected;
        }

        public interface OnDoctorSelectedListener{
            void displayDoctor (Doctor doctor);
        }

        public DoctorViewHolder(View itemView, OnDoctorSelectedListener lsnr) {
            super(itemView);
            setCallBack(lsnr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDoctorSelected.displayDoctor(doctor);
                }
            });
            cv = (CardView)itemView.findViewById(R.id.CardView_note_item);
            doctorName = (TextView)itemView.findViewById(R.id.doctorName);
            doctorPhone = (TextView)itemView.findViewById(R.id.doctorPhone);
            doctorAddress = (TextView)itemView.findViewById(R.id.doctorAddress);
        }
    }

    List<Doctor> doctors;

    DoctorAdapter(List<Doctor> doctors, DoctorAdapter.DoctorViewHolder.OnDoctorSelectedListener lsnr){
        this.doctors = doctors;
        this.onDoctorSelectedListener = lsnr;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_doctor_item, viewGroup, false);
        return new DoctorViewHolder(v, onDoctorSelectedListener);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder personViewHolder, int i) {
        personViewHolder.doctorName.setText(doctors.get(i).getName());
        personViewHolder.doctorPhone.setText(doctors.get(i).getPhone());
        personViewHolder.doctorAddress.setText(doctors.get(i).getAddress());
//        personViewHolder.doctorPhoto.setImageResource(doctors.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

/*  Old Code for file extraction
    public int getCount() {
        return data.size();
    }
    private Context context;
    private ArrayList<Doctor> data;

    public DoctorAdapter(Context context, ArrayList<Doctor> data) {
        this.data = data;
        this.context = context;
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctorToDisplay = data.get(position);

        Log.d("DoctorAdapter", "Doctor:");
        Log.d("DoctorAdapter", "  Name:   "+doctorToDisplay.getName());
        Log.d("DoctorAdapter", "  Phone:  "+doctorToDisplay.getPhone());
        Log.d("DoctorAdapter", "  Address: "+doctorToDisplay.getAddress());
        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_doctor_item, parent, false);
        }

        // populate the views with the data from story
        TextView lblTitle = (TextView)convertView.findViewById(R.id.doctorName);
        lblTitle.setText(doctorToDisplay.getName());

        TextView lblAuthor = (TextView)convertView.findViewById(R.id.doctorPhone);
        lblAuthor.setText(doctorToDisplay.getPhone());

        TextView lblContent = (TextView)convertView.findViewById(R.id.doctorAddress);
        lblContent.setText(doctorToDisplay.getAddress());

        return convertView;
    }*/
}
