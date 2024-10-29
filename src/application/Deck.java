/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: Standard 52 card playing card deck object class
* Sources: n/a
*/

package application;

import java.util.Random;

public class Deck {
	private Card[] deck; // holds the deck
	private int top; // index for current position in deck

	public Deck() {
		// initializes the deck and top at zero
		top = 0;
		deck = new Card[52];
		
		char[] possibleSuits = {'C', 'D', 'S', 'H'};
		
		// fills the deck with cards ranging from rank 2 to 14 for each suit then shuffles.
		for (int num = 1; num<14; num++) {
			for (int suit = 0; suit<4; suit++) {
				deck[((num-1)+13*suit)] = new Card(num, possibleSuits[suit]);
			}
		}
		shuffle();
	}

	private void shuffle() {
		// shuffles the deck by iterating through teh deck, swapping each card 
		// with a card in another random position in the deck
		Random newPosition = new Random();
		for (int i=0; i<52;i++){
			swap(i,newPosition.nextInt(52));
		}
	}

	public Card draw() {
		// increments the index for the top of the deck and returns the card at the top when the method was called
		top+=1;
		return deck[top-1];
	}

	public boolean isEmpty() {
		// returns true when the index for the top gets outside of the range of the deck indices
		return (top >= 52);
	}

	private void swap(int i, int j) {
		// swaps cards in the deck with the two specified indices
		Card temp = deck[j];
		deck[j] = deck[i];
		deck[i] = temp;
	}
	
}