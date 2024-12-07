/**
 * Represents a move in the game, consisting of a position on the board
 * and the disc being placed at that position.
 */
public class Move {
    /**
     * The position on the board where the move is made.
     */
    private Position position;

    /**
     * The disc being placed during the move.
     */
    private Disc disc;

    /**
     * Constructs a Move with a specified position and disc.
     *
     * @param position The position on the board where the disc is placed.
     * @param disc     The disc being placed at the specified position.
     */
    public Move(Position position, Disc disc) {
        this.position = position;
        this.disc = disc;
    }

    /**
     * Gets the position of the move.
     *
     * @return The position on the board where the disc is placed.
     */
    public Position position() {
        return position;
    }

    /**
     * Gets the disc associated with the move.
     *
     * @return The disc being placed during the move.
     */
    public Disc disc() {
        return disc;
    }
}
