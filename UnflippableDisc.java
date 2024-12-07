/**
 * UnflappableDisc represents a special type of disc in the game
 * that cannot be flipped by other players. This disc is associated
 * with a specific player and has a unique representation.
 */
public class UnflippableDisc implements Disc {

    /**
     * The player who owns the disc.
     */
    private Player _owner;

    /**
     * Constructs a new UnflappableDisc with a specified owner.
     *
     * @param player The player who initially owns the disc.
     */
    public UnflippableDisc(Player player) {
        _owner = player;
    }

    /**
     * Constructs a new UnflappableDisc by copying the owner from another UnflappableDisc.
     *
     * @param disc The UnflappableDisc from which to copy the owner. Must not be null.
     */
    public UnflippableDisc(UnflippableDisc disc) {
        this._owner = disc.getOwner();
    }

    /**
     * Get the player who owns the Disc.
     *
     * @return The player who is the owner of this game disc.
     */
    @Override
    public Player getOwner() {
        return _owner;
    }

    /**
     * Set the player who owns the Disc.
     *
     * @param player The new player who owns the disc.
     */
    @Override
    public void setOwner(Player player) {
        _owner = player;
    }

    /**
     * Get the type of the disc.
     *
     * @return Textual representation of an unflappable disc ("⭕").
     */
    @Override
    public String getType() {
        return "⭕";
    }
}
