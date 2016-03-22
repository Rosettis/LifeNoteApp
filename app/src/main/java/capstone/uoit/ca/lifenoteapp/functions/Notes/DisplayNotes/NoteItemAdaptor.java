package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;

/**
 * Created by Peter on 18/01/16.
 */
public class NoteItemAdaptor extends RecyclerView.Adapter<NoteItemAdaptor.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView noteName;
        TextView noteDate;
        TextView noteTime;
        Note note;
        OnNoteSelectedListener onNoteSelected;

        public void setCallBack(OnNoteSelectedListener onNoteSelected) {
            this.onNoteSelected = onNoteSelected;
        }

        public interface OnNoteSelectedListener{
            void displayNote (Note note);
        }


        NoteViewHolder(View itemView, OnNoteSelectedListener lsnr) {
            super(itemView);
            setCallBack(lsnr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNoteSelected.displayNote(note);
                }
            });
            cv = (CardView)itemView.findViewById(R.id.CardView_note_item);
            noteName = (TextView)itemView.findViewById(R.id.TextView_viewNoteName);
            noteDate = (TextView)itemView.findViewById(R.id.TextView_viewNoteDate);
            noteTime = (TextView)itemView.findViewById(R.id.TextView_viewNoteTime);
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
        personViewHolder.noteDate.setText(notes.get(i).getDate());
        personViewHolder.noteTime.setText(notes.get(i).getTime());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
