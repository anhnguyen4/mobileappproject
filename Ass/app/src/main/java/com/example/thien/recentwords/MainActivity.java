package com.example.thien.recentwords;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String SHARED_PREFERENCES_NAME = "recent_words";
    private String KEY_WORD = "key_word_";
    private String COUNT = "count";
    private int ZERO = 0;


    //Variable for saving word to database
    private Button btnSaveWord, btnShowWord, btnClearWord;
    //Variable for input: Keyword and Meaning
    private EditText editKeyword, editMeaning;
    //For SharedPreferences file
    private SharedPreferences recentWords;
    private SharedPreferences.Editor editor;
    //For store all words in json format
    private Gson gson;
    private Word word;
    //Show all saved word in recent word
    private TextView saveWordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSaveWord = findViewById(R.id.btnSaveWord);
        btnShowWord = findViewById(R.id.btnShowWord);
        btnClearWord = findViewById(R.id.btnClearWord);

        btnSaveWord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setSaveWord();
            }

        });

        btnShowWord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getSaveWord();
            }

        });

        btnClearWord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clear();
            }

        });
    }

    /**
     * Function for save a word to database with SharedPreference type.
     * This feature creates a file named: "recent_words.xml" to store all recent words.
    */
    public void setSaveWord() {

        //Variable contain the amount of saved words
        int top;
        //String contain json data
        String data;

        editKeyword = findViewById(R.id.editKeyword);
        editMeaning = findViewById(R.id.editMeaning);

        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();

        word = new Word(editKeyword.getText().toString(), editMeaning.getText().toString());
        gson = new Gson();
        data = gson.toJson(word);

        top = getTop();

        editor.putInt(COUNT, top);
        editor.putString(KEY_WORD + setNum(top) , data);
        editor.apply();
        Toast.makeText(MainActivity.this, "Save success!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Function for getting all saved words and show them on screen
    */
    public void getSaveWord() {

        int pos, length;
        //Contain list all recent saved words-meaning and sorted with time order
        String[][] mListWord;
        //String which show on screen
        StringBuffer result = new StringBuffer("");

        saveWordView = findViewById(R.id.saveWordView);

        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        length = getTop();
        mListWord = new String[2][length];
        Map<String, ?> allEntries = recentWords.getAll();
        for (Map.Entry<String, ?> mEntries : allEntries.entrySet()) {
            if (!mEntries.getKey().equals(COUNT)) {
                gson = new Gson();
                word = gson.fromJson(mEntries.getValue().toString(), Word.class);
                pos = Integer.parseInt(mEntries.getKey().substring(9));
                mListWord[0][pos] = word.getKeyword();
                mListWord[1][pos] = word.getMeaning();
            }
        }

        for (int i = 0;i < length; i++) {
            result.append(i+1).append(". ").append(mListWord[0][i]).append(": ").append(mListWord[1][i]).append("\n");
        }

        saveWordView.setText(result);
    }

    /**
     * Function clear all data on Edit Text.
     * In advanced: remove all data in recent_words.xml
     */
    public void clear() {

        editKeyword = findViewById(R.id.editKeyword);
        editMeaning = findViewById(R.id.editMeaning);
        saveWordView = findViewById(R.id.saveWordView);

        editKeyword.setText("");
        editMeaning.setText("");
        saveWordView.setText("List Word");

        //Clear all data in recent_words.xml
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();
        editor.clear();
        editor.apply();

    }

    /**
     * Function get the amount of recent saved words
     * @return : The amount
     */
    public int getTop() {
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        if (!recentWords.contains(COUNT)) {
            return ZERO;
        } else {
            return recentWords.getInt(COUNT, ZERO) + 1;
        }

    }


    /**
     * Function format the number to specific format string
     * @param num
     * @return : The format string. Example: 00100000
     */
    public String setNum(int num) {

        DecimalFormat pattern = new DecimalFormat( "00000000" );

        return String.valueOf(pattern.format(num));

    }

}
