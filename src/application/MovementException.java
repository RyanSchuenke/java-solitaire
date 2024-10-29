/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: Exception type for improper card movements
* Sources: 
*/

package application;

public class MovementException extends Exception {
    public MovementException() {
        super("Error: No card available to move");
    }
}