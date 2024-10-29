/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: Standard playing card class
* Sources: n/a
*/

package application;

public class Card {
	private int rank; // stores rank number
	private char suit; // stores suit 
    private boolean movable; // determines if card is faceup / movable
	
	Card(int r, char s) {
        // initializes the rank, suit, and movable
		rank = r;
		suit = s;
        movable = true;
	}

	public int getRank() {
        // getter method for the card's rank
		return rank;
	}
	
	public char getSuit() {
        // getter method for the card's suit
		return suit;
	}

    public void setMovable(boolean m) {
        // setter method for the card's movable status
        movable = m;
    }

    public String displayString() {
        // returns a string for how to display the card, rank followed by suit 
        // if not movable, returns XX instead
        if (!movable)
            return " XX ";
        String out;
        if (rank == 0)
            out = "  ";
        else if (rank == 1)
            out = " A";
        else if (rank == 10)
            out = String.valueOf(rank);
        else if (rank == 11)
            out = " J";
        else if (rank == 12)
            out = " Q";
        else if (rank == 13)
            out = " K";
        else out = " "+String.valueOf(rank);
        out= out +suit+" ";
        return out;
    }
}