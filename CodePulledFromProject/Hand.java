package com.michaelaltair.sweptpokerchallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
The Hand class handles all calculations and data based on the player's
specific hand, when the hand is created it counts the number of wild cards
and removes them for later use. It then determines what its own hand type
is (Four of a Kind, Full House, ...) and stores any important card values
to be used in tie breakers.
 */
public class Hand {
    ArrayList<String> cards;
    int handRank; // The ranked value of the hand
    ArrayList<String> handCards; // Useful card values for tie breakers
    int numWild; // The number of wilds that exist in the hand

    public Hand(ArrayList<String> cards) {
        this.cards = cards;
        // Counting and removing wilds from the cards
        numWild = Collections.frequency(cards, "*");
        cards.removeAll(Collections.singleton("*"));
        // Initially sorting the cards, with aces high
        sortHand(cards, true);
        // Calculating the rank of the hand
        handRank = determineRank();
    }

    /* Determines the rank of the hand, from highest value to lowest
    FourOfAKind = 6, FullHouse = 5, Straight = 4, ThreeOfAKind = 3, TwoPair = 2, Pair = 1, HighCard = 0
     */
    private int determineRank() {
        if(checkFourOfAKind()){
            return 6;
        }
        else if(checkFullHouse()) {
            return 5;
        }
        else if(checkStraight()) {
            return 4;
        }
        else if(checkThreeOfAKind()) {
            return 3;
        }
        else if(checkTwoPair()) {
            return 2;
        }
        else if(checkPair()) {
            return 1;
        } else {
            // If this point has been reached, the only hand available is High Card
            handCards = new ArrayList<>(); // Used to store the high card, used for tie breaker
            handCards.add(cards.get(0)); // Since it is ordered, first card will be the highest
            return 0;
        }
    }

