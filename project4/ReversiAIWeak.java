import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class with an AI that incorporates the evaluation function from BoardHeuristic
 * <p>
 * It occasionally makes poor moves, and introduces no randomness into its calculations.
 * </p>
 *
 * @author Nikil Pancha
 */
public class ReversiAIWeak extends Reversi {
    /**
     * field to store which player is the computer
     */
    private int computer;

    /**
     * Constructor to make a new game of a specified height and with with a player as computer
     * <p>
     * 0 corresponds to white computer
     * 1 corresponds to black computer
     * </p>
     *
     * @param height   height of board
     * @param width    width of board
     * @param computer number to tell which player is computer
     */
    public ReversiAIWeak(int height, int width, int computer) {
        super(height, width);
        this.computer = computer;
        if (computer == getWhoseTurn()) {
            makeMove(computerMove());
        }
        updatePrettyBoard();
    }

    /**
     * Constructor to make a new 8x8 game
     *
     * @param computer number to tell which player is computer
     */
    public ReversiAIWeak(int computer) {
        this(8, 8, computer);
    }

    /**
     * Constructor to make a new square game of a given size
     *
     * @param size     size of square board to create
     * @param computer number to tell which player is computer
     */
    public ReversiAIWeak(int size, int computer) {
        this(size, size, computer);
    }

    /**
     * Override actionPerformed to make the player's move and then make computer moves until the player has a valid move
     *
     * @param e JButton clicked event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        Move move = findButton(b);
        if (checkValidity(move)) {
            makeMove(move);
            updatePrettyBoard();
        } else {
            JOptionPane.showMessageDialog(getFrame(), "Not a valid move\nPlease choose another space");
        }
        postMove();
        //keep making moves until the non-computer player has a move
        while (getWhoseTurn() == computer && hasAMove()) {
            makeMove(computerMove());
            updatePrettyBoard();
            if (!hasAMove() && hasAMove(computer)) {
                String text;
                if (getWhoseTurn() == 1) {
                    text = "Black has no valid moves: White's turn";
                } else {
                    text = "White has no valid moves: Black's turn";
                }
                JOptionPane.showMessageDialog(getFrame(), text);
                toggleWhoseTurn();
            }
            postMove();
        }
    }

    /**
     * sequence of actions to perform after a move is made
     */
    private void postMove() {
        if (gameOver()) {
            endOfGame();
        } else if (!hasAMove()) {
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
     * Computer makes a move given the turn and state of the board
     *
     * @return Move that is optimal based on a simple evaluation function
     */
    public Move computerMove() {
        return BoardHeuristic.moveToMake(getWhoseTurn(), getState());
    }

    /**
     * method to create a new game from the command line
     *
     * @param args arguments passed from command line
     */
    private static void makeAGame(String[] args) {
        try {
            if (args.length == 0) {
                new ReversiAIWeak(0);
            } else if (args.length == 1 && (Integer.parseInt(args[0]) == 0 || Integer.parseInt(args[0]) == 1)) {
                new ReversiAIWeak(Integer.parseInt(args[0]));
            } else if (args.length == 2 && Integer.parseInt(args[0]) >= 4 && Integer.parseInt(args[0]) < 22 && (Integer.parseInt(args[1]) == 0 || Integer.parseInt(args[1]) == 1)) { //dimensions must be at least 4x4, less than 22x22
                new ReversiAIWeak(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            } else if (args.length == 3 && Integer.parseInt(args[0]) >= 4 && Integer.parseInt(args[1]) >= 4 && Integer.parseInt(args[0]) < 22 && Integer.parseInt(args[1]) < 35 && (Integer.parseInt(args[2]) == 0 || Integer.parseInt(args[2]) == 1)) { //both dimensions must be larger than 4x4, smaller than 22x35
                new ReversiAIWeak(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else {
                System.out.println("Please enter valid parameters");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter numbers");
        }
    }

    /**
     * Main method to run the game
     * <p>
     * If no arguments are given, makes a new 8x8 game with computer playing white
     * If one is given, makes a new 8x8 game with the computer as the specified player
     * If two are given, makes a square board with the dimension as the first argument and the computer player as the second argument
     * If three are given, makes a board with width, height, and the computer player, in that order
     * </p>
     *
     * @param args arguments to parse
     */
    public static void main(String[] args) {
        makeAGame(args);
    }

}
