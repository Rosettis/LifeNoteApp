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

import java.util.ArrayList;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/01/16.
 */
public class NoteFragmentCodify extends Fragment{
    LinearLayout layout;
    ArrayList<TextView> tags = new ArrayList<>();
    ArrayList<String> removedWords = new ArrayList<>();
    LinearLayout tagHolder = null;
    BitSet spanSet = new BitSet();
    String prevCheckedText;
    TextView prevTextView;
    static int prevTextViewID;
    FragmentManager fragmentManager;
    int cursorPosition;
    String lastClickedTag;
    EditText editText;

    OnRemoveTagListener onRemoveTagLsnr = new OnRemoveTagListener();

    public class OnRemoveTagListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            removedWords.add(lastClickedTag);
            codifyText(editText);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                    System.out.println("LastCharacter:" + s.charAt(start + count - 1) + "|");
                    if (!s.toString().equals(prevCheckedText)) { //stop infinite loop
                        prevCheckedText = s.toString();
                        cursorPosition = editText.getSelectionStart();
                        char lastCharEnter = s.charAt(start + count - 1);
                        if (lastCharEnter == ' ' || spanSet.get(start + count - 1))
                            spanSet = codifyText(editText);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
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
        text.setBackgroundColor(Color.parseColor("#666666"));
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

    public static int dpFromPx(final Context context, final float px) {
        float pxs =  px / context.getResources().getDisplayMetrics().density;
        return Math.round(pxs);
    }

    //TODO implement method with database
    public boolean isKeyWordInDataBase(String keyword, ArrayList<String> currWords) {
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
            KeywordInfoFragment fragment = KeywordInfoFragment.newInstance(clickedWord);
            lastClickedTag = clickedWord;
            fragment.setCallBack(onRemoveTagLsnr);
            fragment.show(fragmentManager, "Keyword Info Fragment");

//            ToolTip fragment = new ToolTip();
//            Bundle bundle = new Bundle();
//            bundle.putString("keyword", clickedWord);
//            fragment.setArguments(bundle);
//            fragment.show(fragmentManager, "tooltip fragment");
        }
    }
}

