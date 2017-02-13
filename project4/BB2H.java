import java.util.*;

/**
 * Class to analyze bitboards and determine moves
 *
 * @author Nikil Pancha
 */
public class BB2H extends BB2 {

    /**
     * Stores the best move from the minimax search
     */
    private static long[] moved;
    /**
     * Stores the maximum depth of the current search
     */
    private static int maxDepth;

    /**
     * Getter for the maxiumum search depth
     *
     * @return value of the maxiumum search depth
     */
    public static int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Setter for the maxiumu search depth
     *
     * @param maxDepth new value of maxiumum search depth
     */
    public static void setMaxDepth(int maxDepth) {
        BB2H.maxDepth = maxDepth;
    }

    /**
     * Gets the value of a position based on several parameters
     *
     * @param thisPlayer  bitboard representing the position of player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return value of the players position
     */
    public static double positionValue(long thisPlayer, long otherPlayer) {
        if (count(~(thisPlayer | otherPlayer)) < 13) { //fewer than 13 open spaces left
            return innerCornerPieces(thisPlayer) * -200. +
                    cornerPieces(thisPlayer) * 200. +
                    edgePieces(thisPlayer) * 35. +
                    threeByFour(thisPlayer) * 200.0 +
                    twoByFive(thisPlayer) * 200.0 +
                    fullSides(thisPlayer) * 400.0 +
                    twoByFour(thisPlayer) * 200 +
                    innerEdgePieces(thisPlayer) * -25 +
                    freeEdges(thisPlayer, otherPlayer) * -35;
        } else {
            return (Long.bitCount(mergedMoveBoard(thisPlayer, otherPlayer)) - Long.bitCount(mergedMoveBoard(otherPlayer, thisPlayer))) * 85. +
                    innerCornerPieces(thisPlayer) * -200. +
                    (cornerPieces(thisPlayer) - cornerPieces(otherPlayer)) * 650. +
                    (edgePieces(thisPlayer) - edgePieces(otherPlayer)) * 159. +
                    (threeByFour(thisPlayer) - threeByFour(otherPlayer)) * 200.0 +
                    (twoByFive(thisPlayer) - twoByFive(otherPlayer)) * 200.0 +
                    (fullSides(thisPlayer) - fullSides(otherPlayer)) * 200.0 +
                    (twoByFour(thisPlayer) - twoByFour(otherPlayer)) * 200 +
                    (fourCorner(thisPlayer) - fourCorner(otherPlayer)) * 200 +
                    (threeCorner(thisPlayer) - fourCorner(otherPlayer)) * 200 +
                    innerEdgePieces(thisPlayer) * -50 +
                    (freeEdges(thisPlayer, otherPlayer) - freeEdges(otherPlayer, thisPlayer)) * -35;
        }
    }

    /**
     * Determines whether or not game is over
     *
     * @param state bitboard array with both players' moves to check
     * @return true if no moves remain, false otherwise
     */
    public static boolean endOfGame(long[] state) {
        return allMoves(state[1], state[0]).size() == 0 && allMoves(state[0], state[1]).size() == 0;
    }

    /**
     * Gets array of all possible resultant positions
     *
     * @param thisPlayer  bitboard with position of player whose move it is
     * @param otherPlayer bitboard with other player's position
     * @return array of bitboards with the first representing this player's position and the second representing the other player's position
     */
    public static long[][] allPositions(long thisPlayer, long otherPlayer) {
        LinkedList<Long> moveList = allMoves(thisPlayer, otherPlayer);
        long[][] permutes = new long[moveList.size()][2];
        int k = 0;
        //make a move for each move in the list of moves
        for (long move : moveList) {
            permutes[k] = makeMove(move, thisPlayer, otherPlayer);
            k++;
        }
        return permutes;
    }

    /**
     * Search to find optimal move based on evaluation function and a minimax algorithm
     *
     * @param thisPlayer  bitboard of player of interest's position
     * @param otherPlayer bitboard of other player's position
     * @param depth       depth to search to
     * @param alpha       low cutoff
     * @param beta        high cutoff
     * @param maxPlayer   boolean representing whether or not the player is the maximizing player
     * @return value of the optimal move
     */
    public static double alphaBeta(long thisPlayer, long otherPlayer, int depth, double alpha, double beta, boolean maxPlayer) {
        if (endOfGame(new long[]{thisPlayer, otherPlayer})) {
            int mp = count(thisPlayer);
            int op = count(otherPlayer);
            if (mp > op) {
                return 100000; //if this player wins, very good
            } else if (op > mp) {
                return -100000; //if other player wins, very bad
            } else {
                return -100; //if tie, acceptable
            }
        } else if (depth == 0) {
            return positionValue(thisPlayer, otherPlayer);
        } else {
            long[][] moveArray = allPositions(thisPlayer, otherPlayer);
            if (moveArray.length == 0) {
                return alphaBeta(otherPlayer, thisPlayer, depth, alpha, beta, !maxPlayer);
            } else {
                if (maxPlayer) { //find best value
                    double v = Double.NEGATIVE_INFINITY;
                    for (long[] child : moveArray) {
                        double y = alphaBeta(child[1], child[0], depth - 1, alpha, beta, false); //other player's turn
                        if (y > v) {
                            if (depth == getMaxDepth()) {
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
                } else { //find worst value
                    double v = Double.POSITIVE_INFINITY;
                    for (long[] child : moveArray) {
                        double y = alphaBeta(child[1], child[0], depth - 1, alpha, beta, true); //other player's turn
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
     * Getter for board after making ideal move
     *
     * @return board position after making move
     */
    public static long[] getMoved() {
        return moved;
    }

    /**
     * Set the position on the saved board
     *
     * @param moved board to set the position to
     */
    public static void setMoved(long[] moved) {
        BB2H.moved = moved;
    }

    /**
     * Gets the ideal move to make based on the board state
     *
     * @param state array of two bitboards to find the move in.  Finds a move for the first player
     * @return Move object with the best move to make
     */
    public static Move moveToMake(long[] state) {
        if (count(~(state[0] | state[1])) < 15) {
            setMaxDepth(14);
        } else {
            setMaxDepth(6); //good search depth, fairly quick
        }
        alphaBeta(state[0], state[1], getMaxDepth(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);

        long move = (getMoved()[0] | getMoved()[1]) & ~(state[0] | state[1]);
        return getMove(move);
    }

    /**
     * Gets the move to make from an integer array of the state
     *
     * @param player player to find move of
     * @param state  2d int array to find move in
     * @return Move object with the best move to make
     */
    public static Move moveToMake(int player, int[][] state) {
        return moveToMake(arrayToBB(player, state));
    }
}
