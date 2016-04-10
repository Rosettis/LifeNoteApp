package capstone.uoit.ca.lifenoteapp.functions.Appointments;

/**
 * Created by Peter on 09/04/16.
 */
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;

        import java.util.List;

        import capstone.uoit.ca.lifenoteapp.R;
        import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
        import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayout;

/**
 * Created by Peter on 18/01/16.
 */
public class AppointmentItemAddapter extends RecyclerView.Adapter<AppointmentItemAddapter.AppointmentViewHolder> {
    public static class AppointmentViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView appointmentName;
        TextView appointmentDate;
        TextView appointmentTime;
        TextView appointmentClinic;
        TextView appointmentDescription;
        NewAppointment appointment;
        OnAppointmentSelectedListener onAppointmentSelected;

        public void setCallBack(OnAppointmentSelectedListener onAppointmentSelected) {
            this.onAppointmentSelected = onAppointmentSelected;
        }

        public interface OnAppointmentSelectedListener{
            void displayAppointment (NewAppointment appointment);
            void deleteAppointment(NewAppointment appointment);
        }


        AppointmentViewHolder(final View itemView, OnAppointmentSelectedListener lsnr) {
            super(itemView);
            setCallBack(lsnr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAppointmentSelected.displayAppointment(appointment);
                }
            });

//            Button deleteBtn = (Button) itemView.findViewById(R.id.btn_deleteNote);
//            deleteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onNoteSelected.deleteNote(note);
//                }
//            });

            cv = (CardView)itemView.findViewById(R.id.CardView_note_item);
            appointmentName = (TextView)itemView.findViewById(R.id.TextView_viewNoteName);
            appointmentDate = (TextView)itemView.findViewById(R.id.TextView_viewNoteDate);
            appointmentTime = (TextView)itemView.findViewById(R.id.TextView_viewNoteTime);
            appointmentClinic = (TextView)itemView.findViewById(R.id.TextView_noteType_list);
            appointmentDescription = (TextView)itemView.findViewById(R.id.TextView_noteDesciption);


            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Button deleteBtn = (Button) v.findViewById(R.id.btn_deleteNote);
//                    deleteBtn.setVisibility(View.VISIBLE);
                    onAppointmentSelected.deleteAppointment(appointment);
                    return true;
                }
            });
        }
    }

    List<NewAppointment> appointments;
    AppointmentViewHolder.OnAppointmentSelectedListener onAppointmentSelectedLsnr;

    AppointmentItemAddapter(List<NewAppointment> appointments, AppointmentViewHolder.OnAppointmentSelectedListener lsnr){
        this.appointments = appointments;
        this.onAppointmentSelectedLsnr = lsnr;
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_appointment_view, viewGroup, false);
        AppointmentViewHolder pvh = new AppointmentViewHolder(v, onAppointmentSelectedLsnr);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder personViewHolder, int i) {
        personViewHolder.appointment = appointments.get(i);
        personViewHolder.appointmentName.setText(appointments.get(i).getName());
        personViewHolder.appointmentDate.setText(appointments.get(i).getDate() + ", ");
        personViewHolder.appointmentTime.setText(appointments.get(i).getTime());
        personViewHolder.appointmentClinic.setText(appointments.get(i).getClinic());


        if (appointments.get(i).getDoc().equals("")) {
            personViewHolder.appointmentDescription.setVisibility(View.GONE);
        } else {
            personViewHolder.appointmentDescription.setText(appointments.get(i).getDoc());
        }

//        NoteLayout layout = notes.get(i).getLayout();
//
//        if(!layout.containsDoctorModule() && layout.containsIllnessModule() && !layout.containsWeightModule()) {
//            personViewHolder.noteDescription.setText(shortenDescription(notes.get(i).getIllName()));
//        } else if (!layout.containsDoctorModule() && !layout.containsIllnessModule() && layout.containsWeightModule()) {
//            personViewHolder.noteDescription.setText(shortenDescription("Weight: " + notes.get(i).getWeight()) + ", Height: " + notes.get(i).getHeight());
//        } else {
//            personViewHolder.noteDescription.setText(shortenDescription(notes.get(i).getDocDetails()));
//        }
//
//        if(personViewHolder.noteDescription.getText().toString().equals("")){
//            personViewHolder.noteDescription.setVisibility(View.GONE);
//        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

