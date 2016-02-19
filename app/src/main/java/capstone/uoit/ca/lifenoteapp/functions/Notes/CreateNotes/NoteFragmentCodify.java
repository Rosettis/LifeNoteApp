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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/01/16.
 */
public class NoteFragmentCodify extends Fragment implements UMLS_Api.OnTermListener {
    LinearLayout layout;
    ArrayList<TextView> tags = new ArrayList<>();
    ArrayList<String> removedWords = new ArrayList<>();
    ArrayList<String> taggedWords = new ArrayList<>();
    ArrayList<String> checkedWords = new ArrayList<>();
    Map<String, String[]> cuiMap = new HashMap();
    LinearLayout tagHolder = null;
    BitSet spanSet = new BitSet();
    String prevCheckedText;
    TextView prevTextView;
    static int prevTextViewID;
    FragmentManager fragmentManager;
    int cursorPosition;
    String lastClickedTag;
    TextView lastClickedView;
    EditText editText;
    boolean viewMode = false;

    public static NoteFragmentCodify newInstance(String mode, String details) {
        NoteFragmentCodify newInstance = new NoteFragmentCodify();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("details", details);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    public static NoteFragmentCodify newInstance(String mode) {
        NoteFragmentCodify newInstance = new NoteFragmentCodify();
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        newInstance.setArguments(bundle);
        return newInstance;
    }


    OnRemoveTagListener onRemoveTagLsnr = new OnRemoveTagListener();

    public class OnRemoveTagListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            removedWords.add(lastClickedTag.toLowerCase());
            tags.remove(lastClickedView);
            updateTags();
        }
    }

    public String getDetails() {
        return editText.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        if ("view".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_codify, container, false);
            editText = (EditText) view.findViewById(R.id.editText_codifyDetails);
//            editText.setKeyListener(null);
            editText.setText(bundle.getString("details") + " ");
            layout = (LinearLayout) view.findViewById(R.id.linearLayout_codifyfragmentLayout);
            fragmentManager = getActivity().getSupportFragmentManager();
            codifyText(editText);
            editText.setKeyListener(null);
            return view;
        } else if ("create".equals(bundle.getString("mode"))) {
            View view = inflater.inflate(R.layout.fragment_note_codify, container, false);
            editText = (EditText) view.findViewById(R.id.editText_codifyDetails);
            layout = (LinearLayout) view.findViewById(R.id.linearLayout_codifyfragmentLayout);
            fragmentManager = getActivity().getSupportFragmentManager();
            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        if (!s.toString().equals(prevCheckedText)) { //stop infinite loop
                            prevCheckedText = s.toString();
                            cursorPosition = editText.getSelectionStart();
                            char lastCharEnter = s.charAt(start + count - 1);
                            if (spanSet.get(start + count - 1)) resetTags();
                            if (lastCharEnter == ' ' || (start + count - 1) != editText.length() - 1)
                                codifyText(editText);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return view;
        } else {
            System.out.println("Error Invalid mod Entered");
            return null;
        }
    }

    public void codifyText(EditText editText) {
        int prevWhiteSpace = 0;
        final String plainText = editText.getText().toString();
        for (int i = 0; i < plainText.length(); i++) {
            if (Character.isWhitespace(plainText.charAt(i))) {
                String word = plainText.substring(prevWhiteSpace, i).toLowerCase();
                if (!(taggedWords.contains(word) || removedWords.contains(word) || checkedWords.contains(word))) {
                    checkedWords.add(word);
                    UMLS_Api apiClient = UMLS_Api.getInstance();
                    apiClient.getTerm(getContext(), word, this, prevWhiteSpace, i);
                }
                prevWhiteSpace = i + 1;
            }
        }
    }

    @Override
    public void onTermResonse(String term, String name, String cui, int start, int end) {
        if (!name.equals("NO RESULTS")) {
            if (!taggedWords.contains(term) && !removedWords.contains(term)) {
                taggedWords.add(term);
                if (tagHolder == null) {
                    tagHolder = addTagHolder(layout);
                }
                spanSet.set(start, end);
                String tagName = term.substring(0,1).toUpperCase()+ term.substring(1);
                tagHolder.addView(addTag(term, getContext()));
                cuiMap.put(term.toLowerCase(), new String[] {name, cui});
            }
        }
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

    private void updateTags() {
        tagHolder.removeAllViews();
        for (TextView tag : tags) {
            tagHolder.addView(tag);
        }
    }

    private void resetTags() {
        tagHolder.removeAllViews();
        checkedWords.clear();
        taggedWords.clear();
        tags.clear();
    }

    public static int dpFromPx(final Context context, final float px) {
        float pxs =  px / context.getResources().getDisplayMetrics().density;
        return Math.round(pxs);
    }

    //TODO implement method with database
    public boolean isKeyWordInDataBase(String keyword) {
        return (keyword.equals("cancer") || keyword.equals("flu") || keyword.equals("cold"));
    }

    private class TagClickListener implements View.OnClickListener {

        String clickedWord;
        public TagClickListener(String clickedWord) {
            this.clickedWord = clickedWord;
        }

        //TODO Implement method with database
        @Override
        public void onClick(View v) {
            KeywordInfoFragment fragment = KeywordInfoFragment.newInstance(clickedWord, cuiMap.get(clickedWord.toLowerCase()));
            lastClickedTag = clickedWord;
            lastClickedView = (TextView) v;
            fragment.setCallBack(onRemoveTagLsnr);
            fragment.show(fragmentManager, "Keyword Info Fragment");
        }
    }
}

