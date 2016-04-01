package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.KeywordInfoFragment;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.UMLS.UMLS_Api;

/**
 * Created by Peter on 05/03/16.
 */
public class DetailsField extends LinearLayout implements UMLS_Api.OnTermListener {
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
    String fieldText = "";
    FragmentManager fragmentManager;
    int cursorPosition;
    String lastClickedTag;
    TextView lastClickedView;
    EditText editText;
    TextView textView;
    String mode;
    String label = "";

    public DetailsField(Context context, String fieldText, String mode) {
        super(context);
        this.mode = mode;
        this.fieldText = fieldText;
        initializeViews(context);
    }

    public DetailsField(Context context, String fieldText, String label, String mode) {
        super(context);
        this.mode = mode;
        this.fieldText = fieldText;
        this.label = label;
        initializeViews(context);
    }

    public DetailsField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public ArrayList<String> getTaggedWords() {
        return taggedWords;
    }

    public String getCodifiedText() {
        return editText.getText().toString();
    }

    public DetailsField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        if (mode.equals("create")) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.custom_codify_details_field, this);

            editText = (EditText) this.findViewById(R.id.editText_details);
            editText.setHint(fieldText);
            layout = (LinearLayout) this.findViewById(R.id.linearLayout_detailsFieldContainer);
            tagHolder = (LinearLayout) this.findViewById(R.id.linearLayout_tagContainer2);
            fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
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

            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        System.out.println("BALLLLSLSLSLLSLSLSLSLS");
                        codifyText((TextView) v);
                    }
                }
            });
//        } else if (mode.equals("view")) {
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            inflater.inflate(R.layout.custom_codify_details_field_view, this);
//
//            TextView labelTextView = (TextView) this.findViewById(R.id.TextView_details_label);
//            labelTextView.setText(label);
//            textView = (TextView) this.findViewById(R.id.editText_details_view);
//            textView.setText(fieldText);
//            layout = (LinearLayout) this.findViewById(R.id.linearLayout_detailsFieldContainer);
//            tagHolder = (LinearLayout) this.findViewById(R.id.linearLayout_tagContainer2);
//
//            fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
//            codifyText(textView);
        } else if (mode.equals("edit")) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.custom_codify_details_field_view, this);

            editText = (EditText) this.findViewById(R.id.editText_details);
            editText.setText(fieldText);

            TextView labelTextView = (TextView) this.findViewById(R.id.TextView_label_details);
            labelTextView.setText(label);

            layout = (LinearLayout) this.findViewById(R.id.linearLayout_detailsFieldContainer);
            tagHolder = (LinearLayout) this.findViewById(R.id.linearLayout_tagContainer2);

            fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
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
            codifyText(editText);

            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        System.out.println("BALLLLSLSLSLLSLSLSLSLS");
                        codifyText((TextView) v);
                    }
                }
            });
        } else {
            Log.e("DetailsField", "initializeViews: Error invalid parameter" + mode);
        }
    }

    public void codifyText(TextView field) {
        if (field.getText().toString().length() > 0){
            int prevWhiteSpace = 0;
            final String plainText;
            if (field.getText().toString().charAt(field.getText().toString().length() - 1) != ' ') plainText = field.getText().toString() + " ";
            else plainText = field.getText().toString();
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
    }

    @Override
    public void onTermResonse(String term, String name, String cui, int start, int end) {
        if (!name.equals("NO RESULTS")) {
            if (!taggedWords.contains(term) && !removedWords.contains(term) && taggedWords.size() < 5) {
                taggedWords.add(term);
//                if (tagHolder == null) {
//                    tagHolder = addTagHolder(layout);
//                }
                spanSet.set(start, end);
//                String tagName = term.substring(0,1).toUpperCase()+ term.substring(1);
                tagHolder.addView(addTag(term, getContext()));
                cuiMap.put(term.toLowerCase(), new String[] {name, cui});
            }
        }
    }

    private LinearLayout addTagHolder(LinearLayout container) {
        LinearLayout tagHolder = new LinearLayout(getContext());
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

    OnRemoveTagListener onRemoveTagLsnr = new OnRemoveTagListener();

    public class OnRemoveTagListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            removedWords.add(lastClickedTag.toLowerCase());
            tags.remove(lastClickedView);
            updateTags();
        }
    }
}
