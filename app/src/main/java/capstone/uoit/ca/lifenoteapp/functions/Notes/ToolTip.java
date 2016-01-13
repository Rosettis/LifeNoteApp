package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToolTip.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToolTip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToolTip extends DialogFragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String title;

    private OnFragmentInteractionListener mListener;

    public ToolTip() {
        // Required empty public constructor
    }

    public static ToolTip newInstance(String title) {
        ToolTip fragment = new ToolTip();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tool_tip, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("EEEEEEEEEEEEEEEEEXXXXXXXXXXXXXXXXXXXXXXXXXXXXIIIIIIIIIIIIIIIIIIIIIIIITTTTTTTTTTTTTTTTTTTTTTTTTT");
        getActivity().getFragmentManager().popBackStack();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
