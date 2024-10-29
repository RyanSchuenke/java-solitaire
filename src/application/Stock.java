/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: Stock class containing the deck for the solitaire
* Sources: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
*/

package application;

import java.util.ArrayList;

public class Stock {
    private ArrayList<Card> stock; // array for holding the cards in the stock (deck).
    private int top; // index for the current top card of the deck.

    public Stock(Deck d) {
        // initializes deck by drawing from the deck until it is empty, then setting the top to the highest index of the deck
        stock = new ArrayList<Card>();
        while(!d.isEmpty())
            stock.add(d.draw());
        top = stock.size() - 1;
    }

    public void draw() {
        // decrements the top index and sets it back to the highest index if the index goes below zero.
        top--;
        if (top < 0)
            top = stock.size()-1;
    }

    public void removeTop() {
        // removes the card indexed by top and moves top back to the highest index if it exceeds it
        stock.remove(top);
        if (top > stock.size()-1)
            top = stock.size()-1;
    }

    public Card getTop() throws MovementException {
        // gets the top card and returns or throws an exception if the deck is empty and no card is on top
        if (top < 0)
            throw new MovementException();
        return stock.get(top);
    }

    public String display() {
        // returns the display string of the top or displays empty when the deck is empty
        if (top<0)
            return ("Deck: empty ");
        return ("Deck:"+stock.get(top).displayString());
    }
}