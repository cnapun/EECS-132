import java.util.LinkedList;

/**
 * Class to find optimal move with a fairly simple evaluation function
 * <p>
 * The function takes into account very few features (edges, piece count, possible moves), but works on any board size
 * </p>
 *
 * @author Nikil Pancha
 */
public class BoardHeuristic {

    /**
     * Weight given to number of pieces in end
     */
    private static final double END_COUNT_WEIGHT = 30;
    /**
     * weight given to avaliable moves
     */
    private static final double MOBILITY_WEIGHT = 75.0;
    /**
     * weight given to value of the position
     */
    private static final double POSITION_WEIGHT = 1;
    /**
     * weight given to open edges
     */
    private static final double EDGE_WEIGHT = -30.0;
    /**
     * Stores the position after making the best move
     */
    private static int[][] moved;

    /**
     * Method to evaluate the value of a player's position
     *
     * @param player player to evaluate
     * @param state  state to evaluate
     * @return double value of the state
     */
    public static double positionValue(int player, int[][] state) {
        int empties = countPieces(-1, state);
        int height = state.length;
        int width = state[0].length;
        if (empties < 18) {
            return (2 * countPieces(player, state) - (width * height - empties)) * END_COUNT_WEIGHT +
                    positionPoints(player, state) * POSITION_WEIGHT +
                    countFreeEdges(player, state) * EDGE_WEIGHT;
        }
        int myPieces = countPieces(player, state);
        return possibleMoves(player, state).size() * MOBILITY_WEIGHT / (myPieces + 1) +
                (positionPoints(player, state) - positionPoints((player + 1) % 2, state)) * POSITION_WEIGHT +
                myPieces * 1.0 / (countFreeEdges(player, state) + 1) * 35.;
    }

