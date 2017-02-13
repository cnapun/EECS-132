import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.concurrent.atomic.AtomicReferenceArray;

import static org.junit.Assert.*;

/**
 * Test class for the main board.  Tests the underlying mechanisms, no graphics involved
 */
public class BoardTest {

    @Test
    public void testGetPlayer() {
        assertEquals("Get player from board", 0, new Board(4, 4).getPlayer(1, 1));
    }

    @Test
    public void testGetWhoseTurn() {
        Board board = new Board(4, 4);
        assertEquals("First Player", 1, board.getWhoseTurn());
        board.toggleWhoseTurn();
        assertEquals("Second Player", 0, board.getWhoseTurn());
    }

    @Test
    public void testToggleWhoseTurn() {
        Board board = new Board(4, 4);
        board.toggleWhoseTurn();
        board.toggleWhoseTurn();
        assertEquals("One full turn elapsed", 1, board.getWhoseTurn());
    }

    @Test
    public void testGetHeight() {
        Board board = new Board(4, 6);
        assertEquals("Board height", 4, board.getHeight());
    }

    @Test
    public void testGetWidth() {
        Board board = new Board(4, 6);
        assertEquals("Board width", 6, board.getWidth());
    }

    @Test
    public void testGetState() {
        Board board = new Board(4, 6);
        int[][] expected = new int[][]{{-1, -1, -1, -1, -1, -1}, {-1, -1, 0, 1, -1, -1}, {-1, -1, 1, 0, -1, -1}, {-1, -1, -1, -1, -1, -1}};
        for (int i = 0; i < board.getHeight(); i++) {
            assertArrayEquals("Row " + i, expected[i], board.getState()[i]);
        }
    }

    @Test
    public void testSetPlayer() {
        Board board = new Board(4, 4);
        board.setPlayer(new Move(0, 0, 0));
        assertEquals("Move with player specified", 0, board.getState()[0][0]);
    }

    @Test
    public void testPossibleMoves() {
        Board board = new Board(4, 4);
        Move[] moves = new Move[]{new Move(0, 1, 1), new Move(1, 0, 1), new Move(3, 2, 1), new Move(2, 3, 1)};
        ArrayList<Move> moveArrayList = new ArrayList<>(Arrays.asList(moves));
        for (Move move : board.possibleMoves()) {
            assertTrue("Move in possible moves", moveArrayList.contains(move));
        }

        board = new Board(8, 8);
        board.setState(BB2.bbToArray(0x0000001818000000L, 0x00003c24243c0000L));
        long expectedMoves = 0x007e424242427e00L;
        assertEquals("Moves possible in all directions", expectedMoves, listMoves(board));

        board.setState(BB2.bbToArray(0x00003c24243c0000L, 0x0000001818000000L));
        assertEquals("No moves possible", 0L, listMoves(board));


        board.setState(BB2.bbToArray(0x0000001818000000L, 0x007E7E66667E7E00L));
        expectedMoves = 0b1101101110000001000000001000000110000001000000001000000111011011L;
        assertEquals("Moves on border", expectedMoves, listMoves(board));


        board.setState(BB2.bbToArray(0xF0000L, 0xF6L));
        assertEquals("Move hits border while checking", 0, listMoves(board));


        board.setState(BB2.bbToArray(0x1L, 0xF6L));
        assertEquals("Move hits border while checking, but is valid in other direction", 0x8L, listMoves(board));
    }

    @Test
    public void testCheckValidity() {
        //this test is not necessary because it is thoroughly tested in testPossibleMoves 
    }

    @Test
    public void testMakeMove() {
        Board board = new Board(5, 5);
        board.setState(new int[][]{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, -1, 0}, {1, 0, 0, -1, 0}});
        board.makeMove(new Move(3, 3));
        int[][] expected = new int[][]{{1, 1, 1, 1, 1}, {1, 1, 0, 1, 0}, {1, 0, 1, 1, 0}, {1, 1, 1, 1, 0}, {1, 0, 0, -1, 0}};
        for (int i = 0; i < board.getState().length; i++) {
            assertArrayEquals("Row " + i, expected[i], board.getState()[i]);
        }
        assertEquals("Turn toggled", 0, board.getWhoseTurn());

        board = new Board(4, 6);
        board.setState(new int[][]{{1, 1, 0, 1, 1, 1}, {-1, 0, 0, 1, 0, 0}, {0, 0, -1, 0, 0, 0}, {1, 0, 0, 0, -1, -1}});
        board.makeMove(new Move(3, 5));
        expected = new int[][]{{1, 1, 0, 1, 1, 1}, {-1, 0, 0, 1, 0, 1}, {0, 0, -1, 0, 1, 1}, {1, 0, 0, 0, -1, 1}};
        for (int i = 0; i < board.getState().length; i++) {
            assertArrayEquals("Row " + i, expected[i], board.getState()[i]);
        }
    }

    @Test
    public void testGameOver() {
        Board board = new Board(8, 8);
        board.setState(BB2.bbToArray(0x11L, 0x1100L));
        assertFalse("One player still has a move", board.gameOver());
        board.setState(BB2.bbToArray(0x11L, 0x110000L));
        assertTrue("No players have moves", board.gameOver());
    }

    @Test
    public void testHasAMove() {
        Board board = new Board(8, 8);
        board.setState(BB2.bbToArray(0x11L, 0x1100L));
        assertTrue("Player has a move", board.hasAMove(1));
        assertFalse("Player has no move", board.hasAMove(0));
    }

    @Test
    public void testHasAMoveNoPlayerSpecified() {
        Board board = new Board(8, 8);
        board.setState(BB2.bbToArray(0x11L, 0x1100L));
        assertTrue("Player has a move", board.hasAMove());
        board.toggleWhoseTurn();
        assertFalse("Player has no move", board.hasAMove());
    }

    @Test
    public void testCountSquares() {
        Board board = new Board(8, 8);
        board.makeMove(new Move(2, 3));
        assertEquals("Black's squares", 4, board.countSquares(1));
        assertEquals("White's squares", 1, board.countSquares(0));
    }

    /**
     * Helper method to create a flattened long list of moves using methods from Board
     *
     * @param board board to get moves for
     * @return bitboard with all moves
     */
    private long listMoves(Board board) {
        LinkedList<Long> actualList = new LinkedList<>();
        actualList.addAll(BB2.moves2Long(board.possibleMoves()));
        return BB2.llFlatten(actualList);
    }

}