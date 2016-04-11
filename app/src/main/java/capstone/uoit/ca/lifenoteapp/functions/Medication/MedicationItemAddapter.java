package capstone.uoit.ca.lifenoteapp.functions.Medication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 09/04/16.
 */

public class MedicationItemAddapter extends RecyclerView.Adapter<MedicationItemAddapter.MedicationViewHolder> {
    public static class MedicationViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView appointmentName;
        TextView appointmentDate;
        TextView appointmentTime;
        TextView appointmentClinic;
        TextView appointmentDescription;
        Medication medication;
        OnMedicationSelectedListener onMedicationSelected;

        public void setCallBack(OnMedicationSelectedListener onMedicationSelected) {
            this.onMedicationSelected = onMedicationSelected;
        }

        public interface OnMedicationSelectedListener{
            void displayMedication (Medication medication);
            void deleteMedication(Medication medication);
        }


        MedicationViewHolder(final View itemView, OnMedicationSelectedListener lsnr) {
            super(itemView);
            setCallBack(lsnr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMedicationSelected.displayMedication(medication);
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
                    onMedicationSelected.deleteMedication(medication);
                    return true;
                }
            });
        }
    }

    List<Medication> medications;
    MedicationViewHolder.OnMedicationSelectedListener onMedicationSelectedLsnr;

    MedicationItemAddapter(List<Medication> medications, MedicationViewHolder.OnMedicationSelectedListener lsnr){
        this.medications = medications;
        this.onMedicationSelectedLsnr = lsnr;
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_medication_view, viewGroup, false);
        MedicationViewHolder pvh = new MedicationViewHolder(v, onMedicationSelectedLsnr);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MedicationViewHolder personViewHolder, int i) {
        personViewHolder.medication = medications.get(i);
        personViewHolder.appointmentName.setText(medications.get(i).getName());
        personViewHolder.appointmentDate.setText("Start: " + medications.get(i).getStart());
        personViewHolder.appointmentClinic.setText("Repeats: " + medications.get(i).getRepeats());

        if (medications.get(i).getOften().equals("")) {
            personViewHolder.appointmentDescription.setVisibility(View.GONE);
        } else {
            personViewHolder.appointmentDescription.setText(medications.get(i).getOften());
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


