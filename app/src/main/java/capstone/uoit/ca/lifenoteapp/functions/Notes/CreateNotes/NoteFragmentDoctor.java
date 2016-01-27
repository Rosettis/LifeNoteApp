package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Doctors.Doctor;

/**
 * Created by Peter on 07/01/16.
 */
public class NoteFragmentDoctor extends Fragment {

    //Details//
    LinearLayout layout;
    LinearLayout tagHolder = null;

    ArrayList<TextView> tags = new ArrayList<>();
    ArrayList<String> removedWords = new ArrayList<>();
    BitSet spanSet = new BitSet();


    EditText detailsText;
    TextView prevTextView;
    AutoCompleteTextView textView;

    String prevCheckedText;
    String lastClickedTag;

    static int prevTextViewID;
    int cursorPosition;

    FragmentManager tagFragmentManager;


    public static NoteFragmentDoctor newInstance(String mode) {
        NoteFragmentDoctor newInstance = new NoteFragmentDoctor();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public static NoteFragmentDoctor newInstance(String mode, String doctor, String details) {
        NoteFragmentDoctor newInstance = new NoteFragmentDoctor();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("doctor", doctor);
        bundle.putString("details", details);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public String getDoctor() {
        return textView.getText().toString();
    }

    public String getVisitDetails() {
        return detailsText.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if ("view".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_doctor_create, container, false);

            textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_doctor);
            textView.setText(bundle.getString("doctor"));

            detailsText = (EditText) view.findViewById(R.id.editText_doctorDetails);
            detailsText.setText(bundle.getString("details"));

            layout = (LinearLayout) view.findViewById(R.id.linearLayout_doctorsFields);
            tagFragmentManager = getActivity().getSupportFragmentManager();
            codifyText(detailsText);
            detailsText.setKeyListener(null);


//            TextView textView = (TextView) view.findViewById(R.id.textView_viewDoctor);
//            String doc = bundle.getString("doctor");
//            textView.setText(doc);
            return view;
        } else if ("create".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_doctor_create, container, false);

            textView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_doctor);
            detailsText = (EditText) view.findViewById(R.id.editText_doctorDetails);
            layout = (LinearLayout) view.findViewById(R.id.linearLayout_doctorsFields);
            tagFragmentManager = getActivity().getSupportFragmentManager();

            detailsText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        System.out.println("LastCharacter:" + s.charAt(start + count - 1) + "|");
                        if (!s.toString().equals(prevCheckedText)) { //stop infinite loop
                            prevCheckedText = s.toString();
                            cursorPosition = detailsText.getSelectionStart();
                            char lastCharEnter = s.charAt(start + count - 1);
                            if (lastCharEnter == ' ' || spanSet.get(start + count - 1))
                                spanSet = codifyText(detailsText);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

            });

            String[] doctors = getResources().getStringArray(R.array.doctors_array);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, doctors);
            textView.setAdapter(adapter);
            return view;
        } else {
            System.out.println("Error Invalid mode Entered");
            return null;
        }
    }


    private BitSet codifyText(EditText editText) {
        boolean hasTags = false;
        for (TextView tag : tags) ((ViewGroup) tag.getParent()).removeView(tag);
        tags.clear();
        prevTextView = null;
        final String plainText = editText.getText().toString();
        int prevWhiteSpace = 0;
        final Context context = getActivity();
        SpannableString codifiedText = new SpannableString(plainText.substring(0, plainText.length()));

        ArrayList<String> currWords = new ArrayList<>();
        BitSet spanSet = new BitSet(plainText.length());
        for (int i = 0; i < plainText.length(); i ++){
            if (Character.isWhitespace(plainText.charAt(i))){
                String currWord = plainText.substring(prevWhiteSpace, i).toLowerCase();
                if (isKeyWordInDataBase(currWord, currWords) && !removedWords.contains(currWord)) {
                    if (!currWords.contains(currWord)) {
                        if (!hasTags){
                            tagHolder = addTagHolder(layout);
                            hasTags = true;
                        }
                        tagHolder.addView(addTag(currWord, context));
                        currWords.add(currWord);
                    }
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {

                        }
                    };
                    codifiedText.setSpan(clickableSpan, prevWhiteSpace, i, 0);
                    spanSet.set(prevWhiteSpace, i);
                }
                prevWhiteSpace = i + 1;
            }
        }
        editText.setMovementMethod(LinkMovementMethod.getInstance());
        editText.setText(codifiedText, TextView.BufferType.SPANNABLE);
        editText.setSelection(cursorPosition);
        return spanSet;
    }

    private LinearLayout addTagHolder(LinearLayout container) {
        LinearLayout tagHolder = new LinearLayout(getActivity());
        tagHolder.setOrientation(LinearLayout.HORIZONTAL);
        tagHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        container.addView(tagHolder);
        return tagHolder;
    }

    private TextView addTag(String word, Context context) {
        TextView text = new TextView(context);
        text.setText(word.substring(0, 1).toUpperCase() + word.substring(1));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpFromPx(context, 40), 0, 0, dpFromPx(context, 40));
        text.setLayoutParams(layoutParams);

        int textPadding = dpFromPx(context, 30);
        text.setPadding(textPadding, textPadding, textPadding, textPadding);
        text.setTextColor(Color.WHITE);
        text.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        text.setOnClickListener(new TagClickListener(word));
        prevTextViewID = View.generateViewId();
        text.setId(prevTextViewID);
        prevTextView = text;
        tags.add(text);
        return text;
    }

    //TODO implement method with database
    public boolean isKeyWordInDataBase(String keyword, ArrayList<String> currWords) {
        return (keyword.equals("cancer") || keyword.equals("flu") || keyword.equals("cold"));
    }

    public static int dpFromPx(final Context context, final float px) {
        float pxs =  px / context.getResources().getDisplayMetrics().density;
        return Math.round(pxs);
    }

    OnRemoveTagListener onRemoveTagLsnr = new OnRemoveTagListener();

    public class OnRemoveTagListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            removedWords.add(lastClickedTag);
            codifyText(detailsText);
        }
    }

    private class TagClickListener implements View.OnClickListener {

        String clickedWord;
        public TagClickListener(String clickedWord) {
            this.clickedWord = clickedWord;
        }

        //TODO Implement method with database
        @Override
        public void onClick(View v) {
            DoctorsKeywordInfoFragment fragment = DoctorsKeywordInfoFragment.newInstance(clickedWord);
            lastClickedTag = clickedWord;
            fragment.setCallBack(onRemoveTagLsnr);
            fragment.show(tagFragmentManager, "Keyword Info Fragment");
        }
    }
}
