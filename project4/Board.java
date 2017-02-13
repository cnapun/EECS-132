import java.util.LinkedList;

/**
 * Class to represent the state of the board with no graphics
 */
public class Board {
    /**
     * Array to store the state of the game (black is 1, white is 0, empty is -1)
     */
    private int[][] state;
    /**
     * field to store height of board
     */
    private int height;
    /**
     * field to store width of board
     */
    private int width;

    /**
     * Setter method for state of the board to facilitate testing
     *
     * @param state state of board to set game to
     */
    public void setState(int[][] state) {
        this.state = state;
    }

    /**
     * field to store whose turn it is (black is 1, white is 0)
     */
    private int whoseTurn;

    /**
     * Constructor for a board
     * <p>
     * Initializes board with the default configuration.
     * -1 represents empty square
     * 0 represents white
     * 1 represents black
     * </p>
     *
     * @param height height of board to create
     * @param width  width of board to create
     */
    public Board(int height, int width) {
        state = new int[height][width];
        this.height = height;
        this.width = width;
        //Loop to initialize state to empty
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                state[i][j] = -1;
            }
        }
        //set center pieces of board to the appropriate colours
        state[height / 2][width / 2 - 1] = 1;
        state[height / 2 - 1][width / 2 - 1] = 0;
        state[height / 2][width / 2] = 0;
        state[height / 2 - 1][width / 2] = 1;
        //black goes first
        whoseTurn = 1;

    }

    /**
     * Get the player in a certain position
     *
     * @param row row of player
     * @param col column of player
     * @return -1 if position is empty, 0 if position is occupied by white, 1 if position is occupied by black
     */
    public int getPlayer(int row, int col) {
        return state[row][col];
    }

    /**
     * Retrieve whose turn it is
     *
     * @return 1 if black's turn, 0 if white's turn
     */
    public int getWhoseTurn() {
        return whoseTurn;
    }

    /**
     * Switches whose turn it is
     */
    protected void toggleWhoseTurn() {
        whoseTurn = (whoseTurn + 1) % 2;
    }

    /**
     * Retrieve the height of the board
     *
     * @return height of board
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retreieve the width of the board
     *
     * @return width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieve the array representing the state of the game
     *
     * @return array representing the state of the game
     */
    public int[][] getState() {
        return state;
    }

    /**
     * Set the player at the position of a move
     *
     * @param move Move containing location and player number
     */
    protected void setPlayer(Move move) {
        state[move.getRow()][move.getColumn()] = move.getPlayer();
    }

    /**
     * List of all possible moves in this position
     *
     * @return a LinkedList of all possible moves in this state
     */
    public LinkedList<Move> possibleMoves() {
        //maximum size possible
        LinkedList<Move> moveList = new LinkedList<>();
        //test each position for validity, and if it is valid, add to the list of moves
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Move move = new Move(i, j, getWhoseTurn());
                if (checkValidity(move)) {
                    moveList.add(move);
                }
            }
        }
        return moveList;
    }

    /**
     * Checks if a move is valid
     *
     * @param move Move to check, with or without player specified
     * @return true if the move is valid, false if invalid
     */
    public boolean checkValidity(Move move) {
        //If move does not specify player, set player to the current player
        if (move.getPlayer() < 0) {
            move.setPlayer(getWhoseTurn());
        }
        //only check if space is not yet occupied
        if (getPlayer(move.getRow(), move.getColumn()) < 0) {
            //loop through all 8 possible directions
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int ypos = move.getRow() + i; //store the first row to check
                    int xpos = move.getColumn() + j; //store the first column to check
                    if (ypos >= 0 && xpos >= 0 && xpos < getWidth() && ypos < getHeight()) { //make sure position is within bounds
                        boolean checking = true; //boolean to track whether or not to continue checking
                        boolean invalid = false; //track whether or not move is invalid
                        int val = getPlayer(ypos, xpos); //player in initial position
                        int k = 0; //index to track how many tiles are flipped
                        //loop until empty or same colour tile is reached
                        while (checking) {
                            ypos += i; //move position one square in direction of checking
                            xpos += j;
                            if (val == move.getPlayer()) {
                                checking = false;
                            } else if (val < 0 || xpos < 0 || ypos < 0 || xpos > getWidth() - 1 || ypos > getHeight() - 1) { //check for out of bounds or empty square
                                checking = false;
                                invalid = true;
                            } else { //tile is of the opposite colour
                                k++;
                                val = getPlayer(ypos, xpos);
                            }

                        }
                        if (!invalid && k > 0) { //if the move was not marked invalid and one tile was of the opposing colour, there is a valid move
                            return true;
                        }

                    }
                }
            }
        }
        return false; //move not found
    }

    /**
     * Makes a move
     * <p>
     * Changes the state to reflect the position after making a move
     * </p>
     *
     * @param move move to make
     */
    public void makeMove(Move move) {
        //If move does not specify player, set player to the current player
        if (move.getPlayer() < 0) {
            move.setPlayer(whoseTurn);
        }
        //loop through all 8 possible directions
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int ypos = move.getRow() + i; //store the first row to check
                int xpos = move.getColumn() + j; //store the first column to check
                if (ypos >= 0 && xpos >= 0 && xpos < getWidth() && ypos < getHeight()) { //make sure position is within bounds
                    boolean checking = true; //boolean to track whether or not to continue checking
                    boolean invalid = false; //track whether or not move is invalid
                    int val = getPlayer(ypos, xpos); //player in initial position
                    int k = 0; //index to track how many tiles are flipped
                    //loop until empty or same colour tile is reached
                    while (checking) {
                        ypos += i; //move position one square in direction of checking
                        xpos += j;
                        if (val == move.getPlayer()) {
                            checking = false;
                        } else if (val < 0 || xpos < 0 || ypos < 0 || xpos > getWidth() - 1 || ypos > getHeight() - 1) { //check for out of bounds or empty square
                            checking = false;
                            invalid = true;
                        } else { //tile is opposite colour, may be flipped
                            k++;
                            val = getPlayer(ypos, xpos);
                        }

                    }
                    //if valid move, flip the tiles (not necessarily necessary, but more reassuring)
                    if (!invalid && k > 0) {
                        int columnShift = 0; //stores offset from move to tile to flip
                        int rowShift = 0;
                        int iter = 0;
                        while (iter < k + 1) { //make the first k+1 tiles the colour of the player
                            setPlayer(new Move(move.getRow() + rowShift, move.getColumn() + columnShift, move.getPlayer())); //set the player
                            rowShift += i;
                            columnShift += j;
                            iter++;
                        }
                    }

                }
            }
        }
        toggleWhoseTurn(); //switch turn after move is made
    }

    /**
     * Determines whether or not the game is over
     *
     * @return true if neither player has a valid move, false otherwise
     */
    public boolean gameOver() {
        return !hasAMove(1) && !hasAMove(0); //if neither player has a move, the game is over
    }

    /**
     * Checks if a player has a move
     *
     * @param player player to check for moves
     * @return true if player has a move, false otherwise
     */
    public boolean hasAMove(int player) {
        //check squares sequentially until there is one with a valid more.  If there are none, return false
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Move move = new Move(i, j, player);
                if (getPlayer(i, j) < 0 && checkValidity(move)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Checks if current player has a move
     *
     * @return true if current player has a move, false otherwise
     */
    public boolean hasAMove() {
        return hasAMove(getWhoseTurn());
    }

    /**
     * Count the number of squares occupied by a player
     *
     * @param player player to count
     * @return number of squares occupied by player
     */
    public int countSquares(int player) {
        int count = 0; //variable to count number of squares
        //count squares with value equal to player
        for (int[] row : getState()) {
            for (int square : row) {
                if (square == player) {
                    count++;
                }
            }
        }
        return count;
    }
}
