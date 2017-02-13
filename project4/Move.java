/**
 * Class to represent a position on the board.  Player does not need to be specified.
 *
 * @author Nikil Pancha
 */
public class Move {
    /**
     * field to store the column of the move
     */
    private int column;
    /**
     * field to store the row of the move
     */
    private int row;
    /**
     * field to store player of the move
     */
    private int player = -1;

    /**
     * Constructor that takes a location but no player
     *
     * @param row    row of move
     * @param column column of move
     */
    public Move(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Constructor that takes a location and player
     *
     * @param row    row of move
     * @param column column of move
     * @param player player of move
     */
    public Move(int row, int column, int player) {
        this(row, column);
        this.player = player;
    }

    /**
     * Retrieves the column of the move
     *
     * @return the column of the move
     */
    public int getColumn() {
        return column;
    }

    /**
     * Retrieves the row of the move
     *
     * @return the column of the move
     */
    public int getRow() {
        return row;
    }

    /**
     * Retrieve the player
     *
     * @return number of player of move
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Set the player of the move
     *
     * @param player player to set move's player to
     */
    public void setPlayer(int player) {
        this.player = player;
    }

    /**
     * toString method to nicely print coordinates of move
     *
     * @return String representation of the move
     */
    @Override
    public String toString() {
        return "Row: " + getRow() + ", Col: " + getColumn();
    }

    /**
     * Overriden equals method
     *
     * @param obj object to compare equality to
     * @return true if the moves are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Move) && ((Move) obj).getPlayer() == (getPlayer()) && ((Move) obj).getRow() == getRow() && ((Move) obj).getColumn() == getColumn();
    }
}
