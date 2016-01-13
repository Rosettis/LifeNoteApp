package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

/**
 * Created by Peter on 11/01/16.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/01/16.
 */

public class NewLayoutFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    BitSet selectedSet = new BitSet();


    public static NewLayoutFragment newInstance(ArrayList<String> fragmentNames) {
        NewLayoutFragment newInstance = new NewLayoutFragment();
        Bundle args = new Bundle();
        args.putSerializable("fragmentNames", fragmentNames);
        newInstance.setArguments(args);
        return newInstance;
    }

    NewLayoutFragment.OnSectionDoneListener onSelectionDone;

    public void setCallBack(NewLayoutFragment.OnSectionDoneListener onRemove) {
        onSelectionDone = onRemove;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (selectedSet.get(position)) {
            selectedSet.clear(position);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else {
            selectedSet.set(position);
            view.setBackgroundColor(Color.parseColor("#888888"));
        }
    }

    public interface OnSectionDoneListener{
        void createLayout(BitSet selected);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select new note entries");

        builder.setPositiveButton(R.string.btn_saveLayout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onSelectionDone.createLayout(selectedSet);
                    }
                })
                .setNegativeButton(R.string.btn_okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });



        ArrayList<String> fragmentNames = (ArrayList<String>) getArguments().getSerializable("fragmentNames");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom, null);
        builder.setView(convertView);
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, fragmentNames);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        return builder.create();
    }
}


