public class UnflippableDisc implements Disc {
    /**
     * The player who own the disk
     */
    private Player _owner;

    /**
     * constructor to build new disk
     *
     * @param player The first player who own the disk
     */
    public UnflippableDisc(Player player) {
        _owner = player;
    }

    /**
     * Get the player who owns the Disc.
     *
     * @return The player who is the owner of this game disc.
     */
    @Override
    public Player get_owner() {
        return _owner;
    }

    /**
     * Set the player who owns the Disc.
     *
     * @param player The new player who own the disk.
     */
    @Override
    public void set_owner(Player player) {
        _owner = player;
    }

    /**
     * Get the type of the disc.
     *
     * @return textual representation of Unflippable disk.
     */
    @Override
    public String getType() {
        return "⭕";
    }
}
