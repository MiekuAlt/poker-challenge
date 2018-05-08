package com.michaelaltair.sweptpokerchallenge;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
/*
    Used for testing the different kinds of hands that should be detected by the app
 */
public class UnitTests {
    @Test
    public void testFourOfAKind() {
        ArrayList<String> testCards = new ArrayList<>(
                Arrays.asList("K", "J", "K", "K", "K"));
        Hand hand = new Hand(testCards);
        assertEquals(6, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("K", "J", "K", "*", "K"));
        hand = new Hand(testCards);
        assertEquals(6, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("*", "*", "9", "6", "9"));
        hand = new Hand(testCards);
        assertEquals(6, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("A", "A", "2", "A", "A"));
        hand = new Hand(testCards);
        assertEquals(6, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("A", "A", "2", "*", "A"));
        hand = new Hand(testCards);
        assertEquals(6, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("*", "2", "*", "A", "*"));
        hand = new Hand(testCards);
        assertEquals(6, hand.handRank);
    }

    @Test
    public void testFullHouse() {
        ArrayList<String> testCards = new ArrayList<>(
                Arrays.asList("K", "J", "K", "J", "K"));
        Hand hand = new Hand(testCards);
        assertEquals(5, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("J", "J", "K", "*", "K"));
        hand = new Hand(testCards);
        assertEquals(5, hand.handRank);
    }

    @Test
    public void testStraight() {
        ArrayList<String> testCards = new ArrayList<>(
                Arrays.asList("2", "4", "3", "6", "5"));
        Hand hand = new Hand(testCards);
        assertEquals(4, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("2", "4", "3", "A", "5"));
        hand = new Hand(testCards);
        assertEquals(4, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("J", "T", "Q", "A", "K"));
        hand = new Hand(testCards);
        assertEquals(4, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("3", "4", "*", "6", "7"));
        hand = new Hand(testCards);
        assertEquals(4, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("*", "*", "K", "A", "Q"));
        hand = new Hand(testCards);
        assertEquals(4, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("*", "*", "2", "A", "3"));
        hand = new Hand(testCards);
        assertEquals(4, hand.handRank);
    }

    @Test
    public void testThreeOfAKind() {
        ArrayList<String> testCards = new ArrayList<>(
                Arrays.asList("4", "J", "K", "K", "K"));
        Hand hand = new Hand(testCards);
        assertEquals(3, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("4", "J", "K", "*", "K"));
        hand = new Hand(testCards);
        assertEquals(3, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("*", "*", "A", "7", "5"));
        hand = new Hand(testCards);
        assertEquals(3, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("K", "*", "*", "6", "7"));
        hand = new Hand(testCards);
        assertEquals(3, hand.handRank);
    }

    @Test
    public void testTwoPair() {
        ArrayList<String> testCards = new ArrayList<>(
                Arrays.asList("K", "4", "4", "5", "5"));
        Hand hand = new Hand(testCards);
        assertEquals(2, hand.handRank);
    }

    @Test
    public void testPair() {
        ArrayList<String> testCards = new ArrayList<>(
                Arrays.asList("T", "J", "3", "7", "T"));
        Hand hand = new Hand(testCards);
        assertEquals(1, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("K", "A", "2", "2", "5"));
        hand = new Hand(testCards);
        assertEquals(1, hand.handRank);

        testCards = new ArrayList<>(
                Arrays.asList("T", "J", "3", "7", "*"));
        hand = new Hand(testCards);
        assertEquals(1, hand.handRank);
    }

    @Test
    public void testHighCard() {
        ArrayList<String> testCards = new ArrayList<>(
                Arrays.asList("3", "8", "T", "4", "9"));
        Hand hand = new Hand(testCards);
        assertEquals(0, hand.handRank);
        assertEquals("T", hand.handCards.get(0)); // Checking the value of the high card

        testCards = new ArrayList<>(
                Arrays.asList("3", "8", "T", "4", "A"));
        hand = new Hand(testCards);
        assertEquals(0, hand.handRank);
        assertEquals("A", hand.handCards.get(0)); // Checking the value of the high card
    }
}
