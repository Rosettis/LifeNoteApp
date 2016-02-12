package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 08/02/16.
 */
public class RestApiTest extends Fragment implements UMLS_Api.onResponseListener, View.OnClickListener{
    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_test_test_api, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.textView_testApi_input);
        Button getTicketButton = (Button) rootView.findViewById(R.id.btn_codify);
        getTicketButton.setOnClickListener(this);
//        final TextView mTextView2 = (TextView) rootView.findViewById(R.id.textView_testApi_input2);

//        UMLSTicketClient UMLSClient = UMLSTicketClient.getInstance();
//        mTextView.setText(UMLSClient.getTGT(getContext()));
//        mTextView2.setText(UMLSClient.getTicket(getContext()));
        return rootView;
    }

    @Override
    public void onResonse(String response) {
        mTextView.setText(response);
    }

    @Override
    public void onClick(View v) {
        UMLS_Api apiClient = UMLS_Api.getInstance();
        apiClient.getApiTicket(getContext(), this);

    }
}
