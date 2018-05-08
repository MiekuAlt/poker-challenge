package com.michaelaltair.sweptpokerchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/*
The MainActivity class handles all of the front-end aspects of the app,
as well as doing the comparisons between the different player's hands.
When the "Compare!" button is pressed, it initially ensures that there are
no errors in what the user has entered.

It then takes the inputted card values and uses them to create the Hand classes
for both of the players. Once the hands are created, their ranks are compared and
the outcome is displayed for the user to see.
 */
public class MainActivity extends AppCompatActivity {

    private TextView p2TextView, p1TextView;
    private TextView p1ResultView, p2ResultView;
    private EditText p2HandInput, p1HandInput;

    private Hand p2Hand, p1Hand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the UI objects
        p2TextView = findViewById(R.id.p2TextView);
        p1TextView = findViewById(R.id.p1TextView);
        p2ResultView = findViewById(R.id.p2ResultView);
        p1ResultView = findViewById(R.id.p1ResultView);
        p2HandInput = findViewById(R.id.p2Hand);
        p1HandInput = findViewById(R.id.p1Hand);
    }

    // Triggers when the user presses the "Compare" button
    public void comparePressed(View v) {
        // Grabbing inputted hands, ensuring uppercase
        String p2HandTemp = p2HandInput.getText().toString().toUpperCase();
        String p1HandTemp = p1HandInput.getText().toString().toUpperCase();


        // Reset textViews, useful for the event of multiple plays
        p1TextView.setText("Player 1");
        p2TextView.setText("Player 2");
        p1ResultView.setText("");
        p2ResultView.setText("");

        // Only computing the results if the hands are valid
        if(checkHands(p1HandTemp, p2HandTemp)) {
            // Converts the strings submitted by the user into sorted array lists
            createHands(p1HandTemp, p2HandTemp);
        }
    }

    // Converts the strings submitted by the user into the Hand class
    private void createHands(String p1, String p2) {
        ArrayList<String> p1HandList = new ArrayList<>();
        ArrayList<String> p2HandList = new ArrayList<>();
        // Converting the string into array lists
        for(int i = 0; i < p1.length(); i++)
            p1HandList.add("" + p1.charAt(i));

        for(int i = 0; i < p2.length(); i++)
            p2HandList.add("" + p2.charAt(i));
        // Creating each of the player's hands
        p1Hand = new Hand(p1HandList);
        p2Hand = new Hand(p2HandList);
        // Comparing to see who wins
        String result = compareHands(p1Hand, p2Hand);
        // Displaying the results to the UI
        displayResult(result);
    }

    // Displays the result of the compared hands
    private void displayResult(String result) {
        // Indicating who the winner and loser are
        if(result.equals("0")) {
            p1ResultView.setText("WINNER!");
            p2ResultView.setText(("LOSER!"));
        } else if(result.equals("1")) {
            p1ResultView.setText("LOSER!");
            p2ResultView.setText(("WINNER!"));
        } else {
            p1ResultView.setText("TIED!");
            p2ResultView.setText(("TIED!"));
        }

        // Displaying the card's hand
        p1TextView.setText(convertHandRank(p1Hand.handRank));
        p2TextView.setText(convertHandRank(p2Hand.handRank));
    }

    // Converts the hand rank value, into its name
    private String convertHandRank(int rank) {
        switch (rank) {
            case 0:
                return "HIGHCARD";
            case 1:
                return "PAIR";
            case 2:
                return "TWOPAIR";
            case 3:
                return "THREEOFAKIND";
            case 4:
                return "STRAIGHT";
            case 5:
                return "FULLHOUSE";
            case 6:
                return "FOUROFAKIND";
        }
        // This should never be reached
        return "ERROR, unknown handRanking: " + rank;
    }

    // Compares the results from the two hands to see which one wins
    // 0 = Player 1, 1 = Player 2, 01 = Tie
    public static String compareHands(Hand p1, Hand p2) {
        if(p1.handRank > p2.handRank) {
            return "0";
        } else if(p1.handRank < p2.handRank) {
            return "1";
        } else { // There is a tie!
            return tieBreaker(p1, p2);
        }
    }

    // Determines the winner of a tie, if there is one
    private static String tieBreaker(Hand p1, Hand p2) {
        // Determine the hand, since ties are handled differently with different hands
        int handType = p1.handRank;
        switch(handType) {
            case 0:
                return highCardTB(p1, p2);
            case 1:
            case 2:
            case 3:
                return groupedTB(p1, p2);
            case 4:
                return highHandTB(p1, p2);
            case 5:
                return highCardTB(p1, p2);
            case 6:
                return highHandTB(p1, p2);
        }
        // This should never be reached
        System.err.println("Error: Unknown Tie-Breaker Case: " + handType);
        return "01";
    }

    // Compares the highest card that the player has, it doesn't matter if it is in the hand itself
    private static String highCardTB(Hand p1, Hand p2) {
        ArrayList<String> order = new ArrayList<>(
                Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"));
        String p1Card = p1.cards.get(0);
        String p2Card = p2.cards.get(0);
        if(order.indexOf(p1Card) < order.indexOf(p2Card)) {
            return "0";
        } else if(order.indexOf(p1Card) > order.indexOf(p2Card)) {
            return "1";
        } else { // Another tie!
            return "01";
        }
    }
    // Compares groupings of cards involved in the hand, works for pair, two pair and three of kind
    private static String groupedTB(Hand p1, Hand p2) {
        ArrayList<String> order = new ArrayList<>(
                Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"));
        String p1Card = p1.handCards.get(0);
        String p2Card = p2.handCards.get(0);
        if(order.indexOf(p1Card) < order.indexOf(p2Card)) {
            return "0";
        } else if(order.indexOf(p1Card) > order.indexOf(p2Card)) {
            return "1";
        } else { // Another tie, find next highest card
            ArrayList<String> tempP1Cards = new ArrayList<>(p1.cards);
            ArrayList<String> tempP2Cards = new ArrayList<>(p2.cards);
            // Remove current pairs
            tempP1Cards.removeAll(p1.handCards);
            tempP2Cards.removeAll(p2.handCards);
            // Since they are ordered the first cards will be highest
            p1Card = tempP1Cards.get(0);
            p2Card = tempP2Cards.get(0);
            if(order.indexOf(p1Card) < order.indexOf(p2Card)) {
                return "0";
            } else if(order.indexOf(p1Card) > order.indexOf(p2Card)) {
                return "1";
            } else { // Final tie
                return "01";
            }
        }
    }
    // Compares the highest card involved in the hand itself
    private static String highHandTB(Hand p1, Hand p2) {
        ArrayList<String> order = new ArrayList<>(
                Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"));
        // Comparing the highest value card involved in the hand
        String p1Card = p1.handCards.get(0);
        String p2Card = p2.handCards.get(0);

        if(order.indexOf(p1Card) < order.indexOf(p2Card)) {
            return "0";
        } else if(order.indexOf(p1Card) > order.indexOf(p2Card)) {
            return "1";
        } else { // Another tie
            return "01";
        }
    }

    // Ensures that the hands submitted by the user are 2-9 and T A J Q K * and 5 chars
    private boolean checkHands(String p1, String p2) {
        boolean result = true;
        String regexRule = "[2-9TAJQK*]{5,}"; // Only characters 2-9 and T A J Q K * and 5 chars are allowed
        // Checking the player 2's hand
        if(!p2.matches(regexRule)) {
            p2TextView.setText("Incorrect hand, please use 2-9 and ATJQK* and be 5 chars long");
            result = false;
        }
        // Checking the player 1's hand
        if(!p1.matches(regexRule)) {
            p1TextView.setText("Incorrect hand, please use 2-9 and ATJQK* and be 5 chars long");
            result = false;
        }

        return result;
    }
}