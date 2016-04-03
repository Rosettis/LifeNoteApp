package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

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
public class NoteItemAdaptor extends RecyclerView.Adapter<NoteItemAdaptor.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView noteName;
        TextView noteDate;
        TextView noteTime;
        TextView noteType;
        TextView noteDescription;
        Note note;
        OnNoteSelectedListener onNoteSelected;

        public void setCallBack(OnNoteSelectedListener onNoteSelected) {
            this.onNoteSelected = onNoteSelected;
        }

        public interface OnNoteSelectedListener{
            void displayNote (Note note);
            void deleteNote(Note note);
        }


        NoteViewHolder(final View itemView, OnNoteSelectedListener lsnr) {
            super(itemView);
            setCallBack(lsnr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNoteSelected.displayNote(note);
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
            noteName = (TextView)itemView.findViewById(R.id.TextView_viewNoteName);
            noteDate = (TextView)itemView.findViewById(R.id.TextView_viewNoteDate);
            noteTime = (TextView)itemView.findViewById(R.id.TextView_viewNoteTime);
            noteType = (TextView)itemView.findViewById(R.id.TextView_noteType_list);
            noteDescription = (TextView)itemView.findViewById(R.id.TextView_noteDesciption);


            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Button deleteBtn = (Button) v.findViewById(R.id.btn_deleteNote);
//                    deleteBtn.setVisibility(View.VISIBLE);
                    onNoteSelected.deleteNote(note);
                    return true;
                }
            });
        }
    }

    List<Note> notes;
    NoteViewHolder.OnNoteSelectedListener onNoteSelectedLsnr;

    NoteItemAdaptor(List<Note> notes, NoteViewHolder.OnNoteSelectedListener lsnr){
        this.notes = notes;
        this.onNoteSelectedLsnr = lsnr;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note_view, viewGroup, false);
        NoteViewHolder pvh = new NoteViewHolder(v, onNoteSelectedLsnr);
        return pvh;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder personViewHolder, int i) {
        personViewHolder.note = notes.get(i);
        personViewHolder.noteName.setText(notes.get(i).getName());
        personViewHolder.noteDate.setText(notes.get(i).getDate() + ", ");
        personViewHolder.noteTime.setText(notes.get(i).getTime());
        personViewHolder.noteType.setText(notes.get(i).getLayout().getLayoutName());

        NoteLayout layout = notes.get(i).getLayout();

        if(layout.containsDoctorModule() && !layout.containsIllnessModule()) {
            personViewHolder.noteDescription.setText(shortenDescription(notes.get(i).getDocDetails()));
        } else if (!layout.containsDoctorModule() && layout.containsIllnessModule()) {
            personViewHolder.noteDescription.setText(shortenDescription(notes.get(i).getIllName()));
        } else {
            personViewHolder.noteDescription.setText(shortenDescription(notes.get(i).getDocDetails()));
        }
    }

    private String shortenDescription (String description) {
        if (description.length() > 50) {
            return description.substring(0, 50) + "...";
        } else return description;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