    /* Checks if the hand is FourOfAKind
    Iterates through the cards and checks the occurrences, takes wilds into
    account. Also handles if someone slipped a 5th card into the deck :)
     */
    private boolean checkFourOfAKind() {
        handCards = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            String value = cards.get(i);
            int occurrences = Collections.frequency(cards, value);
            if(occurrences + numWild >= 4) {
                handCards.add(value);
                return true;
            }
        }
        return false;
    }
    /* Checks if the hand is FullHouse
    Iterates through the cards and checks their occurrences, if three or a pair it will trigger its
    respective bool to true. Also stores the value of the card that caused the most recent trigger,
    in order to avoid double counting. It is a full house if both booleans are true.

    If it fails its first try, it checks if there are any wildcards, it will attempt to apply the
    wild card to the failed boolean to see if the wild cards can allow it to pass.
     */
    private boolean checkFullHouse() {
        String tempVal = ""; // Used to avoid double counting
        boolean hasPair = false;
        boolean hasThree = false;
        for(int i = 0; i < cards.size(); i++) {
            String value = cards.get(i);
            int occurrences = Collections.frequency(cards, value);
            if(occurrences == 3) {
                hasThree = true;
                tempVal = value;
            } else if(occurrences == 2) {
                hasPair = true;
                tempVal = value;
            }
            // Does the check within the loop, to reduce the amount of loops done
            if(hasPair && hasThree) {
                return true;
            }
        }
        // Using wild cards
        int curWild = numWild;
        if(curWild > 0) {
            for(int i = 0; i < cards.size(); i++) {
                String value = cards.get(i);
                int occurrences = Collections.frequency(cards, value);
                if(!value.equals(tempVal) && !hasThree && (occurrences + curWild) == 3) {
                    curWild = 0;
                    hasThree = true;
                    tempVal = value;
                } else if(!value.equals(tempVal) && !hasPair && (occurrences + curWild) == 2) {
                    curWild = 0;
                    hasPair = true;
                    tempVal = value;
                }
                // Does the check within the loop, to reduce the amount of loops done
                if(hasPair && hasThree) {
                    return true;
                }
            }
        }
        return false;
    }
    /* Checks if the hand is Straight
    First chooses if it should check aces high or low, logically if the cards contain a 2 or 3 a
    straight from the high end of the order is impossible. So it uses that logic to choose the value
    of Ace. Once chosen, the cards are then sorted corresponding to its choice.

    It then checks if a straight is possible by making sure the highest value card is not too close
    to the end. It then iterates through the cards and compares it with the expected order of the cards.
    If there is an instance where they don't add up, it checks if there are any wilds available. If so,
    it subtracts one and adds the expected card to its hand. Then sorting it again. The wilds are added
    and sorted for later when handling ties, just in case the wild fills for the highest card.

    This check passes if the iteration makes it to the end without running out of wilds.
     */
    private boolean checkStraight() {
        int curWild = numWild;
        // Choosing if it should check ace low or high, if the hand contains a 2 or a 3 it should focus on the rear
        // Therefore ace is low
        if(cards.contains("2") || cards.contains("3")) {
            ArrayList<String> aceLowOrder = new ArrayList<>(
                    Arrays.asList("K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2", "A"));
            sortHand(cards, false);
            if(aceLowOrder.size() - aceLowOrder.indexOf(cards.get(0)) >= cards.size()) { // Ensuring a straight is possible
                boolean result = true;
                handCards = new ArrayList<>(cards);
                for (int i = 0; i < handCards.size() - 1; i++) {
                    String curVal = handCards.get(i);
                    int indexInOrder = aceLowOrder.indexOf(curVal);
                    if (indexInOrder < aceLowOrder.size() - 1 && !handCards.get(i + 1).equals(aceLowOrder.get(indexInOrder + 1))) {
                        if(curWild > 0) {
                            curWild--;
                            // Figuring out the card the wild has become
                            String wildVal = aceLowOrder.get(indexInOrder + 1);
                            handCards.add(wildVal);
                            sortHand(handCards, false);
                        } else {
                            result = false;
                            break;
                        }
                    }
                }
                return result;
            } else {
                return false;
            }
        } else { // Checking with ace as high
            ArrayList<String> aceHighOrder = new ArrayList<>(
                    Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"));
            sortHand(cards, true);
            if(aceHighOrder.size() - aceHighOrder.indexOf(cards.get(0)) >= cards.size()) { // Ensuring a straight is possible
                boolean result = true;
                handCards = new ArrayList<>(cards);
                for (int i = 0; i < handCards.size() - 1; i++) {
                    String curVal = handCards.get(i);
                    int indexInOrder = aceHighOrder.indexOf(curVal);
                    if (indexInOrder < aceHighOrder.size() - 1 && !handCards.get(i + 1).equals(aceHighOrder.get(indexInOrder + 1))) {
                        if(curWild > 0) {
                            curWild--;
                            // Figuring out the card the wild has become
                            String wildVal = aceHighOrder.get(indexInOrder + 1);
                            handCards.add(wildVal);
                            sortHand(handCards, true);
                        } else {
                            result = false;
                            break;
                        }
                    }
                }
                return result;
            }
            else {
                return false;
            }
        }
    }

    /* Checks if the hand is ThreeOfAKind
    Since there is a possibility that the previous check for a straight can mess up the order of the
    cards, the first thing it does is re-sort them (aces high). It then iterates through the cards and
    checks if there are any occurrences of the cards that are sets of three. It also uses wildcards to
    help achieve the set of three
     */
    private boolean checkThreeOfAKind() {
        sortHand(cards, true); // Ensuring that the ordering is correct after being swapped around in the straight checker
        for(int i = 0; i < cards.size(); i++) {
            String value = cards.get(i);
            int occurrences = Collections.frequency(cards, value);
            if(occurrences + numWild == 3) {
                handCards = new ArrayList<>();
                handCards.add(value);
                return true;
            }
        }
        return false;
    }
    /* Checks if the hand is TwoPair
    This iterates through the cards and counts the number of pairs that it finds. It also records
    the value of the previous pair found, in order to ensure the cards aren't double counted. When
    a pair is found it also stores this value for later in order to handle ties.
     */
    private boolean checkTwoPair() {
        int numPair = 0;
        String pairVal = "";
        handCards = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            String value = cards.get(i);
            int occurrences = Collections.frequency(cards, value);
            if(occurrences == 2 && !pairVal.equals(value)) {
                handCards.add(value);
                pairVal = value; // Ensures that this is not the same pair as previously recorded
                numPair++;
            }
        }
        return numPair == 2;
    }
    /* Checks if the hand is a Pair
    Iterates through the cards to see if there are any occurrences where there are pairs of cards
    if any pairs are found, it stores the value of the pair for tie breaking and immediately leaves
    the loop
     */
    private boolean checkPair() {
        for(int i = 0; i < cards.size(); i++) {
            String value = cards.get(i);
            int occurrences = Collections.frequency(cards, value);
            if(occurrences + numWild == 2) {
                handCards = new ArrayList<>();
                handCards.add(value);
                return true;
            }
        }
        return false;
    }

    // Sorts the hands based on the card's value, based on the whether the ace is high or low
    private void sortHand(ArrayList<String> hand, boolean isAceHigh) {
        // Defining the order, Aces high or low
        final List<String> definedOrder;
        if(isAceHigh) {
            definedOrder = Arrays.asList("*", "A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");
        } else {
            definedOrder = Arrays.asList("*", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2", "A");
        }
        // Built a custom comparator for the unique ordering of poker cards
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(definedOrder.indexOf(o1)).compareTo(Integer.valueOf(definedOrder.indexOf(o2)));
            }
        };
        Collections.sort(hand, comparator);
    }
}
