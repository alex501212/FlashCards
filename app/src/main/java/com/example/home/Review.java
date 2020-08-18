package com.example.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Review extends AppCompatActivity
{
    private static List<String[]> reviewCards = new CopyOnWriteArrayList<String[]>();
    private static int i = 0; // iterator to keep place on what card is being reviewed
    private static Cards review = new Cards(); // new instance of Cards
    private int dayOfYear = 0; // used to compare so reviews can only be seen once a day
    // (is incremented by 1 so that cards are obtained once)
    private int initialDayOfYear = 0; // day of year is retrieved once on device,
    // 0/1 to mark whether retrieved or not
    public static final String prefsName = "prefsFile";

    @Override
    protected void onStop()
    {
        super.onStop();
        review.removeOld(); // remove old instances of cards
        saveCards();
        reviewCards.clear();
        review.emptyCards();
        //review.emptyReviewedCards();
    }

    /*@Override
    protected void onPause()
    {
        super.onPause();
        review.removeOld(); // remove old instances of cards
        saveCards();
        reviewCards.clear();
        review.emptyCards();
        saveCards();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        setTitle("Review"); // set activity title
        loadInitalDayOfYear();
        // get the current day of the year and current year
        Calendar currentDate = Calendar.getInstance();
        int dayOfYearCurrent = currentDate.get(Calendar.DAY_OF_YEAR);
        int currentYear = currentDate.get(Calendar.YEAR);

        if(initialDayOfYear==0)
        {
            setInitialDayOfYear();
            review.presetCards();
        }

        review.removeDupes();
        reviewCards.clear();
        // gets the initial card if there is one
        try // gets reviewed cards
        {
            // checks if the current day is the final day of the year, and also checks if it is a leap year or not
            if(((currentYear%4==0)&&(dayOfYearCurrent==366))||((currentYear%4!=0)&&(dayOfYearCurrent==365)))
            {
                //i = 0;
                dayOfYear = 1;
                loadCards(); // load cards from shared preferences
                getCards(); // get previously reviewed cards
                initialCard(); //needed as all other cards are loaded using the pass/fail button
            }
            else if(dayOfYear==dayOfYearCurrent) // allows cards to be only obtained once
            {
                //i = 0;
                dayOfYear += 1; // increment the dayOfYear so statement can only be true once a day
                loadCards(); // load cards from shared preferences
                getCards();
                getPreLoadedCards(); // get cards from pre loaded deck
                initialCard();
            }
            loadCards(); // load cards from shared preferences
            initialCard(); //needed as all other cards are loaded using the pass/fail button
        }
        catch(Exception e)
        {
            try // get new cards
            {
                getNewCards(); // get previously new cards
                initialCard(); //needed as all other cards are loaded using the pass/fail button
                review.emptyCards();
            }
            catch (Exception ex) // if there is no next card
            {
                review.removeDupes();
                reviewCards.clear();
                showAnsInvisible();
                // sets the textFront/Back textView
                ((TextView) findViewById(R.id.textFront)).setText("You Finished All Due Cards!");
                ((TextView)findViewById(R.id.textReading)).setText("");
                ((TextView) findViewById(R.id.textBack)).setText("");
            }
        }
        removeDupes();
    }

    private void initialCard()
    {
        // get the current day of the year
        Calendar currentDate = Calendar.getInstance();
        int currentDayOfYear = currentDate.get(Calendar.DAY_OF_YEAR);

        while(true)
        {
            // gets the day to review of the current card
            String reviewDay = reviewCards.get(i)[4];
            int reviewDayInt = Integer.parseInt(reviewDay);

            if(currentDayOfYear>=reviewDayInt)
            {
                // sets the textFront/Back textView to the card being reviewed
                ((TextView) findViewById(R.id.textFront)).setText(reviewCards.get(i)[0]);
                ((TextView) findViewById(R.id.textReading)).setText(reviewCards.get(i)[6]);
                ((TextView) findViewById(R.id.textBack)).setText(reviewCards.get(i)[1]);
                break;
            }
            else
            {
                i += 1;
            }
        }
    }

    private void getCards()
    {
        //reviewCards = review.reviewedCards; // sets the reviewCards of this class to the one of Cards
        reviewCards.addAll(review.getReviewed()); // add all cards from reviewedCards(Cards.java) to reviewedCards(Review.java)
        //review.emptyReviewedCards(); // empty reviewed cards in Cards class
    }

    private void getNewCards()
    {
        //reviewCards.addAll(review.cards);
        reviewCards.addAll(review.getNew()); // add all cards from cards(new cards from Cards.java) to reviewedCards(Review.java)
        review.emptyCards(); // empty new cards in Cards class
    }

    private void getPreLoadedCards()
    {
        reviewCards.addAll(review.getPreLoaded()); // add all cards from cards(new cards from Cards.java) to reviewedCards(Review.java)
    }

    private void removeDupes()
    {
        // go through all cards in reviewCards and for each one check if it has a duplicate and if so remove it
        for (String[] card : reviewCards)
        {
            int x = 0;
            for (String[] cards : reviewCards)
            {
                if (Arrays.equals(card, cards))
                {
                    x += 1;
                    if (x == 2)
                    {
                        reviewCards.remove(cards);
                    }
                }
            }
        }
    }

    public void passCard(View v)
    {
        showAnsVisible();
        passFailInvisible();
        backInvisible();
        readingInvisible();
        deleteCardInVisible();
        try
        {
            // add the just reviewed card to reviewed cards in Cards class
            String front = reviewCards.get(i)[0];
            String back = reviewCards.get(i)[1];
            String no_id = reviewCards.get(i)[2]; // rev(iew) or new
            String interval = reviewCards.get(i)[3];
            String reviewDay = reviewCards.get(i)[4];
            String reading = reviewCards.get(i)[6];

            if(no_id.equals("new")) // passed a new card
            {
                NewCard.newCardTotal -= 1; // lower new card count
                // add the new rev card to review card, in this case is a copy of the passed new card
                reviewCards.add(new String[]{front, back, "rev", interval, reviewDay, "reviewing", reading});

            }else if(no_id.equals("pre")) // passed a new card
            {
                // add the new rev card to review card, in this case is a copy of the passed new card
                reviewCards.add(new String[]{front, back, "rev", interval, reviewDay, "reviewing", reading});
            }

            else if((no_id.equals("rev"))||(no_id.equals("fail"))) // will be used when finished reviewing a card
            {
                // get the current date
                Calendar currentDate = Calendar.getInstance();
                int dayOfYearCurrent = currentDate.get(Calendar.DAY_OF_YEAR); // current date which the interval will be added to
                int currentYear = currentDate.get(Calendar.YEAR);


                Float incrementInterval = Float.parseFloat(interval);

                // up the interval on review cards (not previously failed in the review session)
                if((no_id.equals("rev")))
                {
                    incrementInterval = incrementInterval * 2;
                }

                // add new interval to the current date (this will be the date to review the card next)
                Float newReviewDate = Float.parseFloat(String.valueOf(dayOfYearCurrent)); // current date as string
                newReviewDate = newReviewDate + incrementInterval; // add the the interval(days) to current date

                // check if the day is the last day of the year, also checking if it is a leap year or not
                if((currentYear%4==0)&&(dayOfYearCurrent==366))
                {
                    newReviewDate = newReviewDate - 366; // example if the interval is 10(show in 10 days)
                    // and the current day of the year is 366, then adding 10 will equal 376. 376 is
                    // not a day of the year so 366 is subtracted to give 10(10th day of the year)
                }
                else if((currentYear%4!=0)&&(dayOfYearCurrent==365))
                {
                    newReviewDate = newReviewDate - 365;
                }

                reviewDay = String.valueOf(newReviewDate.intValue()); // need to convert newReviewDate to int

                String newInterval = String.valueOf(incrementInterval.intValue()); // new interval as string
                review.reviewed(front,back, newInterval, reviewDay, reading); // add card just reviewed to "reviewed cards array list"
            }
            i += 1; // used to iterate to next card
            initialCard();// sets the textFront/Back textView to the card next card
        }
        catch (Exception ex) // if there is no next card
        {
            try
            {
                i=0;
                // reviewed cards are finished so new cards are now reviewed
                reviewCards.clear(); // empty reviewed cards in Cards class
                getNewCards();
                initialCard(); // sets the textFront/Back textView to the card being reviewed
            }
            catch (Exception e) // if there is no next card
            {
                i=0;
                review.removeDupes();
                reviewCards.clear(); // empty reviewed cards in Cards class
                showAnsInvisible();

                // sets the Front/Back/Reading textView
                ((TextView)findViewById(R.id.textFront)).setText("You Finished All Due Cards!");
                ((TextView)findViewById(R.id.textReading)).setText("");
                ((TextView)findViewById(R.id.textBack)).setText("");
                disable();
                saveCards();
                //review.removeOld(); // remove old instances of cards
            }
        }
    }

    private void disable()
    {
        // disable the view of the pass and fail button
        View passView = findViewById(R.id.pass_button);
        View failView = findViewById(R.id.fail_button);
        View deleteView = findViewById(R.id.deleteCard_button);
        passView.setEnabled(false);
        failView.setEnabled(false);
        deleteView.setEnabled(false);

    }

    public void showAnswer(View v)
    {
        // show answer when pressed will call these methods
        passFailVisible();
        showAnsInvisible();
        backVisible();
        readingVisible();
        deleteCardVisible();
    }

    private void readingVisible()
    {
        // enable the view of the reading field
        View readingView = findViewById(R.id.textReading);
        readingView.setVisibility(View.VISIBLE);
    }

    private void readingInvisible()
    {
        // disable the view of the reading field
        View readingView = findViewById(R.id.textReading);
        readingView.setVisibility(View.INVISIBLE);
    }

    private void passFailVisible()
    {
        // enable the view of the pass and fail button
        View passView = findViewById(R.id.pass_button);
        View failView = findViewById(R.id.fail_button);
        passView.setVisibility(View.VISIBLE);
        failView.setVisibility(View.VISIBLE);
    }

    private void showAnsVisible()
    {
        // enable the view of the show answer button
        View showAnsView = findViewById(R.id.showAnswer_button);
        showAnsView.setVisibility(View.VISIBLE);
    }

    private void deleteCardVisible()
    {
        // enable the view of the show answer button
        View showAnsView = findViewById(R.id.deleteCard_button);
        showAnsView.setVisibility(View.VISIBLE);
    }

    private void deleteCardInVisible()
    {
        // enable the view of the show answer button
        View showAnsView = findViewById(R.id.deleteCard_button);
        showAnsView.setVisibility(View.INVISIBLE);
    }

    private void backVisible()
    {
        // enable the view of the text on the back of a card
        findViewById(R.id.textBack).setVisibility(View.VISIBLE);
    }

    private void passFailInvisible()
    {
        // disable the view of the pass and fail button
        View passView = findViewById(R.id.pass_button);
        View failView = findViewById(R.id.fail_button);
        passView.setVisibility(View.INVISIBLE);
        failView.setVisibility(View.INVISIBLE);
    }

    private void showAnsInvisible()
    {
        // disable the view of the show answer button
        View showAnsView = findViewById(R.id.showAnswer_button);
        showAnsView.setVisibility(View.INVISIBLE);
    }

    private void backInvisible()
    {
        // disable the view of the text on the back of a card
        findViewById(R.id.textBack).setVisibility(View.INVISIBLE);
    }

    public void failCard(View v)
    {
        showAnsVisible();
        passFailInvisible();
        backInvisible();
        readingInvisible();
        deleteCardInVisible();

        // add the just reviewed card to reviewed cards in Cards class
        String front = reviewCards.get(i)[0];
        String back = reviewCards.get(i)[1];
        String interval = reviewCards.get(i)[3];
        String reviewDay = reviewCards.get(i)[4];
        String reading = reviewCards.get(i)[6];

        Float decrementInterval = Float.parseFloat(interval); // convert interval to float

        // cards with an interval of 1 or higher are divided by 2 to give a new decreased interval
        if(decrementInterval>=1)
        {
            decrementInterval = decrementInterval/2;
        }

        // the interval of the card is rounded up
        int roundInterval = Math.round(decrementInterval);
        decrementInterval = (float)roundInterval;

        interval = String.valueOf(decrementInterval);

        reviewCards.add(new String[]{front, back, "fail", interval, reviewDay, "reviewing", reading});

        i += 1; // used to iterate to next card

        initialCard();// sets the textFront/Back textView to the card next card
    }

    private void setInitialDayOfYear()
    {
        Calendar currentDate = Calendar.getInstance();

        // first time cards are reviewed get the current day of the year, this will differ from device
        if(initialDayOfYear==0)
        {
            dayOfYear = currentDate.get(Calendar.DAY_OF_YEAR);
            initialDayOfYear = 1;
        }
    }

    // print lof the contents of reviewCards
    private void printList()
    {
        // combine all attributes of each card into a single string and print Log to console
        for(String[] items : reviewCards)
        {
            String card = items[0] + ", " + items[1] + ", " + items[2] + ", " + items[3] + ", " + items[4] + ", " + items[5] + ", " + items [6];
            Log.d("1234", "reviewCards | " + card);
        }
    }

    private void loadCards()
    {
        SharedPreferences settings = getSharedPreferences(prefsName, 0); // create shared preferences instance

        // retrieve variables from shared preferences
        i = settings.getInt("i", i);

        Gson gson = new Gson();
        Type type = new TypeToken<List<String[]>>() {}.getType(); // Type instance of the List<String[]> type

        String reviewCardsJson = "";
        reviewCardsJson = settings.getString("reviewCards", reviewCardsJson); // get reviewCards from shared preferences
        List<String[]> reviewCardsHold = gson.fromJson(reviewCardsJson, type); // using previous type, Json string to List<String[]>

        String reviewedCardsJson = "";
        reviewedCardsJson = settings.getString("reviewedCards", reviewedCardsJson); // get reviewedCards from shared preferences
        List<String[]> reviewedCardsHold = gson.fromJson(reviewedCardsJson, type); // using previous type, Json string to List<String[]>

        String preLoadedCardsJson = "";
        preLoadedCardsJson = settings.getString("preLoadedCards", preLoadedCardsJson); // get reviewedCards from shared preferences
        List<String[]> preLoadedCardsHold = gson.fromJson(preLoadedCardsJson, type); // using previous type, Json string to List<String[]>

        if((reviewCardsHold!=null)||(reviewedCardsHold!=null))
        {
            // add all cards to their respectable lists (if not null)
            review.reviewedCards.addAll(reviewedCardsHold);
            reviewCards.addAll(reviewCardsHold);
            review.preLoadedCards.addAll(preLoadedCardsHold);
        }
    }

    private void loadInitalDayOfYear()
    {
        SharedPreferences settings = getSharedPreferences(prefsName, 0); // create shared preferences instance

        dayOfYear = settings.getInt("dayOfYear", dayOfYear);
        initialDayOfYear = settings.getInt("initialDayOfYear", initialDayOfYear);
    }

    private void saveCards()
    {
        SharedPreferences settings = getSharedPreferences(prefsName, 0); // create shared preferences instance
        SharedPreferences.Editor editor = settings.edit(); // instance of shared preferences editor

        Gson gson = new Gson();
        String reviewCardsJson = gson.toJson(reviewCards); // reviewCards to a Json string
        String reviewedCardsJson = gson.toJson(review.getReviewed()); // reviewedCards from Cards.java to a Json string
        String preLoadedCardsJson = gson.toJson(review.getPreLoadedAll()); // preLoadedCards from Cards.java to a Json string

        //Log.d("1234", reviewCardsJson);
        Log.d("1234", reviewedCardsJson);

        // use editor to put variables into shared preferences
        editor.putString("reviewedCards", reviewedCardsJson);
        editor.putString("reviewCards", reviewCardsJson);
        editor.putString("preLoadedCards", preLoadedCardsJson);
        editor.putInt("i", i);
        editor.putInt("dayOfYear", dayOfYear);
        editor.putInt("initialDayOfYear", initialDayOfYear);

        editor.commit(); // commit changes
    }

    public void deleteCard(View v)
    {
        showAnsVisible();
        passFailInvisible();
        backInvisible();
        readingInvisible();
        deleteCardInVisible();

        //String front = reviewCards.get(i)[0];
        String no_id = reviewCards.get(i)[2]; // rev(iew) or new
        //TextView frontInput = findViewById(R.id.textFront);
        //String front = frontInput.getText().toString(); // get the entered text and cast to string

        if(no_id.equals("new"))
        {
            NewCard.newCardTotal -= 1;
        }

        Toast.makeText(this, "Card Deleted", Toast.LENGTH_LONG).show(); // delete card toast

        i += 1; // used to iterate to next card
        initialCard();// sets the textFront/Back textView to the card next card
    }
}

