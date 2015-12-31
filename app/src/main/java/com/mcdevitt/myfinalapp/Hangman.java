package com.mcdevitt.myfinalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mark on 21/11/2015.
 */
public class Hangman extends Activity {

    MediaPlayer mySound;
    MediaPlayer mySound2;
    String mWord;
    int mFailCounter = 0;
    int mGuessedLetters = 0;
    int mPoints = 0;
    ArrayList<Character> alreadyGuessed = new ArrayList<Character>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        setRandomWord();
        mySound = MediaPlayer.create(this, R.raw.michael);
        mySound2 = MediaPlayer.create(this, R.raw.micael2);
    }


    public void introduceLetter(View v) {

        EditText myEditText = (EditText) findViewById(R.id.editTextLetter);
        String Letter = myEditText.getText().toString();
        myEditText.setText("");

        if (Letter.length() == 1) {
            checkLetter(Letter.toUpperCase());
        } else {
            Toast.makeText(this, "Please introduce a letter", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * checking if the letter introduced matches any letter in the word to guess
     *
     * @param introducedLetter, letter introduced by the user
     */
    public void checkLetter(String introducedLetter) {

        char charIntroduced = introducedLetter.charAt(0);
        boolean letterGuessed = false;

        if (alreadyGuessed.contains(charIntroduced)) {

            Toast.makeText(this, "Please introduce a letter that you havent used before", Toast.LENGTH_SHORT).show();
            letterGuessed = true;
        } else {
            alreadyGuessed.add(charIntroduced);

            for (int i = 0; i < mWord.length(); i++) {// a break doesnt work beacause the it will go back to zero
                char charFromTheWord = Character.toUpperCase(mWord.charAt(i));

                if (charFromTheWord == charIntroduced) {

                    letterGuessed = true;
                    showLettersAtIndex(i, charFromTheWord);
                    mGuessedLetters++;//increases the guess counter
                }
            }
        }
        if (letterGuessed == false) {
            letterFailed();//method for the boolean , makes the pics change
            showFailedLetters(charIntroduced);//method for the failed letters

        }
        if (mGuessedLetters == mWord.length()) {// if the guessed letters equal the words length , it must be right
            mPoints++;


            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Correct");
            ad.setMessage(mWord + " ✓");
            clearTheScreen();
            setRandomWord();

            ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                }
            });
            ad.show();
        }
    }

    public void setRandomWord() {

        String words = "Afghanistan Albania Algeria Andorra Angola Argentina Armenia Australia Austria Azerbaijan Bahamas Bahrain Bangladesh Barbados Belarus Belgium Belize Benin Bhutan Bolivia Botswana Brazil Brunei Bulgaria Burundi Cambodia Cameroon Canada Chad Chile China Colombia Comoros Croatia Cuba Cyprus Denmark Djibouti Dominica Ecuador Egypt Eritrea Estonia Ethiopia Fiji Finland France Gabon Gambia Georgia Germany Ghana Greece Grenada Guatemala Guinea Guyana Haiti Honduras Hungary Iceland India Indonesia Iran Iraq Ireland Israel Italy Jamaica Japan Jordan Kazakhstan Kenya Kiribati Kosovo Kuwait Kyrgyzstan Laos Latvia Lebanon Lesotho Liberia Libya Liechtenstein Lithuania Luxembourg Macedonia Madagascar Malawi Malaysia Maldives Mali Malta Mauritania Mauritius Mexico Micronesia Moldova Monaco Mongolia Montenegro Morocco Mozambique Namibia Nauru Nepal Netherlands Nicaragua Niger Nigeria Norway Oman Pakistan Palau Palestine Panama Paraguay Peru Philippines Poland Portugal Qatar Romania Russia Rwanda Samoa Senegal Serbia Seychelles Singapore Slovakia Slovenia Somalia Spain Sudan Suriname Swaziland Sweden Switzerland Syria Taiwan Tajikistan Tanzania Thailand Togo Tonga Tunisia Turkey Turkmenistan Tuvalu Uganda Ukraine Uruguay Uzbekistan Vanuatu Venezuela Vietnam Yemen Zambia Zimbabwe";
        String[] arrayWords = words.split(" ");

        int randomNumber = (int) (Math.random() * arrayWords.length);
        String randomWord = arrayWords[randomNumber];
        mWord = randomWord;
        createLines(mWord);

    }

    private void createLines(String mWord) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLetters);
        for (int i = 0; i < mWord.length(); i++) {
            TextView newText = (TextView) getLayoutInflater().inflate(R.layout.activity_letter, null);
            layout.addView(newText);
        }
    }

    public void clearTheScreen() {

        TextView letters = (TextView) findViewById(R.id.failedLetters);
        letters.setText(""); //resets the red letters to blank
        mGuessedLetters = 0;//resets how many letters have been guessed
        mFailCounter = 0;//resets it to 0
        alreadyGuessed.clear();

        LinearLayout layoutLetter = (LinearLayout) findViewById(R.id.layoutLetters);
        if ((layoutLetter).getChildCount() > 0)
            (layoutLetter).removeAllViews();

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.one);//resets the pictures back to the first one
    }

    public void letterFailed() {
        mFailCounter++;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if (mFailCounter == 1) {
            imageView.setImageResource(R.drawable.two);
        } else if (mFailCounter == 2) {
            imageView.setImageResource(R.drawable.three);
        } else if (mFailCounter == 3) {
            imageView.setImageResource(R.drawable.four);
        } else if (mFailCounter == 4) {
            imageView.setImageResource(R.drawable.five);
        } else if (mFailCounter == 5) {
            imageView.setImageResource(R.drawable.six);
            mySound.start();
        } else if (mFailCounter == 6) {
            imageView.setImageResource(R.drawable.seven);
            mySound2.start();
            answer();
        }
    }

    public void answer() {

        new AlertDialog.Builder(Hangman.this)
                .setTitle("Answer")
                .setMessage("The answer is " + mWord)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                gameComplete();
            }
        }.start();
    }

    public void gameComplete() {

        Intent gameOverIntent = new Intent(this, OverActivity.class);
        gameOverIntent.putExtra("POINTS_IDENTIFIER", mPoints);//to send over info
        startActivity(gameOverIntent);
        finish();// will end the stack
    }

    public void showFailedLetters(char LetterGuessed) {

        TextView letters = (TextView) findViewById(R.id.failedLetters);
        letters.append(Character.toString(LetterGuessed));
    }

    public void showLettersAtIndex(int position, char LetterGuessed) {

        LinearLayout layoutLetter = (LinearLayout) findViewById(R.id.layoutLetters);
        TextView textView = (TextView) layoutLetter.getChildAt(position);
        textView.setText(Character.toString(LetterGuessed));


    }

}