    /**
     * Check the validity of a move in a given state
     *
     * @param move  move to check validity of
     * @param state state to make move on
     * @return true if move is valid, false otherwise
     */
    private static boolean checkValidity(Move move, int[][] state) {
        int row = move.getRow();
        int col = move.getColumn();
        int player = move.getPlayer();
        int height = state.length;
        int width = state[0].length;
        if (state[row][col] < 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int ypos = row + i;
                    int xpos = col + j;
                    if (ypos >= 0 && xpos >= 0 && xpos < width && ypos < height) {
                        boolean checking = true;
                        boolean invalid = false;
                        int val = state[ypos][xpos];
                        int k = 0;
                        while (checking) {
                            ypos += i;
                            xpos += j;
                            if (val == player) {
                                checking = false;
                            } else if (val < 0 || xpos < 0 || ypos < 0 || xpos > width - 1 || ypos > height - 1) {
                                checking = false;
                                invalid = true;
                            } else {
                                k++;
                                val = state[ypos][xpos];
                            }

                        }
                        if (!invalid && k > 0) {
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

    /**
     * Count the pieces of a given player in a given state
     *
     * @param player player to count
     * @param state  pieces to count
     * @return Number of pieces player has in state
     */
    private static int countPieces(int player, int[][] state) {
        int count = 0;
        for (int[] row : state) {
            for (int i : row) {
                if (i == player) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * List possible moves of a player in a given state
     *
     * @param player player to get moves of
     * @param state  state to find moves in
     * @return LinkedList containing all possible moves
     */
    private static LinkedList<Move> possibleMoves(int player, int[][] state) {
        int height = state.length;
        int width = state[0].length;
        LinkedList<Move> moves = new LinkedList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Move move = new Move(i, j, player);
                if (checkValidity(move, state)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    /**
     * Get value of player's position
     * <p>
     * Corners: +100
     * Edge pieces inside corners: -25
     * Center Pieces sharing vertices with corner:-100
     * All other edge pieces: +25
     * </p>
     *
     * @param player player to evaluate position for
     * @param state  state to evaluate position in
     * @return value of position based on several parameters
     */
    private static int positionPoints(int player, int[][] state) {
        int height = state.length;
        int width = state[0].length;
        int[][] weightBoard = new int[height][width];
        int cornerWeight = 600;
        weightBoard[0][0] = cornerWeight;
        weightBoard[0][width - 1] = cornerWeight;
        weightBoard[height - 1][0] = cornerWeight;
        weightBoard[height - 1][width - 1] = cornerWeight;

        int insideWeight = -300;
        weightBoard[1][1] = insideWeight;
        weightBoard[1][width - 2] = insideWeight;
        weightBoard[height - 2][1] = insideWeight;
        weightBoard[height - 2][width - 2] = insideWeight;

        int insideSideWeight = -75;
        weightBoard[0][1] = insideSideWeight;
        weightBoard[1][0] = insideSideWeight;
        weightBoard[height - 1][1] = insideSideWeight;
        weightBoard[height - 2][0] = insideSideWeight;
        weightBoard[1][width - 1] = insideSideWeight;
        weightBoard[0][width - 2] = insideSideWeight;
        weightBoard[height - 1][width - 2] = insideSideWeight;
        weightBoard[height - 2][width - 1] = insideSideWeight;

        int value = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (state[i][j] == player) {
                    value += weightBoard[i][j];
                    if (i == height - 1 || i == 0 || j == width - 1 || j == 0) {
                        value += 50;
                    }
                }
            }
        }
        return value;

    }

    /**
     * Array of all possible positions from given state and player
     *
     * @param player player to find possible positions of
     * @param state  state to find possible positions in
     * @return array of all possible positions after player makes a move
     */
    private static int[][][] allPositions(int player, int[][] state) {
        LinkedList<Move> moves = possibleMoves(player, state);
        int[][][] permutes = new int[moves.size()][][];
        int i = 0;
        for (Move move : moves) {
            permutes[i++] = makeMove(move, state);
        }
        return permutes;
    }

    /**
     * Make a move given a move and a state
     *
     * @param move   move to make
     * @param state0 state to make move in
     * @return final state with move made
     */
    private static int[][] makeMove(Move move, int[][] state0) {
        int[][] state = arrayCopy2D(state0);
        int height = state.length;
        int width = state[move.getRow()].length;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int ypos = move.getRow() + i;
                int xpos = move.getColumn() + j;
                if (ypos >= 0 && xpos >= 0 && xpos < width && ypos < height) {
                    boolean checking = true;
                    boolean invalid = false;
                    int val = state[ypos][xpos];
                    int k = 0;
                    while (checking) {
                        ypos += i;
                        xpos += j;
                        if (val == move.getPlayer()) {
                            checking = false;
                        } else if (val < 0 || xpos < 0 || ypos < 0 || xpos > width - 1 || ypos > height - 1) {
                            checking = false;
                            invalid = true;
                        } else {
                            k++;
                            val = state[ypos][xpos];
                        }

                    }
                    if (!invalid && k > 0) {
                        int l2 = 0;
                        int l1 = 0;
                        int iter = 0;
                        while (iter < k + 1) {
                            state[move.getRow() + l1][move.getColumn() + l2] = move.getPlayer();
                            l1 += i;
                            l2 += j;
                            iter++;
                        }
                    }

                }
            }
        }
        return state;
    }

    /**
     * Helper method to copy elements of 2d array into new array
     *
     * @param matrix 2d array to copy
     * @return copy of 2d array
     */
    private static int[][] arrayCopy2D(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++)
            copy[i] = matrix[i].clone();
        return copy;
    }

    /**
     * Determines whether or not game is over
     *
     * @param state array of current state of board
     * @return true if neither player has a valid move, false otherwise
     */
    private static boolean endOfGame(int[][] state) {
        return possibleMoves(1, state).size() == 0 && possibleMoves(0, state).size() == 0;
    }

    /**
     * Recursive search to find the best value possible
     * <p>
     * For a given state, optimize score with an alpha-beta pruning minimax search.
     * </p>
     *
     * @param node      state to search
     * @param depth     recursion depth after cutoff
     * @param alpha     lower bound for search
     * @param beta      upper bound for search
     * @param maxPlayer player for whom the score is currently being maximized
     * @param player    player who is the original maximizing player
     * @return double value of position achieved by making the move that results in the worst move for the opponent
     */
    private static double alphaBeta(int[][] node, int depth, double alpha, double beta, int maxPlayer, int player) {
        if (endOfGame(node)) {
            int mp = countPieces(maxPlayer, node);
            int op = countPieces((maxPlayer + 1) % 2, node);
            if (mp > op) {
                return 100000;
            } else if (op > mp) {
                return -100000;
            } else {
                return positionValue(maxPlayer, node);
            }
        } else if (depth == 0) {
            return positionValue(maxPlayer, node);
        } else {

            int[][][] mArray = allPositions(maxPlayer, node);
            if (mArray.length == 0) {
                return alphaBeta(node, depth, alpha, beta, (maxPlayer + 1) % 2, player);
            } else {
                if (player == maxPlayer) {
                    double v = Double.NEGATIVE_INFINITY;
                    for (int[][] child : mArray) {
                        double y = alphaBeta(child, depth - 1, alpha, beta, (maxPlayer + 1) % 2, player);
                        if (y > v) {
                            if (depth == 6) {
                                setMoved(child);
                            }
                            v = y;
                        }
                        if (alpha < v) {
                            alpha = v;
                        }
                        if (beta <= alpha) {
                            return v;
                        }
                    }
                    return v;
                } else {
                    double v = Double.POSITIVE_INFINITY;
                    for (int[][] child : mArray) {
                        double y = alphaBeta(child, depth - 1, alpha, beta, (maxPlayer + 1) % 2, player);
                        if (y < v) {
                            v = y;
                        }
                        if (beta > v) {
                            beta = v;
                        }
                        if (beta <= alpha) {
                            return v;
                        }
                    }
                    return v;
                }
            }
        }
    }

    /**
     * Determines what move to make based on the evaluation function
     *
     * @param player player whose turn it is
     * @param state  state to find the move in
     * @return Move that is the optimal move as determined by the alpha-beta search
     */
    public static Move moveToMake(int player, int[][] state) {
        alphaBeta(state, 6, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, player, player);
        return andNot(state, getMoved());
    }

    /**
     * Getter for best child array in the search
     *
     * @return 2d int array with position after moving
     */
    public static int[][] getMoved() {
        return moved;
    }

    /**
     * Setter for best child array in the search
     *
     * @param moved 2d int array to change value to
     */
    public static void setMoved(int[][] moved) {
        BoardHeuristic.moved = moved;
    }

    /**
     * Gets the move played from an initial and final state
     * <p>
     * Equivalent of not(black_initial or white_initial) and (black_final or white_final) with a bitboard
     * Returns a move in that position
     * </p>
     *
     * @param first  initial array to compare
     * @param second final array with exactly one change
     * @return Move with the space the move was made in the second array
     */
    public static Move andNot(int[][] first, int[][] second) {
        int[][] out = new int[first.length][first[0].length];
        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < out[i].length; j++) {
                if (first[i][j] == -1 && second[i][j] != -1) {
                    return new Move(i, j);
                }
            }
        }
        return null;

    }

    /**
     * Count edges bordering empty tiles of the player
     * <p>
     * This does not count free corner tiles
     * </p>
     *
     * @param player player to count tiles for
     * @param state  state to count tiles in
     * @return number of edges bordering empty tiles
     */
    private static int countFreeEdges(int player, int[][] state) {
        int count = 0;
        for (int i = 0; i < state.length; i++) {
            int prev = -2;
            for (int j = 0; j < state[i].length; j++) {
                if ((prev == -1 && state[i][j] == player) || (prev == player && state[i][j] == -1)) {
                    count++;
                }
                prev = state[i][j];
            }
        }

        for (int i = 0; i < state[0].length; i++) {
            int prev = -2;
            for (int j = 0; j < state.length; j++) {
                if ((prev == -1 && state[j][i] == player) || (prev == player && state[j][i] == -1)) {
                    count++;
                }
                prev = state[j][i];
            }
        }
        return count;

    }

    /**
     * Make a random move
     *
     * @param player player to move
     * @param state  state to make move in
     * @return Move that is randomly selected from all possible moves
     */
    public static Move randomMove(int player, int[][] state) {
        LinkedList<Move> l = possibleMoves(player, state);
        int i = (int) (Math.random() * l.size());
        return l.get(i);
    }
}
