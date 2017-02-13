/**
 * Class to create an 8x8 game against a computer with a more complex evaluation function than ReversiAIWeak
 *
 * @author Nikil Pancha
 */
public class BitBoardAI extends ReversiAIWeak {
    public BitBoardAI(int computer) {
        super(8, 8, computer);
    }
    
    /**
     * Method to get the best move for the computer
     *
     * @return Move that is the best based on the bitboard evaluation function
     */
    @Override
    public Move computerMove() {
        return BB2H.moveToMake(getWhoseTurn(), getState());
    }
    
    /**
     * Method to create a game by parsing the arguments from the command line
     *
     * @param args array of strings to parse
     */
    public static void makeAGame(String[] args) {
        try {
            if (args.length == 0){
                new BitBoardAI(0);
            }else if (args.length != 1 || !(Integer.parseInt(args[0]) == 1 || Integer.parseInt(args[0]) == 0)) {
                System.out.println("Please enter a valid computer player");
            } else {
                new BitBoardAI(Integer.parseInt(args[0]));
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter numbers");
        }
    }
    
    /**
     * Main method to run game
     * <p>
     * Takes 1 argument: which player is the computer.  If the input is "1", the computer plays black, and if it's "0", the computer plays white.  Otherwise, the program will exit.
     * </p>
     *
     * @param args input to parse to make a game
     */
    public static void main(String[] args) {
        makeAGame(args);
    }
}
