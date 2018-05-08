package com.michaelaltair.sweptpokerchallenge;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

/*
    Used for testing the different results of comparing player's hands
 */
public class UnitTestsCompare {
    @Test
    public void testEx1() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("A", "A", "K", "K", "K"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("2", "3", "4", "5", "6"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);
    }

    @Test
    public void testEx2() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("K", "A", "2", "2", "5"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("3", "3", "A", "4", "7"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);
    }

    @Test
    public void testEx3() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("A", "A", "2", "2", "5"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("4", "4", "4", "6", "5"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);
    }

    @Test
    public void testEx4() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("T", "T", "4", "A", "2"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("T", "T", "A", "8", "5"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("01", result);
    }

    @Test
    public void testEx5() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("A", "3", "4", "5", "*"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("2", "5", "4", "*", "6"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);
    }

    @Test
    public void testEx6() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "2", "A", "T"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "T", "2", "J"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);
    }

    @Test
    public void testHighTie() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("2", "Q", "4", "A", "T"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("Q", "3", "A", "2", "J"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("01", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("2", "A", "4", "J", "T"));
        p2Cards = new ArrayList<>(
                Arrays.asList("Q", "3", "K", "2", "J"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);
    }

    @Test
    public void testPairTie() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("A", "Q", "4", "A", "T"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("K", "3", "A", "2", "A"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("A", "Q", "4", "A", "K"));
        p2Cards = new ArrayList<>(
                Arrays.asList("K", "3", "A", "2", "A"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("01", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("A", "*", "4", "3", "T"));
        p2Cards = new ArrayList<>(
                Arrays.asList("K", "3", "*", "2", "7"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);
    }

    @Test
    public void testTwoPairTie() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("A", "Q", "4", "A", "Q"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("K", "3", "A", "K", "A"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("A", "Q", "4", "A", "Q"));
        p2Cards = new ArrayList<>(
                Arrays.asList("Q", "3", "A", "Q", "A"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("A", "Q", "4", "A", "Q"));
        p2Cards = new ArrayList<>(
                Arrays.asList("Q", "4", "A", "Q", "A"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("01", result);
    }

    @Test
    public void testThreeKindTie() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "4", "A", "Q"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("K", "K", "3", "K", "A"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "4", "K", "Q"));
        p2Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "A", "Q", "2"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "4", "K", "Q"));
        p2Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "K", "Q", "2"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("01", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("Q", "Q", "*", "2", "3"));
        p2Cards = new ArrayList<>(
                Arrays.asList("T", "T", "T", "2", "3"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);
    }

    @Test
    public void testFourKindTie() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("*", "2", "*", "A", "*"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("*", "2", "*", "K", "*"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);
    }

    @Test
    public void testFullHouseTie() {
        // Same high card, so must be tie
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("A", "K", "K", "K", "A"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("Q", "A", "A", "Q", "Q"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("01", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("*", "K", "K", "3", "3"));
        p2Cards = new ArrayList<>(
                Arrays.asList("T", "T", "3", "3", "T"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("0", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("K", "K", "K", "3", "3"));
        p2Cards = new ArrayList<>(
                Arrays.asList("A", "A", "3", "3", "A"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);
    }

    @Test
    public void testStraightTie() {
        ArrayList<String> p1Cards = new ArrayList<>(
                Arrays.asList("A", "2", "3", "4", "5"));
        ArrayList<String> p2Cards = new ArrayList<>(
                Arrays.asList("A", "K", "Q", "J", "T"));
        Hand p1Hand = new Hand(p1Cards);
        Hand p2Hand = new Hand(p2Cards);
        String result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);

        p1Cards = new ArrayList<>(
                Arrays.asList("6", "5", "4", "3", "2"));
        p2Cards = new ArrayList<>(
                Arrays.asList("3", "4", "5", "6", "7"));
        p1Hand = new Hand(p1Cards);
        p2Hand = new Hand(p2Cards);
        result = MainActivity.compareHands(p1Hand, p2Hand);
        assertEquals("1", result);
    }
}
