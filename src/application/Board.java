/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: Board class for the tableau piles of solitaire
* Sources: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
*/

package application;

import java.util.ArrayList;

public class Board {
    private ArrayList<Card>[] board; // array of array lists to hold each of the seven piles

    public Board(Deck d) {
        // initializes each of the seven piles drawing cards from the deck and setting them to unmovable
        board  = new ArrayList[7];
        for (int i = 0; i<7; i++) {
            board[i] = new ArrayList<Card>();
            for (int j = 0; j<=i; j++) {
                board[i].add(d.draw());
                board[i].get(board[i].size()-1).setMovable(false);
            }
        }
    }

    public int getLength(int i) {
        // returns the size of the specified pile
        return board[i].size();
    }

    public Card getCard(String disString) throws MovementException {
        // returns the card object specified by the displaySting as long as it is movable and present in the board, otherwise throws a movement exception
        for (int i = 0; i<7; i++) {
            for (int j = 0; j<board[i].size();j++) {
                if (disString.equals(board[i].get(j).displayString()) && !disString.equals(" XX "))
                    return board[i].get(j);
            }
        }
        throw new MovementException();
    }

    public int getPile(Card c) {
        // returns the index for the pile containing the specified card
        for (int i = 0; i<7; i++) {
            if (board[i].indexOf(c) >-1)
                return i;
        }
        return -1;
    }

    public boolean isHead(Card c, int i) {
        // returns true if the specified card is the top card of a pile, otherwise false
        if (board[i].get(board[i].size()-1) == c)
            return true;
        else return false;
    }

    public void play(Card c, int ori, int des) throws PlacementException {
        // places card from specified origin pile on the destination pile 
        if (!placeable(c, des)) {
            // when it is not placeable, throws an exception
            if (board[des].size() == 0) 
                throw new PlacementException(c, "tableau"); // for an empty pile
            else throw new PlacementException(c, board[des].get(board[des].size()-1), "tableau"); // for a nonempty
        } else {
            // when playable, moves the card and any cards below it onto the destination pile
            int index = board[ori].indexOf(c);
            do {
                board[des].add(board[ori].get(index));
                board[ori].remove(board[ori].get(index));
            } while (index < board[ori].size());
        }
    }

    public void play(Card c, int des) throws PlacementException {
        if (c == null) 
            // throws exception when card is null
            throw new PlacementException("tableau");
        if (!placeable(c, des)) {
            // when it is not playable, throws exception
            if (board[des].size() == 0) 
                throw new PlacementException(c, "tableau"); // for an empty pile
            else throw new PlacementException(c, board[des].get(board[des].size()-1), "tableau"); // for a nonempty
        } else
            // places the card on the indicated pile when playable
            board[des].add(c);
    }

    public void remove(Card c, int ori) {
        // removes the specified card from the pile indicated by the integer
        board[ori].remove(board[ori].indexOf(c));
    }

    private boolean placeable(Card c, int des) {
        // determines if the specified card is allowed to be placed on the specified pile
        if ((board[des].size() == 0) && c.getRank() == 13)
            // true if it is an empty pile and the card is a king
            return true;
        else if (board[des].size() == 0)
            // false if the pile is empty and the card isn't a king
            return false;
        else
            // true if opposite colored suit and rank of one lower, false otherwise
            return (((c.getSuit() == 'H' || c.getSuit() == 'D') && (board[des].get(board[des].size()-1).getSuit() == 'C' || board[des].get(board[des].size()-1).getSuit() == 'S') ||
            (c.getSuit() == 'C' || c.getSuit() == 'S') && (board[des].get(board[des].size()-1).getSuit() == 'H' || board[des].get(board[des].size()-1).getSuit() == 'D')) && 
            (c.getRank()+1 == board[des].get(board[des].size()-1).getRank()));
    }

    public String display(int c, int r) {
        // returns the display string of the specified pile and index from pile and sets any head cards' movable to true
        if (r == board[c].size()-1) 
            board[c].get(r).setMovable(true);
        return board[c].get(r).displayString();
    }
}