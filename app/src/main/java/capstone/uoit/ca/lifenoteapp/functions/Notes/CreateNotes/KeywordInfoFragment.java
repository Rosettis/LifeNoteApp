package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create.DetailsField;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.UMLS.UMLS_Api;

/**
 * Created by Peter on 04/01/16.
 */

public class KeywordInfoFragment extends DialogFragment implements UMLS_Api.OnDefinitionListener {
    private TextView defTextView;
    private View mProgressView;
    private TextView subTitleTextView;


    public static KeywordInfoFragment newInstance(String tagName, String[] apiResults) {
        KeywordInfoFragment newInstance = new KeywordInfoFragment();
        Bundle args = new Bundle();
        args.putString("tag", tagName);
        args.putString("term", apiResults[0]);
        args.putString("cui", apiResults[1]);
        newInstance.setArguments(args);
        return newInstance;
    }

    DetailsField.OnRemoveTagListener onRemoveTag;

    public void setCallBack(DetailsField.OnRemoveTagListener onRemove) {
        onRemoveTag = onRemove;
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        final String keyword = getArguments().getString("tag");
//
//
//        builder.setTitle(keyword.substring(0, 1).toUpperCase() + keyword.substring(1)
//        );
//        //TODO fix with actual info of Tag
//        builder.setMessage("Cancer, also known as a malignant tumor or malignant neoplasm, is a group of diseases involving abnormal cell growth with the potential to invade or spread to other parts of the body. Not all tumors are cancerous; benign tumors do not spread to other parts of the body")
//                .setPositiveButton(R.string.btn_okay, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // FIRE ZE MISSILES!
//                    }
//                })
//                .setNegativeButton(R.string.btn_removeTag, onRemoveTag);
//        // Create the AlertDialog object and return it
//        return builder.create();
//    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View customView = inflater.inflate(R.layout.dialog_keyword_info, null);
        builder.setView(customView);

        final String keyword = getArguments().getString("tag");
        final String term = getArguments().getString("term");
        final String cui = getArguments().getString("cui");

        mProgressView = customView.findViewById(R.id.api_progress);
        TextView titleTextView = (TextView) customView.findViewById(R.id.TextView_keywordInfo_resultTitle);
        subTitleTextView  = (TextView) customView.findViewById(R.id.TextView_keywordInfo_resultTerm);
        defTextView = (TextView) customView.findViewById((R.id.TextView_keywordInfo_resultDefinition));
        defTextView.setMovementMethod(new ScrollingMovementMethod());

        if (keyword != null) titleTextView.setText(keyword.substring(0,1).toUpperCase() + keyword.substring(1));
        subTitleTextView.setText(term);

        showProgress(true);
        UMLS_Api apiClient = UMLS_Api.getInstance();

        System.out.print("YEAHHHHHHH");
        if (term != null && cui != null) {
            apiClient.getDefinition(getContext(), cui, this);
        } else {
            System.out.println("ERROR API RESULTS ARE NULL");
        }

        builder.setPositiveButton(R.string.btn_okay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //exit
            }
        })
                .setNegativeButton(R.string.btn_removeTag, onRemoveTag);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            subTitleTextView.setVisibility(show ? View.GONE : View.VISIBLE);
            defTextView.setVisibility(show ? View.GONE : View.VISIBLE);
            defTextView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    defTextView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            defTextView.setVisibility(show ? View.GONE : View.VISIBLE);
            subTitleTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onDefinitionResponse(String definition) {
        this.defTextView.setText(Html.fromHtml(definition));
        this.defTextView.setMovementMethod(LinkMovementMethod.getInstance());
        if (isAdded()) showProgress(false);
    }
}

