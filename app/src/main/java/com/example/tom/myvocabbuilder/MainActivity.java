package com.example.tom.myvocabbuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    int k;
    int noOfEnglishWords = 0;
    int noOfButtons = 8;

    List<List<List>> indexGermanToEnglishJava = new ArrayList<List<List>>();
    List<String> englishWords = new ArrayList<String>();
    List<TextView> translationButtons = new ArrayList<TextView>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateIndex(indexGermanToEnglishJava);


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

    public void indexTranslationButtons(List<TextView> translationButtons) {
        translationButtons.add((TextView) findViewById(R.id.button_1));
        translationButtons.add((TextView) findViewById(R.id.button_2));
        translationButtons.add((TextView) findViewById(R.id.button_3));
        translationButtons.add((TextView) findViewById(R.id.button_4));
        translationButtons.add((TextView) findViewById(R.id.button_5));
        translationButtons.add((TextView) findViewById(R.id.button_6));
        translationButtons.add((TextView) findViewById(R.id.button_7));
        translationButtons.add((TextView) findViewById(R.id.button_8));
    }


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
        List<Integer> indexNoEnWords = new ArrayList<Integer>();
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
            englishWords.add(new String(entry.get(0)));

        }
        Log.w("MainActivity.java", "i= " + i);
        /**
         * Note that the entry includes the German word, so the size of the entry is one more than
         * the number of English words
         */
        indexNoEnWords.add(noOfEnglishWords);
        noOfEnglishWords = noOfEnglishWords + i - 1;
        indexNoEnWords.add(noOfEnglishWords);

        Log.w("MainActivity.java", "Number of English Words = " + noOfEnglishWords);
        Log.w("MainActivity.java", "Number of English Words = " + englishWords.size());

        /**
         * The De word list has only one entry
         */
        arrayWords.add(new ArrayList(deWord));
        arrayWords.add(new ArrayList(enWords));
        arrayWords.add(new ArrayList(indexNoEnWords));

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

        int entrySizeEn = indexGermanToEnglishJava.get(number).get(1).size();
        Log.w("MainActivity.java", "THE Translation ENTRY SIZE IS " + entrySizeEn);
        int noOfTranslationsToShow = Math.min(entrySizeEn, 3);

        List<Integer> indexNoEnWords = indexGermanToEnglishJava.get(number).get(2);

        Integer entryStartEnWords = indexNoEnWords.get(0);

        List<String> choices = new ArrayList<String>();

        while (choices.size() < noOfTranslationsToShow) {

            int random = (int) (Math.random() * entrySizeEn);
            Log.w("MainActivity.java", "THE chosen entry IS " + random);
            String nextWord = new String(String.valueOf(indexGermanToEnglishJava.get(number).get(1).get(random)));
            if(!choices.contains(nextWord))
                choices.add(nextWord);

        }

        while (choices.size() < noOfButtons) {
            int random = (int) (Math.random() * (englishWords.size() - entrySizeEn));
            Log.w("MainActivity.java", "THE chosen entry number IS " + random);
            if(random >= entryStartEnWords)
                random = random + entrySizeEn;
            Log.w("MainActivity.java", "THE chosen entry IS " + random);

            choices.add(new String(String.valueOf(englishWords.get(random))));
        }

        /**
         *Durstenfeld's version to randomize
         */
        int[] randomizeOrder = new int[noOfButtons];

        for (i=0; i < noOfButtons; i++) {
            randomizeOrder[i] = i;
        }

        for (i = noOfButtons - 1; i > 0; i--) {
            j = (int) (Math.random() * noOfButtons);
            k = randomizeOrder[i];
            randomizeOrder[i] = randomizeOrder[j];
            randomizeOrder[j] = k;
        }

        displayTranslations(choices, randomizeOrder);
        Log.w("MainActivity.java", "Point 5");

    }


    public void displayTranslations(List<String> choices, int[] randomizeOrder) {

        indexTranslationButtons(translationButtons);


        for (i=0; i < noOfButtons; i++) {
            Integer nextElement = randomizeOrder[i];
            Log.w("MainActivity.java", "THE next element is " + nextElement);
            translationButtons.get(i).setText(String.valueOf(choices.get(nextElement)));
        }


    }

}
