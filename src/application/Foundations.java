/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: Foundation class for the solitaire game
* Sources: n/a
*/

package application;

public class Foundations {
    private Card[] topCard; // array of cards for each suit

    public Foundations() {
        // initializes for each of the 4 suits, starting at a rank of zero
        topCard = new Card[4];
        topCard[0] = new Card(0,'C');
        topCard[1] = new Card(0, 'D');
        topCard[2] = new Card(0, 'H');
        topCard[3] = new Card(0, 'S');
    }

    public void place(Card c, int i) throws PlacementException {
        if (c == null) // gives error if no selected card
            throw new PlacementException("foundation");
        else if (topCard[i].getSuit() == c.getSuit() && topCard[i].getRank()+1 == c.getRank())
            // places the new card as the new top for the suit if the suit matches and rank is one higher
            topCard[i] = c;
        else throw new PlacementException(c, topCard[i], "foundation");
        // throws exception if not placeable
    }

    public boolean complete() {
        // returns true when all four of the cards are kings, indicating the player has won, and false otherwise
        for (int i = 0; i<4; i++) {
            if (topCard[i].getRank() <13)
                return false;
        }
        return true;
    }

    public String display(int i) {
        // returns the display string for the card of the indicated suit
        return (topCard[i].displayString());
    }
}