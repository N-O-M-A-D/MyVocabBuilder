package com.example.tom.myvocabbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    int wordNumber = 0;
    int i;
    int j;

    List<List<List>> indexGermanToEnglishJava = new ArrayList<List<List>>();
    List<String> englishWords = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateIndex(indexGermanToEnglishJava);
        generateEnglishWords(englishWords);

        setContentView(R.layout.activity_main);
        displayNextWord(wordNumber);

    }




    public void populateIndex(List<List<List>> structure) {
/**
 * Take the resource xml array and convert to an array of strings
 */
        String[] myResArray = getApplicationContext().getResources().getStringArray(R.array.index);
        /**
         * Convert to List of Strings
         */
        List<String> inputWordsArray = Arrays.asList(myResArray);

        Log.w("MainActivity.java", "THE ResArray SIZE IS " + String.valueOf(myResArray.length));
        Log.w("MainActivity.java", "THE inputWordsArray SIZE IS " + String.valueOf(inputWordsArray.size()));

        for (j=0; j < inputWordsArray.size(); j++) {
            /**
             * for each German word generate a list called "arrayWordsOut". The method to do this
             * initialArrayWords) takes the entry number (j) and the List of Strings as its input
             *The returned arrayWordsOut is a list of 2 lists. The first (sub) list has a single
             * member: the De Word. The second list is a list of all the En translations.
             */
            List<List> arrayWordsOut = new ArrayList<List>(initialArrayWords(j, inputWordsArray));

            /**
             * add to input structure
             */
            structure.add(new ArrayList(arrayWordsOut));
        }

    }

    public void generateEnglishWords(List<String> currentList) {}


    public List<List> initialArrayWords(int lineNumber, List<String> inputWordsArray) {

        List<List> arrayWords = new ArrayList<List>();

        /**
         *This splits the list of words into individual words, each of which is accompanied by two
         * numbers (for DE words) or one number (for En words).
         */
        List<String> entries = new ArrayList<String>(Arrays.asList(inputWordsArray.get(lineNumber).split("`")));
        int entrySize = entries.size();
        Log.w("MainActivity.java", "THE ENTRY SIZE IS " + entrySize);

        List<String> deWord = new ArrayList<String>();
        List<String> enWords = new ArrayList<String>();
        /**
         * The ~ symbol delineates the word and number(s) in each entry
         */
        List<String> entryDe = new ArrayList<String>(Arrays.asList(entries.get(0).split("~")));
        deWord.add(entryDe.get(0));
        /**
         * Make list of En words
         */
        for (i=1; i < entrySize; i++) {
            List<String> entry = new ArrayList<String>(Arrays.asList(entries.get(i).split("~")));
            enWords.add(new String(entry.get(0)));
        }

        /**
         * The De word list has only one entry
         */
        arrayWords.add(new ArrayList(deWord));
        arrayWords.add(new ArrayList(enWords));

        return arrayWords;
    }

    /**
     * Can get rid of eventually, for now just used to cycle through words
     */
    public void nextWord(View v) {
        wordNumber = wordNumber + 1;
        displayNextWord(wordNumber);

    }

    public void displayNextWord(int number) {
        TextView germanWord = (TextView) findViewById(R.id.german_word);
        germanWord.setText(String.valueOf(indexGermanToEnglishJava.get(number).get(0).get(0)));

        int entrySize = indexGermanToEnglishJava.get(number).get(1).size();
        Log.w("MainActivity.java", "THE Translation ENTRY SIZE IS " + entrySize);
        int random = (int) (Math.random() * entrySize);
        Log.w("MainActivity.java", "THE chosen entry IS " + random);

        List<String> choices = new ArrayList<String>();

        choices.add(new String(String.valueOf(indexGermanToEnglishJava.get(number).get(1).get(random))));

        choices.add(new String(String.valueOf(indexGermanToEnglishJava.get(number).get(1).get(random))));


        displayTranslations(choices);
        Log.w("MainActivity.java", "Point 5");

        /**translationChoices.get(0).setText(String.valueOf(index.get(number).get(1).get(random)));
         *
         */
    }


    public void displayTranslations(List<String> choices) {

        TextView translationChoice = (TextView) findViewById(R.id.button_1);
        translationChoice.setText(String.valueOf(choices.get(0)));
        translationChoice = (TextView) findViewById(R.id.button_2);
        translationChoice.setText(String.valueOf(choices.get(1)));
    }

}