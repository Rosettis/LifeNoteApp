package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.content.Context;
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

        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item_row, parent, false);
        }

        // populate the views with the data from note
        TextView lblTitle = (TextView)convertView.findViewById(R.id.lbl_noteName);
        lblTitle.setText(noteToDisplay.getName());

        TextView lblContent = (TextView)convertView.findViewById(R.id.noteDateMod);
        lblContent.setText(noteToDisplay.getDateAsString());

        TextView lblDescription = (TextView)convertView.findViewById(R.id.lbl_noteDescription);
        lblDescription.setText(noteToDisplay.getDescription());
        return convertView;
    }
}
