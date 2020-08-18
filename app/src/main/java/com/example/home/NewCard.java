package com.example.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mariten.kanatools.KanaConverter;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class NewCard extends AppCompatActivity
{
    public static int newCardTotal = 0; // new card count

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        setTitle("New Card"); // set title of activity
        cardCount();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        cardCount();
    }

    public void newCard(View v)
    {
        // get front and back edit text object
        EditText frontInput = findViewById(R.id.editTextFront);
        EditText backInput = findViewById(R.id.editTextBack);
        EditText readingInput = findViewById(R.id.editTextReading);

        // get the entered text and cast to string
        String front = frontInput.getText().toString();
        String back = backInput.getText().toString();
        String reading = readingInput.getText().toString();

        // if either field has no input
        if(front.equals("")||back.equals(""))
        {
            Toast.makeText(this, "Blank Field/s", Toast.LENGTH_LONG).show();
        }
        else {
            // new instance of cards to add the card to cards
            Cards newCard = new Cards();
            newCard.addCards(front, back, reading);

            newCardTotal += 1; // add 1 to new card count
            cardCount();

            Toast.makeText(this, "Card Added", Toast.LENGTH_LONG).show();
            resetText();
        }
    }

    public void generateReading(View v)
    {
        // get front and back edit text object
        EditText readingInput = findViewById(R.id.editTextFront);
        // get the entered text and cast to string
        String reading = readingInput.getText().toString();

        String inputReading = "";

        Tokenizer tokenizer = Tokenizer.builder().build(); // tokenizer builder instance

        // for every token in front add the reading of the token to the input string
        for(Token token : tokenizer.tokenize(reading))
        {
            inputReading = inputReading + token.getReading(); // katakana string
        }

        String outputReading = KanaConverter.convertKana(inputReading, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA); // new full width katakana to full width hiragana string
        ((TextView)findViewById(R.id.editTextReading)).setText(outputReading); // set the text of the reading textbox
    }

    public void callResetText(View v)
    {
        resetText();
    }

    public void resetText()
    {
        // clear both NewCard edit text fields
        EditText clearFront = findViewById(R.id.editTextFront);
        clearFront.setText("");

        EditText clearBack = findViewById(R.id.editTextBack);
        clearBack.setText("");

        EditText clearReading = findViewById(R.id.editTextReading);
        clearReading.setText("");
    }

    private void cardCount()
    {
        // update text view to reflect total new cards
        ((TextView)findViewById(R.id.newCardCount)).setText("New Cards Waiting  " + String.valueOf(NewCard.newCardTotal));
    }
}
