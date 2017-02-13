import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to play a two-player Reversi/Othello game
 *
 * @author Nikil Pancha
 */
public class Reversi extends PrettyBoard {

    /**
     * Constructor to make a new two player game of a given height and width
     *
     * @param height height of board
     * @param width  width of board
     */
    public Reversi(int height, int width) {
        super(height, width);
    }

    /**
     * Create a new 8x8 game
     */
    public Reversi() {
        this(8);
    }

    /**
     * Create a new square game of specified size
     *
     * @param size size of square to make the game
     */
    public Reversi(int size) {
        this(size, size);
    }

    /**
     * Sequence of events that happen after making a move
     */
    private void postMove() {
        if (gameOver()) { //if game is over, update board and execute end of game sequence
            updatePrettyBoard();
            endOfGame();
        } else if (!hasAMove()) { //if player doesn't have a move, toggle turn and change title text
            String text;
            toggleWhoseTurn();
            if (getWhoseTurn() == 1) {
                text = "White has no valid moves: Black's turn";
            } else {
                text = "Black has no valid moves: White's turn";
            }
            JOptionPane.showMessageDialog(getFrame(), text);
        }
        updatePrettyBoard();
    }

    /**
     * Method to be executed up on button press that makes a a move if it is valid indeed
     *
     * @param e JButton clicked event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        Move move = findButton(b);
        if (checkValidity(move)) { //if move is valid, make the move and update board
            makeMove(move);
            updatePrettyBoard();
        } else {
            JOptionPane.showMessageDialog(getFrame(), "Not a valid move\nPlease choose another space");
        }
        postMove();
    }

    /**
     * End of game sequence to execute
     */
    public void endOfGame() {
        int blackTiles = countSquares(1); //store number of black tiles
        int whiteTiles = countSquares(0); //store number of white tiles
        if (blackTiles == whiteTiles) { //if equal, there is a tie
            JOptionPane.showMessageDialog(getFrame(), "Tis a tie");
            getFrame().setVisible(false);
            getFrame().dispose();
        } else if (blackTiles > whiteTiles) {
            JOptionPane.showMessageDialog(getFrame(), "Player One (black) wins\n" + blackTiles + " - " + whiteTiles);
            getFrame().setVisible(false);
            getFrame().dispose();
        } else {
            JOptionPane.showMessageDialog(getFrame(), "Player Two (white) wins\n" + whiteTiles + " - " + blackTiles);
            getFrame().setVisible(false);
            getFrame().dispose();
        }

    }

    /**
     * Get the location of a button press
     *
     * @param b button to find
     * @return move that holds the position of the button input
     */
    public Move findButton(JButton b) {
        //search for button in button array, return location as Move
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (getButtons()[i][j] == b) {
                    return new Move(i, j);
                }
            }
        }
        return null;
    }

    /**
     * method to create a new game from the command line
     *
     * @param args arguments passed from command line
     */
    private static void makeAGame(String[] args) {
        try {
            if (args.length == 0) { //default to 8x8
                new Reversi();
            } else if (args.length == 1 && Integer.parseInt(args[0]) >= 4 && Integer.parseInt(args[0]) < 22) { //dimensions must be at least 4x4, less than 22x22
                new Reversi(Integer.parseInt(args[0]));
            } else if (args.length == 2 && Integer.parseInt(args[0]) >= 4 && Integer.parseInt(args[1]) >= 4 && Integer.parseInt(args[0]) < 22 && Integer.parseInt(args[1]) < 35) { //both dimensions must be larger than 4x4, smaller than 22x35
                new Reversi(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            } else {
                System.out.println("Please enter valid dimensions");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter numbers");
        }
    }

    /**
     * Main method to run the game
     * <p>
     * If no arguments are given, makes a new 8x8 game with computer playing white
     * If one is given, makes a new square game with the specified dimensions
     * If two are given, makes a rectangular board with the first argument as the height and the second argument as the width
     * </p>
     *
     * @param args arguments to parse
     */
    public static void main(String[] args) {
        makeAGame(args);
    }
}