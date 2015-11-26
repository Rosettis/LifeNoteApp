package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class NoteAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Note> data;

    public NoteAdapter(Context context, ArrayList<Note> data) {
        this.data = data;
        this.context = context;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Note noteToDisplay = data.get(position);

        Log.d("NoteAdapter", "Note:");
        Log.d("NoteAdapter", "  Name:   "+noteToDisplay.getName());
        Log.d("NoteAdapter", "  Date Modified:  "+noteToDisplay.getDateModifiedAsString());
        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_doctor_item, parent, false);
        }

        // populate the views with the data from note
        TextView lblTitle = (TextView)convertView.findViewById(R.id.noteName);
        lblTitle.setText(noteToDisplay.getName());

        TextView lblContent = (TextView)convertView.findViewById(R.id.noteDateMod);
        lblContent.setText(noteToDisplay.getDateModifiedAsString());

        return convertView;
    }
}
