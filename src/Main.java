/*
* Name: Ryan Schuenke
* Date: 12/10/2023
* Description: Main GUI of the solitaire game
* Sources: n/a
*/

import application.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    // initializes the deck, then uses it to initialize the board and stock and makes the foundations
    Deck deck = new Deck();
    Board board = new Board(deck);
    Stock stock = new Stock(deck);
    Foundations foundations = new Foundations();

    char[] foundationIndex = {'C', 'D', 'H', 'S'}; // array to get index for foundation

    // initialize variable to hold the selected card, its origin pile, and its destination pile.
    Card selectedCard = null;
    int origin = -1;
    int destination = 7;

    // initializes panes to hold buttons for the board
    GridPane pane = new GridPane();


    // initializes labels for selected card and error and the stock button
    Label selectedLabel = new Label("Selected card:");
    Label errLabel = new Label();
    Button stockButton = new Button(stock.display());

    @Override
    public void start(Stage stage) {
        // initializes top pane to hold the stock, draw, and foundation buttons
        GridPane tPane = new GridPane();

        // add stock to the pane and gives it function
        tPane.add(stockButton, 0, 0);
        stockButton.setOnAction(a -> {
            // when pressed, attempts to set the top card as the selected card, set origin to -1, 
            // and update the selected card and clear the error label
            try {
                selectedCard = stock.getTop();
                origin = -1;
                selectedLabel.setText("Selected card:"+selectedCard.displayString());
                errLabel.setText("");
            } catch (Exception e) {
                // sets error label to the exception message
                errLabel.setText(e.getMessage());
            }
            
        });

        // creates button to draw from the deck and adds it to the top pane
        Button drawButton = new Button("Draw");
        drawButton.setOnAction(a -> {
            // when pressed, draws from the deck, resets the selected card and origin, 
            // and updates the labels for selected card, stock, and error.
            stock.draw();
            selectedCard = null;
            origin = -1;
            selectedLabel.setText("Selected card:");
            stockButton.setText(stock.display());
            errLabel.setText("");
        });
        tPane.add(drawButton,1,0);

        // loops through the foundations and creates buttons for each and adds them to the top pane
        for (int i = 0; i<4; i++) {
            Button fb = new Button(foundations.display(i));
            fb.setOnAction(a -> { // when pressed, 
                // determines which index of the foundation is being placed to by the suit in the display string
                // and the corresponding index in the foundationIndex array
                int index = -1;
                for (int j = 0; j<4; j++) {
                    if (foundationIndex[j] == (fb.getText().charAt(2))) {
                        index = j;
                        break;
                    }
                } 
                try {
                    // attempts to place the selected card on the foundation specified by the index
                    if (!(origin == -1) && !board.isHead(selectedCard, origin)) {
                        // throws exception when the card is not from the deck or if it is not the head of a pile
                        if (selectedCard == null) 
                            throw new PlacementException("foundation");
                        else throw new PlacementException(selectedCard, "foundation");
                    }
                    // places the card on the foundation and updates the buttons text
                    foundations.place(selectedCard, index);
                    fb.setText(foundations.display(index));
                    // removes the card from the pile it originated from
                    if (origin >-1)
                        // removes from the specified pile from the board when origin is greater than -1
                        board.remove(selectedCard, origin);
                    else 
                        // otherwise removes from the deck and updates the stock button text
                        stock.removeTop();
                    // updates the board's buttons
                    update();
                } catch (Exception e) {
                    // sets error label to the exception message
                    errLabel.setText(e.getMessage());
                }
            });
            tPane.add(fb,i+2, 0);
        }

        // initializes the board
        update();
        
        // sets margins
        tPane.setHgap(5);
        pane.setHgap(5);

        // sets the scene and makes the window
        VBox vbox = new VBox(selectedLabel, errLabel, tPane, pane);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Solitaire");
        stage.show();
    }

    public static void main(String[] args) {
        // launches the window
        launch();
    }

    public void update() {
        // method for making the buttons that represent the piles and cards of the board and updating board state when cards are played somewhere

        // resets the selected card and its label, origin, the stock button's text, and the error display
        selectedCard = null;
        origin = -1;
        selectedLabel.setText("Selected card:");
        stockButton.setText(stock.display());
        errLabel.setText("");

        // when foundations are complete, sets error label to indicate victory
        if (foundations.complete()) 
            selectedLabel.setText("You win!");

        // removes all objects from the window
        pane.getChildren().clear();

        for (int col = 0; col<7; col++) { // for each pile,
            for (int row = -1; row<board.getLength(col); row++) { // for each card in the pile and one extra
                if (row == -1) {
                    // creates buttons to specify the pile and allow for placement onto it and adds it to the pane
                    Button b = new Button("Pile "+(col+1));
                    b.setOnAction(a -> { // when pressed, 
                        // sets destination pile to number indicated in button's text minus one
                        destination = Integer.parseInt(String.valueOf(b.getText().charAt(5)))-1;
                        if (origin < 0) { // if the card is from the deck
                            try {
                                // attempts to place the card on the pile
                                board.play(selectedCard, destination);
                                // removes top card of deck and resets selected card and its label and the error label
                                stock.removeTop();
                                // update the board
                                update();
                            } catch (Exception e) {
                                // sets error label to the exception message
                                errLabel.setText(e.getMessage());
                            }
                        } else {
                            try {
                                //attempts to play the selected card from its pile to the destination pile
                                board.play(selectedCard, origin, destination);
                                // update the board
                                update();
                            } catch (Exception e) {
                                // sets error label to the exception message
                                errLabel.setText(e.getMessage());
                            }
                        }
                    });
                    pane.add(b, col, row+1);
                } else {
                    // for each card in the specified pile, creates a new button for the specified card and adds it to the pane
                    Button b = new Button(board.display(col, row));
                    b.setOnAction(a -> { // when pressed
                        try {
                            // attempts to select the card and set the selected card to it and the origin to the pile it is from
                            selectedCard = board.getCard(b.getText());
                            origin = board.getPile(selectedCard);
                            // updates selected card and error labels
                            selectedLabel.setText("Selected card:"+selectedCard.displayString());
                            errLabel.setText("");
                        } catch (Exception e) {
                            // sets error label to the exception message
                            errLabel.setText(e.getMessage());
                        }
                    });
                    pane.add(b, col, row+1);
                }
            }
        }
        // to make the window sufficiently large to accommodate long piles, adds empty labels to extend the window down
        for (int i = 8; i<22; i++)
            pane.add(new Label(), 0, i);
    }
}