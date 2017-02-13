import java.util.LinkedList;

/**
 * Class to do operations with 8x8 BitBoards
 * <p>
 * Most significant bit is in the top left corner, lest significant in the bottom right
 * </p>
 *
 * @author Nikil Pancha
 */
public class BB2 {

    //masks for the edges of the board
    private static final long LEFT_MASK = 0x8080808080808080L;
    private static final long RIGHT_MASK = 0x0101010101010101L;
    private static final long UP_MASK = 0xFF00000000000000L;
    private static final long DOWN_MASK = 0x00000000000000FFL;

    /**
     * Shift bitboard up one row
     *
     * @param board bitboard to shift
     * @return bitboard shifted up one row
     */
    public static long shiftUp(long board) {
        return (board & ~UP_MASK) << 8;
    }

    /**
     * Shift bitboard down one row
     *
     * @param board bitboard to shift
     * @return bitboard shifted down one row
     */
    public static long shiftDown(long board) {
        return (board & ~DOWN_MASK) >> 8;
    }

    /**
     * Shift bitboard left one column
     *
     * @param board bitboard to shift
     * @return bitboard shifted left one one column
     */
    public static long shiftLeft(long board) {
        return (board & ~LEFT_MASK) << 1;
    }

    /**
     * Shift bitboard right one column
     *
     * @param board bitboard to shift
     * @return bitboard shifted right one column
     */
    public static long shiftRight(long board) {
        return (board & ~RIGHT_MASK) >> 1;
    }

    /**
     * Shift bitboard up one row and to the right one column
     *
     * @param board bitboard to shift
     * @return bitboard shifted up one row and right one column
     */
    public static long shiftUpRight(long board) {
        return (board & (~RIGHT_MASK) & (~UP_MASK)) << 7;
    }

    /**
     * Shift bitboard up one row and to the left one column
     *
     * @param board bitboard to shift
     * @return bitboard shifted up one row and left one column
     */
    public static long shiftUpLeft(long board) {
        return (board & (~LEFT_MASK) & (~UP_MASK)) << 9;
    }

    /**
     * Shift bitboard down one row and to the left one column
     *
     * @param board bitboard to shift
     * @return bitboard shifted down one row and left one column
     */
    public static long shiftDownLeft(long board) {
        return (board & (~LEFT_MASK) & (~DOWN_MASK)) >> 7;
    }

    /**
     * Shift bitboard down one row and to the right one column
     *
     * @param board bitboard to shift
     * @return bitboard shifted down one row and right one column
     */
    public static long shiftDownRight(long board) {
        return (board & (~RIGHT_MASK) & (~DOWN_MASK)) >> 9;
    }

    /**
     * Get the column of a move
     *
     * @param move bitboard with exactly one bit set
     * @return column of set bit in the bitboard
     */
    public static int getColumn(long move) {
        long mask = 0x8000000000000000L;
        int i = 0;
        while ((move & mask) == 0) {
            mask >>= 1;
            i++;
        }
        return 7 - i % 8;
    }

    /**
     * Get a bitboard representing all moves
     *
     * @param thisPlayer  bitboard representing this player's position
     * @param otherPlayer bitboard representing other player's position
     * @return bitboard with set bits representing all possible moves of thisPlayer
     */
    public static long mergedMoveBoard(long thisPlayer, long otherPlayer) {
        return (leftMoves(thisPlayer, otherPlayer)) |
                (downLeftMoves(thisPlayer, otherPlayer)) |
                (downMoves(thisPlayer, otherPlayer)) |
                (downRightMoves(thisPlayer, otherPlayer)) |
                (rightMoves(thisPlayer, otherPlayer)) |
                (upRightMoves(thisPlayer, otherPlayer)) |
                (upMoves(thisPlayer, otherPlayer)) |
                (upLeftMoves(thisPlayer, otherPlayer));
    }

    /**
     * Get the row of a move
     *
     * @param move bitboard with exactly one bit set
     * @return row of set bit in the bitboard
     */
    public static int getRow(long move) {
        long mask = 0x8000000000000000L;
        int i = 0;
        while ((move & mask) == 0) {
            mask >>= 1;
            i++;
        }
        return i / 8;
    }

