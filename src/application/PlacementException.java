/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: exception type for improper placement of cards on top of others
* Sources: 
*/

package application;

public class PlacementException extends Exception {
    public PlacementException(Card c1, Card c2, String pileType) {
        super("Error:"+c1.displayString()+"cannot be placed on "+c2.displayString()+"of a "+pileType);
    }
    public PlacementException(String pileType) {
        super("Error: no card selected to place on a "+pileType+" pile");
    }
    public PlacementException(Card c, String pileType) {
        super("Error:"+c.displayString()+"cannot place on a "+pileType+" pile");
    }
}