/**
 * SimpleDisc represents a basic type of disc in the game.
 * This disc is associated with a player and has a simple representation.
 */
public class SimpleDisc implements Disc {
    /**
     * The player who own the disk
     */
    private Player _owner;

    /**
     * constructor to build new disk
     *
     * @param player The first player who own the disk
     */
    public SimpleDisc(Player player) {
        _owner = player;
    }

    /**
     * Constructs a new SimpleDisc by copying the owner from another SimpleDisc.
     *
     * @param disc The SimpleDisc from which to copy the owner. Must not be null.
     */
    public SimpleDisc(SimpleDisc disc){
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
     * @param player the new player who own the disk.
     */
    @Override
    public void setOwner(Player player) {
        _owner = player;
    }

    /**
     * Get the type of the disc.
     *
     * @return textual representation of simple disk.
     */
    @Override
    public String getType() {
        return "⬤";
    }
}
