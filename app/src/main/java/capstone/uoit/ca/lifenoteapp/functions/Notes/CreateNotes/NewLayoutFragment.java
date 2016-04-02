package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

/**
 * Created by Peter on 11/01/16.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/01/16.
 */

public class NewLayoutFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    BitSet selectedSet = new BitSet();
    EditText layoutNameEditText;


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
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public interface OnSectionDoneListener{
        void createLayout(BitSet selected, String layoutName);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select new Note entries");

        builder.setPositiveButton(R.string.btn_saveLayout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //overridden in onstart()
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.setCancelable(false);
        ArrayList<String> fragmentNames = (ArrayList<String>) getArguments().getSerializable("fragmentNames");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_create_note_layout, null);
        builder.setView(convertView);

        layoutNameEditText = (EditText) convertView.findViewById(R.id.editText_enterLayoutTitle);
        layoutNameEditText.requestFocus();


        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, fragmentNames);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        final Dialog built = builder.create();
        layoutNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    built.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        return built;
    }

    @Override
    public void onStart()
    {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    boolean hasSomeSelected = false;
                    for (int i = 0; i < selectedSet.length(); i ++) {
                        if (selectedSet.get(i)) {
                            hasSomeSelected = true;
                            break;
                        }
                    }
                    if (hasSomeSelected && !layoutNameEditText.getText().toString().equals("")){
                        onSelectionDone.createLayout(selectedSet, layoutNameEditText.getText().toString());
                        getDialog().dismiss();
                    } else {
                        if (!hasSomeSelected && layoutNameEditText.getText().toString().equals("")){
                            Toast.makeText(getContext(),
                                    "Please enter the name of the layout and select at least 1 field",
                                    Toast.LENGTH_LONG).show();
                        } else if (!hasSomeSelected) {
                            Toast.makeText(getContext(),
                                    "Please select at least 1 field",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(),
                                    "Please enter the name of the layout",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

}




