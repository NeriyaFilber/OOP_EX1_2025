
/**
 * BombDisc represents a special type of disc in the game that is owned by a player
 * and has unique behavior or characteristics (e.g., represented by a bomb symbol "💣").
 * This class implements the Disc interface.
 */
public class BombDisc implements Disc {
    /**
     * The player who own the disk
     */
    private Player _owner;

    /**
     * constructor to build new disk
     *
     * @param player The first player who own the disk
     */
    public BombDisc(Player player) {
        _owner = player;
    }

    /**
     * Creates a new BombDisc object by copying the owner from the given BombDisc.
     *
     * @param disc the BombDisc object from which the owner information is copied Must not be null.
     */
    public BombDisc(BombDisc disc){
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
     * @param player The new player who own the disk.
     */
    @Override
    public void setOwner(Player player) {_owner = player;}

    /**
     * Get the type of the disc.
     *
     * @return textual representation of Bomb disk.
     */
    @Override
    public String getType() {
        return "💣";
    }
}
