package com.example.jsu.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.lang.StringBuilder;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> secretWords;
    private ArrayList<Character> correctGuessedLetters;
    private Random random;

    private int totalGuesses;
    private int incorrectGuesses;
    private int correctGuesses;
    private int startingPoint;

    int wordLength;

    private String mysteryWord;
    private String scrambledWord;
    private StringBuilder mysteryWordNew;

    private boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        secretWords = new ArrayList<String>(Arrays.asList("APPLE", "BANANA", "CHERRY"));
        random = new Random();
        reset();
    }
    private void selectMysteryWord(){
        mysteryWord = secretWords.get(random.nextInt(secretWords.size()));
        mysteryWordNew = new StringBuilder(mysteryWord);
        wordLength = mysteryWord.length();
        scrambledWord = "";
        ArrayList<String> splitWord = new ArrayList<>(Arrays.asList(mysteryWord.split("")));
        Collections.shuffle(splitWord);
        for (String c : splitWord)
            scrambledWord += c;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void guessLetter(View v){


        if (gameOver){
            reset();
        }
        else{
            TextView guessTextView = (TextView) findViewById(R.id.guessEditText);
            if (guessTextView.getText().length() > 0){
                char guessedLetter = guessTextView.getText().charAt(0);
                if (guessedLetter == mysteryWord.charAt(startingPoint)){

                    correctGuessedLetters.add(guessedLetter);
                    ++ correctGuesses;
                    ++startingPoint;
                    updateCorrectTextView();
                }
                else{
                    ++incorrectGuesses;
                    updateIncorrectTextView();
                }

                if (correctGuessedLetters.size() == wordLength){
                    updateWinBox();

                    gameOver = true;
                    promptForNewGame();
                }

                updateGuessedTextView();
                clearGuessEditText();
            }
        }
    }

    private void promptForNewGame(){
        Button guessButton = (Button) findViewById(R.id.guessButton);
        guessButton.setText("New Game");
    }

    private void reset(){
        gameOver = false;
        totalGuesses = 0;
        correctGuessedLetters = new ArrayList<Character>();

        selectMysteryWord();

        updateScrambledWordTextView();
        updateGuessedTextView();
        updateCorrectTextView();
        updateIncorrectTextView();
        clearGuessEditText();
        updateGuessButtonText();
    }

    private void updateGuessButtonText(){
        Button guessButton = (Button) findViewById(R.id.guessButton);
        guessButton.setText("Guess");
    }

    private void updateScrambledWordTextView(){
        TextView scrambledWordTextView = (TextView) findViewById(R.id.scrambledWordTextView);
        scrambledWordTextView.setText(scrambledWord);
    }

    private void updateGuessedTextView(){
        TextView guessedTextView = (TextView) findViewById(R.id.guessedText);
        String s = "";
        for (char c: correctGuessedLetters){
            s += c + "";
        }
        guessedTextView.setText(s);
    }

    private void updateCorrectTextView(){
        TextView numberCorrectTextView = (TextView) findViewById(R.id.numberCorrectTextView);
        numberCorrectTextView.setText("Correct Guesses: " + correctGuessedLetters.size());
    }

    private void updateIncorrectTextView(){
        TextView numberIncorrectTextView = (TextView) findViewById(R.id.incorrectTextView);
        numberIncorrectTextView.setText("Incorrect Guesses: " + incorrectGuesses);
    }

    private void clearGuessEditText(){
        TextView guessEditText = (TextView) findViewById(R.id.guessEditText);
        guessEditText.setText("");
    }
    private void updateWinBox(){
        TextView winBox = (TextView) findViewById(R.id.winBox);
        winBox.setText("Congrats! You guessed " + mysteryWord);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
