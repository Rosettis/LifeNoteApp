package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.BitSet;

import capstone.uoit.ca.lifenoteapp.R;

public class CodifyTest extends FragmentActivity implements ToolTip.OnFragmentInteractionListener {
    ArrayList<TextView> tags = new ArrayList<>();
    BitSet spanSet = new BitSet();
    String prevCheckedText;
    TextView prevTextView;
    static int prevTextViewID;
    int cursorPosition;
    FragmentManager fragmentManager = getSupportFragmentManager();
    RelativeLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codify_test);
        final EditText editText = (EditText) findViewById(R.id.editText_codifyTest);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout_codifytest);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    System.out.println("LastCharacter:" + s.charAt(start + count - 1) + "|");
                    if (!s.toString().equals(prevCheckedText)) { //stop infinite loop
                        prevCheckedText = s.toString();
                        cursorPosition = editText.getSelectionStart();
                        char lastCharEnter = s.charAt(start + count - 1);
                        if (lastCharEnter == ' ' || spanSet.get(start + count - 1)) spanSet = codifyText(editText);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * Find and underline keywords
     */
    private BitSet codifyText(EditText editText) {
        for (TextView tag : tags) ((ViewGroup) tag.getParent()).removeView(tag);
        tags.clear();
        prevTextView = null;
        String plainText = editText.getText().toString();
        int prevWhiteSpace = 0;
        final Context context = this;
        SpannableString codifiedText = new SpannableString(plainText.substring(0, plainText.length()));

        ArrayList<String> currWords = new ArrayList<>();
        BitSet spanSet = new BitSet(plainText.length());
        for (int i = 0; i < plainText.length(); i ++){
            if (Character.isWhitespace(plainText.charAt(i))){
                String currWord = plainText.substring(prevWhiteSpace, i).toLowerCase();
                if (isKeyWordInDataBase(currWord, currWords)) {
                    if (!currWords.contains(currWord)) {
                        layout.addView(addTag(currWord, context));
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

    public static int dpFromPx(final Context context, final float px) {
        float pxs =  px / context.getResources().getDisplayMetrics().density;
        return Math.round(pxs);
    }

    private TextView addTag(String word, Context context) {
        TextView text = new TextView(this);
        text.setText(word);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpFromPx(context, 60), 0, 0, 0);


        if (prevTextView == null) layoutParams.addRule(RelativeLayout.BELOW, R.id.editText_codifyTest);
        else {
            layoutParams.addRule(RelativeLayout.END_OF, prevTextViewID);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.editText_codifyTest);
        }

        tags.add(text);
        text.setLayoutParams(layoutParams);

        int textPadding = dpFromPx(context, 30);
        text.setPadding(textPadding, textPadding, textPadding, textPadding);
        text.setTextColor(Color.RED);
        text.setBackgroundColor(Color.parseColor("#666666"));
        text.setOnClickListener(new TagClickListener(word));
        prevTextViewID = View.generateViewId();
        text.setId(prevTextViewID);
        prevTextView = text;
        return text;
    }


    //TODO implement method with database
    public boolean isKeyWordInDataBase(String keyword, ArrayList<String> currWords) {
        return (keyword.equals("cancer") || keyword.equals("flu") || keyword.equals("cold"));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class TagClickListener implements View.OnClickListener {

        String clickedWord;
        public TagClickListener(String clickedWord) {
            this.clickedWord = clickedWord;
        }

        @Override
        public void onClick(View v) {
            ToolTip fragment = new ToolTip();
            Bundle bundle = new Bundle();
            bundle.putString("keyword", clickedWord);
            fragment.setArguments(bundle);
            fragment.show(fragmentManager, "tooltip fragment");
        }
    }
}
