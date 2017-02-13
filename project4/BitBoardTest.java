import java.util.LinkedList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Class to test BB2
 *
 * @author Nikil Pancha
 */
public class BitBoardTest {

    @org.junit.Test
    public void shiftUp() throws Exception {
        assertEquals("Shift bit up", 0x100L, BB2.shiftUp(0x1L));
    }

    @org.junit.Test
    public void shiftDown() throws Exception {
        assertEquals("Shift bit down", 0x100L, BB2.shiftDown(0x10000L));
    }

    @org.junit.Test
    public void shiftLeft() throws Exception {
        assertEquals("Shift bit left", 0x2L, BB2.shiftLeft(0x1L));
    }

    @org.junit.Test
    public void shiftRight() throws Exception {
        assertEquals("Shift bit right", 0x4L, BB2.shiftLeft(0x2L));
    }

    @org.junit.Test
    public void shiftUpRight() throws Exception {
        assertEquals("Shift bit up-right", 0x800L, BB2.shiftUpRight(0x10L));
    }

    @org.junit.Test
    public void shiftUpLeft() throws Exception {
        assertEquals("Shift bit up-left", 0x4000L, BB2.shiftUpLeft(0x020L));
    }

    @org.junit.Test
    public void shiftDownLeft() throws Exception {
        assertEquals("Shift bit down-left", 0x1400L, BB2.shiftDownLeft(0xa0000L));
    }

    @org.junit.Test
    public void shiftDownRight() throws Exception {
        assertEquals("Shift bit down-right", 0x500L, BB2.shiftDownRight(0xa0000L));
    }

    @org.junit.Test
    public void getColumn() throws Exception {
        assertEquals("Column number", 2, BB2.getColumn(0x40000L));
    }

    @org.junit.Test
    public void getRow() throws Exception {
        assertEquals("Bottom row", 7, BB2.getRow(0x1L));
        assertEquals("Top row", 0, BB2.getRow(0x800000000000000L));
    }

    @org.junit.Test
    public void makeMove() throws Exception {
        long black = 0x007E424242427E00L;
        long white = 0xFF818181818181FFL;
        long move = 0x0000200000000000L;
        long mv = BB2.moveSinglePlayer(move, white, black);
        assertEquals("Just another move, white", 0xFFF1E1C1818181FFL, mv);
    }

    @org.junit.Test
    public void getMove() throws Exception {
        assertEquals("Row position", 2, BB2.getMove(0x0000200000000000L).getRow());
        assertEquals("Column position", 5, BB2.getMove(0x0000200000000000L).getColumn());
    }

    @org.junit.Test
    public void allMoves() throws Exception {
        LinkedList<Long> moves = BB2.allMoves(0xFF818181818181FFL, 0x007E424242427E00L);
        assertEquals("Flattened move list", 0x00003c24243c0000L, BB2.llFlatten(moves));
    }

    @org.junit.Test
    public void bbToArraySingle() throws Exception {
        int[][] node = BB2.bbToArray(0xFF818181818181FFL);
        int[][] expected = new int[][]{{1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 1}, {1, 1, 1, 1, 1, 1, 1, 1}};
        for (int i = 0; i < 8; i++) {
            assertArrayEquals("Row " + i, expected[i], node[i]);
        }
    }

    @org.junit.Test
    public void bbToArrayMultiple() throws Exception {
        int[][] node = BB2.bbToArray(0x007E424242427E00L, 0xFF818181818181FFL);
        int[][] expected = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 1, 1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
        for (int i = 0; i < 8; i++) {
            assertArrayEquals("Row " + i, expected[i], node[i]);
        }
    }

    @org.junit.Test
    public void arrayToBB() throws Exception {
        int[][] toConvert = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 1, 1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, -1, -1, -1, -1, 1, 0}, {0, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
        long[] expected = new long[]{0x007E424242427E00L, 0xFF818181818181FFL};
        assertArrayEquals("Array converted to bitboard", expected, BB2.arrayToBB(1, toConvert));
    }

    @org.junit.Test
    public void count() throws Exception {
        assertEquals("Count set bits", 4, BB2.count(0xF00L));
    }

    @org.junit.Test
    public void freeEdges() throws Exception {
        assertEquals("Free edges of single piece in corner", 1, BB2.freeEdges(0x1, 0x0));
        assertEquals("Free edges of single piece in middle", 8, BB2.freeEdges(0x80000, 0x0));
        assertEquals("Free edges of single piece on side", 2, BB2.freeEdges(0x100, 0x0));
    }

    @org.junit.Test
    public void edgePieces() throws Exception {
        assertEquals("One piece", 1, BB2.edgePieces(0x1));
        assertEquals("All four edges", 28, BB2.edgePieces(0xFFFFFFFFFFFFFFFFL));
    }

    @org.junit.Test
    public void cornerPieces() throws Exception {
        assertEquals("All four corners", 4, BB2.cornerPieces(0xFFFFFFFFFFFFFFFFL));
        assertEquals("Two corners", 2, BB2.cornerPieces(0x0100000000000080L));
    }

    @org.junit.Test
    public void innerCornerPieces() throws Exception {
        assertEquals("Bottom two", 2, BB2.innerCornerPieces(0x421F));
    }

    @org.junit.Test
    public void threeByFour() throws Exception {
        assertEquals("Vertical and horizontal mirrored", 1, BB2.innerCornerPieces(0x0f0f0fL));
    }

    @org.junit.Test
    public void vertFlip() throws Exception {
        assertEquals("Flip across horizontal axis", 0xFF000000000000CCL, BB2.vertFlip(0xCC000000000000FFL));
    }

    @org.junit.Test
    public void horzFlip() throws Exception {
        assertEquals("Flip across vertical axis", 0x8080808080808080L, BB2.horzFlip(0x0101010101010101L));
    }

}