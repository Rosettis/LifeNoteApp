package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 18/01/16.
 */
public class NoteItemAdaptor extends RecyclerView.Adapter<NoteItemAdaptor.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView noteName;
        TextView noteDetails;

        NoteViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.CardView_note_item);
            noteName = (TextView)itemView.findViewById(R.id.TextView_viewNoteName);
            noteDetails = (TextView)itemView.findViewById(R.id.TextView_NoteDetails);
        }
    }

    List<Note> persons;

    NoteItemAdaptor(List<Note> persons){
        this.persons = persons;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_notes_item, viewGroup, false);
        NoteViewHolder pvh = new NoteViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder personViewHolder, int i) {
        personViewHolder.noteName.setText(persons.get(i).getTitle());
//        personViewHolder.noteDetails.setText(persons.get(i).getDetails());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
