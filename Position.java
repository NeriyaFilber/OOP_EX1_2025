/**
 * Represents a position on a board with row and column coordinates.
 * This class provides methods to retrieve the row and column values
 * and overrides equality for comparison between positions.
 */
public class Position {
    /**
     * The row coordinate of the position.
     */
    private int _row;

    /**
     * The column coordinate of the position.
     */
    private int _col;

    /**
     * Constructs a Position with specified row and column coordinates.
     *
     * @param row The row coordinate of the position.
     * @param col The column coordinate of the position.
     */
    public Position(int row, int col) {
        _row = row;
        _col = col;
    }

    /**
     * Gets the row coordinate of the position.
     *
     * @return The row coordinate.
     */
    public int row() {
        return _row;
    }

    /**
     * Gets the column coordinate of the position.
     *
     * @return The column coordinate.
     */
    public int col() {
        return _col;
    }

    /**
     * Checks whether this position is equal to another object.
     * Two positions are considered equal if they have the same
     * row and column values.
     *
     * @param o The object to compare with this position.
     * @return true if the specified object is a Position with the same
     *         row and column values, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return _row == position._row && _col == position._col;
    }
}