    /**
     * Flip opposing players tiles given move
     *
     * @param move        bitboard representing a move
     * @param thisPlayer  bitboard representing the position of the player to move
     * @param otherPlayer bitboard representing the player whose tiles will be flipped
     * @return position of the player to move after playing the move
     */
    public static long moveSinglePlayer(long move, long thisPlayer, long otherPlayer) {
        long fullMove = 0L;
        long empty = ~thisPlayer & ~otherPlayer & ~move;

        //move down
        long moveShift = move;
        int k = 0;
        boolean found = false;
        //stop once it has been moved too many times or something has been found
        while (k < 8 && !found) {
            moveShift |= shiftDown(moveShift); //smear move downwards
            if ((moveShift & empty) != 0) { //
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }

        //move up
        moveShift = move;
        k = 0;
        found = false;
        while (k < 8 && !found) {
            moveShift |= shiftUp(moveShift);
            if ((moveShift & empty) != 0) {
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }
        //move left
        moveShift = move;
        k = 0;
        found = false;
        while (k < 8 && !found) {
            moveShift |= shiftLeft(moveShift);
            if ((moveShift & empty) != 0) {
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }
        //move right
        moveShift = move;
        k = 0;
        found = false;
        while (k < 8 && !found) {
            moveShift |= shiftRight(moveShift);
            if ((moveShift & empty) != 0) {
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }
        //move up-left
        moveShift = move;
        k = 0;
        found = false;
        while (k < 8 && !found) {
            moveShift |= shiftUpLeft(moveShift);
            if ((moveShift & empty) != 0) {
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }
        //move up-right
        moveShift = move;
        k = 0;
        found = false;
        while (k < 8 && !found) {
            moveShift |= shiftUpRight(moveShift);
            if ((moveShift & empty) != 0) {
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }
        //move down-left
        moveShift = move;
        k = 0;
        found = false;
        while (k < 8 && !found) {
            moveShift |= shiftDownLeft(moveShift);
            if ((moveShift & empty) != 0) {
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }
        //move down-right
        moveShift = move;
        k = 0;
        found = false;
        while (k < 8 && !found) {
            moveShift |= shiftDownRight(moveShift);
            if ((moveShift & empty) != 0) {
                found = true;
            } else if ((moveShift & thisPlayer) != 0 && k > 0) {
                found = true;
                fullMove |= moveShift;
            }
            k++;
        }
        return (fullMove & otherPlayer) | thisPlayer | move;
    }

    /**
     * Make a move given a move to make
     *
     * @param move        bitboard representing a move
     * @param thisPlayer  bitboard representing the position of the player to move
     * @param otherPlayer bitboard representing the player whose tiles will be flipped
     * @return long array with the first element as a bitboard of the first player after the move and the second element as the second player
     */
    public static long[] makeMove(long move, long thisPlayer, long otherPlayer) {
        long mv = moveSinglePlayer(move, thisPlayer, otherPlayer);
        return new long[]{mv, otherPlayer & ~mv};
    }

    /**
     * Convert a bitboard into a Move object
     *
     * @param move bitboard representing a move (with one set bit)
     * @return Move with the location of the set bit
     */
    public static Move getMove(long move) {
        return new Move(getRow(move), getColumn(move));
    }

    /**
     * Moves possible that will flip tiles below it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles below it
     */
    public static long upMoves(long thisPlayer, long otherPlayer) {
        long res = 0L;
        long empty = ~thisPlayer & ~otherPlayer;
        long possibleMoves = thisPlayer;
        //move is possible if current position moved up intersects with other player
        while ((possibleMoves = (shiftUp(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftUp(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Moves possible that will flip tiles above it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles above it
     */
    public static long downMoves(long thisPlayer, long otherPlayer) {
        long res = 0L;
        long empty = ~thisPlayer & ~otherPlayer;
        long possibleMoves = thisPlayer;
        while ((possibleMoves = (shiftDown(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftDown(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Moves possible that will flip tiles to the right of it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles to the right of it
     */
    public static long leftMoves(long thisPlayer, long otherPlayer) {
        long res = 0L;
        long empty = ~thisPlayer & ~otherPlayer;
        long possibleMoves = thisPlayer;
        while ((possibleMoves = (shiftLeft(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftLeft(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Moves possible that will flip tiles to the left of it it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles to the left of it
     */
    public static long rightMoves(long thisPlayer, long otherPlayer) {
        long empty = ~thisPlayer & ~otherPlayer;
        long res = 0L;
        long possibleMoves = thisPlayer;
        while ((possibleMoves = (shiftRight(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftRight(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Moves possible that will flip tiles below and to the left of it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles below and to the left of it
     */
    public static long upRightMoves(long thisPlayer, long otherPlayer) {
        long empty = ~thisPlayer & ~otherPlayer;
        long res = 0L;
        long possibleMoves = thisPlayer;
        while ((possibleMoves = (shiftUpRight(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftUpRight(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Moves possible that will flip tiles below and to the right of it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles below and to the right of it
     */
    public static long upLeftMoves(long thisPlayer, long otherPlayer) {
        long res = 0L;
        long empty = ~thisPlayer & ~otherPlayer;
        long possibleMoves = thisPlayer;
        while ((possibleMoves = (shiftUpLeft(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftUpLeft(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Moves possible that will flip tiles above and to the left of it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles above and to the left of it
     */
    public static long downRightMoves(long thisPlayer, long otherPlayer) {
        long res = 0L;
        long empty = ~thisPlayer & ~otherPlayer;
        long possibleMoves = thisPlayer;
        while ((possibleMoves = (shiftDownRight(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftDownRight(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Moves possible that will flip tiles above and to the right of it
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with moves that will flip tiles above and to the right of it
     */
    public static long downLeftMoves(long thisPlayer, long otherPlayer) {
        long res = 0L;
        long empty = ~thisPlayer & ~otherPlayer;
        long possibleMoves = thisPlayer;
        while ((possibleMoves = (shiftDownLeft(possibleMoves) & otherPlayer)) != 0b0L) {
            res |= (shiftDownLeft(possibleMoves) & empty);
        }
        return res;
    }

    /**
     * Get set of all moves that can be made from a given position
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return bitboard with all possible moves from given position
     */
    public static LinkedList<Long> allMoves(long thisPlayer, long otherPlayer) {
        return long2Moves(mergedMoveBoard(thisPlayer, otherPlayer));
    }

    /**
     * Split bitboard with multiple positions into a set of bitboards with one set position
     *
     * @param moves bitboard with multiple moves to split
     * @return Set of bitboards, each representing a single move
     */
    public static LinkedList<Long> long2Moves(long moves) {
        LinkedList<Long> out = new LinkedList<Long>();
        //while there is a set bit in moves
        while ((moves & (-moves)) != 0) {
            long isolated = moves & (-moves); //isolate rightmost set bit
            out.add(isolated);
            moves &= ~isolated; //remove rightmost set bit

        }
        return out;
    }

    /**
     * Converts a LinkedList of Moves to a LinkedList of bitboards
     *
     * @param list list of moves to convert
     * @return list of bitboards, each containing one move
     */
    public static LinkedList<Long> moves2Long(LinkedList<Move> list) {
        LinkedList<Long> output = new LinkedList<>();
        for (Move move : list) {
            long longMove = 1L << (move.getRow() * 8 + move.getColumn());
            output.add(longMove);
        }
        return output;
    }

    /**
     * Convert two bitboards to an array with two players
     *
     * @param blackPlayer bitboard representing the position of black
     * @param whitePlayer bitboard representing the position of white
     * @return array with the position of black marked as 1 and white marked as 0
     */
    public static int[][] bbToArray(long blackPlayer, long whitePlayer) {
        int[][] out = new int[8][8];
        long k = 0b1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((k & (blackPlayer)) == k) {
                    out[i][j] = 1;
                } else if ((k & (whitePlayer)) == k) {
                    out[i][j] = 0;
                } else {
                    out[i][j] = -1;
                }
                k <<= 1;
            }
        }
        return out;
    }

    /**
     * Convert a single bitboard to an array
     *
     * @param player bitboard to convert
     * @return array with the position of the set bits as 1, and the rest as 0
     */
    public static int[][] bbToArray(long player) {
        int[][] out = new int[8][8];
        long k = 0b1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((k & player) == k) {
                    out[i][j] = 1;
                } else {
                    out[i][j] = 0;
                }
                k <<= 1;
            }
        }
        return out;
    }

    /**
     * Convert an array with two players into two bitboards
     *
     * @param player player to put first in the resultant array (either 0 or 1)
     * @param state  array representing the game
     * @return long array with two bitboards, the first the position of player, and the second the position of the other player
     */
    public static long[] arrayToBB(int player, int[][] state) {
        long out1 = 0L;
        long out2 = 0L;
        for (int i = 0; i < state.length; i++) {
            for (int j = state[0].length - 1; j >= 0; j--) {
                out1 <<= 1;
                out2 <<= 1;
                if (state[i][j] == player) {
                    out1 |= 1;
                } else if (state[i][j] == (player + 1) % 2) {
                    out2 |= 1;
                }
            }
        }
        return new long[]{out1, out2};
    }

    /**
     * More convenient function to count set bits
     *
     * @param position bitboard to count set bits
     * @return number of set bits
     */
    public static int count(long position) {
        return Long.bitCount(position);
    }

    /**
     * Flatten set of moves into single bitboard
     *
     * @param ll LinkedList of moves to flatten
     * @return bitboard with moves represented as set bits
     */
    public static long llFlatten(LinkedList<Long> ll) {
        long out = 0L;
        for (long i : ll) {
            out |= i;
        }
        return out;
    }

    /**
     * Count the number of free edges and corners of a position
     *
     * @param thisPlayer  bitboard representing the position of the player of interest
     * @param otherPlayer bitboard representing the position of the other player
     * @return the number of adjacent cells that are not occupied
     */
    public static int freeEdges(long thisPlayer, long otherPlayer) {
//        long smear = shiftDown(thisPlayer) | shiftDownLeft(thisPlayer) | shiftDownRight(thisPlayer) | shiftLeft(thisPlayer) | shiftRight(thisPlayer) | shiftUpLeft(thisPlayer) | shiftUp(thisPlayer) | shiftUpRight(thisPlayer);

        long smear = ((thisPlayer << 8) |
                (thisPlayer >> 8) |
                (thisPlayer << 1) |
                (thisPlayer >> 1) |
                (thisPlayer << 7) |
                (thisPlayer << 9) |
                (thisPlayer >> 9) |
                (thisPlayer >> 7)) & ~UP_MASK & ~DOWN_MASK & ~LEFT_MASK & ~RIGHT_MASK;

        return count(smear & ~thisPlayer & ~otherPlayer);
    }

    /**
     * Count number of pieces on edge
     *
     * @param board position to count pieces of
     * @return number of edge pieces
     */
    public static int edgePieces(long board) {
        return count(board & (UP_MASK | DOWN_MASK | LEFT_MASK | RIGHT_MASK));
    }

    /**
     * Count the number of pieces in corners (a1, a8, h1, h8)
     *
     * @param board position to count
     * @return number of pieces in corners
     */
    public static int cornerPieces(long board) {
        return count(board & (0x8100000000000081L));
    }

    /**
     * Count the number of pieces in the squares inside corners (b2, b7, g2, g7)
     *
     * @param board position to count
     * @return number pieces in the squares inside corners
     */
    public static int innerCornerPieces(long board) {
        return count(board & 0x0042000000004200L);
    }

    public static int innerEdgePieces(long board) {
        long m1 = 0x4200000000000042L;
        long m2 = 0x0081000000008100L;
        return count(board & (m1 | m2));
    }

    /**
     * Count the number of three by four corners occupied by player
     *
     * @param board position to count
     * @return number of three by four corners occupied by player
     */
    public static int threeByFour(long board) {
        long horzCorner = 0xF0F0F00000000000L;
        long vertCorner = 0xE0E0E0E000000000L;
        int count = 0;
        count += (board & horzCorner) == horzCorner ? 1 : 0;
        count += ((horzFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += ((vertFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += (horzFlip(vertFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += (board & vertCorner) == vertCorner ? 1 : 0;
        count += (horzFlip(vertFlip(board)) & vertCorner) == vertCorner ? 1 : 0;
        count += ((vertFlip(board)) & vertCorner) == vertCorner ? 1 : 0;
        count += ((horzFlip(board)) & vertCorner) == vertCorner ? 1 : 0;
        return count;
    }

    /**
     * Count number of full sides in position
     *
     * @param board position to count
     * @return number of full sides in position
     */
    public static int fullSides(long board) {
        int count = 0;
        count += (board & UP_MASK) == UP_MASK ? 1 : 0;
        count += (board & RIGHT_MASK) == RIGHT_MASK ? 1 : 0;
        count += (board & LEFT_MASK) == LEFT_MASK ? 1 : 0;
        count += (board & DOWN_MASK) == DOWN_MASK ? 1 : 0;
        return count;
    }

    /**
     * Count number of two by five corners
     *
     * @param board position to count
     * @return number of two by five corners
     */
    public static int twoByFive(long board) {
        int count = 0;
        long horzCorner = 0xF8F8000000000000L;
        long vertCorner = 0x1F1F000000000000L;
        count += (board & horzCorner) == horzCorner ? 1 : 0;
        count += ((horzFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += ((vertFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += (horzFlip(vertFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += (board & vertCorner) == vertCorner ? 1 : 0;
        count += (horzFlip(board) & vertCorner) == vertCorner ? 1 : 0;
        count += (vertFlip(board) & vertCorner) == vertCorner ? 1 : 0;
        count += (horzFlip(vertFlip(board)) & vertCorner) == vertCorner ? 1 : 0;
        return count;
    }

    /**
     * Count number of two by four corners
     *
     * @param board position to count
     * @return number of two by four corners
     */
    public static int twoByFour(long board) {
        long horzCorner = 0xF0F0000000000000L;
        long vertCorner = 0xC0C0C0C000000000L;
        int count = 0;
        //using the flips every time is highly inefficient, but it does not impact performance much with a shallow search
        count += (board & horzCorner) == horzCorner ? 1 : 0;
        count += ((horzFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += ((vertFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += (horzFlip(vertFlip(board)) & horzCorner) == horzCorner ? 1 : 0;
        count += (board & vertCorner) == vertCorner ? 1 : 0;
        count += (horzFlip(board) & vertCorner) == vertCorner ? 1 : 0;
        count += (vertFlip(board) & vertCorner) == vertCorner ? 1 : 0;
        count += (horzFlip(vertFlip(board)) & vertCorner) == vertCorner ? 1 : 0;
        return count;
    }

    /**
     * Count corners filled along diagonal of 4x4 square in corner (fully stable corner)
     *
     * @param board position to count
     * @return number of 4x4 corners filled along diagonal
     */
    public static int fourCorner(long board) {
        long topLeft = 0xF0E0C08000000000L;
        long topRight = 0x0F07030100000000L;
        long bottomLeft = 0x0000000080C0E0F0L;
        long bottomRight = 0x000000000103070FL;
        int count = 0;
        count += (board & topLeft) == topLeft ? 1 : 0;
        count += (board & topRight) == topRight ? 1 : 0;
        count += (board & bottomRight) == bottomRight ? 1 : 0;
        count += (board & bottomLeft) == bottomLeft ? 1 : 0;
        return count;
    }

    /**
     * Count corners filled along diagonal of 3x3 square in corner (fully stable corner)
     *
     * @param board position to count
     * @return number of 3x3 corners filled along diagonal
     */
    public static int threeCorner(long board) {
        long topLeft = 0xE0C0800000000000L;
        long topRight = 0x0703010000000000L;
        long bottomLeft = 0x000000000080C0E0L;
        long bottomRight = 0x0000000000010307L;
        int count = 0;
        count += (board & topLeft) == topLeft ? 1 : 0;
        count += (board & topRight) == topRight ? 1 : 0;
        count += (board & bottomRight) == bottomRight ? 1 : 0;
        count += (board & bottomLeft) == bottomLeft ? 1 : 0;
        return count;
    }

    /**
     * Flip board across horizontal axis
     *
     * @param board board to flip
     * @return board flipped about horizontal axis
     */
    public static long vertFlip(long board) {
        return ((board << 56)) |
                ((board << 40) & 0x00ff000000000000L) |
                ((board << 24) & 0x0000ff0000000000L) |
                ((board << 8) & 0x000000ff00000000L) |
                ((board >>> 8) & 0x00000000ff000000L) |
                ((board >>> 24) & 0x0000000000ff0000L) |
                ((board >>> 40) & 0x000000000000ff00L) |
                ((board >>> 56));
    }

    /**
     * Flip board across vertical axis
     *
     * @param board board to flip
     * @return board flipped about vertical axis
     */
    public static long horzFlip(long board) {
        long k1 = 0x5555555555555555L;
        long k2 = 0x3333333333333333L;
        long k4 = 0x0f0f0f0f0f0f0f0fL;
        board = ((board >> 1) & k1) | ((board & k1) << 1);
        board = ((board >> 2) & k2) | ((board & k2) << 2);
        return ((board >> 4) & k4) | ((board & k4) << 4);
    }
}
