import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for moves
 */
public class MoveTest {
    @Test
    public void testGetColumn() throws Exception {
        Move move = new Move(7, 5, 1);
        assertEquals("Column of move", 5, move.getColumn());
    }

    @Test
    public void testGetRow() throws Exception {
        Move move = new Move(7, 5, 1);
        assertEquals("Row of move", 7, move.getRow());
    }

    @Test
    public void testGetPlayer() throws Exception {
        Move move = new Move(5, 5, 0);
        assertEquals("Player", 0, move.getPlayer());
    }

    @Test
    public void testSetPlayer() throws Exception {
        Move move = new Move(5, 5, 1);
        move.setPlayer(0);
        assertEquals("Player changed", 0, move.getPlayer());
    }

    @Test
    public void testToString() throws Exception {
        Move move = new Move(1, 2);
        assertEquals("Convert to string", "Row: 1, Col: 2", move.toString());
    }

    @Test
    public void testEquals() throws Exception {
        Move move = new Move(2, 1);
        assertEquals("Test equals method", new Move(2, 1, -1), move);
    }

}