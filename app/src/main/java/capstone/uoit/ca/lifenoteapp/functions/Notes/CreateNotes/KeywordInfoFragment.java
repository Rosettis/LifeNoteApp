package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/01/16.
 */

public class KeywordInfoFragment extends DialogFragment {

    public static KeywordInfoFragment newInstance(String tagName) {
        KeywordInfoFragment newInstance = new KeywordInfoFragment();
        Bundle args = new Bundle();
        args.putString("tag", tagName);
        newInstance.setArguments(args);
        return newInstance;
    }

    NoteFragmentCodify.OnRemoveTagListener onRemoveTag;

    public void setCallBack(NoteFragmentCodify.OnRemoveTagListener onRemove) {
        onRemoveTag = onRemove;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String keyword = getArguments().getString("tag");
        builder.setTitle(keyword.substring(0, 1).toUpperCase() + keyword.substring(1)
        );
        //TODO fix with actual info of Tag
        builder.setMessage("Cancer, also known as a malignant tumor or malignant neoplasm, is a group of diseases involving abnormal cell growth with the potential to invade or spread to other parts of the body. Not all tumors are cancerous; benign tumors do not spread to other parts of the body")
                .setPositiveButton(R.string.btn_okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.btn_removeTag, onRemoveTag);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

